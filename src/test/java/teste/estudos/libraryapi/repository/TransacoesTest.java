package teste.estudos.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teste.estudos.libraryapi.service.TransacaoService;

@SpringBootTest
public class TransacoesTest {


    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacoesTest(){
        transacaoService.executar();
    }
}
