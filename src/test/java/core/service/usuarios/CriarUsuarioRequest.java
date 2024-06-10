package core.service.usuarios;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class CriarUsuarioRequest {

    public static Response montarDadosUsuario(String nome, String email, String password, String administrador) {

        JSONObject payload = new JSONObject();
        payload.put("nome", nome);
        payload.put("email", email);
        payload.put("password", password);
        payload.put("administrador", administrador);

        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(payload.toString())
                .post("/usuarios");
    }
}
