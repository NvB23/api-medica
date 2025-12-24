package med.voll.api.domain.paciente.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.Endereco;

public record DadosAtualizaPaciente(
        @NotNull
        Long id,

        String nome,
        String telefone,
        Endereco endereco
) {
}
