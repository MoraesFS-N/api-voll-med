package med.voll.api.repository;

import med.voll.api.domain.entity.Consulta;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;
import med.voll.api.domain.model.EnumEspecialidade;
import med.voll.api.domain.model.PostEnderecoDTO;
import med.voll.api.domain.model.PostMedicoDTO;
import med.voll.api.domain.model.PostPacienteDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve retornar null quando medico cadastrado não esta disponivel na data")
    void switchMedicoRandomFreeDate() {

        LocalDateTime data = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EnumEspecialidade.CARDIOLOGIA);
        Paciente paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, data);

        Medico medicoLivre = repository.switchMedicoRandomFreeDate(EnumEspecialidade.CARDIOLOGIA, data);


        Assertions.assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EnumEspecialidade.CARDIOLOGIA);

        Medico medicoLivre = repository.switchMedicoRandomFreeDate(EnumEspecialidade.CARDIOLOGIA, proximaSegundaAs10);
        Assertions.assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, EnumEspecialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private PostMedicoDTO dadosMedico(String nome, String email, String crm, EnumEspecialidade especialidade) {
        return new PostMedicoDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private PostPacienteDTO dadosPaciente(String nome, String email, String cpf) {
        return new PostPacienteDTO(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private PostEnderecoDTO dadosEndereco() {
        return new PostEnderecoDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}