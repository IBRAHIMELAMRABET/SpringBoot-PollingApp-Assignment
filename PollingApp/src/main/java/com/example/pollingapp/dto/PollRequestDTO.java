package com.example.pollingapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PollRequestDTO {
    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotEmpty(message = "Options cannot be empty")
    @Size(min = 2, message = "At least two options are required")
    private List<String> options;
}