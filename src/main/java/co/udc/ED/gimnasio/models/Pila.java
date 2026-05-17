package co.udc.ED.gimnasio.models;

/**
 * @Author: Kevinn Gómez
 * @Description: Pila LIFO (last in first out) usa nodos para almacenar los datos, el último nodo en entrar es el primero en salir, se pueden implementar con arreglos o con listas enlazadas.
 *
 */
public class Pila {
    private Nodo tope;
    private int size;

    public Pila() {
        this.tope = null;
        this.size = 0;
    }

    /**
     * Apila un elemento encima de la pila
     * @param dato
     */
    public void apilar(Object dato){
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        size++;
    }

    /**
     * Elimina y retorna él elemento en la cima de la pila
     * @return Object
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
     * Retorna el elemento en la cima de la pila sin eliminarlo
     * @return Object
     */
    public Object peek(){
        if( tope == null){
            System.out.println("    [ERROR] la pila esta vacia");
            return null;
        }
        return tope.getData();
    }

    // Utilidades
    public int size(){
        return size;
    }

    public boolean esVacia(){
        return tope == null && size == 0;
    }

    public boolean contiene (Object dato){
        if(tope != null){
            Object actual = tope.getData();
            while (actual != null){
                if(actual.equals(dato)) return true;
                actual = tope.getSiguiente().getData();
            }
        }
        return false;
    }

    public void limpiar(){
        tope = null;
        size = 0;
    }

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
     * Busca un elemento de la pila y retorna su index, si no lo encuentra retorna -1
     * @return int
     */
    public int buscar(Object dato){

        Object actual = tope.getData();
        int index = 0;
        while (actual != null){
            if(actual.equals(dato)) return index;
            actual = tope.getSiguiente().getData();
            index++;
        }
        return -1;
    }

    // To String
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
