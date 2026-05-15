import java.util.EnumMap;
import java.util.Map;
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
    private Map<ComponenteTipo, Integer> stockComponentes;
    private List<Vehiculo> stockVehiculos;
    private List<RegistroMontaje> historialMontaje;

    public Almacen() {
        this.stockComponentes = new EnumMap<>(ComponenteTipo.class);
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
        int indice = stockVehiculos.indexOf(v);
        if (indice != -1) {
            stockVehiculos.set(indice, v);
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
        try {
            ComponenteTipo tipo = ComponenteTipo.valueOf(tipoComponente.toUpperCase());
            if (stockComponentes.getOrDefault(tipo, 0) > 0) return true;
        } catch (IllegalArgumentException e) {
            // tipo no reconocido
        }
        Dashboard.mostrarError("Stock agotado para el componente: " + tipoComponente);
        return false;
    }

    @Override
    public int obtenerStockComponente(String tipoComponente) {
        try {
            return stockComponentes.getOrDefault(ComponenteTipo.valueOf(tipoComponente.toUpperCase()), 0);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    @Override
    public void añadirStockComponente(ComponenteVehiculo c, int cantidad) {
        ComponenteTipo tipo = c.obtenerTipo();
        stockComponentes.put(tipo, stockComponentes.getOrDefault(tipo, 0) + cantidad);
        Dashboard.mostrarMensaje("Almacén: Stock aumentado. " + tipo + ": " + stockComponentes.get(tipo));
    }

    @Override
    public void quitarStockComponente(ComponenteVehiculo c, int cantidad) {
        quitarStockComponente(c.obtenerTipo().toString(), cantidad);
    }

    @Override
    public void quitarStockComponente(String tipoComponente, int cantidad) {
        try {
            ComponenteTipo tipo = ComponenteTipo.valueOf(tipoComponente.toUpperCase());
            int actual = stockComponentes.getOrDefault(tipo, 0);
            if (actual >= cantidad) {
                stockComponentes.put(tipo, actual - cantidad);
                Dashboard.mostrarMensaje("Almacén: Stock disminuido. " + tipo + ": " + stockComponentes.get(tipo));
            } else {
                Dashboard.mostrarError("STOCK INSUFICIENTE de " + tipo + ". Se requieren " + cantidad + " y solo hay " + actual);
            }
        } catch (IllegalArgumentException e) {
            Dashboard.mostrarError("Tipo de componente no reconocido: " + tipoComponente);
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
    public String obtenerEstadoCompleto() {
        String reporte = "--- ESTADO ACTUAL DEL ALMACÉN ---\n";
        reporte += "COMPONENTES EN STOCK:\n";
        
        if (stockComponentes.isEmpty()) {
            reporte += "- Sin existencias registradas.\n";
        } else {
            for (Map.Entry<ComponenteTipo, Integer> entrada : stockComponentes.entrySet()) {
                reporte += " > " + entrada.getKey() + ": " + entrada.getValue() + " unidades\n";
            }
        }
        
        reporte += "VEHÍCULOS TERMINADOS: " + stockVehiculos.size() + "\n";
        reporte += "----------------------------------";
        
        return reporte;
    }
}