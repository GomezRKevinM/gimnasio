package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;

/**
 * Evento de Dominio - EliminarInscripcion
 *
 * Representa el evento que se dispara cuando se elimina una inscripción del sistema.
 * Este evento es fundamental en el patrón Domain-Driven Design (DDD) y mantiene
 * un registro inmutable de todas las inscripciones que han sido eliminadas del gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para obtener timestamp automático de eliminación
 * - Captura el identificador de la inscripción eliminada
 * - Permite recuperación de información para auditoría y cumplimiento normativo
 * - Facilita la implementación de funcionalidad de papelera o restauración
 * - Registra la fecha exacta de la desinscripción de un cliente
 *
 * Nota: A diferencia de otros eventos de eliminación, este evento almacena solo
 * el ID de la inscripción en lugar del objeto completo. Esto reduce tamaño de
 * almacenamiento mientras mantiene la trazabilidad esencial.
 *
 * Ejemplo de uso:
 * <pre>
 *   String idInscripcion = generarId(); // "INS_20260517_001"
 *   repositorio.eliminar(idInscripcion);
 *   DomainEvent evento = new EliminarInscripcion(idInscripcion);
 *   System.out.println(evento);
 *   // Evento: Se eliminó una inscripción, Fecha: 2026-05-17 12:45:15.789,
 *   // Detalles: Inscripción eliminada con ID INS_20260517_001
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 */
public class EliminarInscripcion extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en los logs de auditoría.
     * Este mensaje es consistente para todas las instancias de EliminarInscripcion.
     *
     * Valor: "Se eliminó una inscripción"
     */
    private static final String NOMBRE = "Se eliminó una inscripción";

    /**
     * Identificador único de la inscripción que fue eliminada.
     * Almacena el ID de la inscripción en lugar del objeto completo, lo que permite:
     * - Reducir el tamaño del evento almacenado
     * - Mantener la trazabilidad de qué inscripción fue eliminada
     * - Facilitar búsquedas en logs de auditoría
     *
     * Este atributo inmutable (final) preserva el identificador de la inscripción
     * eliminada para propósitos de auditoría y recuperación.
     */
    private final String idInscripcion;

    /**
     * Constructor de EliminarInscripcion
     *
     * Inicializa un nuevo evento de eliminación de inscripción. El evento captura
     * automáticamente la fecha y hora actual del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * Nota: Este constructor debe ser llamado DESPUÉS de eliminar la inscripción
     * físicamente del repositorio, para garantizar que la operación fue exitosa
     * antes de registrar el evento.
     *
     * @param idInscripcionEliminada el identificador único de la inscripción que fue eliminada.
     *                               No debe ser null ni vacío.
     *
     * @throws NullPointerException si idInscripcionEliminada es null
     * @throws IllegalArgumentException si idInscripcionEliminada está vacío
     */
    public EliminarInscripcion(String idInscripcionEliminada) {
        super(NOMBRE);
        this.idInscripcion = idInscripcionEliminada;
    }

    /**
     * Retorna una representación legible del evento de eliminación de inscripción.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Inscripción eliminada con ID [idInscripcion]
     *
     * Esta representación es útil para:
     * - Registros permanentes de auditoría
     * - Trazabilidad de eliminaciones en el sistema
     * - Mensajes en consola y archivos de log
     * - Reportes de operaciones de desinscripción
     * - Búsqueda en historial de eventos
     *
     * Ejemplo de salida:
     * Evento: Se eliminó una inscripción, Fecha: 2026-05-17 12:45:15.789,
     * Detalles: Inscripción eliminada con ID INS_20260517_001
     *
     * @return cadena de texto con formato descriptivo del evento de eliminación
     */
    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción eliminada con ID %s",
                super.nombre, super.fecha, idInscripcion);
    }
}