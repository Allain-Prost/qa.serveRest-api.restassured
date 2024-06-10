package tests.funcional.produto;

import core.service.login.RealizarLoginRequest;
import core.service.produto.BuscarProdutosRequest;
import core.service.produto.CadastrarProdutoRequest;
import core.service.usuarios.CriarUsuarioRequest;
import tests.funcional.base.Base;
import data.utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

public class BuscarProdutosTest extends Base {

    static CadastrarProdutoRequest cadastrarProdutoRequest = new CadastrarProdutoRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static BuscarProdutosRequest buscarProdutosRequest = new BuscarProdutosRequest();
    static String adminToken;
    static String idProduto;
    static String nomeProduto;
    static int precoProduto;
    static String descricaoProduto;
    static int quantidadeProduto;

    @BeforeAll
    public static void setUp() {
        // Configurar usuário administrador
        setUpAdminUser();
    }

    private static void setUpAdminUser() {
        String adminEmail = Utils.faker.internet().emailAddress();
        String adminPassword = Utils.faker.internet().password();
        nomeProduto = Utils.faker.commerce().productName();
        precoProduto = Utils.faker.number().numberBetween(1, 100);
        descricaoProduto = Utils.faker.lorem().paragraph();
        quantidadeProduto = Utils.faker.number().numberBetween(1, 100);

        postCriarUsuarioRequest.montarDadosUsuario(Utils.faker.name().firstName(), adminEmail, adminPassword, "true");
        adminToken = realizarLoginRequest.montarDadosLogin(adminEmail, adminPassword)
                .then().statusCode(200).extract().path("authorization");
        idProduto = cadastrarProdutoRequest.montarDadosProduto(adminToken, nomeProduto, precoProduto, descricaoProduto, quantidadeProduto)
                .then().statusCode(201).extract().path("_id");

    }

    @Test
    public void testDeveValidarBuscarDeProdutoUtilizandoTodosOsFiltros() {

        buscarProdutosRequest.montarDadosProduto(adminToken, idProduto, nomeProduto, precoProduto, descricaoProduto, quantidadeProduto)
                .then().statusCode(200)
                .body("produtos[0].nome", is(nomeProduto))
                .body("produtos[0].descricao", is(descricaoProduto))
                .body("produtos[0].quantidade", is((quantidadeProduto)))
                .body("produtos[0].preco", is(precoProduto));
    }

    @Test
    public void testDeveValidarBuscaDeProdutoAtravesDoId() {

        buscarProdutosRequest.montarDadosProdutoPorId(adminToken, idProduto)
                .then().statusCode(200)
                .body("produtos[0].nome", is(nomeProduto))
                .body("produtos[0].descricao", is(descricaoProduto))
                .body("produtos[0].quantidade", is((quantidadeProduto)))
                .body("produtos[0].preco", is(precoProduto));
    }

    @Test
    public void testDeveValidarBuscaDeProdutoAtravesDoNome() {

        buscarProdutosRequest.montarDadosProdutoPorNome(adminToken, nomeProduto)
                .then().statusCode(200)
                .body("produtos[0].nome", is(nomeProduto))
                .body("produtos[0].descricao", is(descricaoProduto))
                .body("produtos[0].quantidade", is((quantidadeProduto)))
                .body("produtos[0].preco", is(precoProduto));
    }

    @Test
    public void testDeveValidarBuscaDeProdutoAtravesDoPreco() {

        buscarProdutosRequest.montarDadosProdutoPorPreco(adminToken, precoProduto)
                .then().statusCode(200)
                .body("produtos[0].nome", is(nomeProduto))
                .body("produtos[0].descricao", is(descricaoProduto))
                .body("produtos[0].quantidade", is((quantidadeProduto)))
                .body("produtos[0].preco", is(precoProduto));
    }
}
