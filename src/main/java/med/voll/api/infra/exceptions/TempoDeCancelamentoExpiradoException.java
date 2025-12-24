package med.voll.api.infra.exceptions;

public class TempoDeCancelamentoExpiradoException extends RuntimeException {
    public TempoDeCancelamentoExpiradoException(String mensagem) {
        super(mensagem);
    }
}
