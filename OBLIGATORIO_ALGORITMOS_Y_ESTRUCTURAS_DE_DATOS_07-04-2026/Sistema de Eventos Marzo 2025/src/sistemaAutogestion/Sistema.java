package sistemaAutogestion;

import dominio.Calificacion;
import dominio.Cliente;
import dominio.Compra;
import dominio.Evento;
import dominio.Sala;
import tads.Nodo;
import java.time.LocalDate;
import tads.ListaSimpleNodos;

public class Sistema<T> implements IObligatorio<T> {

    /*ACA CREO TODAS LAS LISTAS*/
    private ListaSimpleNodos<Sala> listaSalas;
    private ListaSimpleNodos<Cliente> listaClientes;
    private ListaSimpleNodos<Evento> listaEntradasSistema;
    private ListaSimpleNodos<Evento> listaEventos;//HAY QUE MODIFICAR ESTO PORQUE ESTA LISTA ESTA HECHA EN "SALA". MODIFICAT LOS METODOS QUE USAN ESTA LISTA Y CAMBIARLA POR LA LISTA DE EVENTOS QUE ESTA EN "SALA"
    private ListaSimpleNodos<Calificacion> listaCalificacionesSistema;

    //me hice "listas de Entradas" en el sistema, pero mi idea era tener la lista de entradas en el Cliente.
    //lo que me paso es que al intentar recorrer la lista de entradas que estaria en Cliente, los for me quedaban muy largos y sentia que estaban mal
    //asi que finalmente termine creandola aca para poder tener una solucion temporal para las funciones.
    //preguntarle al profe como podria resolver los ejercicios teniendo la lista de entradas en Cliente y no en el sistema
    @Override
    public Retorno crearSistemaDeGestion() {
        /*INICIO LAS LISTAS*/
        //cantElementos = 0;
        listaSalas = new ListaSimpleNodos();
        listaClientes = new ListaSimpleNodos();
        listaEntradasSistema = new ListaSimpleNodos();
        listaEventos = new ListaSimpleNodos();//esto lo hice porque me parece muy entreverado acceder a los eventos desde las salas
        return Retorno.ok();
    }

