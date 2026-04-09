
package dominio;

import tads.ListaSimpleNodos;


public class Cliente {
    private String CI;
    private String nombre;
    private ListaSimpleNodos<Evento> listaEntradasCliente;
    private ListaSimpleNodos<Compra> listaComprasCliente;

    public Cliente(String CI, String nombre) {
        this.CI = CI;
        this.nombre = nombre;
        listaEntradasCliente = new ListaSimpleNodos();
        listaComprasCliente = new ListaSimpleNodos();
    }

    public ListaSimpleNodos<Compra> getListaComprasCliente() {
        return listaComprasCliente;
    }
    
    public String getCI() {
        return CI;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaSimpleNodos<Evento> getListaEntradasCliente() {
        return listaEntradasCliente;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addListaEntradasCliente(Evento x) {
        this.listaEntradasCliente.agregarInicio(x);
    }

    @Override
    public String toString() {
        return this.getCI() + "-" + this.getNombre();
    }
    
    public boolean equals(Object o){
        return this.getCI().equalsIgnoreCase(((Cliente)o).getCI());
    }
}
