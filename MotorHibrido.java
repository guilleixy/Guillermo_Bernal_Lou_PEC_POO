/**
 * Representa un motor de tipo híbrido.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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