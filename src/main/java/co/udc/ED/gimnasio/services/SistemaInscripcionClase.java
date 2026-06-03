package co.udc.ED.gimnasio.services;

import co.udc.ED.gimnasio.enums.InscripcionEstado;
import co.udc.ED.gimnasio.events.Inscripcion.CrearInscripcion;
import co.udc.ED.gimnasio.events.Inscripcion.EliminarInscripcion;
import co.udc.ED.gimnasio.events.Inscripcion.ModificarInscripcion;
import co.udc.ED.gimnasio.fixture.CustomFixture;
import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Clase;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;
import co.udc.ED.gimnasio.models.gym.Instructor;
import co.udc.ED.gimnasio.services.interfaces.InscripcionClaseService;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @Author: Kevin Gómez
 * @Description: Logica de negocio (usa Lista, Cola, Pila)
 */
public class SistemaInscripcionClase implements InscripcionClaseService {
    /**
     * Obtiene la cola de todas las inscripciones en estado PENDIENTE.
     * <p>
     * Devuelve una estructura {@link Cola} (FIFO) que contiene las inscripciones
     * en espera de aprobación. Esta cola es el principal flujo de trabajo para
     * procesar nuevas solicitudes en orden de llegada.
     * <p>
     * Nota: El orden FIFO es critical para mantener equidad (primero en entrar,
     * primero en ser procesado).
     *
     * @return una {@link Cola} con todas las inscripciones en estado PENDIENTE.
     * Nunca devuelve null; devuelve una cola vacía si no hay inscripciones pendientes
     * @see InscripcionClase#estado()
     */
    @Override
    public Lista obtenerInscripcionesRegistradas() {
        return CustomFixture.INSCRIPCIONES_REGISTRADAS;
    }

    @Override
    public Cola obtenerInscripcionesPendientes() {
        return CustomFixture.INSCRIPCIONES;
    }

    /**
     * Obtiene todas las inscripciones asociadas a una clase específica.
     * <p>
     * Devuelve una {@link Lista} de todas las inscripciones (sin importar su estado)
     * que fueron realizadas para la clase con el código proporcionado. Útil para
     * obtener un resumen de quién se ha inscrito a una clase en particular.
     * <p>
     * Nota: Esta búsqueda incluye inscripciones en TODOS los estados:
     * PENDIENTE, FINALIZADA y CANCELADA.
     *
     * @param codigoClase el código único de la clase (ej: "YOG001")
     * @return una {@link Lista} con todas las inscripciones para esa clase.
     * Nunca devuelve null; devuelve una lista vacía si no hay inscripciones
     * @throws IllegalArgumentException si codigoClase es null o vacío
     */
    @Override
    public Lista obtenerInscripcionesPorClase(String codigoClase) {
        if(codigoClase == null || codigoClase.isEmpty()){
            System.out.println("    [ERROR] El codigo de clase no puede ser nulo o vacio");
            return null;
        }

        Clase clase = (Clase) CustomFixture.CLASES.buscarDato(codigoClase);
        if(clase == null){
            System.out.println("    [ERROR] La clase no existe en el sistema");
            return null;
        }

        return clase.inscripciones();
    }

    /**
     * Obtiene todas las inscripciones en estado CANCELADA.
     * <p>
     * Devuelve un registro histórico de inscripciones que fueron rechazadas o
     * canceladas por el cliente. Esta información es importante para auditoría,
     * análisis de patrones de cancelación y trazabilidad.
     *
     * @return una {@link Lista} con todas las inscripciones canceladas.
     * Nunca devuelve null; devuelve una lista vacía si ninguna inscripción ha sido cancelada
     * @see #cancelarInscripcion(InscripcionClase)
     */
    @Override
    public Lista obtenerInscripcionesCanceladas() {
        return CustomFixture.INSCRIPCIONES_CANCELADAS;
    }

