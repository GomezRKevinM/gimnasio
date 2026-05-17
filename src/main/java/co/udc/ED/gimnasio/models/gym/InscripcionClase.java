package co.udc.ED.gimnasio.models.gym;

import java.sql.Timestamp;

/**
 * @Author: Kevin Gómez
 * @Description: Record usado para modelar datos de una inscripción a una clase
 */
public record InscripcionClase(Timestamp fechaInscripcion, String codigoClase, String cliente) {

    @Override
    public boolean equals(Object obj){
        if(obj instanceof InscripcionClase other){
            return this.codigoClase.equals(other.codigoClase) && this.cliente.equals(other.cliente);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("[ Fecha: %s | Cliente: %s | Código de Clase: %s ]",  fechaInscripcion, cliente, codigoClase);
    }

    @Override
    public int hashCode(){
        return (codigoClase + cliente).hashCode();
    }
}
