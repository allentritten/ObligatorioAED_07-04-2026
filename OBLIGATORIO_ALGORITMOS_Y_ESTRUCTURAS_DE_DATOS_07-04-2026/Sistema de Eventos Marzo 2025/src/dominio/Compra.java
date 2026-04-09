package dominio;

public class Compra {

    private boolean fueDevuelta = false;
    private Cliente clienteComprador;
    private Evento eventoComprado;

    public Compra(Cliente clienteComprador, Evento eventoComprado) {
        this.clienteComprador = clienteComprador;
        this.eventoComprado = eventoComprado;
    }

    public void setFueDevuelta(boolean fueDevuelta) {
        this.fueDevuelta = fueDevuelta;
    }

    public void setClienteComprador(Cliente clienteComprador) {
        this.clienteComprador = clienteComprador;
    }

    public void setEventoComprado(Evento eventoComprado) {
        this.eventoComprado = eventoComprado;
    }

    public boolean isFueDevuelta() {
        return fueDevuelta;
    }

    public Cliente getClienteComprador() {
        return clienteComprador;
    }

    public Evento getEventoComprado() {
        return eventoComprado;
    }

    @Override
    public String toString() {
        return this.getEventoComprado().getCodigo() + "-";
    }
}
