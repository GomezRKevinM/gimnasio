package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

/**
 * Evento de Dominio - ModificarInscripcion
 *
 * Representa el evento que se dispara cuando se modifica una inscripción existente del sistema.
 * Este evento es parte del patrón Domain-Driven Design (DDD) y permite mantener
 * un registro histórico completo de los cambios realizados en las inscripciones del gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para obtener trazabilidad temporal automática
 * - Captura el estado de la inscripción después de la modificación
 * - Facilita la auditoría y el seguimiento de cambios en datos de clientes/clases
 * - Integrable con sistemas de undo/redo usando estructuras como Pila
 *
 * Ejemplo de uso:
 * <pre>
 *   Timestamp ahora = new Timestamp(System.currentTimeMillis());
 *   InscripcionClase inscripcionModificada = new InscripcionClase(ahora, "YOG002", "Juan Pérez");
 *   DomainEvent evento = new ModificarInscripcion(inscripcionModificada);
 *   System.out.println(evento);
 *   // Evento: Se modificó una inscripción, Fecha: 2026-05-17 11:15:30.456, Detalles: Inscripción modificada [...]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see InscripcionClase
 */
public class ModificarInscripcion extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en los logs de auditoría.
     * Este mensaje se mantiene consistente para todas las instancias de ModificarInscripcion.
     *
     * Valor: "Se modificó una inscripción"
     */
    private static final String NOMBRE = "Se modificó una inscripción";

    /**
     * Referencia a la instancia de {@link InscripcionClase} después de ser modificada.
     * Contiene el estado actualizado de la inscripción con todos los cambios aplicados:
     * puede incluir cambios en la clase, fecha de inscripción, etc.
     *
     * Este atributo inmutable (final) preserva el estado de la inscripción en el
     * momento exacto de la modificación para propósitos de auditoría.
     */
    private final InscripcionClase inscripcion;

    /**
     * Constructor de ModificarInscripcion
     *
     * Inicializa un nuevo evento de modificación de inscripción. El evento captura
     * automáticamente la información de fecha y hora del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * Nota: Es responsabilidad del llamador asegurar que la inscripción proporcionada
     * sea el estado actualizado de la inscripción después de los cambios.
     *
     * @param inscripcionModificada la instancia de {@link InscripcionClase} con los cambios aplicados.
     *                              No debe ser null.
     *
     * @throws NullPointerException si inscripcionModificada es null
     */
    public ModificarInscripcion(InscripcionClase inscripcionModificada) {
        super(NOMBRE);
        this.inscripcion = inscripcionModificada;
    }

    /**
     * Retorna una representación legible del evento de modificación de inscripción.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Inscripción modificada [inscripcion.toString()]
     *
     * Esta representación es útil para:
     * - Registros de auditoría del sistema
     * - Mensajes en consola y logs de aplicación
     * - Reportes de modificaciones realizadas
     * - Trazabilidad de cambios en inscripciones
     *
     * Ejemplo de salida:
     * Evento: Se modificó una inscripción, Fecha: 2026-05-17 11:15:30.456,
     * Detalles: Inscripción modificada [ Fecha: 2026-05-17 11:15:30.456 | Cliente: Juan Pérez | Código de Clase: YOG002 ]
     *
     * @return cadena de texto con formato descriptivo del evento de modificación
     */
    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción modificada %s",
                super.nombre, super.fecha, inscripcion.toString());
    }
}