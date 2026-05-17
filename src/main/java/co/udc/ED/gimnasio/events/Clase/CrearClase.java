package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

/**
 * Evento de Dominio - CrearClase
 *
 * Representa el evento que se dispara cuando se crea una nueva clase en el sistema.
 * Este evento forma parte del patrón Domain-Driven Design (DDD) y permite
 * mantener un registro auditable de todas las clases creadas en el gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para mantener la trazabilidad con timestamp automático
 * - Encapsula la información de la clase creada
 * - Proporciona una representación detallada del evento para auditoría
 *
 * Ejemplo de uso:
 * <pre>
 *   Instructor instructor = new Instructor("Carlos López", UUID.randomUUID());
 *   Clase yoga = new Clase("YOG001", "Yoga", "Clase de yoga relajante", 1,
 *                          instructor, new Lista(), new Cola());
 *   DomainEvent evento = new CrearClase(yoga);
 *   System.out.println(evento);
 *   // Evento: Se creó una nueva clase, Fecha: 2026-05-17 10:30:45.123, Detalles: Clase creada [...]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see Clase
 */
public class CrearClase extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en el log de auditoría.
     * Este mensaje es estándar para todas las instancias de CrearClase.
     *
     * Valor: "Se creó una nueva clase"
     */
    private static final String NOMBRE = "Se creó una nueva clase";

    /**
     * Referencia a la instancia de {@link Clase} que fue creada.
     * Contiene todos los detalles de la clase: código, nombre, descripción,
     * duración, instructor asignado, inscripciones y cola de espera.
     *
     * Este atributo es inmutable (final) para garantizar que el evento
     * representa un snapshot de la clase en el momento de su creación.
     */
    private final Clase clase;

    /**
     * Constructor de CrearClase
     *
     * Inicializa un nuevo evento de creación de clase. El evento captura
     * automáticamente la fecha y hora actual del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * @param claseCreada la instancia de {@link Clase} que fue creada recientemente.
     *                     No debe ser null.
     *
     * @throws NullPointerException si claseCreada es null
     */
    public CrearClase(Clase claseCreada){
        super(NOMBRE);
        this.clase = claseCreada;
    }

    /**
     * Retorna una representación legible del evento de creación de clase.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Clase creada [clase.toString()]
     *
     * Esta representación es útil para:
     * - Logging y auditoría del sistema
     * - Información en consola o logs
     * - Reportes de actividad
     *
     * Ejemplo de salida:
     * Evento: Se creó una nueva clase, Fecha: 2026-05-17 10:30:45.123,
     * Detalles: Clase creada [ Código: YOG001 | Nombre: Yoga | ... ]
     *
     * @return cadena de texto con formato descriptivo del evento
     */
    @Override
    public String toString() {
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase creada %s",
                super.nombre, super.fecha, clase.toString());
    }
}