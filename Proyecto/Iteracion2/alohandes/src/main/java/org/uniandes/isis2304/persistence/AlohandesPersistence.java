package org.uniandes.isis2304.persistence;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jdo.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.uniandes.isis2304.business.*;
import org.uniandes.isis2304.utils.OrderedMap;
import org.uniandes.isis2304.utils.Tabulable;

public class AlohandesPersistence {
    public static final String SQL = Query.SQL;
    private final static Logger LOG = LogManager.getLogger(AlohandesPersistence.class);
    public static final String SI = "Si";
    private static AlohandesPersistence instance;
    private final PersistenceManagerFactory pmf;

    private final SQLHandler<ApartmentSpec> sqlApartmentSpec;
    private final SQLHandler<Booking> sqlBooking;
    private final SQLHandler<Client> sqlClient;
    private final SQLHandler<ClientPreference> sqlClientPreference;
    private final SQLHandler<HostelSpec> sqlHostelSpec;
    private final SQLHandler<Hotel> sqlHotel;
    private final SQLHandler<HotelRoomSpec> sqlHotelRoomSpec;
    private final SQLHandler<HouseRoomSpec> sqlHouseRoomSpec;
    private final SQLHandler<Offer> sqlOffer;
    private final SQLHandler<Operator> sqlOperator;
    private final SQLHandler<OperatorService> sqlOperatorService;
    private final SQLHandler<OperatorSpec> sqlOperatorSpec;
    private final SQLHandler<ResidenceSpec> sqlResidenceSpec;
    private final SQLHandler<RoomsHotel> sqlRoomsHotel;
    private final SQLHandler<ServiceScheme> sqlServiceScheme;
    private final SQLHandler<StudentResSpec> sqlStudentResSpec;

    private final SQLUtil sqlUtil;
    private final SQLReq sqlReq;

    {
        sqlApartmentSpec = new SQLHandler<>(ApartmentSpec.class);
        sqlBooking = new SQLHandler<>(Booking.class);
        sqlClient = new SQLHandler<>(Client.class);
        sqlClientPreference = new SQLHandler<>(ClientPreference.class);
        sqlHostelSpec = new SQLHandler<>(HostelSpec.class);
        sqlHotel = new SQLHandler<>(Hotel.class);
        sqlHotelRoomSpec = new SQLHandler<>(HotelRoomSpec.class);
        sqlHouseRoomSpec = new SQLHandler<>(HouseRoomSpec.class);
        sqlOffer = new SQLHandler<>(Offer.class);
        sqlOperator = new SQLHandler<>(Operator.class);
        sqlOperatorService = new SQLHandler<>(OperatorService.class);
        sqlOperatorSpec = new SQLHandler<>(OperatorSpec.class);
        sqlResidenceSpec = new SQLHandler<>(ResidenceSpec.class);
        sqlRoomsHotel = new SQLHandler<>(RoomsHotel.class);
        sqlServiceScheme = new SQLHandler<>(ServiceScheme.class);
        sqlStudentResSpec = new SQLHandler<>(StudentResSpec.class);

        sqlUtil = SQLUtil.getInstance();
        sqlReq = new SQLReq();
    }

    private AlohandesPersistence() {
        pmf = JDOHelper.getPersistenceManagerFactory("Alohandes");
    }

    private AlohandesPersistence(JsonObject tableConfig) {
        String persistenceUnit = tableConfig.get("unidadPersistencia").getAsString();
        LOG.trace("Accediendo a unidad de persistencia: {}", persistenceUnit);
        pmf = JDOHelper.getPersistenceManagerFactory(persistenceUnit);
    }

    public static AlohandesPersistence getInstance(JsonObject tableConfig) {
        if (instance == null) instance = new AlohandesPersistence(tableConfig);
        return instance;
    }

