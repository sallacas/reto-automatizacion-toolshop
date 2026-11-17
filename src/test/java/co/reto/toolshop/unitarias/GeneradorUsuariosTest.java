package co.reto.toolshop.unitarias;

import co.reto.toolshop.datos.GeneradorUsuarios;
import java.util.HashSet;
import java.util.UUID;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneradorUsuariosTest {
    @Test public void generaCorreosUuidUnicosYClavesAceptadasPorElLogin() {
        var correos = new HashSet<String>();
        for (int i = 0; i < 100; i++) {
            var usuario = GeneradorUsuarios.nuevo();
            assertThat(correos.add(usuario.correo())).isTrue();
            String identificador = usuario.correo().substring("toolshop.".length(), usuario.correo().indexOf('@'));
            assertThat(UUID.fromString(identificador).version()).isEqualTo(4);
            assertThat(usuario.clave()).hasSizeBetween(8, 40).matches(".*[A-Z].*").matches(".*[a-z].*")
                    .matches(".*[0-9].*").matches(".*[!@#$%].*");
            assertThat(usuario.toString()).doesNotContain(usuario.clave());
        }
    }
}
