package sistemaAutogestion;

import java.time.LocalDate;

public interface IObligatorio<T> {

    /*
    **************** REGISTROS y ELIMINACIÓN **************************************
     */
    
    public Retorno crearSistemaDeGestion();

    public Retorno registrarSala(String nombre, int capacidad);

    public Retorno eliminarSala(String nombre);

    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario,
            LocalDate fecha);

    public Retorno registrarCliente(String cedula, String nombre);

    public Retorno comprarEntrada(String cedula, String codigoEvento);

    public Retorno eliminarEvento(String codigo);

    public Retorno devolverEntrada(String cedula, String codigoEvento);

    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario);

   
    /*
    **************** REPORTES Y CONSULTAS **************************************
     */
    
    public void agregarInicio(T x);
    
    public void mostrar();
    
    public int cantidadElementos();
    
    public boolean esVacia();
    
    public void vaciar();
    
    public boolean existeElemento (T x);
    
    public Object obtenerElemento(int indice);
    
    public void agregarFinal (T x);
    
    public void eliminarInicio();
    
    public void eliminarFinal();
    
    public void agregarOrdenado(T x);
    
    public boolean eliminarElemento(T x);
    
    public boolean eliminarPorIndice(T x);
    
}
