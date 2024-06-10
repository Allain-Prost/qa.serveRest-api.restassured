package data.login;

import com.github.javafaker.Faker;

public class LoginData {

    private static final Faker faker = new Faker();

    public String name;
    public String email;
    public String password;
    public String administrador;
    public String message;
    public String messageLogin;

    public LoginData(String name, String email, String password, String administrador, String message, String messageLogin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
        this.message = message;
        this.messageLogin = messageLogin;
    }

    public static LoginData createTC060() {
        return new LoginData(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                "true",
                "Cadastro realizado com sucesso",
                "Login realizado com sucesso"
        );
    }

    public static LoginData createTC061() {
        LoginData tc060 = createTC060();
        return new LoginData(
                tc060.name,
                faker.internet().emailAddress(),
                "invalido",
                tc060.administrador,
                tc060.message,
                "Email e/ou senha inválidos"
        );
    }

    public static LoginData createTC062() {
        return new LoginData(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                "false",
                "Cadastro realizado com sucesso",
                "Login realizado com sucesso"
        );
    }

    public static LoginData createTC063() {
        LoginData tc062 = createTC062();
        return new LoginData(
                tc062.name,
                faker.internet().emailAddress(),
                "invalido",
                tc062.administrador,
                tc062.message,
                "Email e/ou senha inválidos"
        );
    }
}
