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
    
    @Override
    public void actualizarVehiculo(Vehiculo v) {
        int index = stockVehiculos.indexOf(v);
        if (index != -1) {
            stockVehiculos.set(index, v);
        }
    }
    
    @Override
    public Vehiculo obtenerStockVehiculo(String tipo) {
        for (Vehiculo v : stockVehiculos) {
            if (v.obtenerTipo().equalsIgnoreCase(tipo)) {
                return v;
            }
        }
        return null;
    }
    
    @Override
    public boolean hayPiezasSuficientes(String tipoComponente) {
        int cantidadActual = obtenerStockComponente(tipoComponente);
        
        if (cantidadActual > 0) {
            return true;
        } else {
            Dashboard.mostrarError("Stock agotado para el componente: " + tipoComponente);
            return false;
        }
    }
    
    @Override
    public int obtenerStockComponente(String identificadorComponente){
        return stockComponentes.getOrDefault(identificadorComponente, 0);
    }
    
    @Override
    public void añadirStockComponente(ComponenteVehiculo c, int cantidad){
        String clave = c.obtenerIdentificador(); 
        stockComponentes.put(clave, stockComponentes.getOrDefault(clave, 0) + cantidad);
        Dashboard.mostrarMensaje("Almacén: Stock aumentado. " + clave + ": " + stockComponentes.get(clave));
    }
    
    @Override
    public void quitarStockComponente(ComponenteVehiculo c, int cantidad){
        String clave = c.obtenerIdentificador(); 
        int stockActual = obtenerStockComponente(clave);
        if(stockActual >= cantidad){
            stockComponentes.put(clave, stockActual - cantidad);  
            Dashboard.mostrarMensaje("Almacén: Stock dismiunido. " + clave + ": " + stockComponentes.get(clave));
        } else {
            Dashboard.mostrarError("STOCK INSUFICIENTE de " + clave + ". Se requieren " + cantidad + " y solo hay " + stockActual);
        }
    }
    
    @Override
    public String obtenerEstadoCompleto(){
        String reporte = "--- ESTADO DEL ALMACÉN ---\n";
        reporte += "COMPONENTES:\n";
        
        for (String clave : stockComponentes.keySet()) {
            reporte += "- " + clave + ": " + stockComponentes.get(clave) + " unidades\n";
        }
        
        reporte += "VEHÍCULOS TERMINADOS: " + stockVehiculos.size() + "\n";
        reporte += "--------------------------";
        
        return reporte;
    }
}