package tests.funcional.produto;

import core.service.login.RealizarLoginRequest;
import core.service.produto.EditarProdutoRequest;
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
import static org.hamcrest.Matchers.is;

@Execution(ExecutionMode.CONCURRENT)
public class EditarProdutoTest extends Base {

    static EditarProdutoRequest editarProdutoRequest = new EditarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static String adminToken;
    static String clientToken;
    static String idProduto;
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
        idProduto = CadastrarProdutoTest.cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, Utils.faker.number().numberBetween(1, 100),
                Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(201).extract().path("_id");
    }

    private static void setUpClientUser() {
        String clientEmail = Utils.faker.internet().emailAddress();
        String clientPassword = Utils.faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), clientEmail, clientPassword, "false");
        clientToken = realizarLoginRequest.montarDadosLogin(clientEmail, clientPassword)
                .then().statusCode(200).extract().path("authorization");
    }

    @Test
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoId() {

        editarProdutoRequest.montarDadosProduto(adminToken, idProduto, Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(200)
                .body("message", Matchers.is(MessagesUtils.EDICAO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoIdComTokenDeUmCliente() {

        editarProdutoRequest.montarDadosProduto(clientToken, idProduto, Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(403)
                .body("message", Matchers.is(MessagesUtils.ROTA_EXCLUSIVA_PARA_ADMIN));
    }

    @Test
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoIdComTokenInvalido() {

        editarProdutoRequest.montarDadosProduto("", idProduto, Utils.faker.commerce().productName(), Utils.faker.number().numberBetween(1, 100),
                        Utils.faker.lorem().paragraph(), Utils.faker.number().numberBetween(1, 100))
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }
}
