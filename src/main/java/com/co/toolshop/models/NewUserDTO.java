package com.co.toolshop.models;

public record NewUserDTO(String name, String lastName, String dateOfBirth, String email,
                           String password, String phone, String country, String postalCode,
                           String houseNumber, String street, String city, String state) {
    @Override public String toString() { return "Usuario de prueba: " + email; }
}
