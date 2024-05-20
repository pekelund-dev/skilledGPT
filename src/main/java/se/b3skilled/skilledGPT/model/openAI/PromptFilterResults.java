package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PromptFilterResults {
    private Integer prompt_index;
    private ContentFilterResults content_filter_results;
}

