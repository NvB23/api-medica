package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.dto.DadosAtualizaMedico;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.medico.dto.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicosController implements med.voll.api.controller.doc.MedicosDoc {

    private final MedicoRepository medicoRepository;

    public MedicosController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    /* @Autowired
    private MedicoRepository medicoRepository; */

    private static final Logger logger = LoggerFactory.getLogger(MedicosController.class);

    @PostMapping
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dadosCadastroMedico);
        medicoRepository.save(medico);
        logger.info("Médico Salvo");

        URI uri = uriBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(
            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = {"nome"},
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<DadosListagemMedico> medicoList = medicoRepository.findAllByAtivoTrue(pageable)
                .map(DadosListagemMedico::new);

        if (medicoList.isEmpty()) logger.info("Lista de Médicos Vazia!");

        return ResponseEntity.ok(medicoList);
    }

    @PutMapping
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoMedico> atualizaMedico(@RequestBody @Valid DadosAtualizaMedico dadosCadastroMedico) {
        Optional<Medico> medicoOptional = medicoRepository.findById(dadosCadastroMedico.id());

        if (medicoOptional.isEmpty()) return ResponseEntity.notFound().build();
        Medico medico = medicoOptional.get();
        medico.atualizarDados(dadosCadastroMedico);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Override
    public ResponseEntity<Void> excluirMedico(@PathVariable Long id) {
        /* medicoRepository.deleteById(id);
           return "Usuário deletado."; */

        Medico medico = medicoRepository.getReferenceById(id);

        medico.desativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DadosDetalhamentoMedico> detalharMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
