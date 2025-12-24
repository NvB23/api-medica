package med.voll.api.domain.consulta.service;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosCancelamentoConsulta;
import med.voll.api.infra.exceptions.TempoDeCancelamentoExpiradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelamentoDeConsultaService {
    private final ConsultaRepository consultaRepository;

    public CancelamentoDeConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        var dataConsulta = dados.consulta().getData();

        if (LocalDateTime.now().isAfter(dataConsulta.minusHours(24))) {
            throw new TempoDeCancelamentoExpiradoException("Tempo mínimo de cancelamento de consulta expirado!");
        }

        consultaRepository.delete(dados.consulta());
    }
}
