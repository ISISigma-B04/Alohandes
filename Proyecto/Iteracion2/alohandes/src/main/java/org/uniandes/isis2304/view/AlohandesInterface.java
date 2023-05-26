package org.uniandes.isis2304.view;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uniandes.isis2304.business.*;
import org.uniandes.isis2304.utils.OrderedMap;
import org.uniandes.isis2304.utils.TextTable;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AlohandesInterface extends JFrame implements ActionListener {
    final static String CANCEL = "Operacion CANCELADA";

    private final static Logger LOG = LogManager.getLogger(AlohandesInterface.class);
    private final static int FIELD_SIZE = 20;
    private final static java.util.function.Supplier<JComboBox<String>> YES_NO = () -> new JComboBox<>(
            new String[]{"Si", "No"});
    private final static Supplier<JTextField> TEXT_FIELD = () -> new JTextField(FIELD_SIZE);
    private final static Supplier<JTextField> NUMERIC_FIELD = () -> new JTextField(new PlainDocument() {
        @Override public void insertString(int offs,
                                           String str,
                                           AttributeSet a)
                throws BadLocationException {
            if (str.matches("\\d+")) super.insertString(offs, str, a);
        }
    }, null, FIELD_SIZE);
    private final static Supplier<TimePicker> TIME = TimePicker::new;
    private final static Supplier<DatePicker> DATE = DatePicker::new;
    private final DataPanel console = new DataPanel();
    private AlohandesBusiness business;

    public AlohandesInterface()
            throws HeadlessException {
        super("Alohandes");
        setLayout(new BorderLayout());
    }

    private static Object[] pkApartment(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID del apartamento", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkBooking(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la reserva", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkClient(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("NUIP del cliente", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkHostel(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID del hostal", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkHotel(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("NIT del hotel", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkHotelRoom(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la habitacion de hotel", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkHouseRoom(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la habitacion de casa", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkOffer(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la oferta", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkResidence(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la residencia no-estudiantil", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkRoomsHotel(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("NIT del hotel", NUMERIC_FIELD.get()).append("ID de la habitacion de hotel", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    private static Object[] pkStudentRes(String methodName)
            throws Exception {
        InputBuilder ib = InputBuilder.supplier();
        ib.append("ID de la residencia estudiantil", NUMERIC_FIELD.get());
        int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput().getValues();
    }

    public void build(JsonObject json) {
        // Banner
        try {
            add(new JLabel(
                    new ImageIcon(
                        Objects.requireNonNull(getClass().getResource(json.get("banner").getAsString())).getPath())),
                BorderLayout.NORTH);
            LOG.info("Se ha encontrado un banner valido");
        } catch (Exception e) {
            LOG.error("No se ha encontrado un banner valido");
            throw new RuntimeException(e);
        }
        add(console, BorderLayout.CENTER);

        int width = json.get("frameW").getAsInt();
        int height = json.get("frameH").getAsInt();
        setSize(width, height);
        // MenuBar
        JMenuBar bar = new JMenuBar();

        JsonArray oneLevel = json.getAsJsonArray("one-level menu");
        JMenu requirements = new JMenu("Requerimientos");
        extractedJSONBuild(oneLevel, requirements);
        bar.add(requirements);

        JsonArray menu = json.getAsJsonArray("two-level menu");
        JMenu mini = new JMenu("Utility");
        for (JsonElement element : menu) {
            JsonObject element1 = element.getAsJsonObject();

            JMenu subMenu = new JMenu(element1.get("tw-menu title").getAsString());
            JsonArray options = element1.getAsJsonArray("tw-menu options");

            extractedJSONBuild(options, subMenu);
            mini.add(subMenu);
        }
        bar.add(mini);
        setJMenuBar(bar);

        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        LOG.info("Se ha encontrado un archivo con el menu valido");
    }

    private void extractedJSONBuild(JsonArray oneLevel,
                                    JMenu requirements) {
        for (JsonElement element3 : oneLevel) {
            JsonObject element12 = element3.getAsJsonObject();
            JMenuItem item1 = new JMenuItem(element12.get("label").getAsString());
            item1.addActionListener(this);
            item1.setActionCommand(element12.get("event").getAsString());
            requirements.add(item1);
        }
    }

    public void setBusiness(AlohandesBusiness negocio) {business = negocio;}

    /** Using reflection, executes a method related to the action command resulted by user-event input */
    @Override public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        try {
            AlohandesInterface.class.getMethod(event).invoke(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String generateErrorMessage(Throwable e) {
        String title = switch (e.getClass().getSimpleName()) {
            case "IndexOutOfBoundsException" -> "No hay elementos";
            case "NullPointerException" -> "Inexistente";
            case "AssertionError" -> "User error";
            case "JDODataStoreException" -> "Error con SQL";
            default -> e.getClass().getSimpleName();
        };
        return "=-------------------" + title + "--------------------=\n"
               + e.getMessage() +
               "\n=-------------------" + title + "--------------------=\n";
    }

    public void req01() {createOperator();}

    public void req02() {createBooking();}

    public void req03() {createClient();}

    public void req04() {createOffer();}

    public void req05() {deleteOffer();}

    public void req06() {deleteBooking();}

    public void reqC01() {simpleCatcher(() -> business.moneyByYear());}

    public void reqC02() {simpleCatcher(() -> business.popularOffers());}

    public void reqC03() {simpleCatcher(() -> business.occupationIndex());}

    public void reqC04() {
        catcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append(new String[]{"Inicio del rango", "Fin del rango"}, new Component[]{DATE.get(), DATE.get()})
              .append("Servicios (separados por ,)", new JTextArea());
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), "getBookingsRange",
                                                         JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.availableInRangeAndServices(input));
    }

    public void reqC05() {simpleCatcher(() -> business.alohandesUse());}

    public void reqC06() {
        catcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append("NUIP del usuario", NUMERIC_FIELD.get());
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), "getAlohandesUse",
                                                         JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.alohandesUse(input));
    }

    public void reqC07() {
        catcher(()->{
            InputBuilder ib = InputBuilder.supplier();
            ib.append("Unidad de tiempo", new JComboBox<>(new String[]{"Semana", "Mes", "Anio"}))
              .append("Tipo de operador", new JComboBox<>(
                      new String[]{"Apartamento", "Habitacion de Hotel", "Habitacion de casa", "Hostal",
                              "Residencia estudiantil", "Residencia no-estudiantil"}));
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), "alohandesOperation",
                                                         JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.alohandesOperation(input));
    }

    public void reqC08() {
        catcher(()->{
            InputBuilder ib = InputBuilder.supplier();
            ib.append("Nombre del operador", TEXT_FIELD.get());
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), "frequentClients",
                                                         JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.frequentClients(input));
    }

    public void reqC09() {
        simpleCatcher(()-> business.lowDemand());
    }

    public void createOperator() {
        String methodName = "createOperator";
        simpleCatcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            OrderedMap<String, String> iOperator = createOperatorFirstInput(ib, methodName);
            ib.clear();
            OrderedMap<String, String> iOperatorSpec = createOperatorSecondInput(ib, methodName);
            ib.clear();
            OrderedMap<String, String> iOperatorService = createOperatorThirdInput(ib, methodName);
            ib.clear();
            String s = iOperator.get("Que tipo de operador es?");
            OrderedMap<String, String> iInheritor = createOperatorFourthInput(s, ib, methodName);
            ib.clear();
            return business.createOperator(iOperator, iOperatorSpec, iOperatorService, iInheritor);
        });

    }

    private OrderedMap<String, String> createOperatorFirstInput(InputBuilder ib,
                                                                String methodName)
            throws Exception {
        ib.append("NUIP del operador", NUMERIC_FIELD.get()).append("Nombre del operador", TEXT_FIELD.get())
          .append("Que tipo de operador es?", new JComboBox<>(
                  new String[]{"Residencia estudiantil", "Residencia no-estudiantil", "Hostal",
                          "Habitacion de Hotel", "Habitacion de casa", "Apartamento"}));
        int first = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (first == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    private OrderedMap<String, String> createOperatorSecondInput(InputBuilder ib,
                                                                 String methodName)
            throws Exception {
        ib.append(new String[]{"Capacidad", "Tamanio", "Localizacion", "Compartido?"},
                  new Component[]{NUMERIC_FIELD.get(), NUMERIC_FIELD.get(), TEXT_FIELD.get(), YES_NO.get()});
        int second = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (second == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    private OrderedMap<String, String> createOperatorThirdInput(InputBuilder ib,
                                                                String methodName)
            throws Exception {
        ib.append(new String[]{"Amueblado?", "Tiene wifi?", "Tiene cocineta?"},
                  new Component[]{YES_NO.get(), YES_NO.get(), YES_NO.get()});
        int third = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (third == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    private OrderedMap<String, String> createOperatorFourthInput(String type,
                                                                 InputBuilder ib,
                                                                 String methodName)
            throws
            Exception {
        switch (type) {
            case "Residencia estudiantil" ->
                    ib.append(new String[]{"Coste del restaurante", "Coste de la sala de estudio"},
                              new Component[]{NUMERIC_FIELD.get(), NUMERIC_FIELD.get()})
                      .append(new String[]{"Coste por sala de ocio", "Coste por gimnasio"},
                              new Component[]{NUMERIC_FIELD.get(), NUMERIC_FIELD.get()});
            case "Residencia no-estudiantil" ->
                    ib.append(new String[]{"Numero de habitaciones", "Cuota administrativa", "Descripcion del seguro"},
                              new Component[]{NUMERIC_FIELD.get(), NUMERIC_FIELD.get(), TEXT_FIELD.get()});
            case "Hostal" -> ib.append("NIT", NUMERIC_FIELD.get())
                               .append(new String[]{"Horario de apertura", "Horario de cierre"},
                                       new Component[]{TIME.get(), TIME.get()});
            case "Habitacion de Hotel" ->
                    ib.append("NIT del hotel", NUMERIC_FIELD.get()).append("Numero de habitacion", NUMERIC_FIELD.get())
                      .append("Tipo de sala", new JComboBox<>(new String[]{"Estandar", "Semi-suite", "Suite"}))
                      .append(new String[]{"Tiene baniera?", "Tiene yacuzzi?", "Tiene sala?"},
                              new Component[]{YES_NO.get(), YES_NO.get(), YES_NO.get()});
            case "Habitacion de casa" -> ib.append(new String[]{"Tiene comidas incluidas?", "Tiene banio privado?"},
                                                   new Component[]{YES_NO.get(), YES_NO.get()});
            case "Apartamento" -> ib.append("Cuota administrativa", NUMERIC_FIELD.get())
                                    .append(new String[]{"Servicios incluidos?", "Television incluida?"},
                                            new Component[]{YES_NO.get(), YES_NO.get()});
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        int fourth = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (fourth == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    public void createClient() {
        String methodName = "createClient";
        simpleCatcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            OrderedMap<String, String> iClient = createClientFirstInput(ib, methodName);
            ib.clear();
            OrderedMap<String, String> iClientPreference = createClientSecondInput(ib, methodName);
            return business.createClient(iClient, iClientPreference);
        });
    }

    private OrderedMap<String, String> createClientFirstInput(InputBuilder ib,
                                                              String methodName)
            throws Exception {
        ib.append(new String[]{"Numero Unico de Identificacion Personal", "Nombre del cliente"},
                  new Component[]{NUMERIC_FIELD.get(), TEXT_FIELD.get()})
          .append("Tipo de cliente?", new JComboBox<>(new String[]{"Estudiante",
                  "Estudiante Graduado",
                  "Empleado",
                  "Profesor",
                  "Familiar de estudiante",
                  "Profesor invitado",
                  "Invitado a evento"}));
        int first = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (first == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    private OrderedMap<String, String> createClientSecondInput(InputBuilder ib,
                                                               String methodName)
            throws Exception {
        ib.append(new String[]{"Amueblado?", "Compartido?", "Que tenga wifi?", "Que tenga cocineta?"},
                  new Component[]{YES_NO.get(), YES_NO.get(), YES_NO.get(), YES_NO.get()});
        int second = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
        if (second == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
        return ib.getInput();
    }

    public void createBooking() {
        String methodName = "createBooking";
        catcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append(new String[]{"NUIP del Cliente", "Nombre del Operador"},
                      new Component[]{NUMERIC_FIELD.get(), TEXT_FIELD.get()})
              .append("Costo", NUMERIC_FIELD.get())
              .append(new String[]{"Inicio", "Fin"}, new Component[]{DATE.get(), DATE.get()});
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.createBooking(input));
    }

    public void createOffer() {
        String methodName = "createOffer";
        catcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append("ID de la reserva", NUMERIC_FIELD.get()).append("Costo por noche", NUMERIC_FIELD.get());
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.createOffer(input));
    }

    public void createHotel() {
        String methodName = "createHotel";
        catcher(() -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append("NIT", NUMERIC_FIELD.get())
              .append(new String[]{"Tiene restaurante?", "Tiene piscina?", "Tiene parqueadero?", "Tiene wifi?", "Tiene television por cable?",},
                      new Component[]{YES_NO.get(), YES_NO.get(), YES_NO.get(), YES_NO.get(), YES_NO.get()});
            int selected = JOptionPane.showConfirmDialog(null, ib.build(), methodName, JOptionPane.OK_CANCEL_OPTION);
            if (selected == JOptionPane.CANCEL_OPTION) throw new Exception(CANCEL);
            return ib.getInput().getValues();
        }, input -> business.createHotel(input));
    }

    public Set<String[]> createServices()
            throws Exception {
        Set<String[]> serviceSet = new HashSet<>();
        Creator service = () -> {
            InputBuilder ib = InputBuilder.supplier();
            ib.append("Nombre del servicio", TEXT_FIELD.get()).append("Costo del servicio", NUMERIC_FIELD.get());
            int dialog = JOptionPane.showConfirmDialog(null, ib.build(), "Servicio", JOptionPane.OK_CANCEL_OPTION);
            OrderedMap<String, String> input = ib.getInput();
            return dialog == JOptionPane.CANCEL_OPTION
                    ? null
                    : input;
        };
        while (true) {
            OrderedMap<String, String> map = service.get();
            if (map == null) break;
            serviceSet.add(new String[]{map.get("Nombre del servicio"), map.get("Costo del servicio")});
        }
        return serviceSet;
    }

    public void retrieveOneApartmentSpec() {
        String methodName = "retrieveOneApartmentSpec";
        catcher(() -> pkApartment(methodName),
                input -> business.retrieveOne(Operator.class, input) + "\n"
                         + business.retrieveOne(OperatorSpec.class, input) + "\n"
                         + business.retrieveOne(OperatorService.class, input) + "\n"
                         + business.retrieveOne(ApartmentSpec.class, input));
    }

    public void retrieveOneBooking() {
        String methodName = "retrieveOneBooking";
        catcher(() -> pkBooking(methodName),
                input -> business.retrieveOne(Booking.class, input) + "");
    }

    public void retrieveOneClient() {
        String methodName = "retrieveOneClient";
        catcher(() -> pkClient(methodName),
                input -> business.retrieveOne(Client.class, input) + "\n"
                         + business.retrieveOne(ClientPreference.class, input));
    }

    public void retrieveOneHostelSpec() {
        String methodName = "retrieveOneHostelSpec";
        catcher(() -> pkHostel(methodName),
                input -> business.retrieveOne(Operator.class, input) + "\n"
                         + business.retrieveOne(OperatorSpec.class, input) + "\n"
                         + business.retrieveOne(OperatorService.class, input) + "\n"
                         + business.retrieveOne(HostelSpec.class, input));
    }

    public void retrieveOneHotel() {
        String methodName = "retrieveOneHotel";
        catcher(() -> pkHotel(methodName),
                input -> business.retrieveOne(Hotel.class, input) + "");
    }

    public void retrieveOneHotelRoomSpec() {
        String methodName = "retrieveOneHotelRoomSpec";
        catcher(() -> pkHotelRoom(methodName),
                input -> business.retrieveOne(Operator.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorSpec.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorService.class, (Object) input) + "\n"
                         + business.retrieveOne(HotelRoomSpec.class, (Object) input));
    }

    public void retrieveOneHouseRoomSpec() {
        String methodName = "retrieveOneHouseRoomSpec";
        catcher(() -> pkHouseRoom(methodName),
                input -> business.retrieveOne(Operator.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorSpec.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorService.class, (Object) input) + "\n"
                         + business.retrieveOne(HouseRoomSpec.class, (Object) input));
    }

    public void retrieveOneOffer() {
        String methodName = "retrieveOneOffer";
        catcher(() -> pkOffer(methodName),
                input -> business.retrieveOne(Offer.class, (Object) input) + "\n"
                         + business.retrieveOne(Booking.class, (Object) input));
    }

    public void retrieveOneResidenceSpec() {
        String methodName = "retrieveOneResidenceSpec";
        catcher(() -> pkResidence(methodName),
                input -> business.retrieveOne(Operator.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorSpec.class, (Object) input) + "\n"
                         + business.retrieveOne(OperatorService.class, (Object) input) + "\n"
                         + business.retrieveOne(ResidenceSpec.class, (Object) input));
    }

    public void retrieveOneRoomsHotel() {
        String methodName = "retrieveOneRoomsHotel";
        catcher(() -> pkRoomsHotel(methodName),
                input -> business.retrieveOne(Operator.class, (Object) input) + "\n"
                         + business.retrieveOne(HotelRoomSpec.class, (Object) input) + "\n"
                         + business.retrieveOne(RoomsHotel.class));
    }

    public void retrieveOneStudentResSpec() {
        String methodName = "retrieveOneStudentResSpec";
        catcher(() -> pkStudentRes(methodName), input -> business.retrieveOne(Operator.class, (Object) input) + "\n"
                                                         + business.retrieveOne(HotelRoomSpec.class,
                                                                                (Object) input) + "\n"
                                                         + business.retrieveOne(StudentResSpec.class, (Object) input));
    }

    public void retrieveAllApartmentSpec() {
        simpleCatcher(() -> business.retrieveAll(ApartmentSpec.class));
    }

    public void retrieveAllBooking() {
        simpleCatcher(() -> business.retrieveAll(Booking.class));
    }

    public void retrieveAllClient() {
        simpleCatcher(() -> business.retrieveAll(Client.class));
    }

    public void retrieveAllClientPreference() {
        simpleCatcher(() -> business.retrieveAll(ClientPreference.class));
    }

    public void retrieveAllHostelSpec() {
        simpleCatcher(() -> business.retrieveAll(HostelSpec.class));
    }

    public void retrieveAllHotel() {
        simpleCatcher(() -> business.retrieveAll(Hotel.class));
    }

    public void retrieveAllHotelRoomSpec() {
        simpleCatcher(() -> business.retrieveAll(HotelRoomSpec.class));
    }

    public void retrieveAllHouseRoomSpec() {
        simpleCatcher(() -> business.retrieveAll(HouseRoomSpec.class));
    }

    public void retrieveAllOffer() {
        simpleCatcher(() -> business.retrieveAll(Offer.class));
    }

    public void retrieveAllOperator() {
        simpleCatcher(() -> business.retrieveAll(Operator.class));
    }

    public void retrieveAllOperatorService() {
        simpleCatcher(() -> business.retrieveAll(OperatorService.class));
    }

    public void retrieveAllOperatorSpec() {
        simpleCatcher(() -> business.retrieveAll(OperatorSpec.class));
    }

    public void retrieveAllResidenceSpec() {
        simpleCatcher(() -> business.retrieveAll(ResidenceSpec.class));
    }

    public void retrieveAllRoomsHotel() {
        simpleCatcher(() -> business.retrieveAll(RoomsHotel.class));
    }

    public void retrieveAllServiceScheme() {
        simpleCatcher(() -> business.retrieveAll(ServiceScheme.class));
    }

    public void retrieveAllStudentResSpec() {
        simpleCatcher(() -> business.retrieveAll(StudentResSpec.class));
    }

    public void deleteApartmentSpec() {
        String methodName = "deleteApartmentSpec";
        catcher(() -> pkApartment(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(OperatorSpec.class, (Object) input) + "\n"
                         + business.delete(OperatorService.class, (Object) input) + "\n"
                         + business.delete(ApartmentSpec.class, (Object) input));
    }

    public void deleteBooking() {
        String methodName = "deleteBooking";
        catcher(() -> pkBooking(methodName),
                input -> business.delete(Booking.class, (Object) input));
    }

    public void deleteClient() {
        String methodName = "deleteClient";
        catcher(() -> pkClient(methodName),
                input -> business.delete(Client.class, (Object) input) + "\n"
                         + business.delete(ClientPreference.class, (Object) input));
    }

    public void deleteHostelSpec() {
        String methodName = "deleteHostelSpec";
        catcher(() -> pkHostel(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(OperatorSpec.class, (Object) input) + "\n"
                         + business.delete(OperatorService.class, (Object) input) + "\n"
                         + business.delete(HostelSpec.class, (Object) input));
    }

    public void deleteHotel() {
        String methodName = "deleteHotel";
        catcher(() -> pkHotel(methodName),
                input -> business.delete(Hotel.class, (Object) input));
    }

    public void deleteHotelRoomSpec() {
        String methodName = "deleteHotelRoomSpec";
        catcher(() -> pkHotelRoom(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(OperatorSpec.class, (Object) input) + "\n"
                         + business.delete(OperatorService.class, (Object) input) + "\n"
                         + business.delete(HotelRoomSpec.class, (Object) input));
    }

    public void deleteHouseRoomSpec() {
        String methodName = "deleteHouseRoomSpec";
        catcher(() -> pkHouseRoom(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(OperatorSpec.class, (Object) input) + "\n"
                         + business.delete(OperatorService.class, (Object) input) + "\n"
                         + business.delete(HouseRoomSpec.class, (Object) input));
    }

    public void deleteOffer() {
        String methodName = "deleteOffer";
        catcher(() -> pkOffer(methodName),
                input -> business.delete(Offer.class, (Object) input) + "\n"
                         + business.delete(Booking.class, (Object) input));
    }

    public void deleteResidenceSpec() {
        String methodName = "deleteResidenceSpec";
        catcher(() -> pkResidence(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(OperatorSpec.class, (Object) input) + "\n"
                         + business.delete(OperatorService.class, (Object) input) + "\n"
                         + business.delete(ResidenceSpec.class, (Object) input));
    }

    public void deleteRoomsHotel() {
        String methodName = "deleteRoomsHotel";
        catcher(() -> pkRoomsHotel(methodName),
                input -> business.delete(Operator.class, (Object) input) + "\n"
                         + business.delete(HotelRoomSpec.class, (Object) input) + "\n"
                         + business.delete(RoomsHotel.class, (Object) input));
    }

    public void deleteStudentResSpec() {
        String methodName = "deleteStudentResSpec";
        catcher(() -> pkStudentRes(methodName),
                input -> business.delete(StudentResSpec.class, (Object) input)
        );
    }

    public void cleanAlohandesLog() {
        try (Writer w = new BufferedWriter(new FileWriter("alohandes.log"))) {
            w.write("");
            console.print("Alohandes.log limpiado exitosamente", null);
        } catch (IOException e) {
            console.print(e.getMessage(), false);
        }
    }

    public void cleanDatanucleusLog() {
        try (Writer w = new BufferedWriter(new FileWriter("datanucleus.log"))) {
            w.write("");
            console.print("Datanucleus.log limpiado exitosamente", null);
        } catch (IOException e) {
            console.print(e.getMessage(), false);
        }
    }

    public void about() {
        console.print("Equipo B-04::\n"
                      + TextTable.builder().append(new Object[]{"Pedro Lobato", "Nicolas Camargo"}),
                      null);
    }

    void catcher(Executable caller,
                 Monad lambda) {
        String result;
        boolean b;
        try {
            result = lambda.apply(caller.call());
            b = true;
        } catch (Exception | AssertionError e) {
            LOG.error(e.getMessage());
            result = generateErrorMessage(e);
            b = false;
            e.printStackTrace();
        }
        console.print(result, b);
    }

    void simpleCatcher(Callable<String> lambda) {
        String result;
        boolean b;
        try {
            result = lambda.call();
            b = true;
        } catch (Exception | AssertionError e) {
            LOG.error(e.getMessage());
            result = generateErrorMessage(e);
            b = false;
            e.printStackTrace();
        }
        console.print(result, b);
    }

    @FunctionalInterface private interface Executable {
        Object[] call()
                throws Exception;
    }

    @FunctionalInterface private interface Monad {
        String apply(Object[] strings)
                throws Exception;
    }

    @FunctionalInterface private interface Creator {
        OrderedMap<String, String> get()
                throws Exception;
    }

}
