package co.udc.ED.gimnasio.models;

/**
 * Clase Cola - Cola FIFO (First In First Out)
 *
 * Implementa una estructura de datos de cola con comportamiento FIFO (primero en entrar,
 * primero en salir). Utiliza nodos enlazados para almacenar los datos. El primer elemento
 * que entra a la cola es el primero en salir.
 *
 * Puede implementarse con arreglos o con listas enlazadas. Esta implementación utiliza
 * nodos para crear una cola dinámica y flexible.
 *
 * Operaciones disponibles:
 * - Encolar: agregar elemento al final
 * - Desencolar: eliminar y retornar el primer elemento
 * - Peek: consultar el primer elemento sin eliminarlo
 * - Validaciones y utilidades
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Nodo
 */
public class Cola {

    /** Referencia al primer elemento de la cola (frente) */
    private Nodo primero;

    /** Referencia al último elemento de la cola (final) */
    private Nodo ultimo;

    /** Cantidad de elementos almacenados en la cola */
    private int size;

    /**
     * Constructor de la cola
     *
     * Inicializa una cola vacía con primero y último nulos y tamaño 0.
     */
    public Cola(){
        this.primero = null;
        this.ultimo = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la cola (encolar)
     *
     * Inserta el nuevo elemento al final de la cola, manteniendo la estructura FIFO.
     * Si la cola está vacía, el nuevo elemento se convierte en primero y último.
     *
     * @param dato el nodo que contiene el objeto a encolar
     */
    public void encolar(Object dato){
        Nodo nuevo = new Nodo(dato);

        if(ultimo == null){
            primero = nuevo;
            ultimo = nuevo;
        }else{
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        size++;
    }

    /**
     * Elimina y retorna el primer elemento de la cola (desencolar)
     *
     * Extrae el elemento del frente de la cola y actualiza la referencia del primero.
     * Si la cola queda vacía, también actualiza la referencia del último.
     * Si la cola está vacía, imprime un mensaje de error y retorna null.
     *
     * @return el dato del primer elemento eliminado, o null si la cola está vacía
     */
    public Object desencolar(){
        if(primero == null){
            System.out.println("    [ERROR] La cola esta vacia");
            return null;
        }

        Object dato = primero.getData();
        primero = primero.getSiguiente();

        if(primero == null) ultimo = null;

        size--;
        return dato;
    }

    /**
     * Retorna el primer elemento sin eliminarlo (peek)
     *
     * Permite consultar el elemento del frente de la cola sin modificar la estructura.
     * Si la cola está vacía, imprime un mensaje de error y retorna null.
     *
     * @return el dato del primer elemento, o null si la cola está vacía
     */
    public Object peek(){
        if (primero == null) {
            System.out.println("    [ERROR] La cola esta vacia");
            return null;
        }
        return primero.getData();
    }

    /**
     * Retorna la cantidad de elementos en la cola
     *
     * @return el número de elementos almacenados
     */
    public int size(){
        return size;
    }

    /**
     * Verifica si la cola está vacía
     *
     * @return true si la cola no contiene elementos, false en caso contrario
     */
    public boolean esVacia(){
        return size == 0;
    }

    /**
     * Verifica si la cola contiene un elemento específico
     *
     * Recorre la cola desde el frente comparando cada elemento con el dato proporcionado.
     *
     * @param dato el objeto a buscar en la cola
     * @return true si el elemento existe en la cola, false en caso contrario
     */
    public boolean contiene(Object dato){
        Nodo actual = primero;
        while (actual != null){
            if (actual.getData().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Vacía completamente la cola
     *
     * Establece primero, último y size a sus valores iniciales.
     */
    public void limpiar(){
        primero = null;
        ultimo = null;
        size = 0;
    }

    /**
     * Imprime todos los elementos de la cola en orden (desde frente a final)
     *
     * Si la cola está vacía, imprime un mensaje indicándolo.
     * Cada elemento se lista con su número de posición.
     */
    public void mostar(){
        if(primero == null){
            System.out.println("    [INFO] La cola esta vacia");
            return;
        }

        Nodo actual = primero;
        System.out.print("    [INFO] Elementos en la cola: ");
        int i = 1;
        while (actual != null){
            System.out.println( i + ". "+ actual.getData());
            actual = actual.getSiguiente();
            i++;
        }
    }

    /**
     * Retorna una representación en cadena de la cola
     *
     * Formato: Cola[tamaño](frente->fin): elem1 | elem2 | elem3 | ...
     *
     * @return una cadena de texto que representa la cola en orden FIFO
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(("Cola[" + size + "](frente->fin): "));

        Nodo actual = primero;
        while (actual != null) {
            sb.append(actual.getData());
            if (actual.getSiguiente() != null) sb.append(" | ");
            actual = actual.getSiguiente();
        }

        return sb.toString();
    }
}