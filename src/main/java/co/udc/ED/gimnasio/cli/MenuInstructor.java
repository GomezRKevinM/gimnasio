package co.udc.ED.gimnasio.cli;

import co.udc.ED.gimnasio.models.gym.Instructor;
import co.udc.ED.gimnasio.services.SistemaInstructor;
import co.udc.ED.gimnasio.utils.Color;

import java.util.UUID;

public class MenuInstructor {
    private final ConsoleInput input;
    private final SistemaInstructor servicio;

    public MenuInstructor(ConsoleInput input, SistemaInstructor servicio) {
        this.input = input;
        this.servicio = servicio;
    }

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = input.leerOpcion("Seleccione una opcion: ", 0, 6);
            switch (opcion) {
                case 1 -> listar();
                case 2 -> buscar();
                case 3 -> crear();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 6 -> listarEliminados();
                case 0 -> { }
                default -> System.out.println("    [ERROR] Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void imprimirMenu() {
        System.out.println();
        System.out.println(Color.CYAN + "--- Instructores ---" + Color.RESET);
        System.out.println("1. Listar instructores");
        System.out.println("2. Buscar instructor por codigo");
        System.out.println("3. Crear instructor");
        System.out.println("4. Modificar instructor");
        System.out.println("5. Eliminar instructor");
        System.out.println("6. Listar instructores eliminados");
        System.out.println("0. Volver");
    }

    private void listar() {
        servicio.obtenerInstructores().mostrarAdelante();
        input.pausar();
    }

    private void listarEliminados() {
        servicio.obtenerInstructoresEliminados().mostrarAdelante();
        input.pausar();
    }

    private void buscar() {
        String codigo = input.leerTextoObligatorio("Codigo UUID: ");
        Instructor instructor = servicio.obtenerInstructorPorCodigo(codigo);
        imprimirResultado(instructor);
        input.pausar();
    }

    private void crear() {
        String nombre = input.leerTextoObligatorio("Nombre: ");
        Instructor instructor = new Instructor(nombre, UUID.randomUUID());
        imprimirResultado(servicio.crearInstructor(instructor));
        input.pausar();
    }

    private void modificar() {
        String codigo = input.leerTextoObligatorio("Codigo UUID del instructor: ");
        Instructor actual = servicio.obtenerInstructorPorCodigo(codigo);
        if (actual == null) {
            System.out.println("    [ERROR] Instructor no encontrado.");
            input.pausar();
            return;
        }

        String nombre = input.leerTextoObligatorio("Nuevo nombre: ");
        imprimirResultado(servicio.modificarInstructor(new Instructor(nombre, actual.codigo())));
        input.pausar();
    }

    private void eliminar() {
        String codigo = input.leerTextoObligatorio("Codigo UUID del instructor: ");
        imprimirResultado(servicio.eliminarInstructor(codigo));
        input.pausar();
    }

    private void imprimirResultado(Instructor instructor) {
        if (instructor != null) {
            System.out.println(Color.GREEN + "    [OK] " + instructor + Color.RESET);
        }
    }
}
