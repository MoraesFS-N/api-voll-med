package med.voll.api.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.model.EnumEspecialidade;

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

}
