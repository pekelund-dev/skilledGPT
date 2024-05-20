package se.b3skilled.skilledGPT;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import se.b3skilled.skilledGPT.model.openAI.OpenAIResponse;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerJava {
    /**
     * This function listens at endpoint "/api/HttpTriggerJava". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerJava
     * 2. curl {your host}/api/HttpTriggerJava?name=HTTP%20Query
     */
    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> requestIn,
            final ExecutionContext context) throws IOException, InterruptedException {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = requestIn.getQueryParameters().get("name");
        String name = requestIn.getBody().orElse(query);

        String apiKey = "c9d4121a9a4944ca875c7ba8a83f7f96";
        var body = String.format("""
        {
        "model": "gpt-3",
        "messages": [
            {
                "role": "user",
                "content": "%s"
            }
        ]
        }
        """, name);

        System.out.println("body = " + body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://skilledgpt.openai.azure.com/openai/deployments/pars-playground/chat/completions?api-version=2024-02-01"))
                .header("Content-Type", "application/json")
                .header("api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("response = " + response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        OpenAIResponse openAIResponse = objectMapper.readValue(response.body(), OpenAIResponse.class);
        openAIResponse.getChoices().forEach(System.out::println);

        return requestIn.createResponseBuilder(HttpStatus.OK).body(openAIResponse.getChoices().get(0).getMessage().getContent()).build();
//        if (name == null) {
//            return requestIn.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
//        } else {
//            return requestIn.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
//        }
    }
}
