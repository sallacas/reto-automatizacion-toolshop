package com.co.toolshop.unit;

import com.co.toolshop.utils.data.UserGenerator;
import java.util.HashSet;
import java.util.UUID;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserGeneratorTest {
    @Test public void generaCorreosUuidUnicosYClavesAceptadasPorElLogin() {
        var correos = new HashSet<String>();
        for (int i = 0; i < 100; i++) {
            var usuario = UserGenerator.newUser();
            assertThat(correos.add(usuario.email())).isTrue();
            String identificador = usuario.email().substring("toolshop.".length(), usuario.email().indexOf('@'));
            assertThat(UUID.fromString(identificador).version()).isEqualTo(4);
            assertThat(usuario.password()).hasSizeBetween(8, 40).matches(".*[A-Z].*").matches(".*[a-z].*")
                    .matches(".*[0-9].*").matches(".*[!@#$%].*");
            assertThat(usuario.toString()).doesNotContain(usuario.password());
        }
    }
}
