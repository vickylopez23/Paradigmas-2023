package HTTPRequest;


/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequester {

    private final String url;

    // constructor
    public HTTPRequester(String url) {
        this.url = url;
    }

    public String getRequestData() throws IOException {
        // Abrimos una conexion al servidor denotado por la URL de urlFeed
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Configuramos la peticion a hacer, particularmente metodo, agente y otros headers que puedan a llegar a ser necesarios.
        // si necesitamos distintos parametros segun el host
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Obtenemos el response code de la peticon, notar que esto hace que se envie la peticion que previamente se configuro.
        connection.getResponseCode();

        // Armamos un Buffer para leer el Stream de datos que nos llegan de respuesta.
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

        // Usamos a StringBuilder para ir construyendo un string que representa la respuesta obtenida del Servidor.
        // I.E, pasamos toda la respuesta a una cadena de caracteres.
        StringBuilder response = new StringBuilder();
        String inputLine;

        // Vamos leyendo el buffer y por cada linea que leemos la agregamos al StringBuilder.
        while ((inputLine = reader.readLine()) != null) response.append(inputLine);

        // Cerramos el stream y buffer ya que no los vamos a usar
        reader.close();
        responseStream.close();

        // Construimos el string y lo retornamos
        return response.toString();
    }
}
