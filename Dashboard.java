
/**
 * Write a description of class Dashboard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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