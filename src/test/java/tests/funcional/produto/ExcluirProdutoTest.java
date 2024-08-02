package tests.funcional.produto;

import core.controller.login.RealizarLoginRequest;
import core.controller.produto.EditarProdutoRequest;
import core.controller.produto.ExcluirProdutoRequest;
import core.controller.usuarios.CriarUsuarioRequest;
import data.produto.ProdutoData;
import data.usuario.UsuarioData;
import tests.funcional.base.Base;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static data.constants.DadosUtils.PRODUTO_INVALIDO;
import static data.constants.DadosUtils.TOKEN_INVALIDO;
import static data.constants.MessagesUtils.*;
import static data.utils.Utils.faker;
import static org.hamcrest.Matchers.is;
import static tests.funcional.produto.CadastrarProdutoTest.cadastrarProdutoRequest;

public class ExcluirProdutoTest extends Base {

    static EditarProdutoRequest editarProdutoRequest = new EditarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static ExcluirProdutoRequest excluirProdutoRequest = new ExcluirProdutoRequest();
    static ProdutoData produtoData = ProdutoData.createTC70();
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
        UsuarioData usuarioData = UsuarioData.createTC01();
        String adminEmail = usuarioData.getEmail();
        String adminPassword = usuarioData.getPassword();

        postCriarUsuarioRequest.montarDadosUsuario(usuarioData.getName(), adminEmail, adminPassword,
                usuarioData.getAdministrador());
        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");
        idProduto =  cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, produtoData.getPreco(), produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(201).extract().path("_id");
    }

    private static void setUpClientUser() {
        UsuarioData usuarioData = UsuarioData.createTC050();
        String clientEmail = usuarioData.getEmail();
        String clientPassword = usuarioData.getPassword();

        postCriarUsuarioRequest.montarDadosUsuario(usuarioData.getName(), clientEmail, clientPassword,
                usuarioData.getAdministrador());
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
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoComTokenDeUmCliente() {

        excluirProdutoRequest.montarDadosProdutoPorId(clientToken, idProduto)
                .then().statusCode(403)
                .body("message", is(ROTA_EXCLUSIVA_PARA_ADMIN));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoAtravesDoIdComTokenInvalido() {

        excluirProdutoRequest.montarDadosProdutoPorId(TOKEN_INVALIDO, idProduto)
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }

    @Test
    public void testDeveValidarExclusaoDeUmProdutoComIdInexistente() {

        excluirProdutoRequest.montarDadosProdutoPorId(adminToken, PRODUTO_INVALIDO)
                .then().statusCode(200)
                .body("message", is(NENHUM_REGISTRO_EXCLUIDO));
    }
}
