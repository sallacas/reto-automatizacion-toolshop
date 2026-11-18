package com.co.toolshop.utils;

import com.co.toolshop.models.ProductDTO;
import java.math.BigDecimal;
import java.util.List;

public final class Amounts {
    private Amounts() { }
    // Toolshop presenta importes USD con punto decimal, incluso con la interfaz en español.
    public static BigDecimal fromText(String texto) {
        String limpio = texto.replace("$", "").replace("USD", "")
                .replaceAll("\\s|\\u00a0", "");
        if (!limpio.matches("-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("Importe USD no reconocido: " + texto);
        }
        return new BigDecimal(limpio.replace(",", ""));
    }
    public static BigDecimal total(List<ProductDTO> productos) {
        return productos.stream().map(ProductDTO::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
