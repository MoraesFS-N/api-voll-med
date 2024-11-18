package med.voll.api.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.model.PostPacienteDTO;
import med.voll.api.domain.model.PutPacienteDTO;

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
    private String telefone;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(PostPacienteDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.telefone = dto.telefone();
        this.endereco = new Endereco(dto.endereco());
        this.ativo = true;
    }

    public void updateData(PutPacienteDTO dto) {
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
