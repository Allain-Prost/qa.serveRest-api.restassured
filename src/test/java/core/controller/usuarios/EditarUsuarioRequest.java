package core.controller.usuarios;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class EditarUsuarioRequest {

    public static Response montarDadosUsuario(String id, String nome, String email, String password, String administrador) {

        JSONObject payload = new JSONObject();
        payload.put("nome", nome);
        payload.put("email", email);
        payload.put("password", password);
        payload.put("administrador", administrador);

        return given()
                .header("Content-Type", "application/json")
                .pathParam("_id", id)
                .when()
                .body(payload.toString())
                .put("/usuarios/{_id}");
    }
}
