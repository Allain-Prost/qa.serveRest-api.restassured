package tests.funcional.login;

import core.controller.login.RealizarLoginRequest;
import core.controller.usuarios.CriarUsuarioRequest;
import org.junit.jupiter.api.BeforeAll;
import tests.funcional.base.Base;
import data.constants.MessagesUtils;
import data.login.LoginData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class RealizarLoginTest extends Base {

    static RealizarLoginRequest realizarLoginRequest = new RealizarLoginRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static LoginData loginAdmin;
    static LoginData loginCliente;

    @BeforeAll
    public static void setUp() {
        loginAdmin = LoginData.createTC060();
        loginCliente = LoginData.createTC062();

        postCriarUsuarioRequest.montarDadosUsuario(
                loginAdmin.name, loginAdmin.email, loginAdmin.password, loginAdmin.administrador
        ).then().statusCode(201);
        postCriarUsuarioRequest.montarDadosUsuario(
                loginCliente.name, loginCliente.email, loginCliente.password, loginCliente.administrador
        ).then().statusCode(201);
    }

    @Test
    public void testDeveRealizarLoginComUsuarioDoTipoAdm() {

        realizarLoginRequest.montarDadosLogin(loginAdmin.email, loginAdmin.password)
                .then().statusCode(200)
                .body("", hasKey("authorization"))
                .body("authorization", instanceOf(String.class))
                .body("authorization", not(empty()))
                .body("message", Matchers.is(MessagesUtils.LOGIN_COM_SUCESSO));
    }

    @Test
    public void testDeveRealizarLoginComUsuarioDoTipoCliente() {

        realizarLoginRequest.montarDadosLogin(loginCliente.email, loginCliente.password)
                .then().statusCode(200)
                .body("", hasKey("authorization"))
                .body("authorization", instanceOf(String.class))
                .body("authorization", not(empty()))
                .body("message", Matchers.is(MessagesUtils.LOGIN_COM_SUCESSO));
    }

    @Test
    public void testDeveRealizarLoginComCredenciaisInvalidasParaUsuarioDoTipoAdm() {

        realizarLoginRequest.montarDadosLogin(loginAdmin.email, MessagesUtils.PASSWORD_INVALIDO)
                .then().statusCode(401)
                .body("message", Matchers.is(MessagesUtils.EMAIL_SENHA_INVALIDOS));
    }


    @Test
    public void testDeveRealizarLoginComCredenciaisInvalidasParaUsuarioDoTipoCliente() {

        realizarLoginRequest.montarDadosLogin(loginCliente.email, MessagesUtils.PASSWORD_INVALIDO)
                .then().statusCode(401)
                .body("message", Matchers.is(MessagesUtils.EMAIL_SENHA_INVALIDOS));
    }
}
