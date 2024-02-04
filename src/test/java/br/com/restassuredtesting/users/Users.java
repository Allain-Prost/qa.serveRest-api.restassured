package br.com.restassuredtesting.users;

import br.com.restassuredtesting.users.models.UsersGeneric;
import br.com.restassuredtesting.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Setter
@Getter
public class Users<T extends UsersGeneric> {

    public UsersGeneric getJson() {
        StringBuilder fileName = new StringBuilder();

        fileName.append(System.getProperty("user.dir") + "/src/test/java/br/com/restassuredtesting/users/users-" + Utils.getEnv().toLowerCase() + ".json");

        UsersGeneric json = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName.toString()), "UTF8"))) {
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(bufferedReader, JsonElement.class);
            element = element.getAsJsonObject().get("users");
            json = gson.fromJson(element, UsersGeneric.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