    /**
     * Creates a new operator with the given information.
     *
     * @param operator        the operator information
     * @param operatorSpec    the operator specification information
     * @param operatorService the operator service information
     * @param inheritor       the inheritor information
     * @param type            the type of operator
     * @return a string representation of the new operator and its details
     */
    public Object[][][] createOperator(OrderedMap<String, String> operator,
                                 OrderedMap<String, String> operatorSpec,
                                 OrderedMap<String, String> operatorService,
                                 OrderedMap<String, String> inheritor,
                                 String type) {
        PersistenceManager pm = pmf.getPersistenceManager();
        final String[] name = new String[1];
        return tx(pm,
                  () -> {
                      var keys00 = new Object() {//Operator values
                          final long nuip = Long.parseLong(operator.get("NUIP del operador"));
                          final String name00 = operator.get("Nombre del operador");
                          final String type = operator.get("Que tipo de operador es?");
                      };
                      LOG.trace("INSERT ⟼ Operator::{} ⟼ {} added", keys00.name00,
                                sqlOperator.create(pm, keys00.nuip, keys00.name00, keys00.type));
                      name[0] = keys00.name00;
                      return new Operator(keys00.nuip, name[0], keys00.type).toTable();
                  },
                  () -> {
                      var keys01 = new Object() {//Operator Spec values
                          final int capacity = Integer.parseInt(operatorSpec.get("Capacidad"));
                          final int size = Integer.parseInt(operatorSpec.get("Tamanio"));
                          final String location = operatorSpec.get("Localizacion");
                          final boolean shared = SI.equals(operatorSpec.get("Compartido?"));
                      };
                      LOG.trace("INSERT ⟼ OperatorSpec::{} ⟼ {} added", name[0],
                                sqlOperatorSpec.create(pm, name[0], keys01.capacity, keys01.size, keys01.location,
                                                       keys01.shared));
                      return new OperatorSpec(name[0], keys01.capacity, keys01.size, keys01.location,
                                              keys01.shared).toTable();
                  },
                  ()->{
                      var keys02 = new Object() {//Operator Service values
                          final boolean furnished = SI.equals(operatorService.get("Amueblado?"));
                          final boolean wifi = SI.equals(operatorService.get("Tiene wifi?"));
                          final boolean kitchenette = SI.equals(operatorService.get("Tiene cocineta?"));
                      };
                      LOG.trace("INSERT ⟼ OperatorService::{} ⟼ {} added", name[0],
                                sqlOperatorService.create(pm, name[0], keys02.furnished, keys02.wifi, keys02.kitchenette));
                      return new OperatorService(name[0], keys02.furnished, keys02.wifi,
                                                 keys02.kitchenette).toTable();
                  },
                  ()-> switch (type) {
                      case "Residencia estudiantil" -> {
                          var keys10 = new Object() {
                              final int restaurant = Integer.parseInt(inheritor.get("Coste del restaurante"));
                              final int studyRoom = Integer.parseInt(inheritor.get("Coste de la sala de estudio"));
                              final int recreationRoom = Integer.parseInt(inheritor.get("Coste por sala de ocio"));
                              final int gym = Integer.parseInt(inheritor.get("Coste por gimnasio"));
                          };
                          LOG.trace("INSERT ⟼ StudentResSpec::{} ⟼ {} added", name[0],
                                    sqlStudentResSpec.create(pm, name[0], keys10.restaurant, keys10.studyRoom,
                                                             keys10.recreationRoom, keys10.gym));

                          yield new StudentResSpec(name[0], keys10.restaurant, keys10.studyRoom, keys10.recreationRoom,
                                                   keys10.gym).toTable();
                      }
                      case "Residencia no-estudiantil" -> {
                          var keys11 = new Object() {
                              final int bedroomNum = Integer.parseInt(inheritor.get("Numero de habitaciones"));
                              final int administrationFee = Integer.parseInt(inheritor.get("Cuota administrativa"));
                              final String insuranceDesc = inheritor.get("Descripcion del seguro");
                          };
                          LOG.trace("INSERT ⟼ ResidenceSpec::{} ⟼ {} added", name[0],
                                    sqlResidenceSpec.create(pm, name[0], keys11.bedroomNum, keys11.administrationFee,
                                                            keys11.insuranceDesc));
                          yield new ResidenceSpec(name[0], keys11.bedroomNum, keys11.administrationFee,
                                                  keys11.insuranceDesc).toTable();
                      }
                      case "Hostal" -> {
                          var keys12 = new Object() {
                              final long nit = Long.parseLong(inheritor.get("NIT"));
                              final Timestamp openingHours = parse(inheritor.get("Horario de apertura"));
                              final Timestamp closingHours = parse(inheritor.get("Horario de cierre"));

                              private static Timestamp parse(String s) {
                                  try {
                                      return Timestamp.from(new SimpleDateFormat("HH:mm").parse(s).toInstant());
                                  } catch (ParseException e) {
                                      throw new RuntimeException(e);
                                  }
                              }
                          };
                          LOG.trace("INSERT ⟼ HostelSpec::{} ⟼ {} added", name[0],
                                    sqlHostelSpec.create(pm, name[0], keys12.nit, keys12.openingHours,
                                                         keys12.closingHours));
                          yield new HostelSpec(name[0], keys12.nit, keys12.openingHours, keys12.closingHours).toTable();
                      }
                      case "Habitacion de Hotel" -> {
                          var keys13 = new Object() {
                              final int roomNumber = Integer.parseInt(inheritor.get("Numero de habitacion"));
                              final String roomType = inheritor.get("Tipo de sala");
                              final boolean bathtub = SI.equals(inheritor.get("Tiene baniera?"));
                              final boolean jacuzzi = SI.equals(inheritor.get("Tiene yacuzzi?"));
                              final boolean livingRoom = SI.equals(inheritor.get("Tiene sala?"));
                          };
                          LOG.trace("INSERT ⟼ HotelRoomSpec::{} ⟼ {} added", name[0],
                                    sqlHotelRoomSpec.create(pm, name[0], keys13.roomNumber, keys13.roomType,
                                                            keys13.bathtub, keys13.jacuzzi, keys13.livingRoom));
                          yield new HotelRoomSpec(name[0], keys13.roomNumber, keys13.roomType, keys13.bathtub,
                                                  keys13.jacuzzi, keys13.livingRoom).toTable();
                      }
                      case "Habitacion de casa" -> {
                          var keys14 = new Object() {
                              final boolean food = SI.equals(inheritor.get("Tiene comidas incluidas?"));
                              final boolean privateBathroom = SI.equals(inheritor.get("Tiene banio privado?"));
                          };
                          LOG.trace("INSERT ⟼ HouseRoomSpec::{} ⟼ {} added", name[0],
                                    sqlHouseRoomSpec.create(pm, name[0], keys14.food, keys14.privateBathroom));
                          yield new HouseRoomSpec(name[0], keys14.food, keys14.privateBathroom).toTable();
                      }
                      case "Apartamento" -> {
                          var keys15 = new Object() {
                              final boolean includedServices = SI.equals(inheritor.get("Servicios incluidos?"));
                              final boolean includedTV = SI.equals(inheritor.get("Television incluida?"));
                              final int administrationFee = Integer.parseInt(inheritor.get("Cuota administrativa"));
                          };
                          LOG.trace("INSERT ⟼ ApartmentSpec::{} ⟼ {} added", name[0],
                                    sqlApartmentSpec.create(pm, name[0], keys15.includedServices, keys15.includedTV,
                                                            keys15.administrationFee));
                          yield new ApartmentSpec(name[0], keys15.includedServices, keys15.includedTV,
                                                  keys15.administrationFee).toTable();
                      }
                      default -> null;
                  },
                  () -> {
                      if (type.equals("Habitacion de Hotel")) {
                          var keys13 = new Object() {
                              final int hotelNIT = Integer.parseInt(inheritor.get("NIT del hotel"));
                              final int roomNumber = Integer.parseInt(inheritor.get("Numero de habitacion"));
                          };
                          LOG.trace("INSERT ⟼ RoomsHotel::{}-{} ⟼ {} added", name[0], keys13.roomNumber,
                                    sqlRoomsHotel.create(pm, keys13.hotelNIT, keys13.roomNumber));
                          return new RoomsHotel(keys13.hotelNIT, name[0]).toTable();
                      }
                      return null;
                  });
    }