    /*
    error_1:Ya existe una sala con ese nombre
    error_2:Si la capacidad es <= 0
     */
    @Override
    public Retorno registrarSala(String nombre, int capacidad) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);
        Sala salaAux = new Sala(nombre, capacidad);

        if (capacidad <= 0) {
            r.resultado = Retorno.Resultado.ERROR_2;
        } else {
            if (listaSalas.existeElemento(salaAux)) {
                r.resultado = Retorno.Resultado.ERROR_1;
            } else {
                listaSalas.agregarInicio(salaAux);
                r.resultado = Retorno.Resultado.OK;
            }
        }
        return r;
    }

    /*
    error_1:No existe una sala con ese nombre
     */
    @Override
    public Retorno eliminarSala(String nombre) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);
        Sala salaAux = new Sala(nombre, 0);

        if (!listaSalas.existeElemento(salaAux)) {
            r.resultado = Retorno.Resultado.ERROR_1;
        } else {

            for (int i = 0; i < listaSalas.cantidadElementos(); i++) {
                if (((Sala) listaSalas.obtenerElemento(i)).equals(salaAux)) {//creo que este if no hace falta, solo con eliminarElemento bastaria
                    listaSalas.eliminarElemento(salaAux);
                    r.resultado = Retorno.Resultado.OK;
                }
            }
        }
        return r;
    }

    /*
    error_1:Ya existe un evento con ese código
    error_2:Si aforo necesario <= 0
    error_3:No hay salas disponibles para esa fecha con aforo suficiente
     */
    public Retorno registrarEvento(String codigo, String descripcion, int aforoNecesario, LocalDate fecha) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA); // valor por defecto

        // Verificamos si ya existe un evento con el mismo código
        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento e = (Evento) listaEventos.obtenerElemento(i);
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                r.resultado = Retorno.Resultado.ERROR_1;
                return r;
            }
        }

        // Validar aforo
        if (aforoNecesario <= 0) {
            r.resultado = Retorno.Resultado.ERROR_2;
            return r;
        }

        // Buscamos la sala más justa (menor capacidad suficiente y libre ese día)
        Sala salaAsignada = null;

        for (int i = 0; i < listaSalas.cantidadElementos(); i++) {
            Sala salaActual = (Sala) listaSalas.obtenerElemento(i);

            if (salaActual.getCapacidad() >= aforoNecesario) {
                boolean disponible = true;

                // Verificamos si hay evento en la misma fecha
                for (int j = 0; j < salaActual.getListaEventos().cantidadElementos(); j++) {
                    Evento eventoEnSala = (Evento) salaActual.getListaEventos().obtenerElemento(j);
                    if (eventoEnSala.getFecha().equals(fecha)) {
                        disponible = false;
                        break;
                    }
                }

                // Si está disponible y es más justa que la anterior, la guardamos
                if (disponible) {
                    if (salaAsignada == null || salaActual.getCapacidad() < salaAsignada.getCapacidad()) {
                        salaAsignada = salaActual;
                    }
                }
            }
        }

        // Si no encontramos sala, devolvemos ERROR_3
        if (salaAsignada == null) {
            r.resultado = Retorno.Resultado.ERROR_3;
            return r;
        }

        // Creamos y registramos el evento
        Evento nuevoEvento = new Evento(codigo, descripcion, aforoNecesario, fecha);
        nuevoEvento.setSalaAsignada(salaAsignada);

        salaAsignada.getListaEventos().agregarFinal(nuevoEvento);

        // Insertar el evento en orden alfabético por código
        boolean agregado = false;
        ListaSimpleNodos nuevaListaEventos = new ListaSimpleNodos();

        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento actual = (Evento) listaEventos.obtenerElemento(i);
            if (!agregado && nuevoEvento.getCodigo().compareToIgnoreCase(actual.getCodigo()) < 0) {
                nuevaListaEventos.agregarFinal(nuevoEvento);
                agregado = true;
            }
            nuevaListaEventos.agregarFinal(actual);
        }

        if (!agregado) {
            nuevaListaEventos.agregarFinal(nuevoEvento);
        }

        listaEventos = nuevaListaEventos;

        r.resultado = Retorno.Resultado.OK;
        return r;
    }

    /*
    error_1:Formato inválido de cédula
    error_2:Cliente ya registrado (cédula ya registrada)
     */
    @Override
    public Retorno registrarCliente(String cedula, String nombre) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);
        Cliente auxCliente = new Cliente(cedula, nombre);

        if (cedula.length() != 8) {
            r.resultado = Retorno.Resultado.ERROR_1;
            return r;
        }

        for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
            Cliente unCliente = (Cliente) listaClientes.obtenerElemento(i);
            if (unCliente.getCI().equalsIgnoreCase(cedula)) {
                r.resultado = Retorno.Resultado.ERROR_2;
                return r;
            }
        }

        // ORDENAR POR CI
        boolean agregado = false;
        ListaSimpleNodos nuevaLista = new ListaSimpleNodos();

        for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
            Cliente actual = (Cliente) listaClientes.obtenerElemento(i);
            if (!agregado && Integer.parseInt(auxCliente.getCI()) < Integer.parseInt(actual.getCI())) {
                nuevaLista.agregarFinal(auxCliente);
                agregado = true;
            }
            nuevaLista.agregarFinal(actual);
        }

        if (!agregado) {
            nuevaLista.agregarFinal(auxCliente);
        }

        listaClientes = nuevaLista;
        r.resultado = Retorno.Resultado.OK;
        return r;
    }

    /*
    error_1: Cliente no existe
    error_2: Evento no existe
     */
    @Override
    public Retorno comprarEntrada(String cedula, String codigoEvento) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);

        Cliente clienteReal = null;
        Evento eventoReal = null;

        // Buscar cliente
        for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
            Cliente c = (Cliente) listaClientes.obtenerElemento(i);
            if (c.getCI().equalsIgnoreCase(cedula)) {
                clienteReal = c;
            }
        }

        // Si no se encontró el cliente
        if (clienteReal == null) {
            r.resultado = Retorno.Resultado.ERROR_1;
            return r;
        }

        // Buscar evento
        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento e = (Evento) listaEventos.obtenerElemento(i);
            if (e.getCodigo().equalsIgnoreCase(codigoEvento)) {
                eventoReal = e;
            }
        }

        // Si no se encontró el evento
        if (eventoReal == null) {
            r.resultado = Retorno.Resultado.ERROR_2;
            return r;
        }

        // Si hay entradas disponibles
        if (eventoReal.getCantEntradas() > 0) {
            clienteReal.addListaEntradasCliente(eventoReal);
            eventoReal.restarEntrada();
            Compra newCompra = new Compra(clienteReal, eventoReal);
            clienteReal.getListaComprasCliente().agregarFinal(newCompra);
            r.resultado = Retorno.Resultado.OK;
        } else {
            // Agregar cliente a la lista de espera
            eventoReal.getListaEspera().agregarFinal(clienteReal);
            r.resultado = Retorno.Resultado.OK;
        }

        return r;
    }

    /*
    error_1: no existe evento
    error_2: el evento tiene entradas vendidas
     */
    @Override
    public Retorno eliminarEvento(String codigo) {
        //Elimina un evento, siempre y cuando no haya entradas vendidas. 
        //En dicho caso, se debe liberar la sala que tenga asignada.

        Retorno r = new Retorno(Retorno.Resultado.ERROR_1); // Por defecto asumimos que no se encuentra el evento

        if (!listaEventos.esVacia()) {
            for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
                Evento eventoActual = (Evento) listaEventos.obtenerElemento(i);

                if (eventoActual.getCodigo().equalsIgnoreCase(codigo)) {
                    if (eventoActual.getCantEntradas() < eventoActual.getAforoNecesario()) {
                        r.resultado = Retorno.Resultado.ERROR_2;
                    } else {
                        listaEventos.eliminarElemento(eventoActual);
                        r.resultado = Retorno.Resultado.OK;
                    }
                    return r;
                }
            }
        }
        return r;
    }

    /*
    error_1:Cliente no existe
    error_2:Evento no existe
     */
    @Override
    public Retorno devolverEntrada(String cedula, String codigoEvento) {
        /*
        Se devuelve una entrada. Si hay clientes esperando por ella, 
        se reasigna automáticamente al primero en la lista, respetando el orden de llegada.
         */
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);

        if (!listaClientes.esVacia() && !listaEventos.esVacia()) {
            Cliente clienteEncontrado = null;
            Evento eventoEncontrado = null;

            // Buscar al cliente
            for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
                Cliente clienteActual = (Cliente) listaClientes.obtenerElemento(i);
                if (clienteActual.getCI().equalsIgnoreCase(cedula)) {
                    clienteEncontrado = clienteActual;
                    break;
                }
            }

            if (clienteEncontrado == null) {
                r.resultado = Retorno.Resultado.ERROR_1;
                return r;
            }

            for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
                Evento eventoActual = (Evento) listaEventos.obtenerElemento(i);
                if (eventoActual.getCodigo().equalsIgnoreCase(codigoEvento)) {
                    eventoEncontrado = eventoActual;
                    break;
                }
            }

            if (eventoEncontrado == null) {
                r.resultado = Retorno.Resultado.ERROR_2;
                return r;
            }

            if (!clienteEncontrado.getListaEntradasCliente().existeElemento(eventoEncontrado)) {
                r.resultado = Retorno.Resultado.ERROR_2;
                return r;
            }

            // Marcar la compra como devuelta en la listaComprasCliente
            boolean compraEncontrada = false;
            for (int i = 0; i < clienteEncontrado.getListaComprasCliente().cantidadElementos(); i++) {
                Compra unaCompra = (Compra) clienteEncontrado.getListaComprasCliente().obtenerElemento(i);
                if (unaCompra.getEventoComprado().equals(eventoEncontrado) && !unaCompra.isFueDevuelta()) {
                    unaCompra.setFueDevuelta(true);
                    break;
                }
            }

            //ELIMINO LA ENTRADA
            clienteEncontrado.getListaEntradasCliente().eliminarElemento(eventoEncontrado);
            eventoEncontrado.sumarEntrada();

            if (!eventoEncontrado.getListaEspera().esVacia()) {
                Cliente clienteEnEspera = (Cliente) eventoEncontrado.getListaEspera().obtenerElemento(0);
                clienteEnEspera.addListaEntradasCliente(eventoEncontrado);
                eventoEncontrado.getListaEspera().eliminarElemento(clienteEnEspera);
                eventoEncontrado.restarEntrada();
            }
            r.resultado = Retorno.Resultado.OK;
        }

        return r;
    }

    /*
    error_1:Cliente no existe
    error_2:Evento no existe
    error_3:Puntaje < 1 o puntaje > 10
    error_4:El evento ya fue calificado por el cliente
     */
    @Override
    public Retorno calificarEvento(String cedula, String codigoEvento, int puntaje, String comentario) {
        Retorno r = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);

        if (!listaClientes.esVacia() && !listaEventos.esVacia()) {
            Cliente auxCliente = null;
            boolean clienteEncontrado = false;
            for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
                Cliente clienteActual = (Cliente) listaClientes.obtenerElemento(i);
                if (clienteActual.getCI().equalsIgnoreCase(cedula)) {
                    auxCliente = clienteActual;
                    clienteEncontrado = true;
                    break;
                }
            }
            if (clienteEncontrado == false) {
                r.resultado = Retorno.Resultado.ERROR_1;
                return r;
            }

            Evento auxEvento = null;
            boolean eventoEncontrado = false;
            //evento
            for (int j = 0; j < listaEventos.cantidadElementos(); j++) {
                Evento eventoActual = (Evento) listaEventos.obtenerElemento(j);
                if (eventoActual.getCodigo().equalsIgnoreCase(codigoEvento)) {
                    auxEvento = eventoActual;
                    eventoEncontrado = true;
                    break;
                }
            }
            if (eventoEncontrado == false) {
                r.resultado = Retorno.Resultado.ERROR_2;
                return r;
            }

            if (puntaje < 0 || puntaje > 10) {
                r.resultado = Retorno.Resultado.ERROR_3;
                return r;
            }

            for (int g = 0; g < auxEvento.getListaCalificaciones().cantidadElementos(); g++) {
                Calificacion calActual = (Calificacion) auxEvento.getListaCalificaciones().obtenerElemento(g);
                if (calActual.getUnCliente().getCI().equalsIgnoreCase(auxCliente.getCI())) {
                    r.resultado = Retorno.Resultado.ERROR_4;
                    return r;
                }
            }
            Calificacion nCalificacion = new Calificacion(puntaje, comentario, auxCliente, auxEvento);
            auxEvento.getListaCalificaciones().agregarFinal(nCalificacion);
            r.resultado = Retorno.Resultado.OK;
        }
        return r;
    }

    public Retorno listarSalas() {
        String listadoSalas = "";
        Retorno r = new Retorno(Retorno.Resultado.OK);

        for (int i = 0; i < listaSalas.cantidadElementos(); i++) {
            Sala auxSala = (Sala) listaSalas.obtenerElemento(i);
            if (i != listaSalas.cantidadElementos() - 1) {
                listadoSalas += auxSala.toString() + "#";
            } else {
                listadoSalas += auxSala.toString();
            }
        }
        r.valorString = listadoSalas;
        return r;
    }

    public Retorno listarEventos() {
        Retorno r = new Retorno(Retorno.Resultado.OK);

        //CONTA R TOTAL EVENTOS
        int totalEventos = 0;
        for (int i = 0; i < listaSalas.cantidadElementos(); i++) {
            Sala sala = (Sala) listaSalas.obtenerElemento(i);
            totalEventos += sala.getListaEventos().cantidadElementos();
        }

        //CREAR ARRAY PARA EVENTOS
        Evento[] eventosArray = new Evento[totalEventos];
        int index = 0;

        //LLENAR ARRAY CON EVENTOS
        for (int i = 0; i < listaSalas.cantidadElementos(); i++) {
            Sala sala = (Sala) listaSalas.obtenerElemento(i);
            for (int j = 0; j < sala.getListaEventos().cantidadElementos(); j++) {
                eventosArray[index] = (Evento) sala.getListaEventos().obtenerElemento(j);
                index++;
            }
        }

        //ORDENAR CON ARREGLO BURBUJA
        for (int i = 0; i < eventosArray.length - 1; i++) {
            for (int j = 0; j < eventosArray.length - i - 1; j++) {
                if (eventosArray[j].getCodigo().compareToIgnoreCase(eventosArray[j + 1].getCodigo()) > 0) {
                    // Intercambiar referencias
                    Evento temp = eventosArray[j];
                    eventosArray[j] = eventosArray[j + 1];
                    eventosArray[j + 1] = temp;
                }
            }
        }

        //CONCATENAR LOS RESULTADOS
        String listadoEventos = "";
        for (int i = 0; i < eventosArray.length; i++) {
            Evento e = eventosArray[i];
            listadoEventos = listadoEventos + e.getCodigo() + "-" + e.getDescripcion() + "-" + e.getSalaAsignada().getNombre();
            if (i != eventosArray.length - 1) {
                listadoEventos = listadoEventos + "#";
            }
        }

        r.valorString = listadoEventos;
        return r;
    }

    public Retorno listarClientes() {
        String listadoClientes = "";
        Retorno r = new Retorno(Retorno.Resultado.OK);
        for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
            Cliente auxCliente = (Cliente) listaClientes.obtenerElemento(i);
            if (i != listaClientes.cantidadElementos() - 1) {
                listadoClientes += auxCliente.toString() + "#";
            } else {
                listadoClientes += auxCliente.toString();
            }
        }
        r.valorString = listadoClientes;
        return r;
    }

    public Retorno esSalaOptima(String[][] vistaSala) {
        Retorno r = new Retorno(Retorno.Resultado.OK);

        if (vistaSala == null || vistaSala.length == 0 || vistaSala[0].length == 0) {
            r.valorString = "No es optima";
        }

        int filas = vistaSala.length;
        int columnas = vistaSala[0].length;
        int columnasOptimas = 0;

        for (int i = 0; i < columnas; i++) {
            int libres = 0;
            int maxOcupadosConsecutivos = 0;
            int ocupadosActuales = 0;
            //j fila, i columna
            for (int j = 0; j < filas; j++) {
                String celda = vistaSala[j][i];
                if (celda.equalsIgnoreCase("X")) {
                    libres++;
                    ocupadosActuales = 0;
                } else if (celda.equalsIgnoreCase("O")) {
                    ocupadosActuales++;
                    if (ocupadosActuales > maxOcupadosConsecutivos) {
                        maxOcupadosConsecutivos = ocupadosActuales;
                    } else {
                        ocupadosActuales = 0;
                    }
                }
                if (maxOcupadosConsecutivos > libres) {
                    columnasOptimas++;
                }
            }
            if (columnasOptimas >= 2) {
                r.valorString = "es optima";
            } else {
                r.valorString = "no es optima";
            }
        }
        return r;
    }

    /*
    error_1:Evento no existe
    error_2:n < 1
     */
    public Retorno listarClientesDeEvento(String código, int n) {
        if (n < 1) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        }

        //BUSCAR EVENTO PARA LISTAR SUS CLIENTES
        Evento eventoBuscado = null;
        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento e = (Evento) listaEventos.obtenerElemento(i);
            if (e.getCodigo().equalsIgnoreCase(código)) {
                eventoBuscado = e;
                break;
            }
        }

        if (eventoBuscado == null) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }

        String listadoClientes = "";
        int cantidadAgregados = 0;
        boolean esPrimero = true;

        for (int i = 0; i < listaClientes.cantidadElementos() && cantidadAgregados < n; i++) {
            Cliente auxCliente = (Cliente) listaClientes.obtenerElemento(i);
            for (int j = 0; j < auxCliente.getListaEntradasCliente().cantidadElementos(); j++) {
                Evento auxEvento = (Evento) auxCliente.getListaEntradasCliente().obtenerElemento(j);
                if (auxEvento.getCodigo().equalsIgnoreCase(código)) {
                    if (!esPrimero) {
                        listadoClientes += "#";
                    }
                    listadoClientes += auxCliente.toString();
                    cantidadAgregados++;
                    esPrimero = false;
                    break;
                }
            }
        }

        Retorno r = new Retorno(Retorno.Resultado.OK);
        r.valorString = listadoClientes;
        return r;
    }

    public Retorno listarEsperaEvento() {
        /*
        Muestra los clientes en lista de espera para cada evento. 
        Los eventos deben estar ordenados en forma alfabética por código de evento 
        y los clientes, ordenados por cédula dentro del mismo evento. 
        Solo se deben listar aquellos eventos con clientes en espera.
         */
        String listaEsperaEvt = "";
        boolean esPrimero = true;

        if (!listaEventos.esVacia()) {
            for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
                Evento eventoActual = (Evento) listaEventos.obtenerElemento(i);
                if (!eventoActual.getListaEspera().esVacia()) {
                    for (int j = 0; j < listaClientes.cantidadElementos(); j++) {
                        Cliente cliente = (Cliente) listaClientes.obtenerElemento(j);
                        for (int k = 0; k < eventoActual.getListaEspera().cantidadElementos(); k++) {
                            Cliente clienteEnEspera = (Cliente) eventoActual.getListaEspera().obtenerElemento(k);
                            if (cliente.getCI().equalsIgnoreCase(clienteEnEspera.getCI())) {
                                if (!esPrimero) {
                                    listaEsperaEvt += "#";
                                }
                                listaEsperaEvt += eventoActual.getCodigo() + "-" + cliente.getCI();
                                esPrimero = false;
                            }
                        }
                    }
                }
            }
        }

        Retorno r = new Retorno(Retorno.Resultado.OK);
        r.valorString = listaEsperaEvt;
        return r;
    }

    public Retorno deshacerUtimasCompras(int n) {
        /*
        Se deben deshacer las últimas n compras de entradas realizadas(tomando en cuenta todos eventos), 
        devolviendo las mismas a sus respectivos eventos. 
        Se deben mostrar las entradas, detallando: 
        código del evento, cédula de identidad del cliente
        (ordenado por código de evento/cédula de cliente)
         */
        Retorno r = new Retorno(Retorno.Resultado.OK);
        ListaSimpleNodos<String> entradasDevueltas = new ListaSimpleNodos<>();
        int cantidadDeshecha = 0;

        for (int i = 0; i < listaEventos.cantidadElementos() && cantidadDeshecha < n; i++) {
            Evento eventoActual = (Evento) listaEventos.obtenerElemento(i);

            for (int j = 0; j < listaClientes.cantidadElementos() && cantidadDeshecha < n; j++) {
                Cliente clienteActual = (Cliente) listaClientes.obtenerElemento(j);

                if (clienteActual.getListaEntradasCliente().existeElemento(eventoActual)) {
                    clienteActual.getListaEntradasCliente().eliminarElemento(eventoActual);
                    eventoActual.sumarEntrada();
                    String entradaDevuelta = eventoActual.getCodigo() + "-" + clienteActual.getCI();
                    entradasDevueltas.agregarFinal(entradaDevuelta);
                    cantidadDeshecha++;
                }
            }
        }

        //CONCATENAR EL STRING
        String resultadoFinal = "";
        for (int i = 0; i < entradasDevueltas.cantidadElementos(); i++) {
            if (i > 0) {
                resultadoFinal += "#";
            }
            resultadoFinal += entradasDevueltas.obtenerElemento(i);
        }

        r.valorString = resultadoFinal;
        return r;
    }

    public Retorno eventoMejorPuntuado() {
        /*
        indica cual fue el evento que obtuvo el mejor puntaje promedio. 
        En caso de que existan más de uno, mostrarlos todos, 
        ordenados por código de evento, 
        indicando el puntaje promedio que obtuvo.
         */
        Retorno r = new Retorno(Retorno.Resultado.OK);

        double maxPromedio = Integer.MIN_VALUE;
        double promedio = 0;

        //BUSCAR PROMEDIO MAX
        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento evento = (Evento) listaEventos.obtenerElemento(i);
            double suma = 0;

            for (int j = 0; j < evento.getListaCalificaciones().cantidadElementos(); j++) {
                Calificacion califActual = (Calificacion) evento.getListaCalificaciones().obtenerElemento(j);
                suma += califActual.getPuntaje();
            }

            if (evento.getListaCalificaciones().cantidadElementos() > 0) {
                promedio = suma / evento.getListaCalificaciones().cantidadElementos();
                if (promedio > maxPromedio) {
                    maxPromedio = promedio;
                }
            }
        }

        String resultado = "";
        for (int i = 0; i < listaEventos.cantidadElementos(); i++) {
            Evento evtActual = (Evento) listaEventos.obtenerElemento(i);
            double suma = 0;
            int cant = evtActual.getListaCalificaciones().cantidadElementos();

            for (int j = 0; j < cant; j++) {
                Calificacion c = (Calificacion) evtActual.getListaCalificaciones().obtenerElemento(j);
                suma += c.getPuntaje();
            }

            if (cant > 0) {
                promedio = suma / cant;
                if (promedio == maxPromedio) {
                    if (!resultado.equals("")) {
                        resultado += " ";
                    }

                    int entero = (int) promedio;
                    int decimal = (int) ((promedio - entero) * 10);
                    resultado += evtActual.getCodigo() + "-" + entero + "." + decimal;
                }
            }
        }

        r.valorString = resultado;
        return r;
    }

    //error_1: Cliente no existe
    public Retorno comprasDeCliente(String cedula) {
        /*
        Se deben mostrar las compras realizadas del cliente, 
        indicando para cada una si fue devuelta (D) o si no fue devuelta (N). 
        Las entradas deben mostrarse en el orden en el que fueron compradas 
        (primero debe mostrarse la primera que fue comprada), incluyendo el código del evento.
         */
        Retorno r = new Retorno(Retorno.Resultado.OK);
        String comprasCliente = "";
        if (!listaClientes.esVacia()) {
            boolean esPrimero = false;
            String esDevuelta = "";
            for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
                Cliente clienteActual = (Cliente) listaClientes.obtenerElemento(i);
                if (!clienteActual.getListaComprasCliente().esVacia()) {
                    for (int j = 0; j < clienteActual.getListaComprasCliente().cantidadElementos(); j++) {
                        Compra compraCliente = (Compra) clienteActual.getListaComprasCliente().obtenerElemento(j);
                        if (compraCliente.isFueDevuelta()) {
                            if (!esPrimero) {
                                comprasCliente += "#";
                            }
                            comprasCliente += compraCliente.toString() + "D";
                        } else {
                            comprasCliente += compraCliente.toString() + "N";
                        }
                    }
                }
            }
        }
        r.valorString = comprasCliente;
        return r;
    }

    public Retorno comprasXDia(int mes) {
        /*
        Se debe mostrar la cantidad de compras que se realizaron para cada uno de los días del mes, 
        tomando en cuenta todos los eventos y clientes. 
        Se debe mostrar: el número de día (ordenado en forma ascendente) y la cantidad. 
        En caso de no haber compras en un determinado día, no debería aparecer.
        Formato:1-10#2-6#4-67#7-5#28-6
         */
        Retorno r = new Retorno(Retorno.Resultado.OK);
        String mostrarCompras = "";
        //CONTROL DE LAS FECHAS
        int[] comprasPorDia = new int[32];

        for (int i = 0; i < listaClientes.cantidadElementos(); i++) {
            Cliente cliente = (Cliente) listaClientes.obtenerElemento(i);

            for (int j = 0; j < cliente.getListaComprasCliente().cantidadElementos(); j++) {
                Compra compra = (Compra) cliente.getListaComprasCliente().obtenerElemento(j);

                if (!compra.isFueDevuelta()) {
                    LocalDate fechaEvento = compra.getEventoComprado().getFecha();

                    if (fechaEvento.getMonthValue() == mes) {
                        int dia = fechaEvento.getDayOfMonth();
                        comprasPorDia[dia]++;
                    }
                }
            }
        }

        //ARMO EL STRING
        for (int dia = 1; dia <= 31; dia++) {
            if (comprasPorDia[dia] > 0) {
                if (!mostrarCompras.equals("")) {
                    mostrarCompras += "#";
                }
                mostrarCompras += dia + "-" + comprasPorDia[dia];
            }
        }

        r.valorString = mostrarCompras;
        return r;
    }

    @Override
    public void agregarInicio(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int cantidadElementos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean esVacia() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existeElemento(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object obtenerElemento(int indice) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarFinal(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarInicio() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarFinal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarOrdenado(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarElemento(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarPorIndice(T x) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
