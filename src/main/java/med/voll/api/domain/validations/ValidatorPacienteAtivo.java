package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorPacienteAtivo implements ValidatorAgendamentoConsulta {

    @Autowired
    private PacienteRepository repository;

    public void valid(PostConsultaDTO dto) {
        Boolean pacienteAtivo = repository.findAtivoById(dto.idPaciente());

        if (!pacienteAtivo) {
            throw new BusinessException("Consulta não pode ser agendada com paciente inativo.");
        }
    }
}
