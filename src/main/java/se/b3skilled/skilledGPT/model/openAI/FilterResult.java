package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterResult {
    private boolean filtered;
    private String severity; // For filters like 'hate', 'self_harm', etc.
    private Boolean detected; // For 'protected_material_code', 'jailbreak', etc.
}
