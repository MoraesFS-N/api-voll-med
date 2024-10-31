package med.voll.api.entity;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.model.EnumEspecialidade;
import med.voll.api.model.PostMedicoDTO;
import med.voll.api.model.PutMedicoDTO;

@Table(name = "medico")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private String email;
    private String crm;

    @Enumerated(EnumType.STRING)
    private EnumEspecialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(PostMedicoDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.crm = dto.crm();
        this.especialidade = dto.especialidade();
        this.endereco = new Endereco(dto.endereco());
        this.ativo = true;
    }

    public void updateData(PutMedicoDTO dto) {

        if (dto.nome() != null) {
            this.nome = dto.nome();
        }

        if (dto.telefone() != null) {
            this.telefone = dto.telefone();
        }

        if (dto.endereco() != null) {
            this.endereco.updateAddress(dto.endereco());
        }
    }

    public void disable() {
        this.ativo = false;
    }
}
