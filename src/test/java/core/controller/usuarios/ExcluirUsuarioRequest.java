package core.controller.usuarios;

import io.restassured.response.Response;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class ExcluirUsuarioRequest {

    public static Response montarDadosUsuario(String id) {

        return given()
                .header("Content-Type", "application/json")
                .pathParam("_id", id)
                .when()
                .delete("/usuarios/{_id}");
    }
}
