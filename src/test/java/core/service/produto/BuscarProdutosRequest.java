package core.service.produto;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BuscarProdutosRequest {

    public static Response montarDadosProduto(String token, String id, String nome, Integer preco, String descricao,
                                              Integer quantidade) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .queryParam("_id", id)
                .queryParam("nome", nome)
                .queryParam("preco", preco)
                .queryParam("descricao", descricao)
                .queryParam("quantidade", quantidade)
                .get("/produtos");
    }

    public static Response montarDadosProdutoPorId(String token, String id) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .queryParam("_id", id)
                .get("/produtos");
    }

    public static Response montarDadosProdutoPorNome(String token, String nome) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .queryParam("nome", nome)
                .get("/produtos");
    }

    public static Response montarDadosProdutoPorPreco(String token, Integer preco) {

        return given()
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .queryParam("preco", preco)
                .get("/produtos");
    }
}
