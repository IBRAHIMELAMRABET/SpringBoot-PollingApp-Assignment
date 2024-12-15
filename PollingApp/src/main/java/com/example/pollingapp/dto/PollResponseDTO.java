package com.example.pollingapp.dto;

import com.example.pollingapp.entity.Option;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PollResponseDTO {
    private Long id;
    private String question;
    private List<Option> options;
}