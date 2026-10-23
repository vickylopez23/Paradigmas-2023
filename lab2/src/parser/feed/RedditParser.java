package parser.feed;


import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import feed.Article;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion
 * */

import feed.Feed;

public class RedditParser extends FeedParser {

    @Override
    public Feed parseFeed(String rawReddit) {
        try {

            //creo un objeto json con el rawReddit
            JSONObject obj = new JSONObject(rawReddit);
            JSONObject data = obj.getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            JSONObject data2 = children.getJSONObject(0).getJSONObject("data");
            //OBTENEMOS TITULO como reddit no tiene titulo colocamos el subreddit para guiarnos
            String title = data2.getString("subreddit");

            //CREAR FEED
            Feed feed = new Feed(title);


            //recorrer los children
            //obtengo el titulo,descripcion,fecha,link de cada articulo
            for (int i = 0; i < children.length(); i++) {
                JSONObject child = children.getJSONObject(i);
                JSONObject dataChild = child.getJSONObject("data");
                String titulo = dataChild.getString("title");
                String description = dataChild.getString("selftext");
                long pubDate = dataChild.getLong("created_utc");
                String link = dataChild.getString("url");

                //formateo la fecha
                // Convertir la fecha de formato flotante a Date
                Date date = new Date((pubDate * 1000));

                // añado el articulo al feed
                Article newArticle = new Article(titulo, description, date, link);
                feed.addArticle(newArticle);
            }
            return feed;
        } catch (JSONException e) {
            System.out.printf("Error en el parseo de JSON: %s%n", e);
        } catch (Exception e) {
            System.out.printf("Error en el parseo: %s%n", e);
        }
        return null;
    }

}


        




