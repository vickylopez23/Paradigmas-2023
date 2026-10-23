package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import subscription.SingleSubscription;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */

public class SubscriptionParser {

    Path filePath;

    // constructor
    public SubscriptionParser(Path filePath) {
        this.filePath = filePath;
    }

    // funcion interna parse, devuelve lista de subscripciones
    public List<SingleSubscription> parse() {
        List<SingleSubscription> subscriptions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath.toString());
            // Leemos el primer elemento JSON como una array (lista de subscripciones)
            JSONArray subscriptionsArray = new JSONArray(new JSONTokener(fileReader));

            // Por cada elemento (subscripcion):
            for (int i = 0; i < subscriptionsArray.length(); i++) {
                try {
                    // Leemos el objeto JSON que representa la subscripcion
                    JSONObject subscriptionObject = subscriptionsArray.getJSONObject((i));

                    // Obtenemos su URL
                    String url = subscriptionObject.getString("url");

                    // Obtenemos el array de los parametros de URL
                    JSONArray urlParamsArray = subscriptionObject.getJSONArray("urlParams");
                    List<String> urlParams = new ArrayList<>();

                    // Cargamos cada uno de los parametros como string en la lista urlParams
                    for (int j = 0; j < urlParamsArray.length(); j++)
                        urlParams.add(urlParamsArray.getString(j));

                    // Obtenemos el tipo de la URL
                    String urlType = subscriptionObject.getString("urlType");

                    // Creamos una subscripcion y la guardamos en la lista a retornar.
                    SingleSubscription subscription = new SingleSubscription(url, urlParams, urlType);
                    subscriptions.add(subscription);
                } catch (JSONException e) {
                    // Si se encuentra un error parseando el JSON de la subscripcion tiramos la causa del error y levantamos la excepcion
                    System.out.printf("Error parseando subscripcion: %s%n", e);
                    throw e;
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.printf("El archivo de subscripciones no se encontro en el sistema. Path: %s%n", filePath.toAbsolutePath());
        } catch (JSONException e) {
            System.out.printf("Error parseando la lista de las subscripciones. %s%n", e);
        } catch (IOException e) {
            System.out.printf("Error procesando el archivo de subscripciones. %s%n", e);
        }
        return subscriptions;
    }

}
