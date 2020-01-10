package com.sdp.petapi.models;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Example {

    private final UUID id;

    @NotBlank
    private final String message;

    public Example(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
