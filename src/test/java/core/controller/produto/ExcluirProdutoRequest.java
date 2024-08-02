package core.controller.produto;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ExcluirProdutoRequest {

    public static Response montarDadosProdutoPorId(String token, String id) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .pathParam("_id", id)
                .delete("/produtos/{_id}");
    }
}
