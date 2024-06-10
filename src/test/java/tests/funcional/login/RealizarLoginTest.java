package tests.funcional.login;

import core.service.login.RealizarLoginRequest;
import core.service.usuarios.CriarUsuarioRequest;
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

    public void criarUsuario(LoginData loginData) {
        postCriarUsuarioRequest.montarDadosUsuario(
                loginData.name, loginData.email, loginData.password, loginData.administrador
        ).then().statusCode(201);
    }

    @Test
    public void testDeveRealizarLoginComUsuarioDoTipoAdm() {
        LoginData loginData = LoginData.createTC060();
        criarUsuario(loginData);
        realizarLoginRequest.montarDadosLogin(loginData.email, loginData.password)
                .then().statusCode(200)
                .body("", hasKey("authorization"))
                .body("authorization", instanceOf(String.class))
                .body("authorization", not(empty()))
                .body("message", Matchers.is(MessagesUtils.LOGIN_COM_SUCESSO));
    }

    @Test
    public void testDeveRealizarLoginComUsuarioDoTipoCliente() {
        LoginData loginData = LoginData.createTC062();
        criarUsuario(loginData);
        realizarLoginRequest.montarDadosLogin(loginData.email, loginData.password)
                .then().statusCode(200)
                .body("", hasKey("authorization"))
                .body("authorization", instanceOf(String.class))
                .body("authorization", not(empty()))
                .body("message", Matchers.is(MessagesUtils.LOGIN_COM_SUCESSO));
    }

    @Test
    public void testDeveRealizarLoginComCredenciaisInvalidasParaUsuarioDoTipoAdm() {
        LoginData loginData = LoginData.createTC060();
        criarUsuario(loginData);
        realizarLoginRequest.montarDadosLogin(loginData.email, MessagesUtils.PASSWORD_INVALIDO)
                .then().statusCode(401)
                .body("message", Matchers.is(MessagesUtils.EMAIL_SENHA_INVALIDOS));
    }

    @Test
    public void testDeveRealizarLoginComCredenciaisInvalidasParaUsuarioDoTipoCliente() {
        LoginData loginData = LoginData.createTC062();
        criarUsuario(loginData);
        realizarLoginRequest.montarDadosLogin(loginData.email, MessagesUtils.PASSWORD_INVALIDO)
                .then().statusCode(401)
                .body("message", Matchers.is(MessagesUtils.EMAIL_SENHA_INVALIDOS));
    }
}
