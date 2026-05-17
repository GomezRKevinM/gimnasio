package co.udc.ED.gimnasio.events.Instructor;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Instructor;

public class EliminarInstructor extends DomainEvent {
    private static final String NOMBRE = "Se eliminó un instructor";
    private final Instructor instructor;

    public EliminarInstructor(Instructor instructorEliminado) {
        super(NOMBRE);
        this.instructor = instructorEliminado;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Instructor eliminado %s",
                super.nombre, super.fecha, instructor.toString());
    }
}
