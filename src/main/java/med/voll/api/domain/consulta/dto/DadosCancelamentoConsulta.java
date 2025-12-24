package med.voll.api.domain.consulta.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.enums.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        Consulta consulta,

        @NotNull
        MotivoCancelamento motivo
) {

}
