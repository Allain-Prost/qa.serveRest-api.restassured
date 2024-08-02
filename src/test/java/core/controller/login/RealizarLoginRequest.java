package core.controller.login;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;

@Execution(ExecutionMode.CONCURRENT)
public class RealizarLoginRequest {

    public static Response montarDadosLogin(String email, String password) {

        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);

        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(payload.toString())
                .post("/login");
    }
}
