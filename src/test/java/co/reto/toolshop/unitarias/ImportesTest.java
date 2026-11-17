package co.reto.toolshop.unitarias;

import co.reto.toolshop.modelos.ProductoCapturado;
import co.reto.toolshop.utilidades.Importes;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class ImportesTest {
    @Test public void calculaDecimalesSinErroresBinarios() {
        var productos = List.of(new ProductoCapturado("a", "Producto A", new BigDecimal("0.10"), 3),
                new ProductoCapturado("b", "Producto B", new BigDecimal("0.20"), 2));
        assertThat(Importes.total(productos)).isEqualByComparingTo("0.70");
        var modificados = List.of(productos.getFirst().conCantidad(4), productos.get(1));
        assertThat(Importes.total(modificados)).isEqualByComparingTo("0.80");
    }
    @Test public void interpretaMilesYEspaciosDelFormatoUsd() {
        assertThat(Importes.desdeTexto("$ 1,234.50\u00a0")).isEqualByComparingTo("1234.50");
        assertThat(Importes.desdeTexto("$14.15")).isEqualByComparingTo("14.15");
    }
    @Test public void rechazaValoresAusentesOAmbiguos() {
        assertThatThrownBy(() -> Importes.desdeTexto("—")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Importes.desdeTexto("$1.234")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Importes.desdeTexto("$1,23")).isInstanceOf(IllegalArgumentException.class);
    }
    @Test public void comparaPreciosConDistintasEscalasYPermiteEmpates() {
        assertThat(List.of(new BigDecimal("9.17"), new BigDecimal("12.0"), new BigDecimal("12.00")))
                .isSortedAccordingTo(BigDecimal::compareTo);
        assertThat(new BigDecimal("12.0")).isEqualByComparingTo("12.00");
    }
    @Test public void rechazaUnOrdenLexicograficoQueNoEsNumerico() {
        var precios = List.of(new BigDecimal("10.00"), new BigDecimal("2.00"));
        assertThatThrownBy(() -> assertThat(precios).isSortedAccordingTo(BigDecimal::compareTo))
                .isInstanceOf(AssertionError.class);
    }
}
