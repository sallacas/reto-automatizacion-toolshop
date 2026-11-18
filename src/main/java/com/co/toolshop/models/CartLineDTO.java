package com.co.toolshop.models;

import java.math.BigDecimal;

public record CartLineDTO(String name, BigDecimal price, int quantity, BigDecimal subtotal) { }
