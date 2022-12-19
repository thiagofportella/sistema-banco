import br.com.cefet.banco.negocio.*;
import br.com.cefet.banco.persistencia.bd.ClienteDAOTest;
import br.com.cefet.banco.util.BancoUtilTest;
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
        ClienteDAOTest.class,
        BancoUtilTest.class,
        ControleDeBonificacoesTest.class,
        SistemaBancarioTest.class,
        EfetuaDepositoTest.class,
        ContaTest.class,
        PromoverFuncionarioTest.class
})
public class TesteDeRegressao {
}
