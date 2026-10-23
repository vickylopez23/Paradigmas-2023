import HTTPRequest.HTTPRequester;
import feed.Article;
import feed.Feed;
import namedEntity.heuristic.DigimonHeuristic;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import parser.*;
import parser.feed.FeedParser;
import parser.feed.RSSParser;
import parser.feed.RedditParser;
import subscription.SingleSubscription;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

            // generamos una lista de feeds, donde vamos agregando la feed de cada subscripcion 
            ArrayList<Feed> feeds = new ArrayList<>();
            for (SingleSubscription subscription : subscriptions) {
                System.out.println(subscription);
                feeds.addAll(FeedReaderMain.getFeeds(subscription));
            }

            if (args.length == 0) {
                // LLamar al prettyPrint del Feed para ver los articulos del feed en forma legible y amigable para el usuario
                for (Feed feed : feeds)
                    feed.prettyPrint();
            } else {
                /*
                 * Llamar a la heuristica para que compute las entidades nombradas de CADA ARTICULO del feed
                 * LLamar al prettyPrint de la tabla de entidades nombradas del feed.
                 */
                for (Feed feed : feeds) {
                    for (Article article : feed.getArticleList()) {
                        article.computeNamedEntities(h);
                    }
                    // Al haber ya calculado las entidades nombradas de los articulos del feed esto va a causar de que se impriman tambien
                    // las entidades nombradas que acabamos de calcular.
                    feed.prettyPrint();
                }
            }
        } else {
            printHelp();
        }
    }

}