    public Object[][] createBooking(Object[] booking,
                                Date start,
                                Date end) {
        PersistenceManager pm = pmf.getPersistenceManager();
        return tx(pm, () -> {
            var keys = new Object() {
                final long id = sqlUtil.nextBooking(pm);
                final long nuip = Long.parseLong((String) booking[0]);
                final String name = (String) booking[1];
                final long cost = Long.parseLong((String) booking[2]);
            };
            LOG.trace("INSERT ⟼ Booking::{}{} ⟼ {} added", booking[0], booking[1],
                      sqlBooking.create(pm, keys.nuip, keys.name, keys.cost, start, end));
            return new Booking(keys.id, keys.nuip, keys.name, keys.cost, start, end).toTable();
        })[0];
    }

    public Object[][] createOffer(Object[] offer) {
        PersistenceManager pm = pmf.getPersistenceManager();
        return tx(pm, ()-> {
            var keys = new Object() {
                final long id = Long.parseLong((String) offer[0]);
                final int costByNight = Integer.parseInt((String) offer[1]);
            };
            LOG.trace("INSERT ⟼ Offer::{} ⟼ {} added", keys.id, sqlOffer.create(pm, keys.id, keys.costByNight));
            return new Offer(keys.id, keys.costByNight).toTable();
        });
    }

