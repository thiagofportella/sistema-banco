import br.com.cefet.banco.negocio.*;
import br.com.cefet.banco.persistencia.bd.ClienteDAOTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BancoTest.class,
        CaixaTest.class,
        ClienteTest.class,
        ContaCorrenteTest.class,
        ContaPoupancaTest.class,
        DiretorTest.class,
        GerenteTest.class,
        GerenteTest.class,
        ClienteDAOTest.class
})
public class TesteDeRegressao {
}
