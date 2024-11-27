package med.voll.api.domain.model;

import med.voll.api.domain.entity.Consulta;

import java.time.LocalDateTime;

public record GetConsultaDTO(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public GetConsultaDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }


}
