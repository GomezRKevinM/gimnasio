package co.udc.ED.gimnasio.cli;

import co.udc.ED.gimnasio.models.gym.Clase;
import co.udc.ED.gimnasio.models.gym.InscripcionClase;
import co.udc.ED.gimnasio.models.Lista;
import co.udc.ED.gimnasio.services.SistemaClase;
import co.udc.ED.gimnasio.services.SistemaInscripcionClase;
import co.udc.ED.gimnasio.utils.Color;

public class MenuInscripcion {
    private final ConsoleInput input;
    private final SistemaInscripcionClase servicioInscripcion;
    private final SistemaClase servicioClase;

    public MenuInscripcion(ConsoleInput input, SistemaInscripcionClase servicioInscripcion, SistemaClase servicioClase) {
        this.input = input;
        this.servicioInscripcion = servicioInscripcion;
        this.servicioClase = servicioClase;
    }

    public void mostrar() {
        int opcion;
        do {
            imprimirMenu();
            opcion = input.leerOpcion("Seleccione una opcion: ", 0, 7);
            switch (opcion) {
                case 1 -> listarPendientes();
                case 2 -> listarFinalizadas();
                case 3 -> listarCanceladas();
                case 4 -> listarPorClase();
                case 5 -> crear();
                case 6 -> aprobarPrimeraPendiente();
                case 7 -> cancelarPrimeraPendiente();
                case 0 -> { }
                default -> System.out.println("    [ERROR] Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void imprimirMenu() {
        System.out.println();
        System.out.println(Color.CYAN + "--- Inscripciones ---" + Color.RESET);
        System.out.println("1. Listar pendientes");
        System.out.println("2. Listar finalizadas");
        System.out.println("3. Listar canceladas");
        System.out.println("4. Listar por clase");
        System.out.println("5. Crear inscripcion");
        System.out.println("6. Aprobar primera pendiente");
        System.out.println("7. Cancelar primera pendiente");
        System.out.println("0. Volver");
    }

    private void listarPendientes() {
        System.out.println(servicioInscripcion.obtenerInscripcionesPendientes());
        input.pausar();
    }

    private void listarFinalizadas() {
        servicioInscripcion.obtenerInscripcionesFinalizadas().mostrarAdelante();
        input.pausar();
    }

    private void listarCanceladas() {
        servicioInscripcion.obtenerInscripcionesCanceladas().mostrarAdelante();
        input.pausar();
    }

    private void listarPorClase() {
        String codigoClase = input.leerTextoObligatorio("Codigo de clase: ");
        Lista inscripciones = servicioInscripcion.obtenerInscripcionesPorClase(codigoClase);
        if (inscripciones != null) {
            inscripciones.mostrarAdelante();
        }
        input.pausar();
    }

    private void crear() {
        if (servicioClase.obtenerClases().cuentaElementos() == 0) {
            System.out.println("    [ERROR] Primero debe crear al menos una clase.");
            input.pausar();
            return;
        }

        System.out.println("Clases disponibles:");
        servicioClase.obtenerClases().mostrarAdelante();
        String codigoClase = input.leerTextoObligatorio("Codigo de clase: ");
        Clase clase = servicioClase.obtenerClasePorCodigo(codigoClase);
        if (clase == null) {
            System.out.println("    [ERROR] Clase no encontrada.");
            input.pausar();
            return;
        }

        String cliente = input.leerTextoObligatorio("Cliente: ");
        imprimirResultado(servicioInscripcion.agregarInscripcion(cliente, clase));
        input.pausar();
    }

    private void aprobarPrimeraPendiente() {
        InscripcionClase pendiente = servicioInscripcion.obtenerInscripcionPendiente();
        imprimirResultado(servicioInscripcion.aprobarInscripcion(pendiente));
        input.pausar();
    }

    private void cancelarPrimeraPendiente() {
        InscripcionClase pendiente = servicioInscripcion.obtenerInscripcionPendiente();
        imprimirResultado(servicioInscripcion.cancelarInscripcion(pendiente));
        input.pausar();
    }

    private void imprimirResultado(InscripcionClase inscripcion) {
        if (inscripcion != null) {
            System.out.println(Color.GREEN + "    [OK] " + inscripcion + Color.RESET);
        }
    }
}
