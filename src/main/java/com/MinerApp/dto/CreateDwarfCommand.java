package com.MinerApp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor

public class CreateDwarfCommand {
    @NotNull(message = "This field cannot be null.")
    @NotEmpty(message = "This field cannot be empty.")
    @NotBlank(message = "This field cannot be blank.")
    @Length(min = 3)
    @Pattern(regexp = "^[A-Za-z]+(\\s+[A-Za-z]+)*$", message = "Invalid input: Name must contain only alphabet characters and cannot start with whitespace.")

    private String name;

    @NotNull(message = "This field cannot be null.")
    @Min(1)
    private int productivity;

}
