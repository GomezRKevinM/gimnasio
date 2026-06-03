package co.udc.ED.gimnasio.models.gym;

import co.udc.ED.gimnasio.enums.InscripcionEstado;

import java.sql.Timestamp;

/**
 * Modelo principal del caso Gimnasio.
 *
 * Representa la inscripcion de un cliente a una clase. El codigo de inscripcion
 * es el identificador principal y se usa para comparar, buscar y evitar duplicados.
 */
public record InscripcionClase(
        String codigoInscripcion,
        Timestamp fechaInscripcion,
        String codigoClase,
        String cliente,
        InscripcionEstado estado) {

    public InscripcionClase(Timestamp fechaInscripcion, String codigoClase, String cliente, InscripcionEstado estado) {
        this(generarCodigo(codigoClase, cliente), fechaInscripcion, codigoClase, cliente, estado);
    }

    private static String generarCodigo(String codigoClase, String cliente) {
        String clase = codigoClase == null ? "SINCLASE" : codigoClase.trim().toUpperCase();
        String nombreCliente = cliente == null ? "SINCLIENTE" : cliente.trim().toUpperCase().replaceAll("\\s+", "-");
        return clase + "-" + nombreCliente;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof String codigo) {
            return this.codigoInscripcion.equalsIgnoreCase(codigo);
        }
        if (obj instanceof InscripcionClase other) {
            return this.codigoInscripcion.equalsIgnoreCase(other.codigoInscripcion);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("[ Codigo: %s | Fecha: %s | Cliente: %s | Codigo de Clase: %s | Estado: %s ]",
                codigoInscripcion, fechaInscripcion, cliente, codigoClase, estado);
    }

    @Override
    public int hashCode() {
        return codigoInscripcion.hashCode();
    }
}
