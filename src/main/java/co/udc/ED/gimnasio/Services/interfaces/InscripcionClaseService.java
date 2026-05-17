package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

/**
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see InscripcionClase
 * @see co.udc.ED.gimnasio.Services.SistemaInscripcionClase
 */
public interface InscripcionClaseService {
    Cola obtenerInscripcionesPendientes();
    Lista obtenerInscripcionesPorClase(String codigoClase);
    Lista obtenerInscripcionesCanceladas();

    /**
     *
     * @return La primera inscripcion en la cola
     */
    InscripcionClase obtenerInscripcionPendiente();
    Lista obtenerInscripcionesFinalizadas();
    InscripcionClase agregarInscripcion(InscripcionClase inscripcion);
    InscripcionClase aprobarInscripcion(InscripcionClase inscripcion);
    InscripcionClase cancelarInscripcion(InscripcionClase inscripcion);
}
