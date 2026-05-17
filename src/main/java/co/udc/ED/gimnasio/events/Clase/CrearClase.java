package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

public class CrearClase extends DomainEvent {
    private static final String NOMBRE = "Se creó una nueva clase";
    private final Clase clase;

    public CrearClase(Clase claseCreada){
        super(NOMBRE);
        this.clase=claseCreada;
    }

    @Override
    public String toString() {
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase creada %s",
                super.nombre, super.fecha, clase.toString());
    }
}
