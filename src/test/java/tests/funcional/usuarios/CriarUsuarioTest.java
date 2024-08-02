package tests.funcional.usuarios;

import core.controller.usuarios.CriarUsuarioRequest;
import tests.funcional.base.Base;
import data.usuario.UsuarioData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static data.constants.MessagesUtils.*;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class CriarUsuarioTest extends Base {

    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();

    @Test
    public void testDeveValidarCadastroDeUsuarioTipoAdm() {
        UsuarioData usuarioData = UsuarioData.createTC01();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(201)
                .body("", hasKey("_id"))
                .body("_id", instanceOf(String.class))
                .body("_id.length()", greaterThan(0))
                .body("message", is(CADASTRO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarCadastroDeUsuarioTipoCliente() {
        UsuarioData usuarioData = UsuarioData.createTC020();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(201)
                .body("", hasKey("_id"))
                .body("_id", instanceOf(String.class))
                .body("_id.length()", greaterThan(0))
                .body("message", is(CADASTRO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarRegistroDeUsuarioDoTipoAdmSemNome() {
        UsuarioData usuarioData = UsuarioData.createTC04();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(400)
                .body("nome", is(NOME_EM_BRANCO));
    }

    @Test
    public void testDeveValidarRegistroDeUsuarioDoTipoAdmSemEmail() {
        UsuarioData usuarioData = UsuarioData.createTC06();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(400)
                .body("email", is(EMAIL_EM_BRANCO));
    }

    @Test
    public void testDeveValidarRegistroDeUsuarioDoTipoAdmSemSenha() {
        UsuarioData usuarioData = UsuarioData.createTC05();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(400)
                .body("password", is(SENHA_EM_BRANCO));
    }

    @Test
    public void testDeveValidarRegistroUsuarioSemAtributoAdministrador() {
        UsuarioData usuarioData = UsuarioData.createTC07();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(400)
                .body("administrador", is(ADMINISTRADOR_EM_BRANCO));
    }

    @Test
    public void testDeveValidarRegistroDeUsuarioDoTipoAdmComEmailRepetido() {
        UsuarioData usuarioData = UsuarioData.createTC01();
        String emailRepetido = usuarioData.getEmail();

        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        emailRepetido,
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(201);

        UsuarioData usuarioDataNovo = UsuarioData.createTC01();
        postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioDataNovo.getName(),
                        emailRepetido,
                        usuarioDataNovo.getPassword(),
                        usuarioDataNovo.getAdministrador())
                .then().statusCode(400)
                .body("message", is(EMAIL_JA_USADO));
    }
}
