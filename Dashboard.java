/**
 * Clase de utilidad que actúa como punto central para la salida de información.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Dashboard
{
    public static VisualizadorDatos visualizador;
    
    public static void configurarVisualizador(VisualizadorDatos v){
        visualizador = v;
    }
    
    public static void mostrarEstado(String infoAlmacen, String infoCadenas) {
        if (visualizador != null) {
            visualizador.imprimirEstadoSistema(infoAlmacen, infoCadenas);
        }
    }
    
    public static void mostrarMensaje(String texto) {
        if (visualizador != null) {
            visualizador.imprimirMensaje(texto);
        }
    }
    
    public static void mostrarError(String error) {
        if (visualizador != null) {
            visualizador.imprimirError(error);
        }
    }
}