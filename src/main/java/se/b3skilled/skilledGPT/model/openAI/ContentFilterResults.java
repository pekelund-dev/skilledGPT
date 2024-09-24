package se.b3skilled.skilledGPT.model.openAI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentFilterResults {
    private FilterResult hate;

    @JsonProperty("protected_material_code")
    private FilterResult protectedMaterialCode;

    @JsonProperty("protected_material_text")
    private FilterResult protectedMaterialText;

    private FilterResult self_harm;
    private FilterResult sexual;
    private FilterResult violence;
    private FilterResult jailbreak; // For 'prompt_filter_results'
}
