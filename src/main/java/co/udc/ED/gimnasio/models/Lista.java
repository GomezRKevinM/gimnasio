package co.udc.ED.gimnasio.models;

/**
 * Clase Lista - Lista doblemente enlazada genérica
 *
 * Implementa una estructura de datos de lista doblemente enlazada que permite
 * almacenar objetos de cualquier tipo. Proporciona operaciones para agregar,
 * eliminar, buscar y recorrer elementos tanto hacia adelante como hacia atrás.
 *
 * Características:
 * - Inserción al inicio, final o en posición específica
 * - Eliminación desde cualquier posición
 * - Búsqueda por índice o por valor
 * - Recorrido bidireccional
 * - Gestión automática del tamaño
 *
 * @since 1.0
 * @version 1.0
 * @author Kevin Gómez
 * @see Nodo
 */
public class Lista {

    /** Referencia al primer nodo de la lista */
    private Nodo cabeza;

    /** Referencia al último nodo de la lista */
    private Nodo cola;

    /** Cantidad de elementos almacenados en la lista */
    private int size;

    /**
     * Constructor de la lista
     *
     * Inicializa una lista vacía con cabeza y cola nulas y tamaño 0.
     */
    public Lista(){
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }

    /**
     * Agrega un elemento al final de la lista
     *
     * @param dato el objeto a agregar al final de la lista
     */
    public void agregar(Object dato){
        Nodo nuevo = new Nodo(dato);

        if(this.cabeza == null){
            cabeza = nuevo;
            cola = nuevo;
        }else{
            nuevo.setAnterior(cola);
            cola.setSiguiente(nuevo);
            cola = nuevo;
        }
        size++;
    }

    /**
     * Agrega un elemento al inicio de la lista
     *
     * @param dato el objeto a agregar al inicio de la lista
     */
    public void agregarAlInicio(Object dato){
        Nodo nuevo = new Nodo(dato);

        if(this.cabeza == null){
            cabeza = nuevo;
            cola = nuevo;
        }else{
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        }
        size++;
    }

    /**
     * Agrega un elemento en una posición específica de la lista
     *
     * Si el índice es 0, inserta al inicio. Si es igual al tamaño, inserta al final.
     * Para posiciones intermedias, inserta entre los nodos correspondientes.
     *
     * @param index la posición donde se insertará el elemento (0 a size)
     * @param dato el objeto a agregar en la posición especificada
     */
    public void agregarEnPosicion(int index, Object dato){
        if(index < 0 || index > size){
            System.out.printf("    [ERROR] Indice fuera de rango (0 - %d): %d\n", index,size-1);
            return;
        }

        if(index == 0){
            agregarAlInicio(dato);
            return;
        }

        if(index == size){
            agregar(dato);
            return;
        }

        Nodo nuevo = new Nodo(dato);
        Nodo actual = cabeza;

        // Este for recorre la lista hasta antes del nodo que se va a insertar, para obtener el nodo donde insertaremos el nuevo
        for(int i = 0; i < index; i++){
            actual = actual.getSiguiente();
        }
        Nodo previo = actual.getAnterior();
        previo.setSiguiente(nuevo);
        nuevo.setAnterior(previo);
        nuevo.setSiguiente(actual);
        actual.setAnterior(nuevo);
        size++;
    }

    /**
     * Elimina y retorna el primer elemento de la lista
     *
     * Si la lista está vacía, imprime un mensaje de error y retorna null.
     *
     * @return el dato del primer elemento eliminado, o null si la lista está vacía
     */
    public Object eliminarPrimero() {
        if (cabeza == null) {
            System.out.println("    [Error] La lista esta vacia.");
            return null;
        }
        Object dato = cabeza.getData();
        cabeza = cabeza.getSiguiente();
        if (cabeza != null) {
            cabeza.setAnterior(null);
        } else {
            cola = null;
        }
        size--;
        return dato;
    }

