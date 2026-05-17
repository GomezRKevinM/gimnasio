package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

/**
 * Interfaz que define el contrato para la gestión de inscripciones a clases del gimnasio.
 *
 * Esta interfaz establece las operaciones principales para administrar las inscripciones
 * de clientes a clases, incluyendo operaciones de búsqueda, creación, aprobación y cancelación.
 * Gestiona el ciclo de vida completo de una inscripción: desde su solicitud (PENDIENTE),
 * hasta su aprobación (FINALIZADA) o cancelación (CANCELADA).
 *
 * Modelo de estados de inscripción:
 * - PENDIENTE: inscripción en cola de espera, esperando aprobación o espacio disponible
 * - FINALIZADA: inscripción confirmada, cliente aceptado en la clase
 * - CANCELADA: inscripción rechazada o cancelada por el cliente
 *
 * Las implementaciones de esta interfaz son responsables de:
 * - Gestionar la cola de inscripciones pendientes (FIFO)
 * - Mantener registros por clase, estado y cliente
 * - Controlar transiciones de estado (PENDIENTE -> FINALIZADA o CANCELADA)
 * - Emitir eventos de dominio para auditoría
 * - Respetar la integridad referencial con clases e instructores
 *
 * Implementaciones esperadas:
 * - Almacenamiento en memoria (CustomFixture con Cola y Lista)
 * - Persistencia en base de datos (JPA/Hibernate, JDBC)
 * - Integración con sistema de notificaciones
 *
 * Ejemplo de uso:
 * <pre>
 *   InscripcionClaseService servicio = new InscripcionClaseServiceImpl(...);
 *
 *   // Agregar nueva inscripción (entra como PENDIENTE)
 *   InscripcionClase nueva = new InscripcionClase(...);
 *   InscripcionClase agregada = servicio.agregarInscripcion(nueva);
 *
 *   // Consultar pendientes
 *   Cola pendientes = servicio.obtenerInscripcionesPendientes();
 *   InscripcionClase primera = servicio.obtenerInscripcionPendiente();
 *
 *   // Aprobar inscripción
 *   InscripcionClase aprobada = servicio.aprobarInscripcion(primera);
 *
 *   // Cancelar si es necesario
 *   InscripcionClase cancelada = servicio.cancelarInscripcion(aprobada);
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see InscripcionClase
 * @see co.udc.ED.gimnasio.Services.SistemaInscripcionClase
 * @see co.udc.ED.gimnasio.enums.InscripcionEstado
 * @see co.udc.ED.gimnasio.fixture.CustomFixture
 */
public interface InscripcionClaseService {

    /**
     * Obtiene la cola de todas las inscripciones en estado PENDIENTE.
     *
     * Devuelve una estructura {@link Cola} (FIFO) que contiene las inscripciones
     * en espera de aprobación. Esta cola es el principal flujo de trabajo para
     * procesar nuevas solicitudes en orden de llegada.
     *
     * Nota: El orden FIFO es critical para mantener equidad (primero en entrar,
     * primero en ser procesado).
     *
     * @return una {@link Cola} con todas las inscripciones en estado PENDIENTE.
     *         Nunca devuelve null; devuelve una cola vacía si no hay inscripciones pendientes
     *
     * @see InscripcionClase#estado()
     */
    Cola obtenerInscripcionesPendientes();

    /**
     * Obtiene todas las inscripciones asociadas a una clase específica.
     *
     * Devuelve una {@link Lista} de todas las inscripciones (sin importar su estado)
     * que fueron realizadas para la clase con el código proporcionado. Útil para
     * obtener un resumen de quién se ha inscrito a una clase en particular.
     *
     * Nota: Esta búsqueda incluye inscripciones en TODOS los estados:
     * PENDIENTE, FINALIZADA y CANCELADA.
     *
     * @param codigoClase el código único de la clase (ej: "YOG001")
     * @return una {@link Lista} con todas las inscripciones para esa clase.
     *         Nunca devuelve null; devuelve una lista vacía si no hay inscripciones
     *
     * @throws IllegalArgumentException si codigoClase es null o vacío
     */
    Lista obtenerInscripcionesPorClase(String codigoClase);

    /**
     * Obtiene todas las inscripciones en estado CANCELADA.
     *
     * Devuelve un registro histórico de inscripciones que fueron rechazadas o
     * canceladas por el cliente. Esta información es importante para auditoría,
     * análisis de patrones de cancelación y trazabilidad.
     *
     * @return una {@link Lista} con todas las inscripciones canceladas.
     *         Nunca devuelve null; devuelve una lista vacía si ninguna inscripción ha sido cancelada
     *
     * @see #cancelarInscripcion(InscripcionClase)
     */
    Lista obtenerInscripcionesCanceladas();

