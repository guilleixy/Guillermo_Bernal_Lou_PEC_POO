
/**
 * Write a description of interface IAlmacen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface IAlmacen
{
    void añadirVehiculo(Vehiculo v);
    void actualizarVehiculo(Vehiculo v);
    Vehiculo obtenerStockVehiculo(String tipo);
    
    boolean hayPiezasSuficientes(String tipoComponente);
        
    int obtenerStockComponente(String tipoComponente);
    void añadirStockComponente(ComponenteVehiculo c, int cantidad);
    void quitarStockComponente(ComponenteVehiculo c, int cantidad);
    
    String obtenerEstadoCompleto();
}