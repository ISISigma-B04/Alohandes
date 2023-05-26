package org.uniandes.isis2304.persistence;

import org.uniandes.isis2304.business.Booking;
import org.uniandes.isis2304.business.Operator;
import org.uniandes.isis2304.core.PK;

import javax.jdo.PersistenceManager;
import java.util.Arrays;

@SuppressWarnings("SqlNoDataSourceInspection") public class SQLUtil {

    private static SQLUtil instance;
    protected final static String SQL = AlohandesPersistence.SQL;

    public static SQLUtil getInstance() {
        if (instance == null) {
            instance = new SQLUtil();
        }
        return instance;
    }

    private SQLUtil() {}

    public long nextOperator(PersistenceManager pm) {
        return (long) pm.newQuery(SQL, "SELECT " + seq(Operator.class) + ".nextval FROM DUAL").executeUnique();
    }

    public long nextBooking(PersistenceManager pm) {
        return (long) pm.newQuery(SQL, "SELECT " + seq(Booking.class) + ".nextval FROM DUAL").executeUnique();
    }

    private static String seq(Class<?> arg) {
        return Arrays.stream(arg.getDeclaredFields())
                     .filter(f -> f.isAnnotationPresent(PK.class))
                     .map(f -> f.getAnnotation(PK.class))
                     .map(PK::sequence)
                     .toArray(String[]::new)[0];
    }

    char parse(boolean b) {return b ? '1' : 0;}
}
