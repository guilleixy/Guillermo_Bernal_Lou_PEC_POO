
/**
 * Write a description of class CadenaMontaje here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CadenaMontaje
{
    private String identificadorCadena;
    private TrabajadorOperario[] operarios;
    private int estadoActual;
    
    public CadenaMontaje(String id) {
        this.identificadorCadena = id;
        this.operarios = new TrabajadorOperario[4];
        this.estadoActual = 0;
    }
    
    public void asignarOperarios(TrabajadorOperario[] nuevosOperarios) {
        if(nuevosOperarios.length == 4){
            this.operarios = nuevosOperarios;
            Dashboard.mostrarMensaje("Operarios de la cadena " + identificadorCadena + " asignados.");
        } else {
            Dashboard.mostrarError("La cadena " + identificadorCadena + " requiere exactamente 4 operarios.");
        }
    }
}