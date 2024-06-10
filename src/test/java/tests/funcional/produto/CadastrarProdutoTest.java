package tests.funcional.produto;

import core.service.login.RealizarLoginRequest;
import core.service.produto.CadastrarProdutoRequest;
import core.service.usuarios.CriarUsuarioRequest;
import tests.funcional.base.Base;
import data.constants.MessagesUtils;
import data.utils.Utils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static data.constants.MessagesUtils.MSG_TOKEN_INVALIDO;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@Execution(ExecutionMode.CONCURRENT)
public class CadastrarProdutoTest extends Base {

    static CadastrarProdutoRequest cadastrarProdutoRequest = new CadastrarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static String adminToken;
    static String clientToken;
    static String nomeProduto = Utils.faker.commerce().productName();

    @BeforeAll
    public static void setUp() {
        // Configurar usuário administrador
        setUpAdminUser();

        // Configurar usuário cliente
        setUpClientUser();
    }

    private static void setUpAdminUser() {
        String adminEmail = Utils.faker.internet().emailAddress();
        String adminPassword = Utils.faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), adminEmail, adminPassword, "true");
        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");
        cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, Utils.faker.number().numberBetween(1, 100),
                Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100));
    }

    private static void setUpClientUser() {
        String clientEmail = Utils.faker.internet().emailAddress();
        String clientPassword = Utils.faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), clientEmail, clientPassword, "false");
        clientToken = realizarLoginRequest.montarDadosLogin(clientEmail, clientPassword)
                .then().statusCode(200).extract().path("authorization");
    }

    @Test
    public void testDeveValidarCadastroDeUmProduto() {

        cadastrarProdutoRequest.montarDadosProduto(adminToken, Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(201)
                .body("", hasKey("_id"))
                .body("_id", instanceOf(String.class))
                .body("_id.length()", greaterThan(0))
                .body("message", Matchers.is(MessagesUtils.CADASTRO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarCadastroDeUmProdutoComNomeJaUtilizado() {

        cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(400)
                .body("message", Matchers.is(MessagesUtils.PRODUTO_EXISTENTE));
    }

    @Test
    public void testDeveValidarCadastroDeUmProdutoSemTokenAutorizacao() {

        cadastrarProdutoRequest.montarDadosProduto("", Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }


    @Test
    public void testDeveValidarCadastroDeUmProdutoUtilizandoUmTokenDeUmCliente() {

        cadastrarProdutoRequest.montarDadosProduto(clientToken, Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(403)
                .body("message", Matchers.is(MessagesUtils.ROTA_EXCLUSIVA_PARA_ADMIN));
    }
}
