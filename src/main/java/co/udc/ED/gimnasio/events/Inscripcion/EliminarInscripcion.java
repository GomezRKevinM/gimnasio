package co.udc.ED.gimnasio.events.Inscripcion;

import co.udc.ED.gimnasio.events.DomainEvent;

public class EliminarInscripcion extends DomainEvent {
    private static final String NOMBRE = "Se eliminó una inscripción";
    private final String idInscripcion;

    public EliminarInscripcion(String idInscripcionEliminada) {
        super(NOMBRE);
        this.idInscripcion = idInscripcionEliminada;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Inscripción eliminada con ID %s",
                super.nombre, super.fecha, idInscripcion);
    }
}
