package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Hate {
    private Boolean filtered;
    private String severity;
}
