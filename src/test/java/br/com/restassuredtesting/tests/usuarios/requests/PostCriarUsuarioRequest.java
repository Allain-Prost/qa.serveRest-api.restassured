package br.com.restassuredtesting.tests.usuarios.requests;

import com.sun.org.glassfish.gmbal.Description;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;

public class PostCriarUsuarioRequest {

    @Description("Solicitar criação de usuário")
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
