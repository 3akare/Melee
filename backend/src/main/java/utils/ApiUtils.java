package utils;

import config.PropertiesConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class ApiUtils {
    private static final Logger log = Logger.getLogger(ApiUtils.class.getName());

    private static final PropertiesConfig props = MeleeUtils.fetchEnvironmentVariables().orElseThrow(
            () -> new RuntimeException("api: error fetching environment variables")
    );

    private static Object parserApiCall(){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = props.getParserUrl() + "/process";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Hello"))
                .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception error){
            log.warning(error.getLocalizedMessage());
        }
        return null;
    }
}
