package med.voll.api.domain.validations;

import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.BusinessException;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorMedicoAtivo implements ValidatorAgendamentoConsulta {

    @Autowired
    private MedicoRepository repository;

    public void valid(PostConsultaDTO dto) {

        if (dto.idMedico() == null) {
            return;
        }

        Boolean medicoAtivo = repository.findAtivoById(dto.idMedico());

        if (!medicoAtivo) {
            throw new BusinessException("Consulta não pode ser excluída com medico inativo");
        }

    }

}
