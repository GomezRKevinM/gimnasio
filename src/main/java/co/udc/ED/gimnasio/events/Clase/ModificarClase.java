package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

/**
 * Evento de Dominio - ModificarClase
 *
 * Representa el evento que se dispara cuando se modifica una clase existente del sistema.
 * Este evento es parte del patrón Domain-Driven Design (DDD) y permite mantener
 * un registro histórico completo de los cambios realizados en las clases del gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para obtener trazabilidad temporal automática
 * - Captura el estado de la clase después de la modificación
 * - Facilita la auditoría y el seguimiento de cambios
 * - Integrable con sistemas de undo/redo usando estructuras como Pila
 *
 * Ejemplo de uso:
 * <pre>
 *   Clase claseModificada = clase.withNombre("Yoga Avanzado");
 *   DomainEvent evento = new ModificarClase(claseModificada);
 *   System.out.println(evento);
 *   // Evento: Se modificó una clase existente, Fecha: 2026-05-17 11:15:30.456, Detalles: Clase modificada [...]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see Clase
 */
public class ModificarClase extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en los logs de auditoría.
     * Este mensaje se mantiene consistente para todas las instancias de ModificarClase.
     *
     * Valor: "Se modificó una clase existente"
     */
    private static final String NOMBRE = "Se modificó una clase existente";

    /**
     * Referencia a la instancia de {@link Clase} después de ser modificada.
     * Contiene el estado actualizado de la clase con todos los cambios aplicados:
     * nombre, descripción, duración, instructor, etc.
     *
     * Este atributo inmutable (final) preserva el estado de la clase en el
     * momento exacto de la modificación para propósitos de auditoría.
     */
    private final Clase clase;

    /**
     * Constructor de ModificarClase
     *
     * Inicializa un nuevo evento de modificación de clase. El evento captura
     * automáticamente la información de fecha y hora del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * Nota: Es responsabilidad del llamador asegurar que la clase proporcionada
     * sea el estado actualizado de la clase después de los cambios.
     *
     * @param claseModificada la instancia de {@link Clase} con los cambios aplicados.
     *                        No debe ser null.
     *
     * @throws NullPointerException si claseModificada es null
     */
    public ModificarClase(Clase claseModificada) {
        super(NOMBRE);
        this.clase = claseModificada;
    }

    /**
     * Retorna una representación legible del evento de modificación de clase.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Clase modificada [clase.toString()]
     *
     * Esta representación es útil para:
     * - Registros de auditoría del sistema
     * - Mensajes en consola y logs aplicados
     * - Reportes de modificaciones realizadas
     * - Trazabilidad de cambios
     *
     * Ejemplo de salida:
     * Evento: Se modificó una clase existente, Fecha: 2026-05-17 11:15:30.456,
     * Detalles: Clase modificada [ Código: YOG001 | Nombre: Yoga Avanzado | ... ]
     *
     * @return cadena de texto con formato descriptivo del evento de modificación
     */
    @Override
    public String toString() {
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase modificada %s",
                super.nombre, super.fecha, clase.toString());
    }
}