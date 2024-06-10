package tests.funcional.carrinho;

import core.service.carrinho.CadastrarCarrinhoRequest;
import core.service.login.RealizarLoginRequest;
import core.service.produto.CadastrarProdutoRequest;
import core.service.usuarios.CriarUsuarioRequest;
import data.constants.MessagesUtils;
import tests.funcional.base.Base;
import data.utils.Utils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class CadastrarCarrinhoTest extends Base {

    static CadastrarProdutoRequest cadastrarProdutoRequest = new CadastrarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static CadastrarCarrinhoRequest cadastrarCarrinhoRequest = new CadastrarCarrinhoRequest();
    static String adminToken;
    static String idProduto;
    static String nomeProduto = Utils.faker.commerce().productName();


    @BeforeAll
    public static void setUp() {
        // Configurar usuário administrador
        setUpAdminUser();
    }

    private static void setUpAdminUser() {
        String adminEmail = Utils.faker.internet().emailAddress();
        String adminPassword = Utils.faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), adminEmail, adminPassword, "true");

        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");

        idProduto = cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(201).extract().path("_id");
    }

    @Test
    public void testDeveValidarCadastroDeCarrinho() {

        cadastrarCarrinhoRequest.montarDadosProduto(adminToken, idProduto, 2)
                .then().statusCode(201)
                .body("", hasKey("_id"))
                .body("_id", instanceOf(String.class))
                .body("_id.length()", greaterThan(0))
                .body("message", Matchers.is(MessagesUtils.CADASTRO_COM_SUCESSO));
    }

}
