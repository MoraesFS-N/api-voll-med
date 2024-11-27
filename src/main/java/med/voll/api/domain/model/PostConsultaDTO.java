package med.voll.api.domain.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostConsultaDTO(Long idMedico, @NotNull Long idPaciente, @NotNull @Future LocalDateTime data, EnumEspecialidade especialidade) {
}
