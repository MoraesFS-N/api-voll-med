package med.voll.api.model;

public record PutMedicoDTO(Long id, String nome, String telefone, String email, PostEnderecoDTO endereco) {
}
