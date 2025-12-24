package med.voll.api.domain.usuario.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import med.voll.api.domain.usuario.enums.RegraUsuario;

public record DadosRegistroUsuario(
        @NotBlank
        String login,

        @NotBlank
        String senha
) {
}
