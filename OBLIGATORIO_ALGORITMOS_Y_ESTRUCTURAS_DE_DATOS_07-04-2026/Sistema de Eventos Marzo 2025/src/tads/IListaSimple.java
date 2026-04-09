
package tads;


public interface IListaSimple<T> {
    
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
    
    public boolean eliminarElemento(T x);
    
    public boolean eliminarPorIndice(T x);
    
}
