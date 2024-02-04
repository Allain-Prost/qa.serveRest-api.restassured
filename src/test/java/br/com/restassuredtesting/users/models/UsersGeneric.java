package br.com.restassuredtesting.users.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersGeneric {

    @Expose
    @SerializedName("usuarioAdmin")
    protected BaseUsersGeneric usuarioAdmin;

    @Expose
    @SerializedName("usuarioCliente")
    protected BaseUsersGeneric usuarioCliente;

}