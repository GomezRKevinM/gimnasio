package co.udc.ED.gimnasio.services;

import co.udc.ED.gimnasio.events.Instructor.CrearInstructor;
import co.udc.ED.gimnasio.events.Instructor.EliminarInstructor;
import co.udc.ED.gimnasio.events.Instructor.ModificarInstructor;
import co.udc.ED.gimnasio.fixture.CustomFixture;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Instructor;
import co.udc.ED.gimnasio.services.interfaces.InstructorService;
import co.udc.ED.gimnasio.utils.Color;

import java.util.UUID;

/**
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 */
public class SistemaInstructor implements InstructorService {

    /**
     * Obtiene la lista de todos los instructores activos en el sistema.
     * <p>
     * Devuelve una estructura {@link Lista} que contiene todos los instructores
     * que se encuentran activos (no eliminados) en el gimnasio. Este método es
     * fundamental para operaciones que requieren acceso al conjunto completo
     * de instructores disponibles.
     * <p>
     * Nota: Los instructores eliminados NO aparecen en este listado;
     * para recuperarlos, usar {@link #obtenerInstructoresEliminados()}.
     *
     * @return una {@link Lista} con todos los instructores activos del sistema.
     * Nunca devuelve null; devuelve una lista vacía si no hay instructores
     */
    @Override
    public Lista obtenerInstructores() {
        return CustomFixture.INSTRUCTORES;
    }

    /**
     * Obtiene la lista de instructores que han sido eliminados del sistema.
     * <p>
     * Devuelve un registro histórico de instructores que fueron removidos pero
     * se mantienen registrados para auditoría y trazabilidad. Esta funcionalidad
     * es importante para mantener la integridad referencial si otras entidades
     * (como clases) hacen referencia a instructores históricos.
     *
     * @return una {@link Lista} con todos los instructores eliminados.
     * Nunca devuelve null; devuelve una lista vacía si ningún instructor ha sido eliminado
     * @see #eliminarInstructor(String)
     */
    @Override
    public Lista obtenerInstructoresEliminados() {
        return CustomFixture.INSTRUCTORES_ELIMINADOS;
    }

    /**
     * Crea un nuevo instructor en el sistema.
     * <p>
     * Persiste una nueva instancia de {@link Instructor} en el almacenamiento.
     * El instructor debe tener todos sus atributos obligatorios completos,
     * incluyendo un UUID único que no exista previamente en el sistema.
     * <p>
     * Comportamiento esperado:
     * - El instructor se identifica de forma única por su UUID
     * - Si el UUID ya existe en el sistema, la implementación puede rechazar la operación
     * (lanzar excepción o devolver silenciosamente)
     * - El instructor se agrega a la lista de instructores activos
     * - Emite un evento de dominio para auditoría (CrearInstructor)
     *
     * @param instructor el instructor a crear en el sistema. Debe estar completamente
     *                   inicializado con nombre y UUID únicos
     * @throws NullPointerException     si instructor es null (comportamiento recomendado)
     * @throws IllegalArgumentException si el instructor carece de datos obligatorios
     *                                  o si el UUID ya existe en el sistema
     * @see Instructor
     */
    @Override
    public Instructor crearInstructor(Instructor instructor) {
        if(instructor == null){
            System.out.println(Color.RED + "    [ERROR] instructor sin argumentos" + Color.RESET);
            return null;
        }

        boolean existe = CustomFixture.INSTRUCTORES.contiene(instructor);

        if(existe){
            System.out.println(Color.RED +"    [ERROR] instructor ya existe en el sistema"+ Color.RESET);
            return null;
        }

        CustomFixture.INSTRUCTORES.agregar(instructor);
        CrearInstructor operacion = new CrearInstructor(instructor);
        System.out.println(Color.GREEN + operacion.toString() + Color.RESET);
        CustomFixture.OPERACIONES.apilar(new CrearInstructor(instructor));
        return instructor;
    }

