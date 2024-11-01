package med.voll.api.domain.model;

import med.voll.api.domain.entity.Endereco;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;

public record GetMedicoDTO(Long id, String nome, String email, String telefone, String crm, EnumEspecialidade especialidade, Endereco endereco) {

    public GetMedicoDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }

}
