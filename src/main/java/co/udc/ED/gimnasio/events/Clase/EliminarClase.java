package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

/**
 * Evento de Dominio - EliminarClase
 *
 * Representa el evento que se dispara cuando se elimina una clase del sistema.
 * Este evento es fundamental en el patrón Domain-Driven Design (DDD) y mantiene
 * un registro inmutable de todas las clases que han sido eliminadas del gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para obtener timestamp automático de eliminación
 * - Captura el estado completo de la clase antes de su eliminación
 * - Permite recuperación de información para auditoría y cumplimiento normativo
 * - Facilita la implementación de funcionalidad de papelera o restauración
 *
 * Ejemplo de uso:
 * <pre>
 *   Clase claseAEliminar = repositorio.encontrar("YOG001");
 *   repositorio.eliminar(claseAEliminar);
 *   DomainEvent evento = new EliminarClase(claseAEliminar);
 *   System.out.println(evento);
 *   // Evento: Se eliminó una clase, Fecha: 2026-05-17 12:45:15.789, Detalles: Clase eliminada [...]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see Clase
 */
public class EliminarClase extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en los logs de auditoría.
     * Este mensaje es consistente para todas las instancias de EliminarClase.
     *
     * Valor: "Se eliminó una clase"
     */
    private static final String NOMBRE = "Se eliminó una clase";

    /**
     * Referencia a la instancia de {@link Clase} que fue eliminada.
     * Contiene todos los detalles históricos de la clase eliminada:
     * código, nombre, descripción, duración, instructor, inscripciones, etc.
     *
     * Este atributo inmutable (final) preserva el estado completo de la clase
     * en el momento de su eliminación, permitiendo auditoría y posible recuperación.
     */
    private final Clase clase;

    /**
     * Constructor de EliminarClase
     *
     * Inicializa un nuevo evento de eliminación de clase. El evento captura
     * automáticamente la fecha y hora actual del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * Nota: Este constructor debe ser llamado DESPUÉS de eliminar la clase
     * físicamente del repositorio, para garantizar que la operación fue exitosa
     * antes de registrar el evento.
     *
     * @param claseEliminada la instancia de {@link Clase} que fue eliminada.
     *                       Contiene el estado de la clase justo antes de la eliminación.
     *                       No debe ser null.
     *
     * @throws NullPointerException si claseEliminada es null
     */
    public EliminarClase(Clase claseEliminada) {
        super(NOMBRE);
        this.clase = claseEliminada;
    }

    /**
     * Retorna una representación legible del evento de eliminación de clase.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Clase eliminada [clase.toString()]
     *
     * Esta representación es útil para:
     * - Registros permanentes de auditoría
     * - Trazabilidad de eliminaciones en el sistema
     * - Mensajes en consola y archivos de log
     * - Reportes de operaciones de limpieza
     *
     * Ejemplo de salida:
     * Evento: Se eliminó una clase, Fecha: 2026-05-17 12:45:15.789,
     * Detalles: Clase eliminada [ Código: YOG001 | Nombre: Yoga | ... ]
     *
     * @return cadena de texto con formato descriptivo del evento de eliminación
     */
    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase eliminada %s",
                super.nombre, super.fecha, clase.toString());
    }
}