package com.co.toolshop.models;

import java.math.BigDecimal;

public record ProductDTO(String id, String name, BigDecimal unitPrice, int quantity) {
    public ProductDTO {
        if (unitPrice.signum() < 0 || quantity < 1) {
            throw new IllegalArgumentException("El precio no puede ser negativo y la cantidad debe ser positiva");
        }
    }
    public BigDecimal subtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
    public ProductDTO withQuantity(int nuevaCantidad) {
        return new ProductDTO(id, name, unitPrice, nuevaCantidad);
    }
}
