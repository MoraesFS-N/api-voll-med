package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidatorPacienteSemConsultaMesmoDia implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void valid(PostConsultaDTO dto) {
        LocalDateTime primeiraHora = dto.data().withHour(7);
        LocalDateTime ultimaHora = dto.data().withHour(18);

        Boolean pacienteTemConsultaNoDia = repository.existsByMedicoIdAndDataBetween(dto.idPaciente(), primeiraHora, ultimaHora);

        if (pacienteTemConsultaNoDia) {
            throw new BusinessException("Paciente possui consulta no dia informado");
        }

    }

}
