package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

public class ModificarInscripcion extends DomainEvent {
    private static final String NOMBRE = "Se modificó una inscripción";
    private final InscripcionClase inscripcion;

    public ModificarInscripcion(InscripcionClase inscripcionModificada) {
        super(NOMBRE);
        this.inscripcion = inscripcionModificada;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción modificada %s",
                super.nombre, super.fecha, inscripcion.toString());
    }
}
