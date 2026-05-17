package co.udc.ED.gimnasio.models.gym;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;

/**
 * Record Clase - Modelo de datos para representar una clase del gimnasio
 *
 * Este record inmutable encapsula toda la información relacionada con una clase
 * ofrecida por el gimnasio. Mantiene referencias a su instructor, lista de inscripciones
 * confirmadas y una cola de espera para quienes desean inscribirse.
 *
 * Características:
 * - Immutable: los valores no pueden cambiar una vez creado el record
 * - Identificación: cada clase tiene un código único
 * - Gestión de inscripciones: mantiene una lista de inscritos y una cola de espera
 * - Comparación por código: permite búsqueda y comparación por String o por objeto Clase
 * - Hasheable: puede utilizarse en estructuras de datos basadas en hash
 *
 * Ejemplo de uso:
 * <pre>
 *   Instructor instructor = new Instructor("Carlos López", UUID.randomUUID());
 *   Clase yoga = new Clase("YOG001", "Yoga", "Clase de yoga relajante", 1,
 *                          instructor, new Lista(), new Cola());
 *   System.out.println(yoga);
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Instructor
 * @see Lista
 * @see Cola
 */
public record Clase(
        /**
         * Código único que identifica la clase
         */
        String codigo,

        /**
         * Nombre de la clase
         */
        String nombre,

        /**
         * Descripción detallada de la clase
         */
        String descripcion,

        /**
         * Duración de la clase en horas
         */
        int duracionHoras,

        /**
         * Instructor responsable de impartir la clase
         */
        Instructor instructor,

        /**
         * Lista de inscripciones confirmadas en la clase
         */
        Lista inscripciones,

        /**
         * Cola de espera para usuarios que desean inscribirse pero no hay cupo disponible
         */
        Cola colaInscripcion) {

    /**
     * Compara esta clase con otro objeto.
     *
     * Implementación personalizada que permite comparación flexible:
     * - Si se compara con otra {@code Clase}, compara los códigos
     * - Si se compara con una {@code String}, intenta comparar directamente con el código
     * - Retorna false en caso contrario
     *
     * @param dato el objeto a comparar
     * @return {@code true} si los códigos coinciden, {@code false} en caso contrario
     */
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

    /**
     * Retorna una representación detallada en cadena de la clase.
     *
     * Formato: [ Código: [código] | Nombre: [nombre] | Descripción: [descripción] |
     *            Duración: [horas] horas | Instructor: [nombre_instructor] |
     *            Total Inscripciones: [cantidad] | Total en Cola: [cantidad] ]
     *
     * @return cadena que representa la clase con todos sus detalles
     */
    @Override
    public String toString(){
        return String.format("[ Código: %s | Nombre: %s | Descripción: %s | Duración: %d horas | Instructor: %s | Total Inscripciones: %d | Total en Cola: %d ]",
                this.codigo, this.nombre, this.descripcion, this.duracionHoras, this.instructor.nombre(), this.inscripciones.cuentaElementos(), this.colaInscripcion.size());
    }

    /**
     * Calcula el código hash de la clase.
     *
     * El hash se basa únicamente en el código de la clase, garantizando que dos clases
     * con el mismo código tengan el mismo hash. Esto es importante para usar Clase en
     * estructuras como HashMap o HashSet.
     *
     * @return código hash basado en el código de la clase
     */
    @Override
    public int hashCode(){
        return this.codigo.hashCode();
    }
}