package parser.feed;

import feed.Article;
import feed.Feed;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * */

public class RSSParser extends FeedParser {

    @Override
    public Feed parseFeed(String rawRSS) {
        try {
            // creo un document de el rawRSS
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xml = builder.parse(new InputSource(new StringReader(rawRSS)));
            xml.getDocumentElement().normalize();
            // consigo la lista de items de la pagina
            NodeList itemNodes = xml.getElementsByTagName("item");

            // genero la lista de titulos para poder crear el feed con su determinado titulo
            NodeList titles = xml.getElementsByTagName("title");
            // genero el feed que retornaremos
            Feed feed = new Feed(titles.item(0).toString());

            // por cada item consigo su titulo, link, descripcion y fecha
            for (int i = 0; i < itemNodes.getLength(); i++) {

                Node itemNode = itemNodes.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    String title = itemElement.getElementsByTagName("title").item(0).getTextContent();
                    String link = itemElement.getElementsByTagName("link").item(0).getTextContent();
                    String description = itemElement.getElementsByTagName("description").item(0).getTextContent();
                    String pubDateS = itemElement.getElementsByTagName("pubDate").item(0).getTextContent();

                    // formateo la fecha
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    Date pubDate = inputDateFormat.parse(pubDateS);

                    // añado el articulo al feed
                    Article newArticle = new Article(title, description, pubDate, link);
                    feed.addArticle(newArticle);
                }
            }
            return feed;
        } catch (ParserConfigurationException e) {
            System.out.printf("Error en la configuración del parser: %s%n", e);
        } catch (SAXException e) {
            System.out.printf("Error en el parseo del documento XML: %s%n", e);
        } catch (IOException e) {
            System.out.printf("Error en la lectura del archivo o flujo de entrada: %s%n", e);
        } catch (ParseException e) {
            System.out.printf("Error en el formato de fecha: %s%n", e);
        } catch (Exception e) {
            System.out.printf("Error desconocido: %s%n", e);
        }
        return null;
    }
}
