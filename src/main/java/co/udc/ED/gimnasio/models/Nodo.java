package co.udc.ED.gimnasio.models;

/**
 * Clase Nodo - Estructura base para las estructura de datos (listas, pilas, colas)
 *
 * Representa un nodo genérico que puede almacenar cualquier tipo de objeto.
 * Cada nodo mantiene referencias a un elemento siguiente y anterior, permitiendo
 * la implementación de estructuras de datos enlazadas bidireccionales.
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 */
public class Nodo {

    /** Atributo que almacena el dato u objeto contenido en el nodo */
    private Object data;

    /** Referencia al siguiente nodo en la estructura de datos */
    private Nodo siguiente;

    /** Referencia al nodo anterior en la estructura de datos */
    private Nodo anterior;

    /**
     * Constructor del Nodo
     *
     * Inicializa un nuevo nodo con el dato especificado. Las referencias
     * al nodo siguiente y anterior se establecen como nulas.
     *
     * @param dato el objeto que será almacenado en este nodo
     */
    public Nodo(Object dato) {
        this.data = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    /**
     * Obtiene el dato almacenado en el nodo
     *
     * @return el objeto almacenado en el atributo data del nodo
     */
    public Object getData() {
        return data;
    }

    /**
     * Establece el dato del nodo
     *
     * @param dato el nuevo objeto a almacenar en el nodo
     */
    public void setData(Object dato) {
        this.data = dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo
     *
     * @return el nodo siguiente en la estructura de datos, o null si no existe
     */
    public Nodo getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo
     *
     * @param siguiente el nodo que será el siguiente en la estructura de datos
     */
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Obtiene la referencia al nodo anterior
     *
     * @return el nodo anterior en la estructura de datos, o null si no existe
     */
    public Nodo getAnterior() {
        return anterior;
    }

    /**
     * Establece la referencia al nodo anterior
     *
     * @param anterior el nodo que será el anterior en la estructura de datos
     */
    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
}