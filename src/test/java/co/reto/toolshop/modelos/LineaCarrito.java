package co.reto.toolshop.modelos;

import java.math.BigDecimal;

public record LineaCarrito(String nombre, BigDecimal precio, int cantidad, BigDecimal subtotal) { }
