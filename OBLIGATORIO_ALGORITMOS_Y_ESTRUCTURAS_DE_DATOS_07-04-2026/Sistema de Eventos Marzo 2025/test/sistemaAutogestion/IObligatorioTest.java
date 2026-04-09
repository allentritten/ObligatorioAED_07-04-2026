package sistemaAutogestion;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pesce
 */
public class IObligatorioTest {

    private Sistema miSistema;

    public IObligatorioTest() {
    }

    @Before
    public void setUp() {
        miSistema = new Sistema();
        miSistema.crearSistemaDeGestion();
    }

    @Test
    public void testCrearSistemaDeGestionOK() {
        Retorno r = miSistema.crearSistemaDeGestion();
        assertEquals(Retorno.ok().resultado, r.resultado);//"Retorno.ok().resultado" tomamos el valor de .resultado y esperamos que sea Retorno.ok, "r.resultado" pasamos el resultado y si el retorno trae un OK entonces estamos bien
    }

    @Test
    public void testRegistrarSalaOK() {
        Retorno r = miSistema.registrarSala("Sala1", 100);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 20);
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testRegistrarSalaERROR_1() {
        Retorno r = miSistema.registrarSala("Sala1", 10);
        r = miSistema.registrarSala("Sala1", 20);
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testRegistrarSalaERROR_2() {
        Retorno r = miSistema.registrarSala("Sala4", 0);
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
        r = miSistema.registrarSala("Sala5", -1);
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testEliminarSalaOK() {
        //REGISTRO DE SALAS
        Retorno r = miSistema.registrarSala("Sala1", 10);
        r = miSistema.registrarSala("Sala2", 20);
        r = miSistema.registrarSala("Sala3", 30);
        r = miSistema.registrarSala("Sala4", 40);

        //ELIMINAR SALAS
        r = miSistema.eliminarSala("Sala1");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testEliminarSalaERROR_1() {
        //ELIMINAR SALAS
        Retorno r = miSistema.eliminarSala("Sala1");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
        r = miSistema.eliminarSala("");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testRegistrarEventoOK() {
        //AGREGAR SALAS
        Retorno r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 20);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 30);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala4", 40);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //AGREGAR EVENTOS
        r = miSistema.registrarEvento("AAA-AA7", "una desc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testRegistrarEventoERROR_1() {
        //AGREGAR SALAS
        Retorno r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 20);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 30);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala4", 40);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //AGREGAR EVENTOS
        r = miSistema.registrarEvento("AAA-AA7", "una desc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("AAA-77B", "una desc", 20, LocalDate.of(2025, Month.MARCH, 02));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("AAA-AA7", "otra desc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testRegistrarEventoERROR_2() {
        //AGREGAR SALAS
        Retorno r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 20);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 30);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala4", 40);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //AGREGAR EVENTOS
        r = miSistema.registrarEvento("AAA-AA7", "una desc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("AAA-77B", "una desc", 20, LocalDate.of(2025, Month.MARCH, 02));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("ABA-A87", "otra desc", 0, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
        r = miSistema.registrarEvento("R2D2-C3PO", "may the 4th be with you", -10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testRegistrarEventoERROR_3() {
        // REGISTRAR SALAS
        Retorno r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 20);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 30);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala4", 40);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        // REGISTRAR EVENTOS OK
        r = miSistema.registrarEvento("AAA-AA7", "una desc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("AAA-77B", "una desc", 20, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("ABA-A87", "otra desc", 10, LocalDate.of(2025, Month.MARCH, 04));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        // ERROR: Evento con más aforo necesario que cualquier sala
        r = miSistema.registrarEvento("EEE-EEE", "big event", 100, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.ERROR_3, r.resultado);

        // OCUPAMOS TODAS LAS SALAS para el 2 de marzo
        r = miSistema.registrarEvento("E1", "Evento Sala1", 10, LocalDate.of(2025, Month.MARCH, 02)); // Sala1
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("E2", "Evento Sala2", 20, LocalDate.of(2025, Month.MARCH, 02)); // Sala2
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("E3", "Evento Sala3", 30, LocalDate.of(2025, Month.MARCH, 02)); // Sala3
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("E4", "Evento Sala4", 40, LocalDate.of(2025, Month.MARCH, 02)); // Sala4
        assertEquals(Retorno.Resultado.OK, r.resultado);

        // ERROR: Ya no hay salas libres para el 2 de marzo
        r = miSistema.registrarEvento("E5", "Evento sin sala libre", 10, LocalDate.of(2025, Month.MARCH, 02));
        assertEquals(Retorno.Resultado.ERROR_3, r.resultado);
    }

    @Test
    public void testRegistrarClienteOK() {
        Retorno r = miSistema.registrarCliente("77777773", "Rock Howard");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888883", "Terry Bogard");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testRegistrarClienteERROR_1() {
        Retorno r = miSistema.registrarCliente("77777773", "Rock Howard");
        r = miSistema.registrarCliente("88888883", "Terry Bogard");
        r = miSistema.registrarCliente("123456789", "Geese Howard");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
        r = miSistema.registrarCliente("1234567", "Andy Bogard");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
        r = miSistema.registrarCliente("", "Luke Skywalker");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testRegistrarClienteERROR_2() {
        Retorno r = miSistema.registrarCliente("77777773", "Rock Howard");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888883", "Terry Bogard");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("12345678", "Geese Howard");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("77777773", "Luke Skywalker");
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testComprarEntradaOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("33333333", "CCC");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testComprarEntradaERROR_1() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("99999999", "CCC");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testComprarEntradaERROR_2() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("33333333", "DDD");
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testEliminarEventoOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //ELIMINAR EVENTO
        r = miSistema.eliminarEvento("AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testEliminarEventoERROR_1() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //ELIMINAR EVENTO
        r = miSistema.eliminarEvento("DDD");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testEliminarEventoERROR_2() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADA
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //ELIMINAR EVENTO
        r = miSistema.eliminarEvento("AAA");
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testDevolverEntradaOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADA
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //DEVOLVER ENTRADA
        r = miSistema.devolverEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testDevolverEntradaERROR_1() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 1, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADA
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //DEVOLVER ENTRADA
        r = miSistema.devolverEntrada("00000000", "AAA");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testDevolverEntradaERROR_2() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 1, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADA
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //DEVOLVER ENTRADA
        r = miSistema.devolverEntrada("77777777", "DDD");
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testCalificarEventoOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTO
        r = miSistema.calificarEvento("77777777", "AAA", 5, "muy buen evento!");
        assertEquals(Retorno.Resultado.OK, r.resultado);
    }

    @Test
    public void testCalificarEventoERROR_1() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTO
        r = miSistema.calificarEvento("00000000", "AAA", 5, "muy buen evento!");
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void testCalificarEventoERROR_2() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTO
        r = miSistema.calificarEvento("77777777", "DDD", 5, "muy buen evento!");
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void testCalificarEventoERROR_3() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTO
        r = miSistema.calificarEvento("77777777", "AAA", 15, "muy buen evento!");
        assertEquals(Retorno.Resultado.ERROR_3, r.resultado);
        r = miSistema.calificarEvento("33333333", "AAA", -15, "muy mal evento!");
        assertEquals(Retorno.Resultado.ERROR_3, r.resultado);
    }

    @Test
    public void testCalificarEventoERROR_4() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTO
        r = miSistema.calificarEvento("77777777", "AAA", 5, "muy buen evento!");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.calificarEvento("77777777", "AAA", 7, "muy buen evento!");
        assertEquals(Retorno.Resultado.ERROR_4, r.resultado);
    }

    @Test
    public void testListarSalas() {
        Retorno r = miSistema.registrarSala("Sala1", 100);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 120);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 100);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        //nombre-capacidad#
        r = miSistema.listarSalas();
        assertEquals("Sala3-100#Sala2-120#Sala1-100", r.valorString);
    }

    @Test
    public void testListarEventos() {

        // REGISTRO DE SALAS
        Retorno r = miSistema.registrarSala("Sala1", 300);  // Cambié capacidad a 300
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala2", 210);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarSala("Sala3", 300);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        // REGISTRO DE EVENTOS
        r = miSistema.registrarEvento("A1", "una desc", 10, LocalDate.of(2025, Month.MARCH, 1));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("B2", "una desc", 110, LocalDate.of(2025, Month.MARCH, 2));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("C3", "una desc", 200, LocalDate.of(2025, Month.MARCH, 3));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        // La cadena esperada con las salas asignadas correctamente
        r = miSistema.listarEventos();
        assertEquals("A1-una desc-Sala2#B2-una desc-Sala2#C3-una desc-Sala1", r.valorString);
    }

    @Test
    public void testListarClientes() {
        Retorno r = miSistema.registrarCliente("77777773", "LukeSkywalker");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("99999993", "HanSolo");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888883", "AnakinSkywalker");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //cedula-nombre#
        r = miSistema.listarClientes();
        assertEquals("77777773-LukeSkywalker#88888883-AnakinSkywalker#99999993-HanSolo", r.valorString);
    }

    @Test
    public void listarClientesDeEventoOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //35679992-Ramiro Perez#45678992-Micaela Ferrez
        r = miSistema.listarClientesDeEvento("AAA", 2);
        assertEquals("33333333-Cammy#77777777-Ryu", r.valorString);
    }

