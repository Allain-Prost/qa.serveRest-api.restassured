package data.produto;

import com.github.javafaker.Faker;


public class ProdutoData {
    private static final Faker faker = new Faker();

    private String nameProduto;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
    private String message;

    public String getNameProduto() {
        return nameProduto;
    }

    public Integer getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getMessage() {
        return message;
    }

    // Constructors
    private ProdutoData(String nameProduto, Integer preco, String descricao, Integer quantidade, String message) {
        this.nameProduto = nameProduto;
        this.preco = Integer.valueOf(preco);
        this.descricao = descricao;
        this.quantidade = Integer.valueOf(quantidade);
        this.message = message;
    }


    public static ProdutoData createTC70() {
        return new ProdutoData(
                faker.name().firstName(),
                faker.number().numberBetween(1, 100),
                faker.lorem().paragraph(),
                faker.number().numberBetween(1, 100),
                "Cadastro realizado com sucesso"
        );
    }

    public static ProdutoData createTC71() {
        return new ProdutoData(
                faker.name().firstName(),
                faker.number().numberBetween(1, 100),
                faker.lorem().paragraph(),
                faker.number().numberBetween(1, 100),
                "Já existe produto com esse nome"
        );
    }

}