    /**
     * Obtiene la primera inscripción en la cola de pendientes (sin removerla).
     *
     * Retorna la inscripción en la cabecera de la cola FIFO de inscripciones
     * pendientes sin modificar la estructura (operación peek, no desencolar).
     * Útil para consultar cuál será la próxima a procesar sin comprometerse.
     *
     * Nota: Si la cola está vacía, el comportamiento depende de la implementación
     * (puede retornar null o lanzar excepción).
     *
     * @return la {@link InscripcionClase} que está en la cabecera de la cola pendiente,
     *         o null si no hay inscripciones pendientes
     *
     * @see #obtenerInscripcionesPendientes()
     */
    InscripcionClase obtenerInscripcionPendiente();

    /**
     * Obtiene todas las inscripciones en estado FINALIZADA.
     *
     * Devuelve una {@link Lista} con todas las inscripciones confirmadas y activas.
     * Estas son los clientes que han sido aprobados y tienen lugar garantizado en
     * sus respectivas clases.
     *
     * @return una {@link Lista} con todas las inscripciones finalizadas.
     *         Nunca devuelve null; devuelve una lista vacía si no hay inscripciones finalizadas
     *
     * @see #aprobarInscripcion(InscripcionClase)
     */
    Lista obtenerInscripcionesFinalizadas();

    /**
     * Agrega una nueva inscripción al sistema.
     *
     * Crea una nueva solicitud de inscripción con estado inicial PENDIENTE.
     * La inscripción se agrega a la cola de espera para ser procesada posteriormente.
     * Esta operación emite un evento de dominio para auditoría (CrearInscripcion).
     *
     * Comportamiento esperado:
     * - La inscripción se crea con estado PENDIENTE
     * - Se agrega a la cola de inscripciones pendientes (FIFO)
     * - Si ya existe inscripción del mismo cliente en la misma clase, el comportamiento
     *   es según política (rechazar, actualizar, o permitir duplicado)
     * - Valida que la clase y el cliente existan
     *
     * @param inscripcion la nueva inscripción a agregar. Debe tener código de clase
     *                    y cliente válidos, y fecha de inscripción
     * @return la {@link InscripcionClase} creada, con estado PENDIENTE y datos persistidos
     *
     * @throws NullPointerException si inscripcion es null
     * @throws IllegalArgumentException si los datos obligatorios son inválidos
     *         o si la clase no existe
     *
     * @see co.udc.ED.gimnasio.enums.InscripcionEstado#PENDIENTE
     */
    InscripcionClase agregarInscripcion(InscripcionClase inscripcion);

    /**
     * Aprueba una inscripción pendiente, cambiando su estado a FINALIZADA.
     *
     * Transiciona una inscripción del estado PENDIENTE a FINALIZADA, confirmando
     * que el cliente tiene lugar garantizado en la clase. Esta operación es
     * típicamente realizada por un administrador o automáticamente cuando hay
     * cupo disponible.
     *
     * Comportamiento esperado:
     * - Solo inscripciones en estado PENDIENTE pueden ser aprobadas
     * - Después de la aprobación, el cliente tiene derecho a asistir a la clase
     * - Se emite un evento de dominio para auditoría (ModificarInscripcion)
     * - Si la inscripción ya fue aprobada/cancelada, el comportamiento depende
     *   de la política (rechazar o ignorar)
     *
     * @param inscripcion la inscripción a aprobar. Debe estar en estado PENDIENTE
     * @return la {@link InscripcionClase} aprobada, con estado actualizado a FINALIZADA
     *
     * @throws NullPointerException si inscripcion es null
     * @throws IllegalArgumentException si la inscripción no está en estado PENDIENTE
     *         o si no existe en el sistema
     *
     * @see co.udc.ED.gimnasio.enums.InscripcionEstado#FINALIZADA
     */
    InscripcionClase aprobarInscripcion(InscripcionClase inscripcion);

    /**
     * Cancela una inscripción, cambiando su estado a CANCELADA.
     *
     * Transiciona una inscripción (tipicamente en estado PENDIENTE o FINALIZADA)
     * a estado CANCELADA. Esto puede ocurrir cuando:
     * - El cliente elige cancelar su inscripción
     * - Un administrador rechaza la solicitud
     * - La clase es cancelada
     *
     * Comportamiento esperado:
     * - Cualquier inscripción puede ser cancelada (sin importar su estado actual)
     * - La inscripción se mueve del estado actual a CANCELADA
     * - Si estaba pendiente, se libera espacio en la cola para la próxima inscripción
     * - Se emite un evento de dominio para auditoría (ModificarInscripcion o EliminarInscripcion)
     * - Se pueden enviar notificaciones al cliente
     *
     * @param inscripcion la inscripción a cancelar
     * @return la {@link InscripcionClase} cancelada, con estado actualizado a CANCELADA
     *
     * @throws NullPointerException si inscripcion es null
     * @throws IllegalArgumentException si la inscripción no existe en el sistema
     *
     * @see co.udc.ED.gimnasio.enums.InscripcionEstado#CANCELADA
     */
    InscripcionClase cancelarInscripcion(InscripcionClase inscripcion);
}