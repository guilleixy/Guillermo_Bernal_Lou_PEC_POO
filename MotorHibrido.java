
/**
 * Write a description of class MotorHibrido here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MotorHibrido extends Motor
{
    public MotorHibrido(int cilindrada, int potencia, int numCilindros){
        super(cilindrada, potencia, numCilindros);
    }
    @Override
    public String obtenerIdentificador() {
        return "MOTOR_HIBRIDO_" + obtenerDatosTecnicos();
    }
}