package dominio;

import java.time.LocalDate;
import tads.ListaSimpleNodos;

public class Evento {

    private String codigo;
    private String descripcion;
    private int aforoNecesario;
    private LocalDate fecha;
    private ListaSimpleNodos<Calificacion> listaCalificaciones;
    private Sala salaAsignada;
    private ListaSimpleNodos<Cliente> listaEspera;
    private int cantEntradas;

    public Evento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.aforoNecesario = aforoNecesario;
        this.fecha = fecha;
        this.cantEntradas = aforoNecesario;
        this.listaCalificaciones = new ListaSimpleNodos<>();
        this.listaEspera = new ListaSimpleNodos<>();
    }

    public int sumarEntrada() {
        return cantEntradas++;
    }

    public int restarEntrada() {
        return cantEntradas--;
    }

    public int getCantEntradas() {
        return cantEntradas;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getAforoNecesario() {
        return aforoNecesario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ListaSimpleNodos<Calificacion> getListaCalificaciones() {
        return listaCalificaciones;
    }

    public Sala getSalaAsignada() {
        return salaAsignada;
    }

    public ListaSimpleNodos<Cliente> getListaEspera() {
        return listaEspera;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAforoNecesario(int aforoNecesario) {
        this.aforoNecesario = aforoNecesario;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void addCalificacion(Calificacion unaCalificacion) {
        this.listaCalificaciones.agregarInicio(unaCalificacion);
    }

    public void setSalaAsignada(Sala salaAsignada) {
        this.salaAsignada = salaAsignada;
    }

    public boolean equals(Object o) {
        return this.codigo.equalsIgnoreCase(((Evento) o).getCodigo());
    }

    public int compareTo(Evento otroEvento) {
        return this.getCodigo().compareToIgnoreCase(otroEvento.codigo);
    }

    @Override
    public String toString() {
        return this.codigo + "-" + this.descripcion + "-" + this.salaAsignada.getNombre();
    }
}
