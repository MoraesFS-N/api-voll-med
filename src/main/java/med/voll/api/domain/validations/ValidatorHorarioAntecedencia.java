package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidatorHorarioAntecedencia implements ValidatorAgendamentoConsulta {

    public void valid(PostConsultaDTO dto) {

        LocalDateTime dataConsulta = dto.data();
        LocalDateTime now = LocalDateTime.now();
        Long diferencaMinutos = Duration.between(now, dataConsulta).toMinutes();

        if (diferencaMinutos < 30) {
            throw new BusinessException("Consulta deve ser agendada com 30 min de antecedência.");
        }
    }

}
