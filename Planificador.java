import java.util.List;

/**
 * Clase responsable de la coordinación temporal y operativa de la fábrica.
 * Gestiona el avance de las líneas de producción mediante un sistema de ticks (reloj).
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class Planificador
{
    private int reloj;
    private List<CadenaMontaje> cadenasMontaje;
    private RecursosHumanos rrhh;
    
    public Planificador(List<CadenaMontaje> cadenasMontaje, RecursosHumanos rrhh){
        this.cadenasMontaje = cadenasMontaje;
        this.rrhh = rrhh;
        this.reloj = 0;
    }
    
    /**
     * Inicia el ciclo de simulación de la fábrica.
     * El proceso continúa hasta que todos los vehículos en curso han sido finalizados.
     */
    public void empezarSimulacionSimple(){
        Dashboard.mostrarMensaje("Iniciando ciclo de producción...");
        
        boolean trabajoPendiente = true;
        
        while(trabajoPendiente){
            reloj++;
            trabajoPendiente = false; // Se asume falso hasta encontrar una cadena activa
            
            for(CadenaMontaje cadena : cadenasMontaje){
                // Si la cadena tiene un vehículo y no ha terminado todas las fases
                if (cadena.obtenerVehiculoEnCurso() != null && !cadena.estaTrabajoFinalizado()) {
                    cadena.avanzarFase();
                    trabajoPendiente = true;
                }
                
                // Si la cadena acaba de terminar el trabajo en este tick
                if (cadena.estaTrabajoFinalizado()) {
                    Dashboard.mostrarMensaje("Vehículo completado en " + cadena.obtenerIdentificadorCadena() + 
                                             " en el tiempo: " + reloj);
                }
            }
            
            // Limitador de seguridad para evitar bucles infinitos en la simulación
            if (reloj > 1000) {
                Dashboard.mostrarError("Simulación interrumpida: Tiempo límite excedido.");
                break;
            }
        }
        
        Dashboard.mostrarMensaje("Simulación finalizada. Tiempo total: " + reloj + " unidades.");
    }

    public int obtenerReloj() { return reloj; }
}