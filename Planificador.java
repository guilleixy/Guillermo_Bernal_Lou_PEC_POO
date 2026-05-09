import java.util.List;

/**
 * Write a description of class Planificador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    
    public void empezarSimulacionSimple(){
        while(()){
            reloj++;
            for(CadenaMontaje cadena : cadenasMontaje){
                cadena.avanzar(reloj);
            }
        
       }
    }
}