package tests.funcional.carrinho;

import core.controller.carrinho.CadastrarCarrinhoRequest;
import core.controller.login.RealizarLoginRequest;
import core.controller.produto.CadastrarProdutoRequest;
import core.controller.usuarios.CriarUsuarioRequest;
import data.constants.MessagesUtils;
import data.produto.ProdutoData;
import data.usuario.UsuarioData;
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
    static ProdutoData produtoData = ProdutoData.createTC70();
    static String adminToken;
    static String idProduto;
    static String nomeProduto = Utils.faker.commerce().productName();


    @BeforeAll
    public static void setUp() {
        // Configurar usuário administrador
        setUpAdminUser();
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
