package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidatorHoraFuncionamento implements ValidatorAgendamentoConsulta {

    public void valid(PostConsultaDTO dto) {
        LocalDateTime dataConsulta = dto.data();

        Boolean sunday = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean antesAberturaClinica = dataConsulta.getHour() < 7;
        Boolean depoisFechamentoClinica = dataConsulta.getHour() > 18;

        if (sunday || antesAberturaClinica || depoisFechamentoClinica) {
            throw new BusinessException("Consulta fora do horario de funcionamento da clinica.");
        }

    }
}
