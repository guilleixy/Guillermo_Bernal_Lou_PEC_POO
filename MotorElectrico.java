
/**
 * Write a description of class MotorElectrico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MotorElectrico extends Motor
{
    public MotorElectrico(int cilindrada, int potencia, int numCilindros){
        super(cilindrada, potencia, numCilindros);
    }
        @Override
    public String obtenerIdentificador() {
        return "MOTOR_ELECTRICO_" + obtenerDatosTecnicos();
    }
}