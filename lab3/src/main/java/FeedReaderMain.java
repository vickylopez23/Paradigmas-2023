import HTTPRequest.HTTPRequester;
import feed.Article;
import feed.Feed;
import namedEntity.NamedEntity;
import namedEntity.heuristic.DigimonHeuristic;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import parser.*;
import parser.feed.FeedParser;
import parser.feed.RSSParser;
import parser.feed.RedditParser;
import scala.Tuple2;
import subscription.SingleSubscription;
import utility.Tuple;

import javax.inject.Named;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class FeedReaderMain {


    static HashMap<String, Heuristic> heuristics = new HashMap<>();

    static {
        heuristics.put("quick", new QuickHeuristic()); //lo usi para ver que pasa si no le pongo ninguna heuristica
        heuristics.put("random", new RandomHeuristic()); //lo uso para ver que pasa si no le pongo ninguna heuristica
        heuristics.put("digimon", new DigimonHeuristic());
    }

    static String DEFAULT_HEURISTIC = "quick";

    private static void printHelp() {
        System.out.println("Please, call this program in correct way: FeedReader [-ne] [Heuristic: quick, random, digimon]");
    }

    // extrae un feed de una subscripcion especifica
    private static List<Feed> getFeeds(SingleSubscription subscription) {
        System.out.println("Obteniendo Feeds para una subscripcion");
        System.out.printf("Tipo de subscripcion: %s%n", subscription.getUrlType());
        List<Feed> feeds = new ArrayList<Feed>();
        // para cada seccion de la suscripcion
        for (String param : subscription.getUrlParams()) {
            try {
                System.out.printf("Parametro de este Feed: %s%n", param);
                // formateamos la url con la seccion
                String formattedUrl = String.format(subscription.getUrl(), param);
                System.out.printf("URL final: %s%n", formattedUrl);

                // hacemos la reequest http de la url formateada
                HTTPRequester requester = new HTTPRequester(formattedUrl);

                // creamos un parser de acuerdo al tipo dado e el json
                FeedParser parser;
                if (subscription.getUrlType().equals("rss")) {
                    parser = new RSSParser();
                } else if (subscription.getUrlType().equals("reddit")) {
                    parser = new RedditParser();
                } else {
                    System.out.println("Tipo de subscripcion invalida!");
                    break;
                }

                // parseamos la data de la request
                System.out.println("Iniciando peticion y parseo del Feed.");
                Feed feed = parser.parseFeed(requester.getRequestData());
                if (feed == null) {
                    System.out.println("Error parseando Feed.");
                    continue;
                }
                feeds.add(feed);
            } catch (MalformedURLException e) {
                System.out.printf("URL de la subscripcion malformada: %s%n", e);
            } catch (IOException e) {
                System.out.printf("No se pudo conectar al servidor del feed, comprobar la conexion al internet: %s%n", e);
            }

        }
        return feeds;
    }

    public static void main(String[] args) {
        System.out.println("************* FeedReader version 1.0 *************");

        //inicializacion de Spark en base a ChatGPT y documentación:
        SparkConf conf = new SparkConf().setAppName("feed_reader").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        if (args.length <= 2) {
            if (args.length >= 1 && !args[0].equalsIgnoreCase("-ne")) {
                printHelp();
                return;
            }
            Heuristic h = heuristics.get(DEFAULT_HEURISTIC);
            if (args.length == 2) {
                h = heuristics.get(args[1].toLowerCase());
                if (h == null) {
                    printHelp();
                    return;
                }
            }

            /*
             *
             * Llamar al httpRequester para obtenr el feed del servidor
             * Llamar al Parser especifico para extrar los datos necesarios por la aplicacion
             * Llamar al constructor de Feed
             */

            // Leer el archivo de suscription por defecto;
            SubscriptionParser parser = new SubscriptionParser(Path.of("config/subscriptions.json"));
            List<SingleSubscription> subscriptions = parser.parse();

            // pasamos la lista de subcripsiones a spark usando parallelize
            JavaRDD<SingleSubscription> subscriptionJavaRDD = sparkContext.parallelize(subscriptions);

            // obtenemos los feeds, de manera distribuida:
            JavaRDD<Feed> feedsRDD = subscriptionJavaRDD.flatMap(s -> FeedReaderMain.getFeeds(s).iterator());

            if (args.length == 0) {
                // LLamar al prettyPrint del Feed para ver los articulos del feed en forma legible y amigable para el usuario
                for (Feed feed : feedsRDD.collect())
                    feed.prettyPrint();
            } else {
                /*
                 * Llamar a la heuristica para que compute las entidades nombradas de CADA ARTICULO del feed
                 * LLamar al prettyPrint de la tabla de entidades nombradas del feed.
                 */

                //vemos de obtener los articulos y por cada uno calcular sus EN
                final Heuristic finalHeuristic = h;
                JavaRDD<Article> articlesRDD = feedsRDD.flatMap(feed -> feed.getArticleList().iterator()).map(article -> {
                    article.computeNamedEntities(finalHeuristic);
                    return article;
                });

                // Cacheamos el RDD hasta el momento
                articlesRDD = articlesRDD.cache();
                // imprimimos cada articulo de una manera linda. Y de una forma clean con forEach y funciones lambda.
                // Pretty print de los articulos
                articlesRDD.collect().forEach(Article::prettyPrint);

                // Pedir la palabra al StdIn.
                System.out.println("Ingrese la entidad que desea buscar: ");
                Scanner scanner = new Scanner(System.in);
                String word = scanner.nextLine();
                scanner.close();

                JavaRDD<Tuple2<Article, NamedEntity>> articlesWithWord = articlesRDD.flatMap((article) -> {
                    ArrayList<Tuple2<Article, NamedEntity>> items = new ArrayList<>();
                    article.getNamedEntities().forEach(entity -> {
                        items.add(new Tuple2<>(article, entity));
                    });
                    return items.iterator();
                }).filter(pair -> word.equals(pair._2().getName())).sortBy(pair -> pair._2().getFrequency(), false, 10);
                List<Tuple2<Article, NamedEntity>> articles = articlesWithWord.collect();
                System.out.printf("Articulos con ocurrencias exactas de %s:%n", word);
                articles.forEach(pair -> {
                    System.out.printf("Article: %s%nEntity Count: %d %n%n", pair._1().getTitle(), pair._2().getFrequency());
                });
            }
        } else {
            printHelp();
        }
        sparkContext.close();
    }

}
