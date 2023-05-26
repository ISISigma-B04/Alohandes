package org.uniandes.isis2304;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.uniandes.isis2304.business.AlohandesBusiness;
import org.uniandes.isis2304.persistence.AlohandesPersistence;
import org.uniandes.isis2304.view.AlohandesInterface;

import javax.swing.*;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;

public class AlohandesApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            AlohandesApplication.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void run() {
        final URL CONFIG_INTERFAZ = AlohandesInterface.class.getResource("/config/interfazApp.json");
        final URL CONFIG_TABLA = AlohandesInterface.class.getResource("/config/tablasBD.json");
        final Gson g = new Gson();
        if (CONFIG_INTERFAZ != null && CONFIG_TABLA != null) try (Reader r1 = new FileReader(CONFIG_INTERFAZ.getFile());
                                                                  Reader r2 = new FileReader(CONFIG_TABLA.getFile())) {
            AlohandesInterface interfaz = AlohandesInterface.class.getDeclaredConstructor().newInstance();
            interfaz.build(g.fromJson(new JsonReader(r1), JsonObject.class));

            AlohandesPersistence persistence = AlohandesPersistence.getInstance(g.fromJson(new JsonReader(r2), JsonObject.class));
            AlohandesBusiness negocio = AlohandesBusiness.class.getDeclaredConstructor(AlohandesInterface.class, AlohandesPersistence.class)
                    .newInstance(interfaz, persistence);

            interfaz.setBusiness(negocio);

            interfaz.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }

    }
}
