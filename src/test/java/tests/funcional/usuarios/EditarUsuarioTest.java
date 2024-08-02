package tests.funcional.usuarios;

import core.controller.usuarios.CriarUsuarioRequest;
import core.controller.usuarios.EditarUsuarioRequest;
import tests.funcional.base.Base;
import data.usuario.UsuarioData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static data.constants.MessagesUtils.EDICAO_COM_SUCESSO;
import static org.hamcrest.Matchers.is;

@Execution(ExecutionMode.CONCURRENT)
public class EditarUsuarioTest extends Base {

    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static EditarUsuarioRequest putEditarUsuarioRequest = new EditarUsuarioRequest();
    static String id;

    @BeforeAll
    public static void setUp() {
        UsuarioData usuarioData = UsuarioData.createTC01();
        id = postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(201)
                .extract().path("_id");
    }

    @Test
    public void testDeveValidarEdicaoDoEmailDeUsuarioDoTipoClienteAtravesDoId() {
        UsuarioData usuarioData = UsuarioData.createTC051();

        putEditarUsuarioRequest.montarDadosUsuario(
                        id,
                        usuarioData.getName(),
                        usuarioData.getEmail(), // Novo email
                        usuarioData.getPassword(),
                        "false")
                .then().statusCode(200)
                .body("message", is(EDICAO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarEdicaoDoNomeDeUsuarioDoTipoClienteAtravesDoId() {
        UsuarioData usuarioData = UsuarioData.createTC050();

        putEditarUsuarioRequest.montarDadosUsuario(
                        id,
                        usuarioData.getName(), // Novo nome
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        "false")
                .then().statusCode(200)
                .body("message", is(EDICAO_COM_SUCESSO));
    }
}
