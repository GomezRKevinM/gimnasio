package co.udc.ED.gimnasio.models.gym;

import java.util.UUID;

/**
 * Record Instructor - Modelo de datos para representar un instructor de gimnasio
 *
 * Este record inmutable encapsula la información de un instructor del gimnasio.
 * Cada instructor se identifica de forma única mediante un código UUID.
 *
 * Características:
 * - Immutable: los valores no pueden cambiar una vez creado el record
 * - Identificación: cada instructor tiene un código UUID único
 * - Comparación flexible: permite búsqueda por String (UUID) o por objeto Instructor
 * - Hasheable: puede utilizarse en estructuras de datos basadas en hash
 *
 * Ejemplo de uso:
 * <pre>
 *   UUID codigo = UUID.randomUUID();
 *   Instructor instructor = new Instructor("Juan Pérez", codigo);
 *   System.out.println(instructor);  // [ Nombre: Juan Pérez | Código: ... ]
 * </pre>
 *
 * @author Kevinn Gómez
 * @version 1.0
 * @since 1.0
 */
public record Instructor(
        /**
         * Nombre del instructor de gimnasio
         */
        String nombre,

        /**
         * Identificador único del instructor (UUID)
         */
        UUID codigo) {

    /**
     * Compara este instructor con otro objeto.
     *
     * Implementación personalizada que permite comparación flexible:
     * - Si se compara con otro {@code Instructor}, compara los códigos UUID
     * - Si se compara con una {@code String}, intenta convertirla a UUID y comparar
     * - Retorna false en caso contrario
     *
     * Nota: esta implementación permite buscar instructores como búsqueda por UUID-String,
     * lo que es útil para búsquedas en colecciones.
     *
     * @param obj el objeto a comparar
     * @return {@code true} si los códigos coinciden, {@code false} en caso contrario
     */
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

    /**
     * Retorna una representación en cadena del instructor.
     *
     * Formato: [ Nombre: [nombre] | Código: [uuid] ]
     *
     * @return cadena que representa el instructor de forma legible
     */
    @Override
    public String toString(){
        return String.format("[ Nombre: %s | Código: %s ]", this.nombre, this.codigo);
    }

    /**
     * Calcula el código hash del instructor.
     *
     * El hash se basa únicamente en el código UUID del instructor, garantizando
     * que dos instructores con el mismo código tengan el mismo hash.
     * Esto es importante para usar Instructor en estructuras como HashMap o HashSet.
     *
     * @return código hash basado en el UUID del instructor
     */
    @Override
    public int hashCode() {
        return this.codigo.hashCode();
    }
}