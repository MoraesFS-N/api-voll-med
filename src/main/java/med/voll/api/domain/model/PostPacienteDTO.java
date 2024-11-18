package med.voll.api.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PostPacienteDTO(@NotBlank
                              String nome,
                              @NotBlank
                              @Email
                              String email,
                              @NotBlank
                              @Pattern(regexp = "\\d{11}")
                              String cpf,
                              String telefone,
                              @NotNull
                              @Valid
                              PostEnderecoDTO endereco) {
}
