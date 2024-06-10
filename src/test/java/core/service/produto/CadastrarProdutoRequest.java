package core.service.produto;

import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class CadastrarProdutoRequest {

    public static Response montarDadosProduto(String token, String nome, Integer preco, String descricao, Integer quantidade) {

        JSONObject payload = new JSONObject();
        payload.put("nome", nome);
        payload.put("preco", preco);
        payload.put("descricao", descricao);
        payload.put("quantidade", quantidade);

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .body(payload.toString())
                .post("/produtos");
    }
}