    public Object[][] createService(String name,
                                String[] service) {
        PersistenceManager pm = pmf.getPersistenceManager();
        return tx(pm, () -> {
            var keys = new Object() {
                final String serviceName = service[0];
                final int serviceCost = Integer.parseInt(service[1]);
            };
            LOG.trace("INSERT ⟼ ServiceScheme::{},{} ⟼ {} added", name, service[0],
                      sqlServiceScheme.create(pm, name, keys.serviceName, keys.serviceCost));
            return new ServiceScheme(name, service[0], Integer.parseInt(service[1])).toTable();
        });
    }

    public Object[][][] createClient(OrderedMap<String, String> client,
                               OrderedMap<String, String> clientPreference) {
        PersistenceManager pm = pmf.getPersistenceManager();
        long[] nuip = new long[1];
        return tx(pm,
                  () -> {
                      var keys00 = new Object() {//Client values
                          final long nuip = Long.parseLong(client.get("Numero Unico de Identificacion Personal"));
                          final String name = client.get("Nombre del cliente");
                          final String clientType = client.get("Tipo de cliente?");
                      };
                      LOG.trace("INSERT ⟼ Client::{} ⟼ {} added", keys00.nuip,
                                sqlClient.create(pm, keys00.nuip, keys00.name, keys00.clientType));
                      nuip[0] = keys00.nuip;
                      return new Client(nuip[0], keys00.name, keys00.clientType).toTable();
                  },
                  () -> {

                      var keys01 = new Object() {//Client Preferences values
                          final boolean furnished = SI.equals(clientPreference.get("Amueblado?"));
                          final boolean shared = SI.equals(clientPreference.get("Compartido?"));
                          final boolean wifi = SI.equals(clientPreference.get("Que tenga wifi?"));
                          final boolean kitchenette = SI.equals(clientPreference.get("Que tenga cocineta?"));
                      };
                      LOG.trace("INSERT ⟼ ClientPreference::{} ⟼ {} added", nuip[0],
                                sqlClientPreference.create(pm, nuip[0], keys01.furnished, keys01.shared, keys01.wifi,
                                                           keys01.kitchenette));
                      return new ClientPreference(nuip[0], keys01.furnished, keys01.shared, keys01.wifi,
                                                  keys01.kitchenette).toTable();
                  });
    }

    public Object[][] createHotel(Object[] hotel) {
        PersistenceManager pm = pmf.getPersistenceManager();
        return tx(pm, () -> {
            var keys00 = new Object() {
                final int nit = Integer.parseInt((String) hotel[0]);
                final boolean restaurant = SI.equals(hotel[1]);
                final boolean pool = SI.equals(hotel[2]);
                final boolean parkingLot = SI.equals(hotel[3]);
                final boolean wifi = SI.equals(hotel[4]);
                final boolean cableTV = SI.equals(hotel[5]);
            };
            LOG.trace("INSERT ⟼ Hotel::{} ⟼ {} added", keys00.nit,
                      sqlHotel.create(pm, keys00.nit, keys00.restaurant, keys00.pool, keys00.parkingLot,
                                      keys00.wifi, keys00.cableTV));
            return new Hotel(keys00.nit, keys00.restaurant, keys00.pool, keys00.parkingLot,
                                              keys00.wifi, keys00.cableTV).toTable();
        });
    }

    /**
     * Generates an error detail message from the given exception.
     *
     * @param e the exception to generate the error detail message from.
     * @return a String containing the error detail message.
     */
    private String generateErrorDetail(Exception e) {
        return e instanceof JDODataStoreException je ? je.getNestedExceptions()[0].getMessage() : "";
    }

