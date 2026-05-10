import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
    
    public TrabajadorOperario[] buscarOperariosAleatorios(int numeroOperarios){
        List<TrabajadorOperario> totalOperarios = new ArrayList<>();
        for (Trabajador t : empleados) {
            if(t.obtenerPuesto() == TrabajadorPuesto.OPERARIO){
                totalOperarios.add((TrabajadorOperario) t);
            }
        }
        Collections.shuffle(totalOperarios);
        TrabajadorOperario[] operariosSeleccionados = new TrabajadorOperario[4];
        for(int i = 0; i < numeroOperarios; i++) {
            operariosSeleccionados[i] = totalOperarios.get(i);
        }
        return operariosSeleccionados;
    }
}