package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.dto.DadosAtualizaPaciente;
import med.voll.api.domain.paciente.dto.DadosCadastroPaciente;
import med.voll.api.domain.paciente.dto.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.dto.DadosListagemPaciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacientesController implements med.voll.api.controller.doc.PacientesDoc {

    private final PacienteRepository pacienteRepository;

    public PacientesController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrarPaciente(
            @Valid
            @RequestBody DadosCadastroPaciente dadosCadastroPaciente,
            UriComponentsBuilder uriBuilder
    ) {
        Paciente paciente = new Paciente(dadosCadastroPaciente);
        pacienteRepository.save(paciente);

        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(
            Pageable pageable
    ) {
        Page<DadosListagemPaciente> listagemPaciente = pacienteRepository.
                findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);

        return ResponseEntity.ok(listagemPaciente);
    }

    @PutMapping
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoPaciente> atualizaPaciente(
            @Valid
            @RequestBody
            DadosAtualizaPaciente dadosAtualizaPaciente
    ) {
        Optional<Paciente> buscaPaciente = pacienteRepository.findById(dadosAtualizaPaciente.id());

        if (buscaPaciente.isEmpty()) return ResponseEntity.notFound().build();

        Paciente paciente = buscaPaciente.get();

        paciente.atualizaPaciente(dadosAtualizaPaciente);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoPaciente> deletaPaciente(
            @PathVariable Long id
    ) {
        Paciente paciente = pacienteRepository.getReferenceById(id);

        paciente.desativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DadosDetalhamentoPaciente> detalharPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
