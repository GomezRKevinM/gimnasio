package co.udc.ED.gimnasio.models.gym;

import co.udc.ED.gimnasio.enums.InscripcionEstado;

import java.sql.Timestamp;

/**
 * Record InscripcionClase - Modelo de datos para representar una inscripción a una clase
 *
 * Este record inmutable encapsula la información de una inscripción de un cliente a una
 * clase específica del gimnasio. Registra cuándo se realizó la inscripción, qué cliente
 * se inscribió y a qué clase.
 *
 * Características:
 * - Immutable: los valores no pueden cambiar una vez creado el record
 * - Identificación única: la combinación de codigoClase + cliente forma la identidad
 * - Auditoría temporal: registra la fecha y hora exacta de la inscripción
 * - Comparación compuesta: dos inscripciones son iguales si pertenecen al mismo cliente
 *   y la misma clase, independientemente de cuándo se realizó
 * - Hasheable: puede utilizarse en estructuras de datos basadas en hash
 *
 * Ejemplo de uso:
 * <pre>
 *   Timestamp ahora = new Timestamp(System.currentTimeMillis());
 *   InscripcionClase inscripcion = new InscripcionClase(ahora, "YOG001", "Juan Pérez");
 *   System.out.println(inscripcion);
 *   // [ Fecha: 2026-05-17 10:30:45.123 | Cliente: Juan Pérez | Código de Clase: YOG001 ]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Clase
 */
public record InscripcionClase(
        /**
         * Fecha y hora en que se registró la inscripción
         */
        Timestamp fechaInscripcion,

        /**
         * Código identificador de la clase a la que se inscribe el cliente
         */
        String codigoClase,

        /**
         * Nombre o identificador del cliente que se inscribe
         */
        String cliente,

        /**
         * Estado de la inscripción, indicando si está confirmada, en espera o cancelada
         */
        InscripcionEstado estado) {

    /**
     * Compara esta inscripción con otro objeto.
     *
     * Dos inscripciones se consideran iguales si pertenecen al mismo cliente
     * y a la misma clase. La fecha de inscripción no se considera en la comparación,
     * lo que permite identificar si un cliente ya está inscrito en una clase
     * independientemente de cuándo se realizó la inscripción.
     *
     * @param obj el objeto a comparar
     * @return {@code true} si el cliente y código de clase coinciden, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof InscripcionClase other){
            return this.codigoClase.equals(other.codigoClase) && this.cliente.equals(other.cliente);
        }
        return false;
    }

    /**
     * Retorna una representación legible de la inscripción.
     *
     * Formato: [ Fecha: [timestamp] | Cliente: [nombre_cliente] | Código de Clase: [codigo] ]
     *
     * @return cadena que representa la inscripción con todos sus datos
     */
    @Override
    public String toString(){
        return String.format("[ Fecha: %s | Cliente: %s | Codigo de Clase: %s | Estado: %s ]",
                fechaInscripcion, cliente, codigoClase, estado);
    }

    /**
     * Calcula el código hash de la inscripción.
     *
     * El hash se basa en la concatenación de codigoClase y cliente, garantizando que
     * dos inscripciones con el mismo cliente y clase tengan el mismo hash.
     * Esto es importante para usar InscripcionClase en estructuras como HashMap o HashSet,
     * y permite identificar duplicados de inscripciones.
     *
     * @return código hash basado en la combinación de código de clase y cliente
     */
    @Override
    public int hashCode(){
        return (codigoClase + cliente).hashCode();
    }
}
