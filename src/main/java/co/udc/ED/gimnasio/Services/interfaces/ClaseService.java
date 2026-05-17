package co.udc.ED.gimnasio.Services.interfaces;

import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Clase;

public interface ClaseService {
    Lista obtenerClases();
    Clase obtenerClasePorCodigo(String codigo);
    void agregarClase(Clase clase);
    void eliminarClase(Clase clase);
    Clase modificarClase(Clase clase);
}
