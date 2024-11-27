package med.voll.api.domain.service;

import jakarta.validation.ValidationException;
import med.voll.api.domain.entity.Consulta;
import med.voll.api.domain.entity.Medico;
import med.voll.api.domain.entity.Paciente;
import med.voll.api.domain.model.GetConsultaDTO;
import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.infra.exception.ValidatorAgendamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidatorAgendamentoConsulta> validators;

    public GetConsultaDTO agendar(PostConsultaDTO dto) {

        if (!pacienteRepository.existsById(dto.idPaciente())) {
            throw new ValidationException("Id do paciente invalido!");
        }

        if (dto.idMedico() != null && !medicoRepository.existsById(dto.idMedico())) {
            throw new ValidationException("Id do medico invalido!");
        }

        validators.forEach(v -> v.valid(dto));

        Paciente paciente = pacienteRepository.getReferenceById(dto.idPaciente());
        Medico medico = findMedico(dto);

        if (medico == null) {
            throw new ValidationException("Não há nenhum medico disponível nessa data!");
        }

        Consulta consulta = new Consulta(null, medico, paciente, dto.data());

        repository.save(consulta);

        return new GetConsultaDTO(consulta);
    }

    private Medico findMedico(PostConsultaDTO dto) {
        if (dto.idMedico() != null) {
            return medicoRepository.getReferenceById(dto.idMedico());
        }

        if (dto.especialidade() == null) {
            throw new ValidationException("Especialidade precisa ser informada!");
        }

        return medicoRepository.switchMedicoRandomFreeDate(dto.especialidade(), dto.data());
    }

}
