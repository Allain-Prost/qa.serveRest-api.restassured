package tests.funcional.produto;

import core.controller.login.RealizarLoginRequest;
import core.controller.produto.EditarProdutoRequest;
import core.controller.usuarios.CriarUsuarioRequest;
import data.produto.ProdutoData;
import data.usuario.UsuarioData;
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
import static tests.funcional.produto.CadastrarProdutoTest.cadastrarProdutoRequest;

@Execution(ExecutionMode.CONCURRENT)
public class EditarProdutoTest extends Base {

    static EditarProdutoRequest editarProdutoRequest = new EditarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static ProdutoData produtoData = ProdutoData.createTC70();
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
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoId() {

        editarProdutoRequest.montarDadosProduto(adminToken, idProduto, produtoData.getNameProduto(), produtoData.getPreco(),
                        produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(200)
                .body("message", Matchers.is(MessagesUtils.EDICAO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoIdComTokenDeUmCliente() {

        editarProdutoRequest.montarDadosProduto(clientToken, idProduto,  produtoData.getNameProduto(), produtoData.getPreco(),
                        produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(403)
                .body("message", Matchers.is(MessagesUtils.ROTA_EXCLUSIVA_PARA_ADMIN));
    }

    @Test
    public void testDeveValidarEdicaoDeUmProdutoAtravesDoIdComTokenInvalido() {

        editarProdutoRequest.montarDadosProduto("", idProduto, produtoData.getNameProduto(), produtoData.getPreco(),
                        produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }
}
