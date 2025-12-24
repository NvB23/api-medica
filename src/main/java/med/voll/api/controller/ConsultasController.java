package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.service.AgendamentoDeConsultaService;
import med.voll.api.domain.consulta.service.CancelamentoDeConsultaService;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultasController implements med.voll.api.controller.doc.ConsultasDoc {

    private final AgendamentoDeConsultaService consultaService;
    private final CancelamentoDeConsultaService cancelamentoService;

    public ConsultasController(AgendamentoDeConsultaService consultaService, CancelamentoDeConsultaService cancelamentoService) {
        this.consultaService = consultaService;
        this.cancelamentoService = cancelamentoService;
    }

    @PostMapping("/agendar")
    @Transactional
    @Override
    public ResponseEntity<DadosDetalhamentoConsulta> agendarConsulta(@Valid @RequestBody DadosAgendamentoConsulta dados) {
        var consulta = consultaService.agendar(dados);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/cancelar")
    @Override
    public ResponseEntity<Void> cancelarConsulta(@Valid @RequestBody DadosCancelamentoConsulta dados) {
        cancelamentoService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
