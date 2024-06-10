package data.usuario;

import com.github.javafaker.Faker;

public class UsuarioData {
    private static final Faker faker = new Faker();

    public static UsuarioData createTC01() {
        return new UsuarioData(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                "true",
                "Cadastro realizado com sucesso"
        );
    }

    public static UsuarioData createTC02() {
        return createTC01();
    }

    public static UsuarioData createTC03() {
        String newName = faker.name().firstName();
        String newEmail = faker.internet().emailAddress();
        return new UsuarioData(
                newName,
                newName, // newName for newName field
                faker.internet().emailAddress(),
                newEmail,
                faker.internet().password(),
                "true",
                "Este email já está sendo usado"
        );
    }

    public static UsuarioData createTC04() {
        UsuarioData tc01 = createTC01();
        tc01.setName(""); // Empty name
        tc01.setMessage("nome não pode ficar em branco");
        return tc01;
    }

    public static UsuarioData createTC05() {
        UsuarioData tc01 = createTC01();
        tc01.setEmail(faker.internet().emailAddress()); // New email
        tc01.setPassword(""); // Empty password
        tc01.setMessage("password não pode ficar em branco");
        return tc01;
    }

    public static UsuarioData createTC06() {
        UsuarioData tc01 = createTC01();
        tc01.setEmail(""); // Empty email
        tc01.setMessage("email não pode ficar em branco");
        return tc01;
    }

    public static UsuarioData createTC07() {
        UsuarioData tc01 = createTC01();
        tc01.setEmail(faker.internet().emailAddress()); // New email
        tc01.setAdministrador(""); // Empty administrator
        tc01.setMessage("administrador deve ser 'true' ou 'false'");
        return tc01;
    }

    public static UsuarioData createTC020() {
        return new UsuarioData(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                "false",
                null // message is null for this case
        );
    }

    public static UsuarioData createTC021() {
        return createTC020();
    }

    public static UsuarioData createTC022() {
        return createTC020();
    }

    public static UsuarioData createTC023() {
        return createTC020();
    }

    public static UsuarioData createTC040() {
        return new UsuarioData(
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.internet().password(),
                "true",
                "Registro excluído com sucesso"
        );
    }

    public static UsuarioData createTC041() {
        return new UsuarioData(
                null, // name is null for this case
                null, // email is null for this case
                null, // password is null for this case
                null, // administrador is null for this case
                "Nenhum registro excluído"
        );
    }

    public static UsuarioData createTC050() {
        String newName = faker.name().firstName();
        String newEmail = faker.internet().emailAddress();
        return new UsuarioData(
                newName,
                newName, // newName for newName field
                faker.internet().emailAddress(),
                newEmail,
                faker.internet().password(),
                "false",
                "Registro alterado com sucesso"
        );
    }

    public static UsuarioData createTC051() {
        UsuarioData tc050 = createTC050();
        tc050.setEmail(faker.internet().emailAddress()); // New email
        tc050.setNewEmail(faker.internet().emailAddress()); // New email
        tc050.setMessage("Registro alterado com sucesso");
        return tc050;
    }

    // Fields
    private String name;
    private String newName;
    private String email;
    private String newEmail;
    private String password;
    private String administrador;
    private String message;

    // Constructors
    private UsuarioData(String name, String email, String password, String administrador, String message) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
        this.message = message;
    }

    private UsuarioData(String name, String newName, String email, String newEmail, String password, String administrador, String message) {
        this(name, email, password, administrador, message);
        this.newName = newName;
        this.newEmail = newEmail;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
