package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Instructor;

/**
 * Interfaz que define el contrato para la gestión de instructores del gimnasio.
 *
 * Esta interfaz establece las operaciones principales que un servicio de instructores
 * debe implementar, incluyendo operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre los instructores del gimnasio. Además, proporciona funcionalidad para
 * gestionar un registro de instructores eliminados.
 *
 * Las implementaciones de esta interfaz son responsables de:
 * - Recuperar todos los instructores activos o buscar por criterios específicos
 * - Crear nuevos instructores en el sistema
 * - Modificar información de instructores existentes
 * - Eliminar instructores (con opción de recuperar historial de eliminados)
 * - Mantener un registro de instructores que han sido eliminados
 *
 * Implementaciones esperadas:
 * - Almacenamiento en memoria (CustomFixture)
 * - Persistencia en base de datos (JPA/Hibernate, JDBC)
 * - Servicios remotos o capas de integración
 *
 * Ejemplo de uso:
 * <pre>
 *   InstructorService servicio = new InstructorServiceImpl(...);
 *   Lista instructores = servicio.obtenerInstructores();
 *   Instructor carlos = servicio.obtenerInstructorPorCodigo("uuid-123");
 *   servicio.modificarInstructor(carlos);
 *   servicio.eliminarInstructor("uuid-123");
 *   Lista eliminados = servicio.obtenerInstructoresEliminados();
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Instructor
 * @see co.udc.ED.gimnasio.Services.SistemaInstructor
 * @see co.udc.ED.gimnasio.fixture.CustomFixture
 */
public interface InstructorService {

    /**
     * Obtiene la lista de todos los instructores activos en el sistema.
     *
     * Devuelve una estructura {@link Lista} que contiene todos los instructores
     * que se encuentran activos (no eliminados) en el gimnasio. Este método es
     * fundamental para operaciones que requieren acceso al conjunto completo
     * de instructores disponibles.
     *
     * Nota: Los instructores eliminados NO aparecen en este listado;
     * para recuperarlos, usar {@link #obtenerInstructoresEliminados()}.
     *
     * @return una {@link Lista} con todos los instructores activos del sistema.
     *         Nunca devuelve null; devuelve una lista vacía si no hay instructores
     */
    Lista obtenerInstructores();

    /**
     * Obtiene la lista de instructores que han sido eliminados del sistema.
     *
     * Devuelve un registro histórico de instructores que fueron removidos pero
     * se mantienen registrados para auditoría y trazabilidad. Esta funcionalidad
     * es importante para mantener la integridad referencial si otras entidades
     * (como clases) hacen referencia a instructores históricos.
     *
     * @return una {@link Lista} con todos los instructores eliminados.
     *         Nunca devuelve null; devuelve una lista vacía si ningún instructor ha sido eliminado
     *
     * @see #eliminarInstructor(String)
     */
    Lista obtenerInstructoresEliminados();

    /**
     * Crea un nuevo instructor en el sistema.
     *
     * Persiste una nueva instancia de {@link Instructor} en el almacenamiento.
     * El instructor debe tener todos sus atributos obligatorios completos,
     * incluyendo un UUID único que no exista previamente en el sistema.
     *
     * Comportamiento esperado:
     * - El instructor se identifica de forma única por su UUID
     * - Si el UUID ya existe en el sistema, la implementación puede rechazar la operación
     *   (lanzar excepción o devolver silenciosamente)
     * - El instructor se agrega a la lista de instructores activos
     * - Emite un evento de dominio para auditoría (CrearInstructor)
     *
     * @param instructor el instructor a crear en el sistema. Debe estar completamente
     *                   inicializado con nombre y UUID únicos
     *
     * @throws NullPointerException si instructor es null (comportamiento recomendado)
     * @throws IllegalArgumentException si el instructor carece de datos obligatorios
     *         o si el UUID ya existe en el sistema
     *
     * @see Instructor
     */
    Instructor crearInstructor(Instructor instructor);

    /**
     * Obtiene un instructor específico por su código UUID.
     *
     * Busca en el sistema un instructor que coincida con el código (UUID) proporcionado.
     * El UUID es el identificador único e inmutable de cada instructor.
     *
     * Nota: Esta búsqueda solo incluye instructores activos; para buscar en eliminados,
     * usar {@link #obtenerInstructoresEliminados()}.
     *
     * @param codigo el UUID del instructor a buscar, típicamente en formato String
     *               (ej: "550e8400-e29b-41d4-a716-446655440000")
     * @return el {@link Instructor} con el UUID especificado, o null si no existe
     *         ningún instructor activo con ese código
     *
     * @throws IllegalArgumentException si el código es null o no es un UUID válido
     *         (comportamiento recomendado, depende de la implementación)
     */
    Instructor obtenerInstructorPorCodigo(String codigo);

    /**
     * Modifica los datos de un instructor existente.
     *
     * Actualiza la información de un instructor ya registrado en el sistema.
     * El instructor se identifica por su UUID (que no cambia). Los demás atributos
     * como nombre pueden ser actualizados.
     *
     * Comportamiento esperado:
     * - El instructor debe existir previamente (identificado por su UUID)
     * - Se actualiza todos los atributos con los nuevos valores proporcionados
     * - Devuelve el instructor modificado (potencialmente con metadatos como fecha de modificación)
     * - Si el instructor no existe, el comportamiento es según política de la implementación
     * - Emite un evento de dominio para auditoría (ModificarInstructor)
     *
     * @param instructor el instructor con los datos modificados. Su UUID debe
     *                   corresponder a un instructor existente en el sistema
     * @return el instructor actualizado después de la modificación, o null si no pudo completarse
     *         la operación
     *
     * @throws NullPointerException si instructor es null (comportamiento recomendado)
     * @throws IllegalArgumentException si el instructor no existe en el sistema
     *
     * @see #crearInstructor(Instructor)
     */
    Instructor modificarInstructor(Instructor instructor);

    /**
     * Elimina un instructor del sistema.
     *
     * Remueve un instructor de la lista de activos y lo registra en el histórico
     * de instructores eliminados. Esta operación es importante para mantener
     * la auditoría y para preservar referencias si otras entidades (como clases)
     * apuntan al instructor eliminado.
     *
     * Comportamiento esperado:
     * - El instructor se mueve de la lista activa al histórico de eliminados
     * - Los datos del instructor se conservan (no se pierden completamente)
     * - Si el instructor tiene clases asociadas, el comportamiento depende de la política
     *   de la implementación (puede cascada, bloquear, o dejar referencia orfila)
     * - Emite un evento de dominio para auditoría (EliminarInstructor)
     *
     * @param codigo el UUID del instructor a eliminar (mismo códico usado en búsqueda)
     *
     * @throws IllegalArgumentException si el código es null o si el instructor no existe
     *         (comportamiento recomendado)
     * @throws Exception si ocurre un error al eliminar (ej: violación de integridad referencial)
     *
     * @see #obtenerInstructoresEliminados()
     */
    Instructor eliminarInstructor(String codigo);
}