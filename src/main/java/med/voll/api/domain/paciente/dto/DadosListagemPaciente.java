package med.voll.api.domain.paciente.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.Paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String telefone,
        String cpf
) {
    public DadosListagemPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getTelefone(),
                paciente.getCpf()
        );
    }
}
