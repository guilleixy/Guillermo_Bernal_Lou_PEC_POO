
/**
 * Write a description of class MotorGasolina here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MotorGasolina extends Motor
{
    public MotorGasolina(int cilindrada, int potencia, int numCilindros){
        super(cilindrada, potencia, numCilindros);
    }
    @Override
    public String obtenerIdentificador() {
        return "MOTOR_GASOLINA_" + obtenerDatosTecnicos();
    }
}