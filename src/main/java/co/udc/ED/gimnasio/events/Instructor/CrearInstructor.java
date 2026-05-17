package co.udc.ED.gimnasio.events.Instructor;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Instructor;

public class CrearInstructor extends DomainEvent {
    private static final String NOMBRE = "Se creó un nuevo instructor";
    private final Instructor instructor;

    public CrearInstructor(Instructor instructorCreado) {
        super(NOMBRE);
        this.instructor = instructorCreado;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Instructor creado %s",
                super.nombre, super.fecha, instructor.toString());
    }
}
