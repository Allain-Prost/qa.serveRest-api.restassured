package tests.funcional.produto;

import core.service.login.RealizarLoginRequest;
import core.service.produto.CadastrarProdutoRequest;
import core.service.usuarios.CriarUsuarioRequest;
import data.login.LoginData;
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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@Execution(ExecutionMode.CONCURRENT)
public class CadastrarProdutoTest extends Base {

    static CadastrarProdutoRequest cadastrarProdutoRequest = new CadastrarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static ProdutoData produtoData = ProdutoData.createTC71();
    static String adminToken;
    static String clientToken;
    static String nomeProduto = produtoData.getNameProduto();

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
        cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, produtoData.getPreco(), produtoData.getDescricao(), produtoData.getQuantidade());
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
    public void testDeveValidarCadastroDeUmProduto() {
        ProdutoData produtoData = ProdutoData.createTC70();
        cadastrarProdutoRequest.montarDadosProduto(adminToken, produtoData.getNameProduto(),
                        produtoData.getPreco(), produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(201)
                .body("", hasKey("_id"))
                .body("_id", instanceOf(String.class))
                .body("_id.length()", greaterThan(0))
                .body("message", Matchers.is(MessagesUtils.CADASTRO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarCadastroDeUmProdutoComNomeJaUtilizado() {

        cadastrarProdutoRequest.montarDadosProduto(adminToken,
                        produtoData.getNameProduto(), produtoData.getPreco(),
                        produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(400)
                .body("message", Matchers.is(MessagesUtils.PRODUTO_EXISTENTE));
    }

    @Test
    public void testDeveValidarCadastroDeUmProdutoSemTokenAutorizacao() {

        cadastrarProdutoRequest.montarDadosProduto("", produtoData.getNameProduto(),
                        produtoData.getPreco(), produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(401)
                .body("message", is(MSG_TOKEN_INVALIDO));
    }

    @Test
    public void testDeveValidarCadastroDeUmProdutoUtilizandoUmTokenDeUmCliente() {

        cadastrarProdutoRequest.montarDadosProduto(clientToken, produtoData.getNameProduto(),
                        produtoData.getPreco(), produtoData.getDescricao(), produtoData.getQuantidade())
                .then().statusCode(403)
                .body("message", Matchers.is(MessagesUtils.ROTA_EXCLUSIVA_PARA_ADMIN));
    }
}
