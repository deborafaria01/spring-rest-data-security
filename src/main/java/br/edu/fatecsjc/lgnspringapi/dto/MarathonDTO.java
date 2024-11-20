package br.edu.fatecsjc.lgnspringapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarathonDTO {

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Marathon name cannot be blank")
    private String name;

    @Min(value = 0, message = "Weight must be non-negative")
    private double weight;

    @Min(value = 0, message = "Score must be non-negative")
    private double score;
}
