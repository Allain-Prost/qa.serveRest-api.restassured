package tests.funcional.usuarios;

import core.service.usuarios.BuscarUsuariosCadastradosRequest;
import core.service.usuarios.CriarUsuarioRequest;
import data.usuario.UsuarioData;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import tests.funcional.base.Base;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class BuscarUsuariosCadastradosTest extends Base {

    static BuscarUsuariosCadastradosRequest listarUsuariosCadastrados = new BuscarUsuariosCadastradosRequest();
    static CriarUsuarioRequest postCriarUsuarioRequest = new CriarUsuarioRequest();
    static UsuarioData usuarioData;
    static String id;

    @BeforeAll
    public static void setUp() {
        // Gerando dados do usuário utilizando a classe UsuarioData
        usuarioData = UsuarioData.createTC01();

        // Criando usuário com dados gerados
        id = postCriarUsuarioRequest.montarDadosUsuario(
                        usuarioData.getName(),
                        usuarioData.getEmail(),
                        usuarioData.getPassword(),
                        usuarioData.getAdministrador())
                .then().statusCode(201)
                .extract().path("_id");
    }

    @Test
    public void testDeveValidarBuscaDeUsuarioDoTipoClienteAtravesDoId() {
        listarUsuariosCadastrados.buscarUsuarioPorId(id)
                .then().statusCode(200)
                .body("usuarios[0].nome", is(usuarioData.getName()))
                .body("usuarios[0].email", is(usuarioData.getEmail()))
                .body("usuarios[0].password", is(usuarioData.getPassword()))
                .body("usuarios[0].administrador", is(usuarioData.getAdministrador()))
                .body("usuarios[0]", hasKey("_id"))
                .body("usuarios[0]._id", instanceOf(String.class))
                .body("usuarios[0]._id.length()", greaterThan(0));
    }

    @Test
    public void testDeveValidarBuscaDeUsuarioDoTipoClienteAtravesDoNome() {
        listarUsuariosCadastrados.buscarUsuarioPorNome(usuarioData.getName())
                .then().statusCode(200)
                .body("usuarios[0].nome", is(usuarioData.getName()))
                .body("usuarios[0].email", is(usuarioData.getEmail()))
                .body("usuarios[0].password", is(usuarioData.getPassword()))
                .body("usuarios[0].administrador", is(usuarioData.getAdministrador()))
                .body("usuarios[0]", hasKey("_id"))
                .body("usuarios[0]._id", instanceOf(String.class))
                .body("usuarios[0]._id.length()", greaterThan(0));
    }

    @Test
    public void testDeveValidarBuscaDeUsuarioDoTipoClienteAtravesDoEmail() {
        listarUsuariosCadastrados.buscarUsuarioPorEmail(usuarioData.getEmail())
                .then().statusCode(200)
                .body("usuarios[0].nome", is(usuarioData.getName()))
                .body("usuarios[0].email", is(usuarioData.getEmail()))
                .body("usuarios[0].password", is(usuarioData.getPassword()))
                .body("usuarios[0].administrador", is(usuarioData.getAdministrador()))
                .body("usuarios[0]", hasKey("_id"))
                .body("usuarios[0]._id", instanceOf(String.class))
                .body("usuarios[0]._id.length()", greaterThan(0));
    }

    @Test
    public void testDeveValidarBuscaDeUsuarioDoTipoClienteAtravesDoPassword() {
        listarUsuariosCadastrados.buscarUsuarioPorPassword(usuarioData.getPassword())
                .then().statusCode(200)
                .body("usuarios[0].nome", is(usuarioData.getName()))
                .body("usuarios[0].email", is(usuarioData.getEmail()))
                .body("usuarios[0].password", is(usuarioData.getPassword()))
                .body("usuarios[0].administrador", is(usuarioData.getAdministrador()))
                .body("usuarios[0]", hasKey("_id"))
                .body("usuarios[0]._id", instanceOf(String.class))
                .body("usuarios[0]._id.length()", greaterThan(0));
    }
}
