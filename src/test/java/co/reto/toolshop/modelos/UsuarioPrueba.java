package co.reto.toolshop.modelos;

public record UsuarioPrueba(String nombre, String apellido, String nacimiento, String correo,
                           String clave, String telefono, String pais, String codigoPostal,
                           String numero, String calle, String ciudad, String estado) {
    @Override public String toString() { return "Usuario de prueba: " + correo; }
}
