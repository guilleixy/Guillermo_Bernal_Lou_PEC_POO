
/**
 * Write a description of class VisualizadorTerminal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VisualizadorTerminal implements VisualizadorDatos
{
    
    @Override
    public void imprimirEstadoSistema(String informacionAlmacen, String informacionCadenas){
        
    }
    
    @Override
    public void imprimirMensaje(String mensaje){
        System.out.println("[INFO]: " + mensaje);
    }
    
    @Override
    public void imprimirError(String error){
        System.err.println("[ERROR]: " + error);
    }
    
}