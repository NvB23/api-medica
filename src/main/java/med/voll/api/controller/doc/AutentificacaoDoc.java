package med.voll.api.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.dto.DadosAutentificacao;
import med.voll.api.domain.usuario.dto.DadosRegistroUsuario;
import med.voll.api.infra.security.dto.DadosTokenJWT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação")
public interface AutentificacaoDoc {

    @Operation(
            summary = "Fazer Login",
            description = "Faz Login",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosTokenJWT.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutentificacao body);

    @Operation(
            summary = "Registrar Usuário",
            description = "Registro de Usuário",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosTokenJWT.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Void> registrar(@RequestBody @Valid DadosRegistroUsuario body);
}
