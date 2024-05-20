package se.b3skilled.skilledGPT.model.openAI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Usage {
    private Integer completion_tokens;
    private Integer prompt_tokens;
    private Integer total_tokens;
}

