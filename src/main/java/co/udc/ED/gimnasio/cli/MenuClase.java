package co.udc.ED.gimnasio.cli;

import co.udc.ED.gimnasio.models.Cola;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.models.gym.Clase;
import co.udc.ED.gimnasio.models.gym.Instructor;
import co.udc.ED.gimnasio.services.SistemaClase;
import co.udc.ED.gimnasio.services.SistemaInstructor;
import co.udc.ED.gimnasio.utils.Color;

public class MenuClase {
    private final ConsoleInput input;
    private final SistemaClase servicioClase;
    private final SistemaInstructor servicioInstructor;

    public MenuClase(ConsoleInput input, SistemaClase servicioClase, SistemaInstructor servicioInstructor) {
        this.input = input;
        this.servicioClase = servicioClase;
        this.servicioInstructor = servicioInstructor;
    }

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = input.leerOpcion("Seleccione una opcion: ", 0, 5);
            switch (opcion) {
                case 1 -> listar();
                case 2 -> buscar();
                case 3 -> crear();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 0 -> { }
                default -> System.out.println(Color.RED+"    [ERROR] Opcion no valida."+Color.RESET);
            }
        } while (opcion != 0);
    }

    private void imprimirMenu() {
        System.out.println();
        System.out.println(Color.CYAN + "--- Clases ---" + Color.RESET);
        System.out.println("1. Listar clases");
        System.out.println("2. Buscar clase por codigo");
        System.out.println("3. Crear clase");
        System.out.println("4. Modificar clase");
        System.out.println("5. Eliminar clase");
        System.out.println("0. Volver");
    }

    private void listar() {
        servicioClase.obtenerClases().mostrarAdelante();
        input.pausar();
    }

    private void buscar() {
        String codigo = input.leerTextoObligatorio("Codigo de clase: ");
        imprimirResultado(servicioClase.obtenerClasePorCodigo(codigo));
        input.pausar();
    }

    private void crear() {
        Clase clase = leerDatosClase(null);
        if (clase != null) {
            imprimirResultado(servicioClase.agregarClase(clase));
        }
        input.pausar();
    }

    private void modificar() {
        String codigo = input.leerTextoObligatorio("Codigo de clase a modificar: ");
        Clase actual = servicioClase.obtenerClasePorCodigo(codigo);
        if (actual == null) {
            System.out.println(Color.RED+"    [ERROR] Clase no encontrada."+Color.RESET);
            input.pausar();
            return;
        }

        Clase modificada = leerDatosClase(actual);
        if (modificada != null) {
            imprimirResultado(servicioClase.modificarClase(modificada));
        }
        input.pausar();
    }

    private void eliminar() {
        String codigo = input.leerTextoObligatorio("Codigo de clase a eliminar: ");
        Clase clase = servicioClase.obtenerClasePorCodigo(codigo);
        if (clase == null) {
            System.out.println(Color.RED+"    [ERROR] Clase no encontrada."+Color.RESET);
        } else {
            imprimirResultado(servicioClase.eliminarClase(clase));
        }
        input.pausar();
    }

    private Clase leerDatosClase(Clase actual) {
        if (servicioInstructor.obtenerInstructores().cuentaElementos() == 0) {
            System.out.println(Color.RED+"    [ERROR] Primero debe crear al menos un instructor."+Color.RESET);
            return null;
        }

        String codigo = actual == null
                ? input.leerTextoObligatorio("Codigo: ")
                : actual.codigo();
        String nombre = input.leerTextoObligatorio("Nombre: ");
        String descripcion = input.leerTextoObligatorio("Descripcion: ");
        int duracion = input.leerEntero("Duracion en horas: ");

        System.out.println("Instructores disponibles:");
        servicioInstructor.obtenerInstructores().mostrarAdelante();
        String codigoInstructor = input.leerTextoObligatorio("Codigo UUID del instructor: ");
        Instructor instructor = servicioInstructor.obtenerInstructorPorCodigo(codigoInstructor);
        if (instructor == null) {
            System.out.println(Color.RED+"    [ERROR] Instructor no encontrado."+Color.RESET);
            return null;
        }

        Lista inscripciones = actual == null ? new Lista() : actual.inscripciones();
        Cola colaInscripcion = actual == null ? new Cola() : actual.colaInscripcion();
        return new Clase(codigo, nombre, descripcion, duracion, instructor, inscripciones, colaInscripcion);
    }

    private void imprimirResultado(Clase clase) {
        if (clase != null) {
            System.out.println(Color.GREEN + "    [OK] " + clase + Color.RESET);
        }
    }
}
