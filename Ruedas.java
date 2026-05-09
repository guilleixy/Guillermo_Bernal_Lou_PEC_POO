
/**
 * Write a description of class Rueda here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ruedas extends ComponenteVehiculo
{
    private double ancho;
    private double diametro;
    private double indiceCarga;
    private double codigoVelocidad;
    private RuedasTipo tipo;
        
    public Ruedas(double ancho, double diametro, double indiceCarga, double codigoVelocidad, RuedasTipo tipo){
        this.ancho = ancho;
        this.diametro = diametro;
        this.indiceCarga = indiceCarga;
        this.codigoVelocidad = codigoVelocidad;
        this.tipo = tipo;
    }
    
    @Override
    public String obtenerDatosTecnicos(){
        return ancho + "ancho_" + diametro + "diametro_" + indiceCarga + "indiceCarga_" + codigoVelocidad + "codigoVelocidad_" + tipo + "tipo";
    }
    
    @Override
    public String obtenerIdentificador(){
        return "RUEDAS_" + obtenerDatosTecnicos();
    }
    
}