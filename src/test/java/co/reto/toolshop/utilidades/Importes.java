package co.reto.toolshop.utilidades;

import co.reto.toolshop.modelos.ProductoCapturado;
import java.math.BigDecimal;
import java.util.List;

public final class Importes {
    private Importes() { }
    // Toolshop presenta importes USD con punto decimal, incluso con la interfaz en español.
    public static BigDecimal desdeTexto(String texto) {
        String limpio = texto.replace("$", "").replace("USD", "")
                .replaceAll("\\s|\\u00a0", "");
        if (!limpio.matches("-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("Importe USD no reconocido: " + texto);
        }
        return new BigDecimal(limpio.replace(",", ""));
    }
    public static BigDecimal total(List<ProductoCapturado> productos) {
        return productos.stream().map(ProductoCapturado::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
