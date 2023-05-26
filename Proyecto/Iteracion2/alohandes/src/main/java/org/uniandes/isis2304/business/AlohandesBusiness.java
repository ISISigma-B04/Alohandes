package org.uniandes.isis2304.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uniandes.isis2304.persistence.AlohandesPersistence;
import org.uniandes.isis2304.utils.OrderedMap;
import org.uniandes.isis2304.utils.Tabulable;
import org.uniandes.isis2304.utils.TextTable;
import org.uniandes.isis2304.view.AlohandesInterface;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AlohandesBusiness {
    private final static Logger LOG = LogManager.getLogger(AlohandesBusiness.class);
    private final AlohandesInterface parent;
    private final AlohandesPersistence ap;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public AlohandesBusiness(AlohandesInterface parent,
                             AlohandesPersistence ap) {
        super();
        this.parent = parent;
        this.ap = ap;
    }

    public String createOperator(OrderedMap<String, String> operator,
                                 OrderedMap<String, String> operatorSpec,
                                 OrderedMap<String, String> operatorService,
                                 OrderedMap<String, String> inheritor)
            throws Exception {
        String type = operator.get("Que tipo de operador es?");
        LOG.info("START ⟼ Creando un operador {} [Tipo::{}]", operator.get("Nombre del operador"), type);
        Object[][][] response = ap.createOperator(operator, operatorSpec, operatorService, inheritor, type);
        LOG.info("END ⟼ Operador {} creado [Tipo::{}]", operator.get("Nombre del operador"), type + "::\n");
        String[] keys = new String[]{"Operator::\n", "OperatorSpec::\n", "OperatorService::\n"};
        StringBuilder sb = new StringBuilder(128);
        IntStream.range(0, keys.length)
                 .forEach(i -> sb.append(keys[i]).append(TextTable.builder().append(response[i]).toString()));

        switch (type) {
            case "Habitacion de casa" -> {
                TextTable builder = TextTable.builder().append(new String[]{"name", "serviceName", "serviceCost"});
                parent.createServices().stream()
                      .map(val -> ap.createService(operator.get("Nombre del operador"), val)[1])
                      .forEach(builder::append);
                sb.append("Service::\n").append(builder);
            }
            case "Habitacion de Hotel" ->
                    sb.append("RoomsHotel::\n").append(TextTable.builder().append(response[4]).toString());
        }
        return sb.toString();
    }

    public String createClient(OrderedMap<String, String> client,
                               OrderedMap<String, String> clientPreference) {
        LOG.info("START ⟼ Creando un cliente {}", client.get("Numero Unico de Identificacion Personal"));
        Object[][][] apClient = ap.createClient(client, clientPreference);
        LOG.info("END ⟼ Cliente {} creado", client.get("Numero Unico de Identificacion Personal"));
        return "Client::\n" + TextTable.builder().append(apClient[0]).toString() +
               "ClientPreference::\n" + TextTable.builder().append(apClient[1]).toString();
    }

    public String createBooking(Object[] booking) {
        LOG.info("START ⟼ Creando una reserva {}-{}", booking);
        try {
            Object[][] response = ap.createBooking(booking, (Date) sdf.parse((String) booking[3]), (Date) sdf.parse(
                    (String) booking[4]));
            LOG.info("END ⟼ Reserva creada");
            return TextTable.builder().append(response).toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String createOffer(Object[] offer) {
        LOG.info("START ⟼ Creando una oferta {}", offer[0]);
        Object[][] response = ap.createOffer(offer);
        LOG.info("END ⟼ Oferta creada");
        return TextTable.builder().append(response).toString();
    }

    public String createHotel(Object[] hotel) {
        LOG.info("START ⟼ Creando un hotel {}", hotel[0]);
        Object[][] response = ap.createHotel(hotel);
        LOG.info("END ⟼ Hotel {} creado", hotel[0]);
        return TextTable.builder().append(response).toString();
    }

    public <R extends VO, VO extends Tabulable> String retrieveAll(Class<R> arg)
            throws ReflectiveOperationException {
        LOG.info("START ⟼ Recuperando todos los {}", arg.getSimpleName());
        List<Object[]> vos = ap.retrieveAll(arg);
        LOG.info("END ⟼ {} de {} recuperados", vos.size(), arg.getSimpleName());

        TextTable builder = TextTable.builder();
        vos.forEach(builder::append);

        return arg.getSimpleName() + "::\n" + builder;
    }

    public <R extends VO, VO extends Tabulable> String retrieveOne(Class<R> arg, Object... pks)
            throws ReflectiveOperationException {
        LOG.info("START ⟼ Recuperando {} con id(s) {}", arg.getSimpleName(), pks);
        Object[][] vo = ap.retrieveOne(arg, pks);
        LOG.info("END ⟼ {} con id(s) {} recuperado [{}]", arg, Arrays.toString(pks), vo);
        return TextTable.builder().append(vo).toString();
    }

    public <R> String delete(Class<R> arg, Object... input)
            throws ReflectiveOperationException {
        LOG.info("START ⟼ Borrando {} con id(s) {}", arg.getSimpleName(), input);
        long l = switch (arg.getSimpleName()) {
            case "RoomsHotel", "ServiceScheme" -> ap.delete(arg, input[0], input[1]);
            case "Offer" -> {
                //TODO Cobrar por fecha de cancelacion
                System.out.println("TODO");
                yield ap.delete(arg, input[0]);
            }
            default -> ap.delete(arg, input[0]);
        };
        LOG.info("END ⟼ {} {} borrado(s)", l, arg);
        return "Borrado " + l;
    }


    public String moneyByYear() {
        LOG.info("START ⟼ Consulta01");
        List<Object[]> list = ap.reqC01();
        LOG.info("END ⟼ Consulta01");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String popularOffers() {
        LOG.info("START ⟼ Consulta02");
        List<Object[]> list = ap.reqC02();
        LOG.info("END ⟼ Consulta02");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String occupationIndex() {
        LOG.info("START ⟼ Consulta03");
        List<Object[]> list = ap.reqC03();
        LOG.info("END ⟼ Consulta03");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String availableInRangeAndServices(Object[] input) {
        LOG.info("START ⟼ Consulta04");
        try {
            Date[] r = new Date[]{
                    (Date) sdf.parse((String) input[0]),
                    (Date) sdf.parse((String) input[1])
            };
            String servicesGet = (String) input[2];
            boolean[] services = new boolean[]{
                    servicesGet.contains("cocineta"),
                    servicesGet.contains("wifi"),
                    servicesGet.contains("amueblado")
            };
            List<Object[]> list = ap.reqC04(r, services);
            LOG.info("END ⟼ Consulta04");
            TextTable builder = TextTable.builder();
            list.forEach(builder::append);
            return builder.toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String alohandesUse() {
        LOG.info("START ⟼ Consulta05");
        List<Object[]> list = ap.reqC05();
        LOG.info("END ⟼ Consulta05");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String alohandesUse(Object[] input) {
        LOG.info("START ⟼ Consulta06");
        List<Object[]> list = ap.reqC06(Long.parseLong((String) input[0]));
        LOG.info("END ⟼ Consulta06");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    //Iter3
    public String alohandesOperation(Object[] input) {
        LOG.info("START ⟼ Consulta07");
        List<Object[]> list = ap.reqC07((String) input[0], (String) input[1]);
        LOG.info("END ⟼ Consulta07");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String frequentClients(Object[] input) {
        LOG.info("START ⟼ Consulta08");
        List<Object[]> list = ap.reqC08((String) input[0]);
        LOG.info("END ⟼ Consulta08");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }

    public String lowDemand() {
        LOG.info("START ⟼ Consulta09");
        List<Object[]> list = ap.reqC09();
        LOG.info("END ⟼ Consulta09");
        TextTable builder = TextTable.builder();
        list.forEach(builder::append);
        return builder.toString();
    }
}