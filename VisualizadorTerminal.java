import java.util.Scanner;

/**
 * Implementación de la interfaz de visualización para salida por consola (System.out).
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class VisualizadorTerminal implements VisualizadorDatos
{
    private Scanner scanner;

    
    @Override
    public void imprimirEstadoSistema(String informacionAlmacen, String informacionCadenas) {
        System.out.println("\n==================================================");
        System.out.println("       ESTADO ACTUAL DE LA FACTORÍA");
        System.out.println("==================================================");
        System.out.println("[ALMACÉN]");
        System.out.println(informacionAlmacen);
        System.out.println("--------------------------------------------------");
        System.out.println("[CADENAS DE MONTAJE]");
        System.out.println(informacionCadenas);
        System.out.println("==================================================\n");
    }
    
    @Override
    public void imprimirCadena(String mensaje){
        System.out.println(mensaje);
    }    

    @Override
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
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