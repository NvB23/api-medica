package med.voll.api.domain.consulta.service;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoDeConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoConsultas> validacoes;

    public AgendamentoDeConsultaService(ConsultaRepository consultaRepository,
                                        MedicoRepository medicoRepository,
                                        PacienteRepository pacienteRepository,
                                        List<ValidadorAgendamentoConsultas> validacoes) {

        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validacoes = validacoes;
    }


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        validacoes.forEach(validador -> validador.validar(dados));

        if (!medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        if (dados.idMedico() != null && !pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        

        var medico = escolherMedico(dados);
        var paciente  = pacienteRepository.getReferenceById(dados.idPaciente());

        Consulta consulta = new Consulta(
                null,
                medico,
                paciente,
                dados.data(),
                false,
                null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("A especialidade deve ser informada, caso o médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