    /**
     * Elimina y retorna el último elemento de la lista
     *
     * Si la lista está vacía, imprime un mensaje de error y retorna null.
     *
     * @return el dato del último elemento eliminado, o null si la lista está vacía
     */
    public Object eliminarUltimo() {
        if (cola == null) {
            System.out.println("    [Error] La lista esta vacia.");
            return null;
        }
        Object dato = cola.getData();;
        cola = cola.getAnterior();
        if (cola != null) {
            cola.setSiguiente(null);
        } else {
            cabeza = null;
        }
        size--;
        return dato;
    }

    /**
     * Elimina y retorna el elemento en la posición especificada
     *
     * @param indice la posición del elemento a eliminar (0 a size-1)
     * @return el dato del elemento eliminado, o null si el índice es inválido
     */
    public Object eliminarEnPosicion(int indice) {
        if (indice < 0 || indice >= size) {
            System.out.println("    [Error] Indice fuera de rango: " + indice);
            return null;
        }
        if (indice == 0) return eliminarPrimero();
        if (indice == size - 1) return eliminarUltimo();

        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        actual.getAnterior().setSiguiente(actual.getSiguiente());
        actual.getSiguiente().setAnterior(actual.getAnterior());
        size--;
        return actual.getData();
    }

    /**
     * Busca y retorna el elemento en la posición especificada
     *
     * @param indice la posición del elemento a buscar (0 a size-1)
     * @return el dato en la posición especificada, o null si el índice es inválido
     */
    public Object buscarDato(int indice) {
        if (indice < 0 || indice >= size) {
            System.out.printf("    [ERROR] Indice fuera de rango (0 - %d): %d\n", indice, size-1);
            return null;
        }
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getData();
    }

    /**
     * Busca el primer elemento que coincida con el dato especificado
     *
     * Recorre la lista desde el inicio comparando cada elemento con el dato proporcionado.
     *
     * @param dato el objeto a buscar en la lista
     * @return el primer dato que coincida, o null si no se encontró
     */
    public Object buscarDato(Object dato) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getData().equals(dato)) {
                return actual.getData();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Verifica si la lista contiene el elemento especificado
     *
     * @param dato el objeto a buscar
     * @return true si el elemento existe en la lista, false en caso contrario
     */
    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    /**
     * Retorna la cantidad de elementos en la lista
     *
     * @return el número de elementos almacenados
     */
    public int cuentaElementos() {
        return size;
    }

    /**
     * Vacía completamente la lista
     *
     * Establece cabeza, cola y size a sus valores iniciales.
     */
    public void limpiar() {
        cabeza = null;
        cola = null;
        size = 0;
    }

    /**
     * Imprime todos los elementos de la lista en orden (desde cabeza a cola)
     *
     * Si la lista está vacía, imprime un mensaje indicándolo.
     */
    public void mostrarAdelante() {
        if (cabeza == null) {
            System.out.println("  [Lista vacia]");
            return;
        }
        Nodo actual = cabeza;
        int i = 1;
        while (actual != null) {
            System.out.println("  " + i + ". " + actual.getData());
            actual = actual.getSiguiente();
            i++;
        }
    }

    /**
     * Imprime todos los elementos de la lista en orden inverso (desde cola a cabeza)
     *
     * Si la lista está vacía, imprime un mensaje indicándolo.
     */
    public void mostrarAtras() {
        if (cola == null) {
            System.out.println("  [Lista vacia]");
            return;
        }
        Nodo actual = cola;
        int i = size;
        while (actual != null) {
            System.out.println("  " + i + ". " + actual.getData());
            actual = actual.getAnterior();
            i--;
        }
    }

    /**
     * Retorna una representación en cadena de la lista
     *
     * Formato: Lista[tamaño]: elem1 -> elem2 -> elem3 -> ...
     *
     * @return una cadena de texto que representa la lista
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Lista[" + size + "]: ");
        Nodo actual = cabeza;

        while (actual != null) {
            sb.append(actual.getData());
            if (actual.getSiguiente() != null) sb.append(" -> ");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}