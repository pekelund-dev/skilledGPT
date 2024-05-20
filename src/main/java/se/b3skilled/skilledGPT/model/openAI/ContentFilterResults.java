package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentFilterResults {
    private Hate hate;
    private SelfHarm self_harm;
    private Sexual sexual;
    private Violence violence;
}
