package med.voll.api.repository;

import med.voll.api.domain.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByMedicoIdAndData(Long id, LocalDateTime data);

    Boolean existsByMedicoIdAndDataBetween(Long id, LocalDateTime primeiraHora, LocalDateTime ultimaHora);

}
