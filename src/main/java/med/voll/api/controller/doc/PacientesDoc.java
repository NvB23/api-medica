package med.voll.api.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.dto.DadosListagemMedico;
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

@Tag(name = "Paciente")
public interface PacientesDoc {

    @Operation(
            summary = "Criar um Paciente",
            description = "Cria um Paciente",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosDetalhamentoMedico.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DadosDetalhamentoPaciente> cadastrarPaciente(
            @Valid
            @RequestBody DadosCadastroPaciente dadosCadastroPaciente,
            UriComponentsBuilder uriBuilder
    );

    @Operation(
            summary = "Listar dodos os Pacientes",
            description = "Lista dodos os Pacientes",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = DadosListagemMedico.class))
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(
            Pageable pageable
    );

    @Operation(
            summary = "Atualizar um Paciente",
            description = "Atualiza informações de um Paciente",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosDetalhamentoMedico.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DadosDetalhamentoPaciente> atualizaPaciente(
            @Valid
            @RequestBody
            DadosAtualizaPaciente dadosAtualizaPaciente
    );

    @Operation(
            summary = "Deletar um Paciente",
            description = "Deleta um Paciente",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosDetalhamentoMedico.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DadosDetalhamentoPaciente> deletaPaciente(
            @PathVariable Long id
    );

    @Operation(
            summary = "Detalhar um Paciente",
            description = "Detalha um Paciente",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    schema = @Schema(implementation = DadosDetalhamentoMedico.class)
                            )
                    }),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Erro", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<DadosDetalhamentoPaciente> detalharPaciente(@PathVariable Long id);
}
