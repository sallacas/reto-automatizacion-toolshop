package co.reto.toolshop.modelos;

import java.math.BigDecimal;

public record ProductoCapturado(String identificador, String nombre, BigDecimal precioUnitario, int cantidad) {
    public ProductoCapturado {
        if (precioUnitario.signum() < 0 || cantidad < 1) {
            throw new IllegalArgumentException("El precio no puede ser negativo y la cantidad debe ser positiva");
        }
    }
    public BigDecimal subtotal() { return precioUnitario.multiply(BigDecimal.valueOf(cantidad)); }
    public ProductoCapturado conCantidad(int nuevaCantidad) {
        return new ProductoCapturado(identificador, nombre, precioUnitario, nuevaCantidad);
    }
}
