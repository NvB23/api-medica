package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.endereco.dto.DadosEndereco;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    String logradouro;
    String bairro;
    String numero;
    String complemento;
    String cep;
    String cidade;
    String uf;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.bairro = dadosEndereco.bairro();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cep = dadosEndereco.cep();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
    }

    public void atualizarEndereco(DadosEndereco endereco) {
        if (this.logradouro != null) this.logradouro = endereco.logradouro();
        if (this.bairro != null) this.bairro = endereco.bairro();
        if (this.numero != null) this.numero = endereco.numero();
        if (this.complemento != null) this.complemento = endereco.complemento();
        if (this.cep != null) this.cep = endereco.cep();
        if (this.cidade != null) this.cidade = endereco.cidade();
        if (this.uf != null) this.uf = endereco.uf();
    }
}
