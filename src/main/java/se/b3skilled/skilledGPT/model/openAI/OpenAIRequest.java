package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OpenAIRequest {
    private String model;
    private List<Message> messages;

    // Constructors
    public OpenAIRequest() {}

    public OpenAIRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
}

