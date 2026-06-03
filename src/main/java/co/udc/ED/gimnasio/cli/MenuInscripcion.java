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
            opcion = input.leerOpcion("Seleccione una opcion: ", 0, 10);
            switch (opcion) {
                case 1 -> crear();
                case 2 -> listarRegistradas();
                case 3 -> listarPendientes();
                case 4 -> procesarSiguiente();
                case 5 -> listarHistorialProcesadas();
                case 6 -> buscarPorCodigo();
                case 7 -> cancelarPendiente();
                case 8 -> deshacerProcesamiento();
                case 9 -> mostrarCantidades();
                case 10 -> listarPorClase();
                case 0 -> { }
                default -> System.out.println(Color.RED+"    [ERROR] Opcion no valida."+Color.RESET);
            }
        } while (opcion != 0);
    }

    private void imprimirMenu() {
        System.out.println();
        System.out.println(Color.CYAN + "--- Inscripciones ---" + Color.RESET);
        System.out.println("1. Registrar inscripcion");
        System.out.println("2. Ver todas las inscripciones registradas");
        System.out.println("3. Ver inscripciones pendientes");
        System.out.println("4. Procesar siguiente inscripcion");
        System.out.println("5. Ver historial de inscripciones procesadas");
        System.out.println("6. Buscar inscripcion por codigo");
        System.out.println("7. Cancelar inscripcion pendiente");
        System.out.println("8. Deshacer ultimo procesamiento");
        System.out.println("9. Ver cantidad de elementos");
        System.out.println("10. Ver inscripciones por clase");
        System.out.println("0. Volver");
    }

    private void listarRegistradas() {
        servicioInscripcion.obtenerInscripcionesRegistradas().mostrarAdelante();
        input.pausar();
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

    private void listarHistorialProcesadas() {
        servicioInscripcion.obtenerHistorialProcesadas().mostrar();
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
            System.out.println(Color.RED+"    [ERROR] Primero debe crear al menos una clase."+Color.RESET);
            input.pausar();
            return;
        }

        System.out.println("Clases disponibles:");
        servicioClase.obtenerClases().mostrarAdelante();
        String codigoClase = input.leerTextoObligatorio("Codigo de clase: ");
        Clase clase = servicioClase.obtenerClasePorCodigo(codigoClase);
        if (clase == null) {
            System.out.println(Color.RED+"    [ERROR] Clase no encontrada."+Color.RESET);
            input.pausar();
            return;
        }

        String cliente = input.leerTextoObligatorio("Cliente: ");
        imprimirResultado(servicioInscripcion.agregarInscripcion(cliente, clase));
        input.pausar();
    }

    private void procesarSiguiente() {
        imprimirResultado(servicioInscripcion.procesarSiguienteInscripcion());
        input.pausar();
    }

    private void buscarPorCodigo() {
        String codigo = input.leerTextoObligatorio("Codigo de inscripcion: ");
        imprimirResultado(servicioInscripcion.obtenerInscripcionPorCodigo(codigo));
        input.pausar();
    }

    private void cancelarPendiente() {
        String codigo = input.leerTextoObligatorio("Codigo de inscripcion pendiente: ");
        imprimirResultado(servicioInscripcion.cancelarInscripcionPendiente(codigo));
        input.pausar();
    }

    private void deshacerProcesamiento() {
        imprimirResultado(servicioInscripcion.deshacerUltimoProcesamiento());
        input.pausar();
    }

    private void mostrarCantidades() {
        System.out.println("Registradas: " + servicioInscripcion.obtenerInscripcionesRegistradas().cuentaElementos());
        System.out.println("Pendientes: " + servicioInscripcion.obtenerInscripcionesPendientes().tamanio());
        System.out.println("Procesadas: " + servicioInscripcion.obtenerHistorialProcesadas().tamanio());
        System.out.println("Finalizadas: " + servicioInscripcion.obtenerInscripcionesFinalizadas().cuentaElementos());
        System.out.println("Canceladas: " + servicioInscripcion.obtenerInscripcionesCanceladas().cuentaElementos());
        input.pausar();
    }

    private void imprimirResultado(InscripcionClase inscripcion) {
        if (inscripcion != null) {
            System.out.println(Color.GREEN + "    [OK] " + inscripcion + Color.RESET);
        }
    }
}
