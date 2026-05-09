
/**
 * Write a description of class Motor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Motor extends ComponenteVehiculo
{
    private int cilindrada;
    private int potencia;
    private int numCilindros;
    
    public Motor(int cilindrada, int potencia, int numCilindros){
        this.cilindrada = cilindrada;
        this.potencia = potencia;
        this.numCilindros = numCilindros;
    }
    public String obtenerDatosTecnicos() {
        return cilindrada + "cc_" + potencia + "cv_" + numCilindros + "cil";
    }
}