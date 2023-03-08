package edu.neuroginarium.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VotingResultDto {
    Boolean isVotingFinished;
    Boolean isGameFinished;
}
