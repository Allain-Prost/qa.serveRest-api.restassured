package core.service.produto;

import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class EditarProdutoRequest {

    public static Response montarDadosProduto(String token, String id, String nome, int preco, String descricao, int quantidade) {

        JSONObject payload = new JSONObject();
        payload.put("nome", nome);
        payload.put("preco", preco);
        payload.put("descricao", descricao);
        payload.put("quantidade", quantidade);

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .pathParam("_id", id)
                .when()
                .body(payload.toString())
                .put("/produtos/{_id}");
    }
}
