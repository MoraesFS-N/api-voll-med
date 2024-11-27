package med.voll.api.repository;

import med.voll.api.domain.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    @Query("""
            select p.ativo from Paciente p 
            where
            p.id =:id 
            and
            p.ativo = true
            """)
    Boolean findAtivoById(Long id);
}
