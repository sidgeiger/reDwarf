package com.MinerApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDwarfCommand {
    @NotNull(message = "This field cannot be null.")
    @NotEmpty(message = "This field cannot be empty.")
    @NotBlank(message = "This field cannot be blank.")
    private String name;

    @NotNull(message = "This field cannot be null.")
    @Min(1)
    private int productivity;
}
