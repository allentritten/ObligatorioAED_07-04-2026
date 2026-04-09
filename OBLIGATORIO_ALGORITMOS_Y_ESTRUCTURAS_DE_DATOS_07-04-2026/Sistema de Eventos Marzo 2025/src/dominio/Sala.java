
package dominio;

import tads.ListaSimpleNodos;


public class Sala {
    private String nombre;
    private int capacidad;
    private ListaSimpleNodos<Evento> listaEventos;

    public Sala(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        listaEventos = new ListaSimpleNodos();
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setListaEventos(ListaSimpleNodos<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    
    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ListaSimpleNodos<Evento> getListaEventos() {
        return listaEventos;
    }

    
    public boolean equals(Object o){
        return this.getNombre().equalsIgnoreCase(((Sala)o).getNombre());
    }
    
    @Override
    public String toString() {
        //Sala1-100#
        return this.nombre+"-"+this.capacidad;
    }
}