    /**
     * Obtiene un instructor específico por su código UUID.
     * <p>
     * Busca en el sistema un instructor que coincida con el código (UUID) proporcionado.
     * El UUID es el identificador único e inmutable de cada instructor.
     * <p>
     * Nota: Esta búsqueda solo incluye instructores activos; para buscar en eliminados,
     * usar {@link #obtenerInstructoresEliminados()}.
     *
     * @param codigo el UUID del instructor a buscar, típicamente en formato String
     *               (ej: "550e8400-e29b-41d4-a716-446655440000")
     * @return el {@link Instructor} con el UUID especificado, o null si no existe
     * ningún instructor activo con ese código
     * @throws IllegalArgumentException si el código es null o no es un UUID válido
     *                                  (comportamiento recomendado, depende de la implementación)
     */
    @Override
    public Instructor obtenerInstructorPorCodigo(String codigo) {
        if(codigo == null || codigo.isEmpty()){
            System.out.println(Color.RED +"    [ERROR] El codigo no puede estar vacio."+Color.RESET);
            return null;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(codigo);
        } catch (IllegalArgumentException error) {
            System.out.println(Color.RED +"    [ERROR] El codigo no tiene formato UUID valido."+Color.RESET);
            return null;
        }

        Instructor instructor = new Instructor(null, uuid);
        return (Instructor) CustomFixture.INSTRUCTORES.buscarDato(instructor);
    }

    /**
     * Modifica los datos de un instructor existente.
     * <p>
     * Actualiza la información de un instructor ya registrado en el sistema.
     * El instructor se identifica por su UUID (que no cambia). Los demás atributos
     * como nombre pueden ser actualizados.
     * <p>
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
     * la operación
     * @throws NullPointerException     si instructor es null (comportamiento recomendado)
     * @throws IllegalArgumentException si el instructor no existe en el sistema
     * @see #crearInstructor(Instructor)
     */
    @Override
    public Instructor modificarInstructor(Instructor instructor) {
        if(instructor == null){
            System.out.println(Color.RED + "    [ERROR] instructor sin argumentos" + Color.RESET);
            return null;
        }

        Instructor oldData = (Instructor) CustomFixture.INSTRUCTORES.buscarDato(instructor);
        int index = CustomFixture.INSTRUCTORES.encontrarIndexDe(oldData);

        if(index < 0){
            System.out.println(Color.RED +"    [ERROR] El instructor no existe en el sistema."+Color.RESET);
            return null;
        }

        if(oldData.nombre().equals(instructor.nombre())){
            System.out.println(Color.RED +"    [ERROR] El instructor no ha sido modificado, el nombre es igual al anterior."+Color.RESET);
            return oldData;
        }

        if (instructor.nombre() == null) {
            System.out.println(Color.RED +"    [ERROR] El nombre no puede estar vacío."+Color.RESET);
            return null;
        }

        ModificarInstructor operacion = new ModificarInstructor(instructor);
        CustomFixture.OPERACIONES.apilar(operacion);
        System.out.println(Color.YELLOW + operacion.toString() + Color.RESET);
        return (Instructor) CustomFixture.INSTRUCTORES.editarNodo(index,instructor);
    }

    /**
     * Elimina un instructor del sistema.
     * <p>
     * Remueve un instructor de la lista de activos y lo registra en el histórico
     * de instructores eliminados. Esta operación es importante para mantener
     * la auditoría y para preservar referencias si otras entidades (como clases)
     * apuntan al instructor eliminado.
     * <p>
     * Comportamiento esperado:
     * - El instructor se mueve de la lista activa al histórico de eliminados
     * - Los datos del instructor se conservan (no se pierden completamente)
     * - Si el instructor tiene clases asociadas, el comportamiento depende de la política
     * de la implementación (puede cascada, bloquear, o dejar referencia orfila)
     * - Emite un evento de dominio para auditoría (EliminarInstructor)
     *
     * @param codigo el UUID del instructor a eliminar (mismo códico usado en búsqueda)
     * @throws IllegalArgumentException si el código es null o si el instructor no existe
     *                                  (comportamiento recomendado)
     * @throws Exception                si ocurre un error al eliminar (ej: violación de integridad referencial)
     * @see #obtenerInstructoresEliminados()
     */
    @Override
    public Instructor eliminarInstructor(String codigo) {
        Instructor instructor = obtenerInstructorPorCodigo(codigo);
        if(instructor == null){
            System.out.println(Color.RED +"    [ERROR] instructor no encontrado"+Color.RESET);
            return null;
        }
        int index = CustomFixture.INSTRUCTORES.encontrarIndexDe(instructor);
        if(index < 0){
            System.out.println(Color.RED +"    [ERROR] instructor no encontrado"+Color.RESET);
            return null;
        }
        EliminarInstructor operacion = new EliminarInstructor(instructor);
        CustomFixture.OPERACIONES.apilar(operacion);
        Instructor eliminado = (Instructor) CustomFixture.INSTRUCTORES.eliminarEnPosicion(index);
        CustomFixture.INSTRUCTORES_ELIMINADOS.agregar(eliminado);
        System.out.println(Color.RED + operacion.toString() + Color.RESET);
        return eliminado;
    }
}
