package med.voll.api.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAutentificacao(
        @NotBlank
        String login,

        @NotBlank
        String senha
) {
}
