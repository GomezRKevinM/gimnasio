package co.udc.ED.gimnasio.cli;

import co.udc.ED.gimnasio.services.SistemaClase;
import co.udc.ED.gimnasio.services.SistemaInscripcionClase;
import co.udc.ED.gimnasio.services.SistemaInstructor;
import co.udc.ED.gimnasio.utils.Color;

import java.util.Scanner;

public class GimnasioCli {
    private final ConsoleInput input;
    private final MenuInstructor menuInstructor;
    private final MenuClase menuClase;
    private final MenuInscripcion menuInscripcion;

    public GimnasioCli() {
        SistemaInstructor sistemaInstructor = new SistemaInstructor();
        SistemaClase sistemaClase = new SistemaClase();
        SistemaInscripcionClase sistemaInscripcion = new SistemaInscripcionClase();

        this.input = new ConsoleInput(new Scanner(System.in));
        this.menuInstructor = new MenuInstructor(input, sistemaInstructor);
        this.menuClase = new MenuClase(input, sistemaClase, sistemaInstructor);
        this.menuInscripcion = new MenuInscripcion(input, sistemaInscripcion, sistemaClase);
    }

    public void iniciar() {
        int opcion;
        do {
            imprimirMenuPrincipal();
            opcion = input.leerOpcion("Seleccione una opcion: ", 0, 4);
            switch (opcion) {
                case 1 -> menuInstructor.mostrar();
                case 2 -> menuClase.mostrar();
                case 3 -> menuInscripcion.mostrar();
                case 4 -> mostrarOperaciones();
                case 0 -> System.out.println(Color.GREEN + "Hasta luego." + Color.RESET);
                default -> System.out.println("    [ERROR] Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void imprimirMenuPrincipal() {
        System.out.println();
        System.out.println(Color.CYAN + "===== SISTEMA DE GIMNASIO =====" + Color.RESET);
        System.out.println("1. Instructores");
        System.out.println("2. Clases");
        System.out.println("3. Inscripciones");
        System.out.println("4. Ver historial de operaciones");
        System.out.println("0. Salir");
    }

    private void mostrarOperaciones() {
        System.out.println(Color.YELLOW + "\n--- Historial de operaciones ---" + Color.RESET);
        co.udc.ED.gimnasio.fixture.CustomFixture.OPERACIONES.mostrar();
        input.pausar();
    }
}
