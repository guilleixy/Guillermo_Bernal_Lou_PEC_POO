/**
 * Clase abstracta que define las características técnicas base de un motor.
 * Almacena cilindrada, potencia y número de cilindros.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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

    public int obtenerCilindrada() { return cilindrada; }
    public void establecerCilindrada(int cilindrada) { this.cilindrada = cilindrada; }
    public int obtenerPotencia() { return potencia; }
    public void establecerPotencia(int potencia) { this.potencia = potencia; }

    public int obtenerNumCilindros() { return numCilindros; }
    public void establecerNumCilindros(int numCilindros) { this.numCilindros = numCilindros; }
    
    @Override
    public ComponenteTipo obtenerTipo(){ return ComponenteTipo.MOTOR; }

    /**
     * Genera una cadena con los datos técnicos base del motor.
     * @return String formateado con cilindrada, potencia y cilindros.
     */
    public String obtenerDatosTecnicos() {
        return cilindrada + "cc_" + potencia + "cv_" + numCilindros + "cil";
    }
}