    /**
     * Retrieves a single object of type R using the provided primary key values.
     *
     * @param arg the class of the object to be retrieved
     * @param pks the primary key values to be used in the retrieval process
     * @param <R> the type of object to be retrieved
     * @return a single object of type R
     * @throws ReflectiveOperationException if an error occurs during reflection
     */
    @SuppressWarnings("unchecked")
    public <R extends VO, VO extends Tabulable> Object[][] retrieveOne(Class<R> arg, Object... pks)
            throws ReflectiveOperationException {
        assert pks.length > 1;
        String sqlClass = "sql" + arg.getSimpleName();
        Field field = AlohandesPersistence.class.getDeclaredField(sqlClass);
        field.setAccessible(true);
        SQLHandler<R> value = (SQLHandler<R>) field.get(this);
        return value.retrieveByPK(pmf.getPersistenceManager(), pks).toTable();
    }

    /**
     * Retrieves all objects of type R from the database using an SQL handler.
     *
     * @param arg the class of the objects to retrieve
     * @param <R> the type of object to be retrieved
     * @return a list of tables of R
     * @throws ReflectiveOperationException if there are problems with the reflection
     */
    @SuppressWarnings("unchecked")
    public <R extends VO, VO extends Tabulable> List<Object[]> retrieveAll(Class<R> arg)
            throws ReflectiveOperationException {
        String sqlClass = "sql" + arg.getSimpleName();
        Field field = AlohandesPersistence.class.getDeclaredField(sqlClass);
        field.setAccessible(true);
        SQLHandler<R> value = (SQLHandler<R>) field.get(this);

        List<R> retrieveAll = value.retrieveAll(pmf.getPersistenceManager());
        List<Object[]> rs = new ArrayList<>();

        rs.add(retrieveAll.get(0).toTable()[0]);
        retrieveAll.stream().map(r -> r.toTable()[1]).forEach(rs::add);
        return rs;
    }

    @SuppressWarnings("unchecked")
    public <R> long delete(Class<R> arg, Object... pks)
            throws ReflectiveOperationException {
        String sqlClass = "sql" + arg.getSimpleName();
        Field field = AlohandesPersistence.class.getDeclaredField(sqlClass);
        field.setAccessible(true);
        SQLHandler<R> value = (SQLHandler<R>) field.get(this);
        return value.deleteByPK(pmf.getPersistenceManager(), pks);
    }

    public List<Object[]> reqC01() {return sqlReq.rfc01(pmf.getPersistenceManager());}

    public List<Object[]> reqC02() {return sqlReq.rfc02(pmf.getPersistenceManager());}

    public List<Object[]> reqC03() {return sqlReq.rfc03(pmf.getPersistenceManager());}

    public List<Object[]> reqC04(Date[] r,
                                 boolean[] services) {
        return sqlReq.rfc04(pmf.getPersistenceManager(), r, services);
    }

    public List<Object[]> reqC05() {return sqlReq.rfc05(pmf.getPersistenceManager());}

    public List<Object[]> reqC06(long nuip) {return sqlReq.rfc06(pmf.getPersistenceManager(), nuip);}

    //Iter 3
    public List<Object[]> reqC07(String timeUnit,
                                 String operatorType) {
        return sqlReq.rfc07(pmf.getPersistenceManager(), timeUnit, operatorType);
    }

    public List<Object[]> reqC08(String operatorName) {
        return sqlReq.rfc08(pmf.getPersistenceManager(), operatorName);
    }

    public List<Object[]> reqC09() {return sqlReq.rfc09(pmf.getPersistenceManager());}

    @FunctionalInterface private interface TxManager { Object[][] call() throws Exception; }

    Object[][][] tx(PersistenceManager pm, TxManager... lambdas) {
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Object[][][] objs = new Object[lambdas.length][][];
            for (int i = 0; i < lambdas.length; i++) objs[i] = lambdas[i].call();
            tx.commit();
            return objs;
        } catch (Exception e) {
            LOG.error("{}: {}\n {}", e.getClass().getSimpleName(), e.getMessage(), generateErrorDetail(e));
            return null;
        } finally {
            if (tx.isActive()) tx.rollback();
        }
    }
}
