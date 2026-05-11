import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementación del almacén central de la fábrica.
 * Gestiona el control de existencias de piezas y el registro de vehículos producidos.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Almacen implements IAlmacen
{
    private Map<String, Integer> stockComponentes;
    private List<Vehiculo> stockVehiculos;
    private List<RegistroMontaje> historialMontaje;
    
    public Almacen() {
        this.stockComponentes = new HashMap<>();
        this.stockVehiculos = new ArrayList<>();
        this.historialMontaje = new ArrayList<>();
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
        quitarStockComponente(c.obtenerIdentificador(), cantidad);
    }

    @Override
    public void quitarStockComponente(String identificador, int cantidad){
        int stockActual = obtenerStockComponente(identificador);
        if(stockActual >= cantidad){
            stockComponentes.put(identificador, stockActual - cantidad);
            Dashboard.mostrarMensaje("Almacén: Stock disminuido. " + identificador + ": " + stockComponentes.get(identificador));
        } else {
            Dashboard.mostrarError("STOCK INSUFICIENTE de " + identificador + ". Se requieren " + cantidad + " y solo hay " + stockActual);
        }
    }
    
    @Override
    public List<Vehiculo> obtenerVehiculosTerminados() {
        return new ArrayList<>(stockVehiculos);
    }

    @Override
    public void registrarMontaje(RegistroMontaje registro) {
        historialMontaje.add(registro);
    }

    @Override
    public List<RegistroMontaje> consultarHistorialPorFecha(Date desde, Date hasta) {
        List<RegistroMontaje> resultado = new ArrayList<>();
        for (RegistroMontaje r : historialMontaje) {
            if (!r.obtenerFecha().before(desde) && !r.obtenerFecha().after(hasta)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public String obtenerEstadoCompleto(){
        StringBuilder reporte = new StringBuilder("--- ESTADO ACTUAL DEL ALMACÉN ---\n");
        reporte.append("COMPONENTES EN STOCK:\n");
        
        if (stockComponentes.isEmpty()) {
            reporte.append("- Sin existencias registradas.\n");
        } else {
            for (Map.Entry<String, Integer> entrada : stockComponentes.entrySet()) {
                reporte.append(String.format(" > %s: %d unidades\n", entrada.getKey(), entrada.getValue()));
            }
        }
        
        reporte.append("VEHÍCULOS TERMINADOS: ").append(stockVehiculos.size()).append("\n");
        reporte.append("----------------------------------");
        
        return reporte.toString();
    }
}