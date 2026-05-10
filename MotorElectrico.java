/**
 * Representa un motor de tipo eléctrico.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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