package co.udc.ED.gimnasio.models.gym;

import java.util.UUID;

/**
 * @Author: Kevinn Gómez
 * @Description: Record empleado para modelar al instructor del gimnasio
 */
public record Instructor(String nombre, UUID codigo) {

    @Override
    public boolean equals(Object obj) {
        if(this == obj ) return true;
        if(obj == null) return false;

        // Permite buscar por texto o por objeto
        if(obj instanceof String){
            return this.codigo.equals(UUID.fromString((String) obj));
        }
        if(obj instanceof Instructor){
            Instructor otro = (Instructor) obj;
            return this.codigo.equals(otro.codigo);
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("[ Nombre: %s | Código: %s ]", this.nombre, this.codigo);
    }

    @Override
    public int hashCode() {
        return this.codigo.hashCode();
    }
}
