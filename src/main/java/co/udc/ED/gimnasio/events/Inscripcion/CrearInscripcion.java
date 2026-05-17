package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

/**
 * Evento de Dominio - CrearInscripcion
 *
 * Representa el evento que se dispara cuando se crea una nueva inscripción de un cliente
 * a una clase del gimnasio. Este evento es parte del patrón Domain-Driven Design (DDD)
 * y permite mantener un registro auditable de todas las inscripciones registradas.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para mantener la trazabilidad con timestamp automático
 * - Encapsula la información completa de la inscripción creada
 * - Proporciona una representación detallada del evento para auditoría
 * - Registra el momento exacto en que un cliente se inscribe a una clase
 *
 * Ejemplo de uso:
 * <pre>
 *   Timestamp ahora = new Timestamp(System.currentTimeMillis());
 *   InscripcionClase nuevaInscripcion = new InscripcionClase(ahora, "YOG001", "Juan Pérez");
 *   DomainEvent evento = new CrearInscripcion(nuevaInscripcion);
 *   System.out.println(evento);
 *   // Evento: Se creó una nueva inscripción, Fecha: 2026-05-17 10:30:45.123,
 *   // Detalles: Inscripción creada [ Fecha: 2026-05-17 10:30:45.123 | Cliente: Juan Pérez | Código de Clase: YOG001 ]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see InscripcionClase
 */
public class CrearInscripcion extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en el log de auditoría.
     * Este mensaje es estándar para todas las instancias de CrearInscripcion.
     *
     * Valor: "Se creó una nueva inscripción"
     */
    private static final String NOMBRE = "Se creó una nueva inscripción";

    /**
     * Referencia a la instancia de {@link InscripcionClase} que fue creada.
     * Contiene todos los detalles de la inscripción: fecha de inscripción,
     * código de la clase y nombre del cliente.
     *
     * Este atributo es inmutable (final) para garantizar que el evento
     * representa un snapshot de la inscripción en el momento de su creación.
     */
    private final InscripcionClase inscripcion;

    /**
     * Constructor de CrearInscripcion
     *
     * Inicializa un nuevo evento de creación de inscripción. El evento captura
     * automáticamente la fecha y hora actual del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * @param inscripcionCreada la instancia de {@link InscripcionClase} que fue creada recientemente.
     *                          Contiene la información del cliente, clase y fecha de inscripción.
     *                          No debe ser null.
     *
     * @throws NullPointerException si inscripcionCreada es null
     */
    public CrearInscripcion(InscripcionClase inscripcionCreada) {
        super(NOMBRE);
        this.inscripcion = inscripcionCreada;
    }

    /**
     * Retorna una representación legible del evento de creación de inscripción.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Inscripción creada [inscripcion.toString()]
     *
     * Esta representación es útil para:
     * - Logging y auditoría del sistema
     * - Información en consola o logs
     * - Reportes de inscripciones realizadas
     * - Trazabilidad de altas de clientes
     *
     * Ejemplo de salida:
     * Evento: Se creó una nueva inscripción, Fecha: 2026-05-17 10:30:45.123,
     * Detalles: Inscripción creada [ Fecha: 2026-05-17 10:30:45.123 | Cliente: Juan Pérez | Código de Clase: YOG001 ]
     *
     * @return cadena de texto con formato descriptivo del evento
     */
    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción creada %s",
                super.nombre, super.fecha, inscripcion.toString());
    }
}