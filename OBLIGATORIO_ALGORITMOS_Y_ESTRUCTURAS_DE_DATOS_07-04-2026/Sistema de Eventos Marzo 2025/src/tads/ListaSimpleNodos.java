package tads;

public class ListaSimpleNodos<T> implements IListaSimple<T> {

    private Nodo inicio;
    private int cantElementos;

    public ListaSimpleNodos() {
        inicio = null;
        cantElementos = 0;
    }

    @Override
    public void agregarInicio(T x) {
        Nodo nuevo = new Nodo(x);
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
        cantElementos++;
    }

    @Override
    public void mostrar() {
        Nodo aux = inicio;

        while (aux != null) {
            System.out.print(aux.getDato() + " - ");
            aux = aux.getSiguiente();
        }
    }

    @Override
    public int cantidadElementos() {
        /*-
        Nodo aux = inicio;
        int cant = 0;

        while (aux != null) {
            cant++;
            aux = aux.getSiguiente();
        }
        return cant;*/
        return cantElementos;
    }

    @Override
    public boolean esVacia() {
        return inicio == null;
    }

    @Override
    public void vaciar() {
        inicio = null;
        cantElementos = 0;
    }

    @Override
    public boolean existeElemento(T x) {

        Nodo aux = inicio;
        boolean existe = false;

        while (aux != null && !existe) {
            if (aux.getDato().equals(x)) {
                existe = true;
            }
            aux = aux.getSiguiente();
        }

        return existe;

    }

    /*
    pre: indice >= 0 y < que cantidad de elementos
    post: retorna el elemento que esta en indice (primer elemento esta en indice 0)
     */
    @Override
    public Object obtenerElemento(int indice) {

        Nodo aux = inicio;
        int pos = 0;

        while (aux != null) {
            if (pos == indice) {
                return aux.getDato(); // Retornás directamente cuando lo encontrás
            }
            aux = aux.getSiguiente();
            pos++;
        }

        return null; // Si no se encuentra, devolvés null
    }

    @Override
    public void agregarFinal(T x) {
        if (esVacia()) {
            agregarInicio(x);
        } else {
            Nodo aux = inicio;

            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }

            Nodo nuevo = new Nodo(x);
            aux.setSiguiente(nuevo);
            cantElementos++;
        }
    }

    @Override
    public void eliminarInicio() {
        if (!esVacia()) {
            Nodo aBorrar = inicio;
            inicio = inicio.getSiguiente();
            aBorrar.setSiguiente(null);
            cantElementos--;
        }
    }

    @Override
    public void eliminarFinal() {
        if (!esVacia()) {

            if (inicio.getSiguiente() == null) {
                eliminarInicio();
            } else {
                Nodo aux = inicio;
                while (aux.getSiguiente().getSiguiente() != null) {
                    aux = aux.getSiguiente();
                }
                aux.setSiguiente(null);
                cantElementos--;

            }
        }
    }

    @Override
    public boolean eliminarElemento(T x) {
        boolean elimine = false;
        if (!esVacia()) {
            if (inicio.getDato().equals(x)) {
                eliminarInicio();
                elimine = true;
            } else {
                Nodo<T> aux = inicio;

                while (aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(x)) {//aca antes estaba .equals(aux), tuve que cambiarlo a .equals(x)
                    aux = aux.getSiguiente();
                }

                if (aux.getSiguiente() != null) {
                    elimine = true;
                    Nodo<T> aEliminar = aux.getSiguiente();
                    aux.setSiguiente(aux.getSiguiente().getSiguiente());
                    aEliminar.setSiguiente(null);
                    cantElementos--;
                }
            }
        }
        return elimine;
    }

    @Override
    public boolean eliminarPorIndice(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
