package core.controller.carrinho;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CadastrarCarrinhoRequest {

    public static Response montarDadosProduto(String token, String idProduto, Integer quantidade) {

        // Criar o JSON do produto individual
        JSONObject produtoJson = new JSONObject();
        produtoJson.put("idProduto", idProduto);
        produtoJson.put("quantidade", quantidade);

        // Criar o JSON array de produtos
        JSONArray produtosArray = new JSONArray();
        produtosArray.put(produtoJson);

        // Criar o JSON payload final
        JSONObject payload = new JSONObject();
        payload.put("produtos", produtosArray);

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .body(payload.toString())
                .post("/carrinhos");
    }
}
