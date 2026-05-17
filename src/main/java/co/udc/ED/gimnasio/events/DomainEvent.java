package co.udc.ED.gimnasio.events;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    protected final String nombre;
    protected final LocalDateTime fecha;

    protected DomainEvent(final String eventName) {
        this.nombre = eventName;
        this.fecha = LocalDateTime.now();
    }
}
