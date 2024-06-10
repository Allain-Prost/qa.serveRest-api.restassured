package tests.funcional.carrinho;

import core.service.carrinho.CadastrarCarrinhoRequest;
import core.service.carrinho.ExcluirCarrinhoRequest;
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

import static data.utils.Utils.faker;

@Execution(ExecutionMode.CONCURRENT)
public class ExcluirCarrinhoTest extends Base {

    static ExcluirCarrinhoRequest excluirCarrinhoRequest = new ExcluirCarrinhoRequest();
    static CadastrarProdutoRequest cadastrarProdutoRequest = new CadastrarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static CadastrarCarrinhoRequest cadastrarCarrinhoRequest = new CadastrarCarrinhoRequest();
    static String clientToken;
    static String adminToken;
    static String idProduto;
    static String nomeProduto = Utils.faker.commerce().productName();


    @BeforeAll
    public static void setUp() {
        // Configurar usuário cliente
        setUpClientUser();
    }

    private static void setUpClientUser() {
        String adminEmail = Utils.faker.internet().emailAddress();
        String adminPassword = Utils.faker.internet().password();
        String clientEmail = faker.internet().emailAddress();
        String clientPassword = faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), adminEmail, adminPassword, "true");
        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), clientEmail, clientPassword, "false");

        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");

        clientToken = realizarLoginRequest.montarDadosLogin(clientEmail, clientPassword)
                .then().statusCode(200).extract().path("authorization");

        idProduto = cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(201).extract().path("_id");

        cadastrarCarrinhoRequest.montarDadosProduto(clientToken, idProduto, 2);
    }

    @Test
    public void testDeveValidarExclusaoDeUmCarrinhoAtravesDoTokenDoCliente() {

        excluirCarrinhoRequest.montarDadosExcluirCarrinho(clientToken)
                .then().statusCode(200)
                .body("message", Matchers.is(MessagesUtils.REGISTRO_EXCLUIDO_COM_SUCESSO));
    }
}
