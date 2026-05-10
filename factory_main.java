import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase principal encargada de la instanciación y arranque del sistema.
 * @author Guillermo Bernal
 * @version 10 de mayo de 2026
 */
public class factory_main
{
    /**
     * Punto de entrada de la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args){
        // Inicialización de componentes del sistema
        IAlmacen almacen = new Almacen();
        RecursosHumanos rrhh = new RecursosHumanos();
        Dashboard dashboard = new Dashboard();
        // 2. Creación de componentes para el stock del almacén
        // Creamos piezas con identificadores únicos según lo definido en sus clases
        Chasis chasisAcero = new Chasis("Acero Galvanizado");
        Motor motorH = new MotorHibrido(2000, 150, 4);
        Tapiceria tapiceriaCuero = new Tapiceria("Negro", 5.5, TapiceriaTipoMaterial.CUERO);
        Ruedas ruedasDeportivas = new Ruedas(225, 18, 92, 240, RuedasTipo.DEPORTIVO);
        
        // Añadimos stock al almacén
        almacen.añadirStockComponente(chasisAcero, 10);
        almacen.añadirStockComponente(motorH, 10);
        almacen.añadirStockComponente(tapiceriaCuero, 10);
        almacen.añadirStockComponente(ruedasDeportivas, 10);
        
        // 3. Registro de personal en Recursos Humanos
        // Creamos 4 operarios (mínimo requerido para una cadena)
        for (int i = 1; i <= 4; i++) {
            rrhh.altaTrabajador(new TrabajadorOperario(
                "1234567" + i + "X", "Operario" + i, "Apellido", "Calle Falsa 123", 
                98765432, 1200.0, TrabajadorPuesto.OPERARIO, new Date(), 
                TrabajadorNivelProductividad.ESTANDAR
            ));
        }

        // 4. Configuración de la línea de producción
        CadenaMontaje linea1 = new CadenaMontaje("Línea de Ensamblaje A");
        
        // El planificador necesita la lista de cadenas y acceso a RRHH
        List<CadenaMontaje> listaCadenas = new ArrayList<>();
        listaCadenas.add(linea1);
        Planificador planificador = new Planificador(listaCadenas, rrhh);
        
        // 5. Inicio del Sistema de Gestión
        SistemaGestion sistema = new SistemaGestion(almacen, rrhh, dashboard, planificador);
        Dashboard.mostrarMensaje("¡Fábrica iniciada correctamente!");
        
        // --- PRUEBA DE PRODUCCIÓN ---
        
        // A. Seleccionamos operarios aleatorios y los asignamos a la cadena
        TrabajadorOperario[] equipo = rrhh.buscarOperariosAleatorios(4);
        linea1.asignarOperarios(equipo);
        
        // B. Creamos un vehículo para fabricar (un Turismo)
        Vehiculo nuevoCoche = new VehiculoTurismo("Rojo", 5, 1200, 1800);
        
        // C. Iniciamos el montaje en la cadena
        linea1.iniciarMontaje(nuevoCoche);
        
        // D. Mostramos el estado inicial del almacén
        Dashboard.mostrarEstado(almacen.obtenerEstadoCompleto(), "Cadena A: Preparada");
        
        // E. Ejecutamos la simulación (avanzará las fases de montaje)
        planificador.empezarSimulacionSimple();
        
        // F. Resultado final
        Dashboard.mostrarMensaje("Prueba finalizada con éxito.");
        Dashboard.mostrarEstado(almacen.obtenerEstadoCompleto(), "Cadena A: Vacía (Trabajo entregado)");
    }
}