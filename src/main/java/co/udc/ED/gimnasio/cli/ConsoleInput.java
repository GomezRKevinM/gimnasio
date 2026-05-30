package co.udc.ED.gimnasio.cli;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public String leerTextoObligatorio(String mensaje) {
        String valor;
        do {
            valor = leerTexto(mensaje);
            if (valor.isEmpty()) {
                System.out.println("    [ERROR] El valor no puede estar vacio.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException error) {
                System.out.println("    [ERROR] Debe ingresar un numero entero.");
            }
        }
    }

    public int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            int opcion = leerEntero(mensaje);
            if (opcion >= minimo && opcion <= maximo) {
                return opcion;
            }
            System.out.printf("    [ERROR] Opcion fuera de rango (%d - %d).%n", minimo, maximo);
        }
    }

    public void pausar() {
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();
    }
}
