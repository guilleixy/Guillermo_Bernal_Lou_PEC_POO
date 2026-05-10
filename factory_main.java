
/**
 * Write a description of class factory_main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class factory_main
{
    public static void main(String[] args){
        IAlmacen almacen = new Almacen();
        RecursosHumanos rrhh = new RecursosHumanos();
        Dashboard dashboard = new Dashboard();
        Planificador planificador = new Planificador();
        SistemaGestion sistema = new SistemaGestion(almacen, rrhh, dashboard, planificador);
        Dashboard.mostrarMensaje("¡Fábrica iniciada correctamente!");
    }
}