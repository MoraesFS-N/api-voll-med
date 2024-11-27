package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorConsultaMedicoOutroHorario implements ValidatorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void valid(PostConsultaDTO dto) {
        Boolean temConsulta = consultaRepository.existsByMedicoIdAndData(dto.idMedico(), dto.data());

        if (temConsulta) {
            throw new BusinessException("O medico informado ja possui consulta marcada neste horário");
        }
    }

}
