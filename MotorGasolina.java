/**
 * Representa un motor de tipo gasolina.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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