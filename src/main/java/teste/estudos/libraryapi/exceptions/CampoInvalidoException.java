package teste.estudos.libraryapi.exceptions;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

public class CampoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoInvalidoException(String campo, String mensagem){
        super(mensagem);
        this.campo= campo;
    }
}
