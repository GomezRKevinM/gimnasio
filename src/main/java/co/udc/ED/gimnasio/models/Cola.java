package co.udc.ED.gimnasio.models;

/**
 * @Author: Kevinn Gómez
 * @Description: Cola FIFO (first in first out) usa nodos para almacenar los datos, el primer nodo en entrar es el primero en salir
 *              se pueden implementar con arreglos o con listas enlazadas.
 */
public class Cola {
    private Nodo primero; // Primer elemento
    private Nodo ultimo; // Ultimo elemento
    private int size;

    public Cola(){

        this.primero = null;
        this.ultimo = null;
        this.size = 0;

    }

    /**
     * Agrega un elemento al final de la cola
     * @param dato
     */
    public void encolar(Nodo dato){

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
     * Elimina el primer elemento de la cola y lo devuelve, si la cola esta vacia devuelve null
     * @return
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
     * Retorna el primer elemento sin eliminarlo
     * @return
     */
    public Object peek(){
        if (primero == null) {
            System.out.println("    [ERROR] La cola esta vacia");
            return null;
        }
        return primero.getData();
    }

    // Utilidades

    public int size(){
        return size;
    }

    public boolean esVacia(){
        return size == 0;
    }

    public boolean contiene(Object dato){
        Nodo actual = primero;
        while (actual != null){
            if (actual.getData().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public void limpiar(){
        primero = null;
        ultimo = null;
        size = 0;
    }

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
