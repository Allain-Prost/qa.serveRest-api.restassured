package br.com.restassuredtesting.users.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUsersGeneric extends UsersGeneric {

    @SerializedName("nome")
    @Expose
    public String nome;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("admin")
    @Expose
    public String admin;

}
