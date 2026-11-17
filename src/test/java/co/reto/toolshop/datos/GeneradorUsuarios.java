package co.reto.toolshop.datos;

import co.reto.toolshop.modelos.UsuarioPrueba;
import java.util.UUID;

public final class GeneradorUsuarios {
    private GeneradorUsuarios() { }
    public static UsuarioPrueba nuevo() {
        String identificador = UUID.randomUUID().toString();
        long numero = Integer.toUnsignedLong(identificador.hashCode());
        return new UsuarioPrueba("Cliente", "Prueba" + identificador.substring(0, 8), "1994-05-15",
                "toolshop." + identificador + "@example.com", "Ts!9aA" + UUID.randomUUID(),
                "3" + String.format("%09d", numero % 1_000_000_000L), "CO", "110111",
                Long.toString(10 + numero % 900), "Calle de Pruebas " + numero,
                "Bogota", "Cundinamarca");
    }
}
