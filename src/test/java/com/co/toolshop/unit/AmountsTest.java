package com.co.toolshop.unit;

import com.co.toolshop.models.ProductDTO;
import com.co.toolshop.utils.Amounts;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class AmountsTest {
    @Test public void calculaDecimalesSinErroresBinarios() {
        var productos = List.of(new ProductDTO("a", "Producto A", new BigDecimal("0.10"), 3),
                new ProductDTO("b", "Producto B", new BigDecimal("0.20"), 2));
        assertThat(Amounts.total(productos)).isEqualByComparingTo("0.70");
        var modificados = List.of(productos.getFirst().withQuantity(4), productos.get(1));
        assertThat(Amounts.total(modificados)).isEqualByComparingTo("0.80");
    }
    @Test public void interpretaMilesYEspaciosDelFormatoUsd() {
        assertThat(Amounts.fromText("$ 1,234.50\u00a0")).isEqualByComparingTo("1234.50");
        assertThat(Amounts.fromText("$14.15")).isEqualByComparingTo("14.15");
    }
    @Test public void rechazaValoresAusentesOAmbiguos() {
        assertThatThrownBy(() -> Amounts.fromText("—")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Amounts.fromText("$1.234")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Amounts.fromText("$1,23")).isInstanceOf(IllegalArgumentException.class);
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