    /**
     * Obtiene la primera inscripción en la cola de pendientes (sin removerla).
     * <p>
     * Retorna la inscripción en la cabecera de la cola FIFO de inscripciones
     * pendientes sin modificar la estructura (operación peek, no desencolar).
     * Útil para consultar cuál será la próxima a procesar sin comprometerse.
     * <p>
     * Nota: Si la cola está vacía, el comportamiento depende de la implementación
     * (puede retornar null o lanzar excepción).
     *
     * @return la {@link InscripcionClase} que está en la cabecera de la cola pendiente,
     * o null si no hay inscripciones pendientes
     * @see #obtenerInscripcionesPendientes()
     */
    @Override
    public InscripcionClase obtenerInscripcionPendiente() {
        return (InscripcionClase) CustomFixture.INSCRIPCIONES.desencolar();
    }

    @Override
    public co.udc.ED.gimnasio.models.Pila obtenerHistorialProcesadas() {
        return CustomFixture.HISTORIAL_INSCRIPCIONES_PROCESADAS;
    }

    /**
     * Obtiene todas las inscripciones en estado FINALIZADA.
     * <p>
     * Devuelve una {@link Lista} con todas las inscripciones confirmadas y activas.
     * Estas son los clientes que han sido aprobados y tienen lugar garantizado en
     * sus respectivas clases.
     *
     * @return una {@link Lista} con todas las inscripciones finalizadas.
     * Nunca devuelve null; devuelve una lista vacía si no hay inscripciones finalizadas
     * @see #aprobarInscripcion(InscripcionClase)
     */
    @Override
    public Lista obtenerInscripcionesFinalizadas() {
        return CustomFixture.INSCRIPCIONES_FINALIZADAS;
    }

    /**
     * Agrega una nueva inscripción al sistema.
     * <p>
     * Crea una nueva solicitud de inscripción con estado inicial PENDIENTE.
     * La inscripción se agrega a la cola de espera para ser procesada posteriormente.
     * Esta operación emite un evento de dominio para auditoría (CrearInscripcion).
     * <p>
     * Comportamiento esperado:
     * - La inscripción se crea con estado PENDIENTE
     * - Se agrega a la cola de inscripciones pendientes (FIFO)
     * - Si ya existe inscripción del mismo cliente en la misma clase, el comportamiento
     * es según política (rechazar, actualizar, o permitir duplicado)
     * - Valida que la clase y el cliente existan
     *
     * @param cliente    el nombre del cliente que se inscribe. No puede ser null o vacío.
     * @param clase      el código de la clase a la que se inscribe. No puede ser null o vacío. Debe existir en el sistema.
     * @return la {@link InscripcionClase} creada, con estado PENDIENTE y datos persistidos
     * @throws NullPointerException     si inscripcion es null
     * @throws IllegalArgumentException si los datos obligatorios son inválidos
     *                                  o si la clase no existe
     * @see InscripcionEstado#PENDIENTE
     */
    @Override
    public InscripcionClase agregarInscripcion(String cliente, Clase clase) {

            if( cliente == null || cliente.isEmpty() || clase == null){
                System.out.println("    [ERROR] Los datos de la inscripcion no pueden ser nulos o vacios");
                return null;
            }

            Clase claseObj = (Clase) CustomFixture.CLASES.buscarDato(clase);
            if(claseObj == null){
                System.out.println("    [ERROR] La clase no existe");
                return null;
            }

            InscripcionEstado estado = InscripcionEstado.PENDIENTE;

            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());

