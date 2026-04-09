
package dominio;


public class Calificacion {
    private int puntaje;
    private String comentario;
    private Cliente unCliente;
    private Evento unEvento;

    public Calificacion(int puntaje, String comentario, Cliente unCliente, Evento unEvento) {
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.unCliente = unCliente;
        this.unEvento = unEvento;
    }

    public Cliente getUnCliente() {
        return unCliente;
    }

    public Evento getUnEvento() {
        return unEvento;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setUnCliente(Cliente unCliente) {
        this.unCliente = unCliente;
    }

    public void setUnEvento(Evento unEvento) {
        this.unEvento = unEvento;
    }

    
    
    
    
    
    
    
}

