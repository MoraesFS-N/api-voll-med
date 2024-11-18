package med.voll.api.domain.model;

import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;

public record GetPacienteDTO(Long id, String nome, String email, String cpf, String telefone) {

    public GetPacienteDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone());
    }

}
