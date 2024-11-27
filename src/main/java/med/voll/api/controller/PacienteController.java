package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.entity.Paciente;
import med.voll.api.domain.model.GetPacienteListDTO;
import med.voll.api.domain.model.PostPacienteDTO;
import med.voll.api.domain.model.PutPacienteDTO;
import med.voll.api.domain.model.GetPacienteDTO;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity add(@RequestBody @Valid PostPacienteDTO dto, UriComponentsBuilder uri) {

        Paciente paciente = repository.save(new Paciente(dto));

        var URI = uri.path("/pacientes/{id}").buildAndExpand(paciente.getId());

        return ResponseEntity.created(URI.toUri()).body(new GetPacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<GetPacienteListDTO>> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<GetPacienteListDTO> page = repository.findAllByAtivoTrue(pageable).map(GetPacienteListDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPacienteDTO> detail(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new GetPacienteDTO(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid PutPacienteDTO dto) {
        Paciente paciente = repository.getReferenceById(dto.id());
        paciente.updateData(dto);

        return ResponseEntity.ok(new GetPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.disable();
        return ResponseEntity.noContent().build();
    }
}
