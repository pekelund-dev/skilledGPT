package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Choices {
    private ContentFilterResults content_filter_results;
    private String finish_reason;
    private Integer index;
    private Message message;
}
