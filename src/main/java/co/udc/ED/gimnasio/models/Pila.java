package co.udc.ED.gimnasio.models;

/**
 * Clase Pila - Implementación simple de una pila LIFO (Last In First Out).
 *
 * Esta clase proporciona operaciones básicas de pila usando nodos enlazados:
 * - apilar: insertar un elemento en la cima
 * - desapilar: eliminar y retornar el elemento de la cima
 * - peek: consultar el elemento de la cima sin eliminarlo
 * - utilidades: obtener tamaño, comprobar si está vacía, búsqueda, limpieza y visualización
 *
 * Comportamiento importante:
 * - Los métodos que retornan elementos devuelven {@code null} cuando la pila está vacía
 *   y también imprimen un mensaje informativo en consola.
 * - Las búsquedas y comprobaciones usan {@code equals} para comparar objetos; por tanto,
 *   los objetos almacenados deben implementar correctamente {@code equals} para una
 *   búsqueda fiable.
 *
 * @author Kevinn
 * @version 1.0
 * @since 1.0
 * @see Nodo
 */
public class Pila {
    /**
     * Referencia al nodo que está en la cima de la pila (tope).
     * El elemento contenido en el nodo tope es el primero en salir al desapilar.
     */
    private Nodo tope;

    /**
     * Número de elementos contenidos en la pila.
     */
    private int size;

    /**
     * Constructor: crea una pila vacía.
     */
    public Pila() {
        this.tope = null;
        this.size = 0;
    }

    /**
     * Inserta (apila) un elemento en la cima de la pila.
     *
     * Tiempo esperado: O(1).
     *
     * @param dato el objeto a apilar. La implementación actual acepta {@code null},
     *             pero las operaciones que usan {@code equals} pueden producir NPE si
     *             se almacena {@code null}.
     */
    public void apilar(Object dato){
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        size++;
    }

    /**
     * Elimina y retorna el elemento en la cima de la pila.
     *
     * Si la pila está vacía, imprime un mensaje de error y retorna {@code null}.
     *
     * @return el objeto que estaba en la cima de la pila, o {@code null} si la pila estaba vacía
     */
    public Object desapilar(){
        if(tope == null){
            System.out.println("    [ERROR] la pila esta vacia");
            return null;
        }

        Object dato = tope.getData();
        tope = tope.getSiguiente();
        size--;
        return dato;
    }

    /**
     * Retorna el elemento en la cima sin eliminarlo (peek).
     *
     * Si la pila está vacía, imprime un mensaje de error y retorna {@code null}.
     *
     * @return el objeto en la cima, o {@code null} si la pila está vacía
     */
    public Object peek(){
        if( tope == null){
            System.out.println("    [ERROR] la pila esta vacia");
            return null;
        }
        return tope.getData();
    }

    // Utilidades

    /**
     * Retorna la cantidad de elementos actualmente en la pila.
     *
     * @return tamaño de la pila (>= 0)
     */
    public int size(){
        return size;
    }

    /**
     * Indica si la pila está vacía.
     *
     * @return {@code true} si la pila no contiene elementos, {@code false} en caso contrario
     */
    public boolean esVacia(){
        return tope == null && size == 0;
    }

    /**
     * Verifica si la pila contiene un elemento igual al especificado.
     *
     * La comparación se realiza con {@code equals}. Retorna {@code true} en cuanto
     * encuentra la primera coincidencia.
     *
     * @param dato el objeto a buscar en la pila
     * @return {@code true} si el elemento está presente, {@code false} si no se encuentra
     */
    public boolean contiene (Object dato){
        if(tope != null){
            Nodo actual = tope;
            while (actual != null){
                if(actual.getData().equals(dato)) return true;
                actual = tope.getSiguiente();
            }
        }
        return false;
    }

    /**
     * Vacía la pila, eliminando todos los elementos. Después de llamar a este método,
     * la pila quedará en su estado inicial (vacía).
     */
    public void limpiar(){
        tope = null;
        size = 0;
    }

    /**
     * Imprime los elementos de la pila desde el tope hacia la base.
     * Si la pila está vacía, imprime un mensaje informativo.
     */
    public void mostrar(){
        if(tope == null){
            System.out.println("    [ERROR] la pila esta vacia");
            return;
        }

        Nodo actual = tope;
        int i = 1;
        while (actual != null){
            System.out.println("     " +i + ". " + actual.getData());
            actual = actual.getSiguiente();
            i++;
        }
    }

    /**
     * Busca un elemento en la pila y retorna su índice relativo al tope.
     *
     * El tope tiene índice 0, el siguiente índice 1, y así sucesivamente.
     * Si no se encuentra el elemento, retorna -1.
     *
     * @param dato el objeto a buscar
     * @return índice del elemento desde el tope (0..size-1), o -1 si no se encuentra
     */
    public int buscar(Object dato){

        Nodo actual = tope;
        int index = 0;
        while (actual != null){
            if(actual.getData().equals(dato)) return index;
            actual = actual.getSiguiente();
            index++;
        }
        return -1;
    }

    /**
     * Retorna una representación en cadena de la pila.
     *
     * Formato: Pila: [N](tope->base): elemTope | elem2 | elem3 ...
     *
     * @return cadena que representa el contenido de la pila
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Pila: [" + size + "](tope->base):");
        Nodo actual = tope;
        while (actual != null){
            sb.append(actual.getData());
            if(actual.getSiguiente() != null) sb.append(" | ");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}