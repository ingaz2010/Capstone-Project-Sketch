package com.israr.sketch.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String role;

    @NotEmpty
    private String party;
    private String description;
}
