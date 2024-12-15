package com.example.pollingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class VoteDTO {
    @NotNull(message = "Option ID cannot be null")
    private Long optionId;
}