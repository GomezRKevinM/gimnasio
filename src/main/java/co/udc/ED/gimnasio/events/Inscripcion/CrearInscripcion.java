package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;

public class CrearInscripcion extends DomainEvent {
    private static final String NOMBRE = "Se creó una nueva inscripción";
    private final InscripcionClase inscripcion;

    public CrearInscripcion(InscripcionClase inscripcionCreada) {
        super(NOMBRE);
        this.inscripcion = inscripcionCreada;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción creada %s",
                super.nombre, super.fecha, inscripcion.toString());
    }
}
