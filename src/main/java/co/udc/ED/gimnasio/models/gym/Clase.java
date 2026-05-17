package co.udc.ED.gimnasio.models.gym;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;

/**
 * @Author: Kevin Gómez
 * @Description: Record empleado para modelar datos sobre una clase del gimnasio
 */
public record Clase(String codigo,String nombre, String descripcion, int duracionHoras, Instructor instructor, Lista inscripciones, Cola colaInscripcion) {

    @Override
    public boolean equals(Object dato){
        if(this == dato) return true;
        if(dato == null)return false;

        if(dato instanceof String){
            return this.codigo.equals((String) dato);
        }

        if(dato instanceof Clase clase){
            return this.codigo.equals(clase.codigo);
        }

        return false;
    }

    @Override
    public String toString(){
        return String.format("[ Código: %s | Nombre: %s | Descripción: %s | Duración: %d horas | Instructor: %s | Total Inscripciones: %d | Total en Cola: %d ]",
                this.codigo, this.nombre, this.descripcion, this.duracionHoras, this.instructor.nombre(), this.inscripciones.cuentaElementos(), this.colaInscripcion.size());
    }

    @Override
    public int hashCode(){
        return this.codigo.hashCode();
    }
}
