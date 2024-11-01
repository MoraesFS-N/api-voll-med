package med.voll.api.model;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank String login, @NotBlank String senha) {
}
