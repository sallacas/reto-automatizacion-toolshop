package com.co.toolshop.utils.data;

import com.co.toolshop.models.NewUserDTO;
import java.util.UUID;

public final class UserGenerator {
    private UserGenerator() { }
    public static NewUserDTO newUser() {
        String identificador = UUID.randomUUID().toString();
        long numero = Integer.toUnsignedLong(identificador.hashCode());
        return new NewUserDTO("Cliente", "Prueba" + identificador.substring(0, 8), "1994-05-15",
                "toolshop." + identificador + "@example.com", "Ts!9aA" + UUID.randomUUID().toString().substring(0, 12),
                "3" + String.format("%09d", numero % 1_000_000_000L), "CO", "110111",
                Long.toString(10 + numero % 900), "Calle de Pruebas " + numero,
                "Bogota", "Cundinamarca");
    }
}
