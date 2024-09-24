package se.b3skilled.skilledGPT;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import se.b3skilled.skilledGPT.model.openAI.Message;
import se.b3skilled.skilledGPT.model.openAI.OpenAIResponse;
import se.b3skilled.skilledGPT.model.openAI.OpenAIRequest;

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
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> requestIn,
            final ExecutionContext context) throws IOException, InterruptedException {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Initialize the ObjectMapper (for Jackson) to parse JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read the body from the request
            String requestBody = requestIn.getBody();
            context.getLogger().info("inBody:" + requestBody);

            // Parse the JSON array to a List of Message objects
            List<Message> messages = objectMapper.readValue(requestBody, new TypeReference<List<Message>>() {
            });

            String messagesJson = objectMapper.writeValueAsString(messages);
            context.getLogger().info("Messages array (JSON): " + messagesJson);

            // Create the OpenAIRequest object
            OpenAIRequest openAIRequest = new OpenAIRequest("skilledGPT", messages);

            // Serialize to JSON string
            String jsonBody = objectMapper.writeValueAsString(openAIRequest);

            // Log the JSON body
            context.getLogger().info("OpenAI Request Body: " + jsonBody);

            String apiKey = System.getenv("apikey");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://skilledgpt.openai.azure.com/openai/deployments/skilledGPT/chat/completions?api-version=2024-06-01"))
                    .header("Content-Type", "application/json")
                    .header("api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            context.getLogger().info("response = " + response.body());

            OpenAIResponse openAIResponse = objectMapper.readValue(response.body(), OpenAIResponse.class);
            openAIResponse.getChoices().forEach(System.out::println);

            return requestIn.createResponseBuilder(HttpStatus.OK).body(openAIResponse.getChoices().get(0).getMessage().getContent()).build();
        } catch (Exception e) {
            // Log the error and return an error response
            context.getLogger().severe("Error parsing JSON: " + e.getMessage());
            return requestIn.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Invalid JSON data.")
                    .build();
        }
    }
}
