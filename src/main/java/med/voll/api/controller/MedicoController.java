package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.model.GetMedicoDTO;
import med.voll.api.domain.model.GetMedicoListDTO;
import med.voll.api.domain.model.PostMedicoDTO;
import med.voll.api.domain.model.PutMedicoDTO;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {


    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity add(@RequestBody @Valid PostMedicoDTO dto, UriComponentsBuilder uri) {

        Medico medico = repository.save(new Medico(dto));

        var URI = uri.path("/medicos/{id}").buildAndExpand(medico.getId());

        return ResponseEntity.created(URI.toUri()).body(new GetMedicoDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<GetMedicoListDTO>> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<GetMedicoListDTO> page = repository.findAllByAtivoTrue(pageable).map(GetMedicoListDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMedicoDTO> detail(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new GetMedicoDTO(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid PutMedicoDTO dto) {
        Medico medico = repository.getReferenceById(dto.id());
        medico.updateData(dto);

        return ResponseEntity.ok(new GetMedicoDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        medico.disable();
        return ResponseEntity.noContent().build();
    }
}
