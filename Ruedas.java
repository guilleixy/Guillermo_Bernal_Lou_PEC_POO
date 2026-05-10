/**
 * Representa el componente de ruedas de un vehículo.
 * Incluye dimensiones, índices de carga y códigos de velocidad.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
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
    
    public double obtenerAncho() { return ancho; }
    public double obtenerDiametro() { return diametro; }
    public double obtenerIndiceCarga() { return indiceCarga; }
    public double obtenerCodigoVelocidad() { return codigoVelocidad; }
    public RuedasTipo obtenerTipo() { return tipo; }

    public void establecerAncho(double ancho) { this.ancho = ancho; }
    public void establecerDiametro(double diametro) { this.diametro = diametro; }
    public void establecerIndiceCarga(double indiceCarga) { this.indiceCarga = indiceCarga; }
    public void establecerCodigoVelocidad(double codigoVelocidad) { this.codigoVelocidad = codigoVelocidad; }
    public void establecerTipo(RuedasTipo tipo) { this.tipo = tipo; }
    
    @Override
    public String obtenerDatosTecnicos(){
        return ancho + "ancho_" + diametro + "diametro_" + indiceCarga + "indiceCarga_" + codigoVelocidad + "codigoVelocidad_" + tipo + "tipo";
    }
    
    @Override
    public String obtenerIdentificador(){
        return "RUEDAS_" + obtenerDatosTecnicos();
    }
    
}