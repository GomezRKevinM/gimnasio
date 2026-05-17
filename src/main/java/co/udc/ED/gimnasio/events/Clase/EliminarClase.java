package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

public class EliminarClase extends DomainEvent {
    private static final String NOMBRE = "Se eliminó una clase";
    private final Clase clase;

    public EliminarClase(Clase claseEliminada) {
        super(NOMBRE);
        this.clase = claseEliminada;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase eliminada %s",
                super.nombre, super.fecha, clase.toString());
    }
}
