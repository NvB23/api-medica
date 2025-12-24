package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.dto.DadosAtualizaMedico;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.medico.enums.Especialidade;

import java.util.List;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private List<Consulta> consulta;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco dadosEndereco;

    private boolean ativo;

    public Medico(DadosCadastroMedico dadosCadastroMedico) {
        this.id = null;
        this.nome = dadosCadastroMedico.nome();
        this.email = dadosCadastroMedico.email();
        this.telefone = dadosCadastroMedico.telefone();
        this.crm = dadosCadastroMedico.crm();
        this.especialidade = dadosCadastroMedico.especialidade();
        this.dadosEndereco = new Endereco(dadosCadastroMedico.endereco());
        this.ativo = true;
    }

    public void atualizarDados(DadosAtualizaMedico dadosAtualizaMedico) {
        if (dadosAtualizaMedico.nome() != null)
            this.nome = dadosAtualizaMedico.nome();
        if (dadosAtualizaMedico.telefone() != null)
            this.telefone = dadosAtualizaMedico.telefone();
        if (dadosAtualizaMedico.endereco() != null)
            this.dadosEndereco.atualizarEndereco(dadosAtualizaMedico.endereco());
    }

    public void desativar() {
        this.ativo = false;
    }
}
