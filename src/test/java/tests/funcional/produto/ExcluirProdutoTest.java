package tests.funcional.produto;

import core.service.login.RealizarLoginRequest;
import core.service.produto.EditarProdutoRequest;
import core.service.produto.ExcluirProdutoRequest;
import core.service.usuarios.CriarUsuarioRequest;
import tests.funcional.base.Base;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static data.constants.DadosUtils.PRODUTO_INVALIDO;
import static data.constants.DadosUtils.TOKEN_INVALIDO;
import static data.constants.MessagesUtils.*;
import static data.utils.Utils.faker;
import static org.hamcrest.Matchers.is;

public class ExcluirProdutoTest extends Base {

    static EditarProdutoRequest editarProdutoRequest = new EditarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static ExcluirProdutoRequest excluirProdutoRequest = new ExcluirProdutoRequest();
    static String adminToken;
    static String clientToken;
    static String idProduto;
    static String nomeProduto = faker.commerce().productName();


    @BeforeAll
    public static void setUp() {
        // Configurar usuário administrador
        setUpAdminUser();

        // Configurar usuário cliente
        setUpClientUser();
    }

    private static void setUpAdminUser() {
        String adminEmail = faker.internet().emailAddress();
        String adminPassword = faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(faker.name().firstName(), adminEmail, adminPassword, "true");
        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");
        idProduto = CadastrarProdutoTest.cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, faker.number().numberBetween(1, 100),
                        faker.lorem().paragraph(), faker.number().numberBetween(1, 100))
                .then().statusCode(201).extract().path("_id");
    }

    private static void setUpClientUser() {
        String clientEmail = faker.internet().emailAddress();
        String clientPassword = faker.internet().password();

        postCriarUsuarioRequest.montarDadosUsuario(faker.name().firstName(), clientEmail, clientPassword, "false");
        clientToken = realizarLoginRequest.montarDadosLogin(clientEmail, clientPassword)
                .then().statusCode(200).extract().path("authorization");
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoAtravesDoId() {

        excluirProdutoRequest.montarDadosProdutoPorId(adminToken, idProduto)
                .then().statusCode(200)
                .body("message", is(EXCLUSAO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoAtravesDoIdComTokenAusente() {

        excluirProdutoRequest.montarDadosProdutoPorId("", idProduto)
                .then().statusCode(200)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoComTokenDeUmCliente() {

        excluirProdutoRequest.montarDadosProdutoPorId(clientToken, idProduto)
                .then().statusCode(200)
                .body("message", is(ROTA_EXCLUSIVA_PARA_ADMIN));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoAtravesDoIdComTokenInvalido() {

        excluirProdutoRequest.montarDadosProdutoPorId(TOKEN_INVALIDO, idProduto)
                .then().statusCode(200)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoComIdInexistente() {

        excluirProdutoRequest.montarDadosProdutoPorId(adminToken, PRODUTO_INVALIDO)
                .then().statusCode(200)
                .body("message", is(NENHUM_REGISTRO_EXCLUIDO));
    }
}
