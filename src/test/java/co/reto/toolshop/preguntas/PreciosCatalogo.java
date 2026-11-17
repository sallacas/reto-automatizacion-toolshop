package co.reto.toolshop.preguntas;

import co.reto.toolshop.ui.Catalogo;
import co.reto.toolshop.utilidades.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.screenplay.*;

public class PreciosCatalogo implements Question<List<BigDecimal>> {
    public static PreciosCatalogo deLaPagina() { return new PreciosCatalogo(); }
    public List<BigDecimal> answeredBy(Actor actor) {
        Esperas.visible(actor, Catalogo.PRODUCTOS);
        return Esperas.todos(actor, Catalogo.PRODUCTOS).stream()
                .map(tarjeta -> Importes.desdeTexto(Esperas.dentro(tarjeta, Catalogo.PRECIO_TARJETA).getText())).toList();
    }
}
