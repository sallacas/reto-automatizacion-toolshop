package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class CatalogPage {
    private CatalogPage() { }
    public static final Target LST_PRODUCTS = css("tarjetas de productos", "a.card[data-test^='product-']");
    public static final Target LBL_PRODUCT_NAME = test("nombre de tarjeta", "product-name");
    public static final Target LBL_PRODUCT_PRICE = test("precio de tarjeta", "product-price");
    public static final Target LBL_OUT_OF_STOCK = test("producto agotado", "out-of-stock");
    public static final Target LBL_PROMOTIONAL_PRICE = test("precio promocional", "product-discount-price");
    public static final Target LBL_ACTIVE_CO2_RATING = css("calificación CO2 activa", "[data-test='co2-rating-badge'] .active");
    public static final Target SELECT_SORT = test("orden del catálogo", "sort");
    public static final Target LST_SUBCATEGORIES = css("subcategorías", "ul input[name='category_id'][data-test^='category-']");
    public static final Target BTN_NEXT_PAGE = test("página siguiente", "pagination-next");
    public static final Target BTN_NEXT_PAGE_ENABLED = css("página siguiente habilitada", "li:not(.disabled) > [data-test='pagination-next']");
    public static final Target LBL_SORT_COMPLETED = test("ordenamiento terminado", "sorting_completed");
    public static final Target LBL_FILTER_COMPLETED = test("filtrado terminado", "filter_completed");
    public static final By BY_PARENT_LABEL = By.xpath("..");
    public static final Target LBL_ACTIVE_PAGE = css("página actual", "app-pagination li.active button");
    public static final Target LBL_NO_RESULTS = test("sin resultados", "no-results");
    public static final Target LBL_DETAIL_NAME = css("nombre del producto", "h1[data-test='product-name']");
    public static final Target LBL_UNIT_PRICE = test("precio unitario", "unit-price");
    public static final Target LBL_DETAIL_CATEGORY = css("categoría real del producto", "[aria-label='category']");
    public static final Target TXT_QUANTITY = test("cantidad a agregar", "quantity");
    public static final Target BTN_ADD_TO_CART = test("añadir al carrito", "add-to-cart");
}
