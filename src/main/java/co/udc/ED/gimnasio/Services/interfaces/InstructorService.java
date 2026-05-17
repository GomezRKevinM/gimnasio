package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Instructor;

/**
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 * @see Instructor
 * @see co.udc.ED.gimnasio.Services.SistemaInstructor
 */
public interface InstructorService {
    Lista obtenerInstructores();
    Lista obtenerInstructoresEliminados();
    void crearInstructor(Instructor instructor);
    Instructor obtenerInstructorPorCodigo(String codigo);
    Instructor modificarInstructor(Instructor instructor);
    void eliminarInstructor(String codigo);
}
