import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gestión del personal de la fábrica. Permite el registro, búsqueda y 
 * selección de trabajadores para las diferentes tareas de producción.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class RecursosHumanos
{
    private List<Trabajador> empleados;
    
    public RecursosHumanos() {
        this.empleados = new ArrayList<>();
    }
    
    /**
     * Registra un nuevo trabajador en el sistema.
     * @param nuevoEmpleado Objeto trabajador a dar de alta.
     */
    public void altaTrabajador(Trabajador nuevoEmpleado) {
        if (nuevoEmpleado != null) {
            empleados.add(nuevoEmpleado);
        }
    }
    
    
    /**
     * Localiza a un trabajador mediante su Documento Nacional de Identidad.
     * @param dni Cadena de texto con el DNI a buscar.
     * @return El trabajador encontrado o null si no existe.
     */
    public Trabajador buscarPorDni(String dni) {
        for (Trabajador t : empleados) {
            if (t.obtenerDni().equals(dni)) {
                return t;
            }
        }
        return null;
    }
    
    
    /**
     * Selecciona un grupo de operarios de forma aleatoria para asignar a una fase de montaje.
     * @param numeroOperarios Cantidad de operarios requeridos.
     * @return Array con los operarios seleccionados.
     */
    public TrabajadorOperario[] buscarOperariosAleatorios(int numeroOperarios){
        List<TrabajadorOperario> totalOperarios = new ArrayList<>();
        
        // Filtramos solo aquellos trabajadores que ocupan el puesto de operario
        for (Trabajador t : empleados) {
            if(t.obtenerPuesto() == TrabajadorPuesto.OPERARIO){
                totalOperarios.add((TrabajadorOperario) t);
            }
        }
        
        // Mezclamos la lista para garantizar la selección aleatoria
        Collections.shuffle(totalOperarios);
        
        // Ajustamos el número dependiendo de la disponibilidad real de operarios
        int cantidadARetornar = Math.min(numeroOperarios, totalOperarios.size());
        TrabajadorOperario[] operariosSeleccionados = new TrabajadorOperario[cantidadARetornar];
        for(int i = 0; i < numeroOperarios; i++) {
            operariosSeleccionados[i] = totalOperarios.get(i);
        }
        return operariosSeleccionados;
    }
    
    public List<Trabajador> obtenerEmpleados() {
        return empleados;
    }
}