package co.udc.ED.gimnasio.events.Clase;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Clase;

public class ModificarClase extends DomainEvent {
    private static final String NOMBRE = "Se modificó una clase existente";
    private final Clase clase;

    public ModificarClase(Clase claseModificada) {
        super(NOMBRE);
        this.clase = claseModificada;
    }

    @Override
    public String toString() {
        return String.format("Evento: %s, Fecha: %s, Detalles: Clase modificada %s",
                super.nombre, super.fecha, clase.toString());
    }
}
