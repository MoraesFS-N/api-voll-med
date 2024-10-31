package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.Medico;
import med.voll.api.model.GetMedicoListDTO;
import med.voll.api.model.PostMedicoDTO;
import med.voll.api.model.PutMedicoDTO;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {


    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void add(@RequestBody @Valid PostMedicoDTO dto) {
        repository.save(new Medico(dto));
    }

    @GetMapping
    public Page<GetMedicoListDTO> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(GetMedicoListDTO::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid PutMedicoDTO dto) {
        Medico medico = repository.getReferenceById(dto.id());
        medico.updateData(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        medico.disable();
    }
}
