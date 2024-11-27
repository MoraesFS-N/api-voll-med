package med.voll.api.infra.exception;

import med.voll.api.domain.model.PostConsultaDTO;

public interface ValidatorAgendamentoConsulta {

    void valid(PostConsultaDTO dto);
}
