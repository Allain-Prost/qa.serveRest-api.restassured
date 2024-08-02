package tests.funcional.usuarios;

import core.controller.usuarios.CriarUsuarioRequest;
import core.controller.usuarios.ExcluirUsuarioRequest;
import data.usuario.UsuarioData;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import tests.funcional.base.Base;
import data.constants.MessagesUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Execution(ExecutionMode.CONCURRENT)
public class ExcluirUsuarioTest extends Base {

    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static ExcluirUsuarioRequest excluirUsuarioRequest = new ExcluirUsuarioRequest();
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
    public void testDeveValidarExclusaoDeUsuarioDoTipoAdmAtravesDoId() {
        excluirUsuarioRequest.montarDadosUsuario(id)
                .then().statusCode(200)
                .body("message", Matchers.is(MessagesUtils.EXCLUSAO_COM_SUCESSO));
    }

    @Test
    public void testDeveValidarExclusaoDeUsuarioComIdInexistente() {
        String fakeId = "1234567890abcdef12345678"; // Exemplo de ID falso que não deve existir
        excluirUsuarioRequest.montarDadosUsuario(fakeId)
                .then().statusCode(200)
                .body("message", Matchers.is(MessagesUtils.NENHUM_REGISTRO_EXCLUIDO));
    }
}
