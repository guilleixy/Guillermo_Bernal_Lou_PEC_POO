import java.util.Date;
import java.util.List;

/**
 * Interfaz que define las operaciones de gestión de inventario tanto
 * para componentes individuales como para vehículos finalizados.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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
    void quitarStockComponente(String identificador, int cantidad);
    
    String obtenerEstadoCompleto();

    void registrarMontaje(RegistroMontaje registro);
    List<RegistroMontaje> consultarHistorialPorFecha(Date desde, Date hasta);
    List<Vehiculo> obtenerVehiculosTerminados();
}