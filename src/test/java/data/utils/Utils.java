package data.utils;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import java.util.Locale;

@Slf4j
public class Utils {

    public static final Faker faker = new Faker(new Locale("pt-BR"));

    public static String getEnv() {
        final String env = System.getProperty("env");
        if (env != null) {
            return env.toUpperCase();
        } else {
            //fail("Ambiente não definido. \n Defina o ambiente conforme exemplo: -Denv:DEV");
            return "DSV";
        }
    }

}
