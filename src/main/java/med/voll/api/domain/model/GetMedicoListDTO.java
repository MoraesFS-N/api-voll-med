package med.voll.api.domain.model;

import med.voll.api.domain.entity.Medico;

public record GetMedicoListDTO(Long id, String nome, String email, String crm, EnumEspecialidade especialidade) {

    public GetMedicoListDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}
