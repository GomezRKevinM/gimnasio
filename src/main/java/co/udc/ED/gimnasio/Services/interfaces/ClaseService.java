package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Clase;

/**
 * Interfaz que define el contrato para la gestión de clases del gimnasio.
 *
 * Esta interfaz establece las operaciones principales que un servicio de clases
 * debe implementar, incluyendo operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre las clases ofrecidas por el gimnasio.
 *
 * Las implementaciones de esta interfaz son responsables de:
 * - Recuperar todas las clases o buscar por criterios específicos
 * - Agregar nuevas clases al sistema
 * - Modificar información de clases existentes
 * - Eliminar clases del sistema
 *
 * Implementaciones esperadas pueden usar diferentes fuentes de datos:
 * - Almacenamiento en memoria (CustomFixture)
 * - Persistencia en base de datos (JPA/Hibernate, JDBC)
 * - Servicios remotos
 *
 * Ejemplo de uso:
 * <pre>
 *   ClaseService servicio = new ClaseServiceImpl(...);
 *   Lista todasLasClases = servicio.obtenerClases();
 *   Clase yoga = servicio.obtenerClasePorCodigo("YOG001");
 *   servicio.modificarClase(yoga);
 * </pre>
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Clase
 * @see Lista
 */
public interface ClaseService {

    /**
     * Recupera todas las clases registradas en el sistema.
     *
     * Este método devuelve una estructura de datos {@link Lista} que contiene
     * todas las clases disponibles en el gimnasio. Si no hay clases registradas,
     * devuelve una lista vacía.
     *
     * Complejidad: depende de la implementación (O(1) si ya está cacheada, O(n) si requiere consulta).
     *
     * @return una {@link Lista} con todas las clases del sistema, nunca null
     *         (puede estar vacía si no hay clases)
     */
    Lista obtenerClases();

    /**
     * Obtiene una clase específica por su código único.
     *
     * Busca en el sistema una clase que coincida con el código proporcionado.
     * El código es el identificador único de cada clase en el gimnasio.
     *
     * @param codigo el código único de la clase a buscar (ej: "YOG001", "PILATES_01")
     * @return la {@link Clase} con el código especificado, o null si no existe
     *         ninguna clase con ese código
     *
     * @throws IllegalArgumentException si el código es null o vacío (comportamiento recomendado,
     *         pero depende de la implementación)
     */
    Clase obtenerClasePorCodigo(String codigo);

    /**
     * Agrega una nueva clase al sistema.
     *
     * Este método persiste una nueva instancia de {@link Clase} en el almacenamiento
     * del sistema. La clase debe tener todos sus atributos obligatorios completos,
     * incluyendo un código único que no exista previamente en el sistema.
     *
     * Comportamiento esperado:
     * - Si la clase ya existe (mismo código), la implementación puede lanzar excepción
     *   o rechazar silenciosamente según política definida
     * - Si los datos son inválidos, puede devolver null o lanzar excepción
     * - Los cambios se persisten en el almacenamiento elegido
     *
     * @param clase la clase a agregar al sistema. Debe estar completamente inicializada
     *              con código único, nombre, instructor, etc.
     *
     * @throws NullPointerException si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase carece de datos obligatorios
     *         o si el código ya existe
     */
    void agregarClase(Clase clase);

    /**
     * Elimina una clase del sistema.
     *
     * Remueve la clase especificada del almacenamiento. Si la clase está asociada
     * con inscripciones activas, el comportamiento depende de la política de
     * la implementación (puede ejecutarse cascada, lanzar excepción, etc.).
     *
     * @param clase la clase a eliminar. Típicamente se busca por su código
     *              para identificar el registro a eliminar
     *
     * @throws NullPointerException si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase no existe en el sistema
     */
    void eliminarClase(Clase clase);

    /**
     * Modifica los datos de una clase existente.
     *
     * Actualiza la información de una clase ya registrada en el sistema.
     * La clase se identifica por su código (que no debe cambiar). Los demás
     * atributos pueden ser actualizados: nombre, descripción, duración, instructor, etc.
     *
     * Comportamiento esperado:
     * - La clase debe existir previamente (identificada por su código)
     * - Se actualiza todos los atributos con los nuevos valores proporcionados
     * - Devuelve la clase modificada (posiblemente con datos adicionales como timestamp de modificación)
     * - Si la clase no existe, comportamiento según política (null, excepción, agregar nueva)
     *
     * @param clase la clase con los datos modificados. El código debe corresponder
     *              a una clase existente en el sistema
     * @return la clase actualizada después de la modificación, o null si no pudo completarse
     *
     * @throws NullPointerException si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase no existe en el sistema
     *
     * @see #agregarClase(Clase)
     */
    Clase modificarClase(Clase clase);
}