    @Test
    public void listarClientesDeEventoERROR_1() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //35679992-Ramiro Perez#45678992-Micaela Ferrez
        r = miSistema.listarClientesDeEvento("DDD", 2);
        assertEquals(Retorno.Resultado.ERROR_1, r.resultado);
    }

    @Test
    public void listarClientesDeEventoERROR_2() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //35679992-Ramiro Perez#45678992-Micaela Ferrez
        r = miSistema.listarClientesDeEvento("AAA", 0);
        assertEquals(Retorno.Resultado.ERROR_2, r.resultado);
    }

    @Test
    public void listarEsperaEventoOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 1, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //KAK34-2333111# KAK34-45678992# TEC43-35679992
        r = miSistema.listarEsperaEvento();
        assertEquals("AAA-77777777#AAA-88888888", r.valorString);
    }

    @Test
    public void testDeshacerUtimasComprasOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //DESHACER COMPRAS
        //KAK34-2333111# KAK34-45678992
        r = miSistema.deshacerUtimasCompras(2);
        assertEquals("AAA-33333333#AAA-77777777", r.valorString);
    }

    @Test
    public void testEventoMejorPuntuado() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //PUNTUAR EVENTOS
        r = miSistema.calificarEvento("33333333", "AAA", 7, "increible");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.calificarEvento("77777777", "AAA", 6, "increible");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.calificarEvento("88888888", "BBB", 6, "increible");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //KAK34-9#TEC43-9
        r = miSistema.eventoMejorPuntuado();
        assertEquals("AAA-6.5", r.valorString);
    }

    @Test
    public void testComprasDeClienteOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("33333333", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("33333333", "CCC");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //DEVOLVER ENTRADAS
        r = miSistema.devolverEntrada("33333333", "CCC");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.devolverEntrada("33333333", "BBB");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //r = AAA-N#BBB-D#CCC-D
        r = miSistema.comprasDeCliente("33333333");
        assertEquals("AAA-N#BBB-D#CCC-D", r.valorString);
    }

    @Test
    public void testComprasXDiaOK() {
        //REGISTRAR CLIENTES
        Retorno r = miSistema.registrarCliente("77777777", "Ryu");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("88888888", "Ken");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarCliente("33333333", "Cammy");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR SALAS PARA EVENTOS
        r = miSistema.registrarSala("Sala1", 10);
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //REGISTRAR EVENTOS
        r = miSistema.registrarEvento("AAA", "unoDesc", 10, LocalDate.of(2025, Month.MARCH, 07));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("BBB", "dosDesc", 10, LocalDate.of(2025, Month.MARCH, 01));
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.registrarEvento("CCC", "tresDesc", 10, LocalDate.of(2025, Month.MARCH, 03));
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //COMPRAR ENTRADAS
        r = miSistema.comprarEntrada("33333333", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("77777777", "AAA");
        assertEquals(Retorno.Resultado.OK, r.resultado);
        r = miSistema.comprarEntrada("88888888", "CCC");
        assertEquals(Retorno.Resultado.OK, r.resultado);

        //1-10#2-6#4-67#7-5#28-6
        r = miSistema.comprasXDia(3);
        assertEquals("3-1#7-2", r.valorString);
    }

    @Test
    public void testEsSalaOptima() {
        String[][] vistaSala = {
            {"#", "#", "#", "#", "#"},
            {"#", "X", "X", "X", "#"},
            {"#", "O", "O", "O", "#"},
            {"#", "O", "O", "O", "#"},
            {"#", "O", "O", "O", "#"},
            {"#", "O", "O", "O", "#"},
            {"#", "X", "X", "X", "#"},
            {"#", "X", "X", "X", "#"},
            {"#", "#", "#", "#", "#"}
        };

        Retorno r = miSistema.esSalaOptima(vistaSala);
        assertEquals(Retorno.Resultado.OK, r.resultado);
        assertEquals("es optima", r.valorString);
    }

}
