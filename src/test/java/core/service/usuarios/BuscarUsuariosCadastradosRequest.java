package core.service.usuarios;

import io.restassured.response.Response;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;


public class BuscarUsuariosCadastradosRequest {

    private static Response montarDadosUsuario(String param, String value) {
        return given()
                .header("Content-Type", "application/json")
                .queryParam(param, value)
                .when()
                .get("/usuarios/");
    }

    public static Response buscarUsuarioPorPassword(String password) {
        return montarDadosUsuario("password", password);
    }

    public static Response buscarUsuarioPorId(String id) {
        return montarDadosUsuario("_id", id);
    }

    public static Response buscarUsuarioPorNome(String nome) {
        return montarDadosUsuario("nome", nome);
    }

    public static Response buscarUsuarioPorEmail(String email) {
        return montarDadosUsuario("email", email);
    }
}