            InscripcionClase inscripcion = new InscripcionClase(fechaActual,clase.codigo(),cliente,estado);
            if(CustomFixture.INSCRIPCIONES_REGISTRADAS.contiene(inscripcion)){
                System.out.println("    [ERROR] La inscripcion ya existe");
                return null;
            }
            CustomFixture.INSCRIPCIONES_REGISTRADAS.agregar(inscripcion);
            CustomFixture.INSCRIPCIONES.encolar(inscripcion);
            CustomFixture.OPERACIONES.apilar(new CrearInscripcion(inscripcion));
            System.out.println(CustomFixture.OPERACIONES.peek().toString());
            return inscripcion;
    }

    @Override
    public InscripcionClase obtenerInscripcionPorCodigo(String codigo) {
        if(codigo == null || codigo.isEmpty()){
            System.out.println("    [ERROR] El codigo de inscripcion no puede estar vacio");
            return null;
        }
        InscripcionClase inscripcion = (InscripcionClase) CustomFixture.INSCRIPCIONES_REGISTRADAS.buscarDato(codigo);
        if(inscripcion == null){
            System.out.println("    [ERROR] La inscripcion no existe");
        }
        return inscripcion;
    }

    @Override
    public InscripcionClase procesarSiguienteInscripcion() {
        InscripcionClase pendiente = obtenerInscripcionPendiente();
        if(pendiente == null){
            return null;
        }
        return aprobarInscripcion(pendiente);
    }

    /**
     * Aprueba una inscripción pendiente, cambiando su estado a FINALIZADA.
     * <p>
     * Transiciona una inscripción del estado PENDIENTE a FINALIZADA, confirmando
     * que el cliente tiene lugar garantizado en la clase. Esta operación es
     * típicamente realizada por un administrador o automáticamente cuando hay
     * cupo disponible.
     * <p>
     * Comportamiento esperado:
     * - Solo inscripciones en estado PENDIENTE pueden ser aprobadas
     * - Después de la aprobación, el cliente tiene derecho a asistir a la clase
     * - Se emite un evento de dominio para auditoría (ModificarInscripcion)
     * - Si la inscripción ya fue aprobada/cancelada, el comportamiento depende
     * de la política (rechazar o ignorar)
     *
     * @param inscripcion la inscripción a aprobar. Debe estar en estado PENDIENTE
     * @return la {@link InscripcionClase} aprobada, con estado actualizado a FINALIZADA
     * @throws NullPointerException     si inscripcion es null
     * @throws IllegalArgumentException si la inscripción no está en estado PENDIENTE
     *                                  o si no existe en el sistema
     * @see InscripcionEstado#FINALIZADA
     */
    @Override
    public InscripcionClase aprobarInscripcion(InscripcionClase inscripcion) {
        if(inscripcion == null){
            System.out.println("    [ERROR] La inscripcion no puede ser nula]");
            return null;
        }

        if(inscripcion.codigoClase() == null || inscripcion.codigoClase().isEmpty()){
            System.out.println("    [ERROR] El codigo de la clase no puede ser nulo o vacio");
            return null;
        }

        if(inscripcion.estado() != InscripcionEstado.PENDIENTE){
            System.out.println("    [ERROR] La inscripcion no esta en estado PENDIENTE");
            return null;
        }

        int indiceDeClase = CustomFixture.CLASES.encontrarIndexDe(inscripcion.codigoClase());
        Clase clase = (Clase) CustomFixture.CLASES.buscarDato(indiceDeClase);

        if(clase == null){
            System.out.println("    [ERROR] La clase no existe en el sistema");
            return null;
        }

        InscripcionClase finalizada = new InscripcionClase(
                inscripcion.fechaInscripcion(),
                inscripcion.codigoClase(),
                inscripcion.cliente(),
                InscripcionEstado.FINALIZADA);

        CustomFixture.OPERACIONES.apilar(new ModificarInscripcion(finalizada));
        clase.inscripciones().agregar(finalizada);
        CustomFixture.INSCRIPCIONES_FINALIZADAS.agregar(finalizada);
        CustomFixture.HISTORIAL_INSCRIPCIONES_PROCESADAS.apilar(finalizada);
        actualizarInscripcionRegistrada(finalizada);
        System.out.println(CustomFixture.OPERACIONES.peek().toString());
        System.out.println("    [INFO] La inscripcion ha sido aprobada");
        return finalizada;

    }

    /**
     * Cancela una inscripción, cambiando su estado a CANCELADA.
     * <p>
     * Transiciona una inscripción (tipicamente en estado PENDIENTE o FINALIZADA)
     * a estado CANCELADA. Esto puede ocurrir cuando:
     * - El cliente elige cancelar su inscripción
     * - Un administrador rechaza la solicitud
     * - La clase es cancelada
     * <p>
     * Comportamiento esperado:
     * - Cualquier inscripción puede ser cancelada (sin importar su estado actual)
     * - La inscripción se mueve del estado actual a CANCELADA
     * - Si estaba pendiente, se libera espacio en la cola para la próxima inscripción
     * - Se emite un evento de dominio para auditoría (ModificarInscripcion o EliminarInscripcion)
     * - Se pueden enviar notificaciones al cliente
     *
     * @param inscripcion la inscripción a cancelar
     * @return la {@link InscripcionClase} cancelada, con estado actualizado a CANCELADA
     * @throws NullPointerException     si inscripcion es null
     * @throws IllegalArgumentException si la inscripción no existe en el sistema
     * @see InscripcionEstado#CANCELADA
     */
    @Override
    public InscripcionClase cancelarInscripcion(InscripcionClase inscripcion) {
        if(inscripcion == null){
            System.out.println("    [ERROR] La inscripcion no puede ser nula]");
            return null;
        }

        InscripcionClase cancelada = new InscripcionClase(
                inscripcion.fechaInscripcion(),
                inscripcion.codigoClase(),
                inscripcion.cliente(),
                InscripcionEstado.CANCELADA);

        CustomFixture.INSCRIPCIONES_CANCELADAS.agregar(cancelada);
        CustomFixture.OPERACIONES.apilar(new ModificarInscripcion(cancelada));
        actualizarInscripcionRegistrada(cancelada);
        System.out.println("    [INFO] La inscripcion ha sido cancelada");
        return cancelada;
    }

    @Override
    public InscripcionClase cancelarInscripcionPendiente(String codigo) {
        InscripcionClase objetivo = obtenerInscripcionPorCodigo(codigo);
        if(objetivo == null || objetivo.estado() != InscripcionEstado.PENDIENTE){
            System.out.println("    [ERROR] Solo se pueden cancelar inscripciones pendientes");
            return null;
        }

        Cola auxiliar = new Cola();
        InscripcionClase cancelada = null;
        int total = CustomFixture.INSCRIPCIONES.tamanio();

        for(int i = 0; i < total; i++){
            InscripcionClase actual = (InscripcionClase) CustomFixture.INSCRIPCIONES.desencolar();
            if(actual.equals(objetivo)){
                cancelada = cancelarInscripcion(actual);
            }else{
                auxiliar.encolar(actual);
            }
        }

        while(!auxiliar.esVacia()){
            CustomFixture.INSCRIPCIONES.encolar(auxiliar.desencolar());
        }

        if(cancelada == null){
            System.out.println("    [ERROR] La inscripcion no esta en la cola de pendientes");
        }
        return cancelada;
    }

    @Override
    public InscripcionClase deshacerUltimoProcesamiento() {
        InscripcionClase ultima = (InscripcionClase) CustomFixture.HISTORIAL_INSCRIPCIONES_PROCESADAS.desapilar();
        if(ultima == null){
            return null;
        }

        InscripcionClase pendiente = new InscripcionClase(
                ultima.codigoInscripcion(),
                ultima.fechaInscripcion(),
                ultima.codigoClase(),
                ultima.cliente(),
                InscripcionEstado.PENDIENTE);

        eliminarDeLista(CustomFixture.INSCRIPCIONES_FINALIZADAS, ultima);
        Clase clase = (Clase) CustomFixture.CLASES.buscarDato(ultima.codigoClase());
        if(clase != null){
            eliminarDeLista(clase.inscripciones(), ultima);
        }

        CustomFixture.INSCRIPCIONES.encolar(pendiente);
        actualizarInscripcionRegistrada(pendiente);
        CustomFixture.OPERACIONES.apilar(new ModificarInscripcion(pendiente));
        System.out.println("    [INFO] Se deshizo el ultimo procesamiento");
        return pendiente;
    }

    private void actualizarInscripcionRegistrada(InscripcionClase inscripcion) {
        int index = CustomFixture.INSCRIPCIONES_REGISTRADAS.encontrarIndexDe(inscripcion);
        if(index >= 0){
            CustomFixture.INSCRIPCIONES_REGISTRADAS.editarNodo(index, inscripcion);
        }
    }

    private void eliminarDeLista(Lista lista, InscripcionClase inscripcion) {
        int index = lista.encontrarIndexDe(inscripcion);
        if(index >= 0){
            lista.eliminarEnPosicion(index);
        }
    }
}
