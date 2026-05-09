import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class RecursosHumanos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecursosHumanos
{
    private List<Trabajador> empleados;
    
    public RecursosHumanos() {
        this.empleados = new ArrayList<>();
    }
    
    public void altaTrabajador(Trabajador nuevoEmpleado) {
        if (nuevoEmpleado != null) {
            empleados.add(nuevoEmpleado);
        }
    }
    
    public Trabajador buscarPorDni(String dni) {
        for (Trabajador t : empleados) {
            if (t.obtenerDni().equals(dni)) {
                return t;
            }
        }
        return null;
    }
}