package med.voll.api.domain.model;

import med.voll.api.domain.entity.Paciente;

public record GetPacienteListDTO(Long id, String nome, String cpf) {
    public GetPacienteListDTO(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getCpf());
    }
}
