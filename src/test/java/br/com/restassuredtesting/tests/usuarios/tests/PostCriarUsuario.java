package br.com.restassuredtesting.tests.usuarios.tests;

import br.com.restassuredtesting.tests.base.tests.Base;
import br.com.restassuredtesting.tests.usuarios.requests.PostCriarUsuarioRequest;
import br.com.restassuredtesting.users.Users;
import br.com.restassuredtesting.users.models.UsersGeneric;
import br.com.restassuredtesting.users.models.UsersModel;
import com.github.javafaker.Faker;
import com.sun.org.glassfish.gmbal.Description;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static br.com.restassuredtesting.constants.MessagesUtils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostCriarUsuario extends Base {

    static PostCriarUsuarioRequest postCriarUsuarioRequest = new PostCriarUsuarioRequest();
    public static final Faker faker = new Faker(new Locale("pt-BR"));
    static UsersGeneric users = new Users<UsersModel>().getJson();

    @BeforeClass
    public static void setUp() {
        postCriarUsuarioRequest.montarDadosUsuario(
                users.getUsuarioCliente().nome,
                faker.internet().emailAddress(),
                users.getUsuarioCliente().password,
                users.getUsuarioCliente().admin);
    }

    @Test
    @Description("Deve retornar sucesso ao criar uma sucesso do tipo administrador")
    public void testValidarCriacaoDeUsuarioTipoAdmin() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioAdmin().nome,
                        faker.internet().emailAddress(),
                        users.getUsuarioAdmin().password,
                        users.getUsuarioAdmin().admin)
                .then().statusCode(201)
                .body("_id", notNullValue())
                .body("message", is(CADASTRO_COM_SUCESSO));
    }

    @Test
    @Description("Deve retornar sucesso ao criar uma sucesso do tipo cliente")
    public void testValidarCriacaoDeUsuarioTipoCliente() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        faker.internet().emailAddress(),
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(201)
                .body("_id", notNullValue())
                .body("message", is(CADASTRO_COM_SUCESSO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário sem informar nome")
    public void testValidarErroAoTentarCriacaoUsuarioSemInformarNome() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        "",
                        faker.internet().emailAddress(),
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(400)
                .body("nome", is(NOME_EM_BRANCO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário sem informar email")
    public void testValidarErroAoTentarCriacaoUsuarioSemInformarEmail() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        "",
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(400)
                .body("email", is(EMAIL_EM_BRANCO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário com email inválido")
    public void testValidarErroAoTentarCriacaoUsuarioComEmailInvalido() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        faker.name().firstName(),
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(400)
                .body("email", is(EMAIL_INVALIDO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário sem informar senha")
    public void testValidarErroAoTentarCriacaoUsuarioSemInformarSenha() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        faker.internet().emailAddress(),
                        "",
                        users.getUsuarioCliente().admin)
                .then().statusCode(400)
                .body("password", is(SENHA_EM_BRANCO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário sem informar admin")
    public void testValidarErroAoTentarCriacaoUsuarioSemInformarAdmin() {

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        faker.internet().emailAddress(),
                        users.getUsuarioCliente().password,
                        "")
                .then().statusCode(400)
                .body("administrador", is(ADMINISTRADOR_EM_BRANCO));
    }

    @Test
    @Description("Deve retornar erro ao tentar criar usuário duplicado")
    public void testValidarErroAoTentarCriacaoUsuarioDuplicado() {

        String email = faker.internet().emailAddress();

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        email,
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(201);

        postCriarUsuarioRequest.montarDadosUsuario(
                        users.getUsuarioCliente().nome,
                        email,
                        users.getUsuarioCliente().password,
                        users.getUsuarioCliente().admin)
                .then().statusCode(400)
                .body("message", is(EMAIL_JA_USADO));
    }
}
