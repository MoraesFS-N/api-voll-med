package med.voll.api.domain.model;

import jakarta.validation.constraints.NotNull;

public record PutPacienteDTO(@NotNull Long id, String nome, String telefone, PostEnderecoDTO endereco) {
}
