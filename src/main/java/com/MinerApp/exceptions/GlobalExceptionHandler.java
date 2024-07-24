package com.MinerApp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        validationErrors.forEach(validationError -> {
            log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        });
        return new ResponseEntity<>(validationErrors, HttpStatusCode.valueOf(400));
    }


    @ExceptionHandler(DwarfExistsWithSameNameException.class)
    public ResponseEntity<List<ValidationError>> handleDwarfAlreadyExistException(DwarfExistsWithSameNameException exception) {
        ValidationError validationError = new ValidationError("Name",
                "Dwarf is already exists with this name: " + exception.getName());
        log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        return new ResponseEntity<>(List.of(validationError), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(DwarfNotExistsWithGivenId.class)
    public ResponseEntity<List<ValidationError>> handleDwarfNotExistsWithGivenIdException(DwarfNotExistsWithGivenId exception) {
        ValidationError validationError = new ValidationError("ID",
                "Dwarf is not exists with given ID: " + exception.getId());
        log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        return new ResponseEntity<>(List.of(validationError), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(LazyMuddaFakkaException.class)
    public ResponseEntity<List<ValidationError>> handleLazyMuddaFakkaException(LazyMuddaFakkaException exception) {
        ValidationError validationError = new ValidationError(exception.get_MESSAGE());
        log.error("Error in validation: " + validationError.getErrorMessage());
        return new ResponseEntity<>(List.of(validationError), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(RuneRepositoryIsEmptyException.class)
    public ResponseEntity<List<ValidationError>> handleRuneRepositoryIsEmptyException(RuneRepositoryIsEmptyException exception) {
        ValidationError validationError = new ValidationError("Rune Name",
                "Rune repository is empty: " + exception.getMessage());
        return new ResponseEntity<>(List.of(validationError), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(ThereIsNoRuneWithGivenNameException.class)
    public ResponseEntity<List<ValidationError>> handleThereIsNoRuneWithGivenNameException(ThereIsNoRuneWithGivenNameException exception) {
        ValidationError validationError = new ValidationError("Rune Name",
                "Rune is not exists with given name: " + exception.getName());
        log.error("Error in validation: " + validationError.getField() + ": " + validationError.getErrorMessage());
        return new ResponseEntity<>(List.of(validationError), HttpStatusCode.valueOf(400));
    }

}