package co.udc.ED.gimnasio.utils;

/**
 * Clase Color - Constantes de códigos ANSI para colorear salida en consola
 *
 * Esta clase de utilidad proporciona códigos ANSI estándar y extendidos para
 * aplicar colores a la salida de texto en terminales y consolas que soporten
 * secuencias de escape ANSI.
 *
 * Uso:
 * <pre>
 *   System.out.println(Color.GREEN + "Texto en verde" + Color.RESET);
 *   System.out.println(Color.LIGHT_RED + "Texto en rojo claro" + Color.RESET);
 * </pre>
 *
 * Nota: siempre terminar con {@code Color.RESET} para restaurar el color por defecto.
 *
 * @author Kevin Gómez
 * @version 1.0
 * @since 1.0
 */
public final class Color {

    /**
     * Código ANSI para restablecer el color y formato por defecto
     */
    public final static String RESET = "\u001B[0m";

    // ===== COLORES ESTÁNDAR =====

    /**
     * Código ANSI para texto en color negro
     */
    public final static String BLACK = "\u001B[30m";

    /**
     * Código ANSI para texto en color rojo
     */
    public final static String RED = "\u001B[31m";

    /**
     * Código ANSI para texto en color verde
     */
    public final static String GREEN = "\u001B[32m";

    /**
     * Código ANSI para texto en color amarillo
     */
    public final static String YELLOW = "\u001B[33m";

    /**
     * Código ANSI para texto en color azul
     */
    public final static String BLUE = "\u001B[34m";

    /**
     * Código ANSI para texto en color púrpura/magenta
     */
    public final static String PURPLE = "\u001B[35m";

    /**
     * Código ANSI para texto en color cian
     */
    public final static String CYAN = "\u001B[36m";

    /**
     * Código ANSI para texto en color blanco
     */
    public final static String WHITE = "\u001B[37m";

    // ===== COLORES CLAROS (LIGHT/BRIGHT) =====

    /**
     * Código ANSI para texto en color gris claro (negro brillante)
     */
    public final static String LIGHT_BLACK = "\u001B[90m";

    /**
     * Código ANSI para texto en color rojo claro/brillante
     */
    public final static String LIGHT_RED = "\u001B[91m";

    /**
     * Código ANSI para texto en color verde claro/brillante
     */
    public final static String LIGHT_GREEN = "\u001B[92m";

    /**
     * Código ANSI para texto en color amarillo claro/brillante
     */
    public final static String LIGHT_YELLOW = "\u001B[93m";

    /**
     * Código ANSI para texto en color azul claro/brillante
     */
    public final static String LIGHT_BLUE = "\u001B[94m";

    /**
     * Código ANSI para texto en color púrpura/magenta claro/brillante
     */
    public final static String LIGHT_PURPLE = "\u001B[95m";

    /**
     * Código ANSI para texto en color cian claro/brillante
     */
    public final static String LIGHT_CYAN = "\u001B[96m";

    /**
     * Código ANSI para texto en color blanco brillante
     */
    public final static String LIGHT_WHITE = "\u001B[97m";

    // ===== COLORES DE FONDO ESTÁNDAR =====

    /**
     * Código ANSI para fondo de color negro
     */
    public final static String BG_BLACK = "\u001B[40m";

    /**
     * Código ANSI para fondo de color rojo
     */
    public final static String BG_RED = "\u001B[41m";

    /**
     * Código ANSI para fondo de color verde
     */
    public final static String BG_GREEN = "\u001B[42m";

    /**
     * Código ANSI para fondo de color amarillo
     */
    public final static String BG_YELLOW = "\u001B[43m";

    /**
     * Código ANSI para fondo de color azul
     */
    public final static String BG_BLUE = "\u001B[44m";

    /**
     * Código ANSI para fondo de color púrpura/magenta
     */
    public final static String BG_PURPLE = "\u001B[45m";

    /**
     * Código ANSI para fondo de color cian
     */
    public final static String BG_CYAN = "\u001B[46m";

    /**
     * Código ANSI para fondo de color blanco
     */
    public final static String BG_WHITE = "\u001B[47m";

    // ===== COLORES DE FONDO CLAROS =====

    /**
     * Código ANSI para fondo de color gris claro
     */
    public final static String BG_LIGHT_BLACK = "\u001B[100m";

    /**
     * Código ANSI para fondo de color rojo claro/brillante
     */
    public final static String BG_LIGHT_RED = "\u001B[101m";

    /**
     * Código ANSI para fondo de color verde claro/brillante
     */
    public final static String BG_LIGHT_GREEN = "\u001B[102m";

    /**
     * Código ANSI para fondo de color amarillo claro/brillante
     */
    public final static String BG_LIGHT_YELLOW = "\u001B[103m";

    /**
     * Código ANSI para fondo de color azul claro/brillante
     */
    public final static String BG_LIGHT_BLUE = "\u001B[104m";

    /**
     * Código ANSI para fondo de color púrpura/magenta claro/brillante
     */
    public final static String BG_LIGHT_PURPLE = "\u001B[105m";

    /**
     * Código ANSI para fondo de color cian claro/brillante
     */
    public final static String BG_LIGHT_CYAN = "\u001B[106m";

    /**
     * Código ANSI para fondo de color blanco brillante
     */
    public final static String BG_LIGHT_WHITE = "\u001B[107m";

    // ===== ESTILOS DE TEXTO =====

    /**
     * Código ANSI para texto en negrita/bold
     */
    public final static String BOLD = "\u001B[1m";

    /**
     * Código ANSI para texto en cursiva/itálica
     */
    public final static String ITALIC = "\u001B[3m";

    /**
     * Código ANSI para texto subrayado
     */
    public final static String UNDERLINE = "\u001B[4m";
}