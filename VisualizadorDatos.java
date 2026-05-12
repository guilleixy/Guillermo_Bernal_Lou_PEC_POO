/**
 * Define las operaciones necesarias para la representación de datos del sistema.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public interface VisualizadorDatos
{
    void imprimirEstadoSistema(String informacionAlmacen, String informacionCadenas);
    void imprimirCadena(String mensaje);
    String leerTexto(String mensaje);
    void imprimirMensaje(String mensaje);
    void imprimirError(String error);
}