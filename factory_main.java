import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase principal encargada de la instanciación y arranque del sistema.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class factory_main
{
    /**
     * Punto de entrada de la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args){
        // Subsistemas principales
        IAlmacen almacen = new Almacen();
        RecursosHumanos rrhh = new RecursosHumanos();
        Dashboard dashboard = new Dashboard();
        Dashboard.configurarVisualizador(new VisualizadorTerminal());

        List<CadenaMontaje> listaCadenas = new ArrayList<>();
        listaCadenas.add(new CadenaMontaje("Línea A - Turismo"));
        listaCadenas.add(new CadenaMontaje("Línea B - Biplaza"));
        listaCadenas.add(new CadenaMontaje("Línea C - Furgoneta"));

        Planificador planificador = new Planificador(listaCadenas, rrhh, almacen);
        new SistemaGestion(almacen, rrhh, dashboard, planificador);

        Dashboard.mostrarMensaje("¡Sistema iniciado!");

        new InterfazTextual(almacen, rrhh, planificador, listaCadenas).iniciar();
    }
}