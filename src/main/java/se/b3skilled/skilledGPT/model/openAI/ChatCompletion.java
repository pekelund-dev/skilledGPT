package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ChatCompletion {
    private Long created;
    private String id;
    private String model;
    private String object;
    private List<Choices> choices;
    private List<PromptFilterResults> prompt_filter_results;
    private Object system_fingerprint;
    private Usage usage;
}

