package co.udc.ED.gimnasio.models;

/**
 * Lista doblemente enlazada generica.
 * Permite agregar, eliminar, buscar y recorrer elementos
 * tanto hacia adelante como hacia atras usando referencias de Nodo.
 *
 * @since 1.0
 * @version 1.0
 * @author Kevin Gómez
 * @see Nodo
 */
public class Lista {

    private Nodo cabeza;
    private Nodo cola;
    private int size;

    public Lista(){
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }

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

    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    // Utilidades

    public int cuentaElementos() {
        return size;
    }

    public void limpiar() {
        cabeza = null;
        cola = null;
        size = 0;
    }

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
