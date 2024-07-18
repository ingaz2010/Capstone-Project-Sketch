package com.israr.sketch.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ElectionDto {
    private Long id;
    @NotEmpty
    private String position;
    private Boolean isActive;
    private String year;
}
