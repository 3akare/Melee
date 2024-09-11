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

    public static Object parserApiCall(String raw_data){
        String endpoint = props.getGeminiUrl() + "?key=" + props.getGeminiApiKey();
        String json = """
            {
                "contents": [{
                    "parts": [{
                        "text": "Please provide the following information for each article related to the specified topics, formatted as JSON: the publisher's name, the direct and valid URL to the article, the exact title of the article, the author's name, and the publication date. Ensure that the URL is correct and leads to the actual article without errors. Do not fabricate or guess any details—only include accurate and verifiable information as if you were a professional data analyst or editor. The JSON should look like this:{publisher: Name of Publisher, url: URL to the actual article,title: Title of the post, author: Author of the article, date: Date of publication} %s"
                    }]
                }]
            }
            """.formatted(raw_data);
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception error){
            log.warning(error.getLocalizedMessage());
        }
        return null;
    }
}
