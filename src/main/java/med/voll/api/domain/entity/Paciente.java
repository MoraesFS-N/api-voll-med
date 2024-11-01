package med.voll.api.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.model.EnumEspecialidade;
import med.voll.api.domain.model.PostPacienteDTO;
import med.voll.api.domain.model.PutMedicoDTO;

@Table(name = "paciente")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(PostPacienteDTO dto) {
    }

    public void updateData(PutMedicoDTO dto) {
    }

    public void disable() {
        this.setAtivo(false);
    }
}
