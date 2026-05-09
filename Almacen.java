import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class Almacen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Almacen implements IAlmacen
{
    private Map<String, Integer> stockComponentes;
    private List<Vehiculo> stockVehiculos;
    
    public Almacen() {
        this.stockComponentes = new HashMap<>();
        this.stockVehiculos = new ArrayList<>();
    }
    
    @Override
    public void añadirVehiculo(Vehiculo v) {
        if (v != null) {
            stockVehiculos.add(v);
        }
    }
    
    public void actualizarVehiculo(Vehiculo v) {
        int index = stockVehiculos.indexOf(v);
        if (index != -1) {
            stockVehiculos.set(index, v);
        }
    }
    
    @Override
    public Vehiculo obtenerStockVehiculo(String tipo) {
        for (Vehiculo v : stockVehiculos) {
            if (v.obtenerTipo().equalsIgnoreCase(tipo) && v.estaTerminado()) {
                return v;
            }
        }
        return null; // O lanzar una excepción si no hay stock
    }
    
    public int obtenerStockComponente(String identificadorComponente){
        return stockComponentes.getOrDefault(identificadorComponente, 0);
    }
    
    public void añadirStockComponente(ComponenteVehiculo c, int cantidad){
        String clave = c.obtenerIdentificador(); 
        stockComponentes.put(clave, stockComponentes.getOrDefault(clave, 0) + cantidad);
    }
    
    public void quitarStockComponente(ComponenteVehiculo c, int cantidad){
        String clave = c.obtenerIdentificador(); 
        if(obtenerStockComponente(clave) >= cantidad){
            stockComponentes.put(clave, stockComponentes.getOrDefault(clave, 0) - cantidad);  
        }
    }
}