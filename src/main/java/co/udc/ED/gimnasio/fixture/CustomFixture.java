package co.udc.ED.gimnasio.fixture;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.Pila;

/**
 * Implemenacion de singleton para listas, colas y pilas del sistema
 */
public class CustomFixture {

    public static final Lista CLASES = new Lista();
    public static final Lista INSTRUCTORES = new Lista();
    public static final Cola INSCRIPCIONES = new Cola();

    public static final Lista CLASES_ELIMINADAS = new Lista();
    public static final Lista INSTRUCTORES_ELIMINADOS = new Lista();
    public static final Lista INSCRIPCIONES_CANCELADAS = new Lista();

    public static final Pila OPERACIONES = new Pila();
}
