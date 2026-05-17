package co.udc.ED.gimnasio.events.Instructor;

import co.udc.ED.gimnasio.events.DomainEvent;
import co.udc.ED.gimnasio.models.gym.Instructor;

public class ModificarInstructor extends DomainEvent {
    private static final String NOMBRE = "Se modificó un instructor";
    private final Instructor instructor;

    public ModificarInstructor(Instructor instructorModificado) {
        super(NOMBRE);
        this.instructor = instructorModificado;
    }

    @Override
    public String toString(){
        return String.format("Evento: %s, Fecha: %s, Detalles: Instructor modificado %s",
                super.nombre, super.fecha, instructor.toString());
    }

}
