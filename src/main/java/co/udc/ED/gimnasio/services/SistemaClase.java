package co.udc.ED.gimnasio.services;

import co.udc.ED.gimnasio.events.Clase.CrearClase;
import co.udc.ED.gimnasio.events.Clase.EliminarClase;
import co.udc.ED.gimnasio.events.Clase.ModificarClase;
import co.udc.ED.gimnasio.fixture.CustomFixture;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Clase;
import co.udc.ED.gimnasio.services.interfaces.ClaseService;

public class SistemaClase implements ClaseService {

    /**
     * Recupera todas las clases registradas en el sistema.
     * <p>
     * Este método devuelve una estructura de datos {@link Lista} que contiene
     * todas las clases disponibles en el gimnasio. Si no hay clases registradas,
     * devuelve una lista vacía.
     * <p>
     * Complejidad: depende de la implementación (O(1) si ya está cacheada, O(n) si requiere consulta).
     *
     * @return una {@link Lista} con todas las clases del sistema, nunca null
     * (puede estar vacía si no hay clases)
     */
    @Override
    public Lista obtenerClases() {
        return CustomFixture.CLASES;
    }

    /**
     * Obtiene una clase específica por su código único.
     * <p>
     * Busca en el sistema una clase que coincida con el código proporcionado.
     * El código es el identificador único de cada clase en el gimnasio.
     *
     * @return la {@link Clase} con el código especificado, o null si no existe
     * ninguna clase con ese código
     * @throws IllegalArgumentException si el código es null o vacío (comportamiento recomendado,
     *                                  pero depende de la implementación)
     */
    @Override
    public Clase obtenerClasePorCodigo(String codigo) {
        if(codigo == null){
            System.out.println("    [ERROR] clase vacia");
            return null;
        }

        int index = CustomFixture.CLASES.encontrarIndexDe(codigo);

        if(index < 0){
            System.out.println("    [ERROR] clase no existe en el sistema");
            return null;
        }
        return (Clase) CustomFixture.CLASES.buscarDato(index);
    }

    /**
     * Agrega una nueva clase al sistema.
     * <p>
     * Este método persiste una nueva instancia de {@link Clase} en el almacenamiento
     * del sistema. La clase debe tener todos sus atributos obligatorios completos,
     * incluyendo un código único que no exista previamente en el sistema.
     * <p>
     * Comportamiento esperado:
     * - Si la clase ya existe (mismo código), la implementación puede lanzar excepción
     * o rechazar silenciosamente según política definida
     * - Si los datos son inválidos, puede devolver null o lanzar excepción
     * - Los cambios se persisten en el almacenamiento elegido
     *
     * @param clase la clase a agregar al sistema. Debe estar completamente inicializada
     *              con código único, nombre, instructor, etc.
     * @throws NullPointerException     si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase carece de datos obligatorios
     *                                  o si el código ya existe
     */
    @Override
    public Clase agregarClase(Clase clase) {
        if(clase == null) return null;

         boolean existe = CustomFixture.CLASES.contiene(clase);
         if (existe){
             System.out.println("    [ERROR] clase ya existe en el sistema");
             return null;
         }
         CustomFixture.OPERACIONES.apilar(new CrearClase(clase));
         String print = CustomFixture.OPERACIONES.peek().toString();
        System.out.println(print);
         CustomFixture.CLASES.agregar(clase);
         return clase;
    }

    /**
     * Elimina una clase del sistema.
     * <p>
     * Remueve la clase especificada del almacenamiento. Si la clase está asociada
     * con inscripciones activas, el comportamiento depende de la política de
     * la implementación (puede ejecutarse cascada, lanzar excepción, etc.).
     *
     * @param clase la clase a eliminar. Típicamente se busca por su código
     *              para identificar el registro a eliminar
     * @throws NullPointerException     si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase no existe en el sistema
     */
    @Override
    public Clase eliminarClase(Clase clase) {
        if(clase == null){
            System.out.println("    [ERROR] clase vacia");
            return null;
        }

        if (!CustomFixture.CLASES.contiene(clase)){
            System.out.println("    [ERROR] clase no existe en el sistema");
            return null;
        }
        CustomFixture.OPERACIONES.apilar(new EliminarClase(clase));
        String print = CustomFixture.OPERACIONES.peek().toString();
        System.out.println(print);
        Clase eliminada = (Clase) CustomFixture.CLASES.eliminarEnPosicion(CustomFixture.CLASES.encontrarIndexDe(clase));
        CustomFixture.CLASES_ELIMINADAS.agregar(eliminada);
        return eliminada;
    }

    /**
     * Modifica los datos de una clase existente.
     * <p>
     * Actualiza la información de una clase ya registrada en el sistema.
     * La clase se identifica por su código (que no debe cambiar). Los demás
     * atributos pueden ser actualizados: nombre, descripción, duración, instructor, etc.
     * <p>
     * Comportamiento esperado:
     * - La clase debe existir previamente (identificada por su código)
     * - Se actualiza todos los atributos con los nuevos valores proporcionados
     * - Devuelve la clase modificada (posiblemente con datos adicionales como timestamp de modificación)
     * - Si la clase no existe, comportamiento según política (null, excepción, agregar nueva)
     *
     * @param clase la clase con los datos modificados. El código debe corresponder
     *              a una clase existente en el sistema
     * @return la clase actualizada después de la modificación, o null si no pudo completarse
     * @throws NullPointerException     si clase es null (recomendado)
     * @throws IllegalArgumentException si la clase no existe en el sistema
     * @see #agregarClase(Clase)
     */
    @Override
    public Clase modificarClase(Clase clase) {
        if(clase == null){
            System.out.println("    [ERROR] clase vacia");
            return null;
        }

        int index = CustomFixture.CLASES.encontrarIndexDe(clase);
        if(index < 0){
            System.out.println("    [ERROR] clase no existe en el sistema");
            return null;
        }
        CustomFixture.OPERACIONES.apilar(new ModificarClase(clase));
        CustomFixture.CLASES.editarNodo(index, clase);
        String print = CustomFixture.OPERACIONES.peek().toString();
        System.out.println(print);
        return (Clase) CustomFixture.CLASES.buscarDato(index);
    }
}
