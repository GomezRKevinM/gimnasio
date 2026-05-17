package co.udc.ED.gimnasio.events.Instructor;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Instructor;

/**
 * Evento de Dominio - CrearInstructor
 *
 * Representa el evento que se dispara cuando se crea un nuevo instructor en el sistema.
 * Este evento forma parte del patrón Domain-Driven Design (DDD) y permite
 * mantener un registro auditable de todas los instructores registrados en el gimnasio.
 *
 * Características:
 * - Hereda de {@link DomainEvent} para mantener la trazabilidad con timestamp automático
 * - Encapsula la información del instructor creado
 * - Proporciona una representación detallada del evento para auditoría
 * - Registra el momento exacto de la creación del instructor
 *
 * Ejemplo de uso:
 * <pre>
 *   Instructor nuevoInstructor = new Instructor("Carlos López", UUID.randomUUID());
 *   DomainEvent evento = new CrearInstructor(nuevoInstructor);
 *   System.out.println(evento);
 *   // Evento: Se creó un nuevo instructor, Fecha: 2026-05-17 10:30:45.123, Detalles: Instructor creado [...]
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see DomainEvent
 * @see Instructor
 */
public class CrearInstructor extends DomainEvent {

    /**
     * Nombre descriptivo del evento que será registrado en el log de auditoría.
     * Este mensaje es estándar para todas las instancias de CrearInstructor.
     *
     * Valor: "Se creó un nuevo instructor"
     */
    private static final String NOMBRE = "Se creó un nuevo instructor";

    /**
     * Referencia a la instancia de {@link Instructor} que fue creada.
     * Contiene todos los detalles del instructor: nombre e identificador único (UUID).
     *
     * Este atributo es inmutable (final) para garantizar que el evento
     * representa un snapshot del instructor en el momento de su creación.
     */
    private final Instructor instructor;

    /**
     * Constructor de CrearInstructor
     *
     * Inicializa un nuevo evento de creación de instructor. El evento captura
     * automáticamente la fecha y hora actual del sistema a través del
     * constructor padre {@link DomainEvent}.
     *
     * @param instructorCreado la instancia de {@link Instructor} que fue creada recientemente.
     *                         No debe ser null.
     *
     * @throws NullPointerException si instructorCreado es null
     */
    public CrearInstructor(Instructor instructorCreado) {
        super(NOMBRE);
        this.instructor = instructorCreado;
    }

    /**
     * Retorna una representación legible del evento de creación de instructor.
     *
     * Formato: Evento: [nombre del evento], Fecha: [timestamp], Detalles: Instructor creado [instructor.toString()]
     *
     * Esta representación es útil para:
     * - Logging y auditoría del sistema
     * - Información en consola o logs
     * - Reportes de actividad de instructores
     * - Trazabilidad de altas en el sistema
     *
     * Ejemplo de salida:
     * Evento: Se creó un nuevo instructor, Fecha: 2026-05-17 10:30:45.123,
     * Detalles: Instructor creado [ Nombre: Carlos López | ID: a1b2c3d4-e5f6-... ]
     *
     * @return cadena de texto con formato descriptivo del evento
     */
    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Instructor creado %s",
                super.nombre, super.fecha, instructor.toString());
    }
}