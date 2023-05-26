package org.uniandes.isis2304.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "SqlNoDataSourceInspection", "SqlResolve"}) public class SQLReq {
    protected final static String SQL = AlohandesPersistence.SQL;

    //Iter 2
    public List<Object[]> rfc01(PersistenceManager pm) {
        class RFC01 {
            String operatorName;
            Long totalMoney;
        }
        Query<RFC01> q = pm.newQuery(SQL, """
                SELECT o.name AS operator_name, SUM(b.cost) AS total_money
                FROM operator o
                         JOIN booking b ON o.name = b.name
                WHERE b."start" >= ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), -12)
                GROUP BY o.name""");
        return add(q.executeList().stream().map(item -> new Object[]{item.operatorName, item.totalMoney}), "Operator name",
            "Total money");
    }

    public List<Object[]> rfc02(PersistenceManager pm) {
        class RFC02 {
            String bookingName;
            int numBookings;
        }
        Query<RFC02> q = pm.newQuery(SQL, """
                SELECT name, COUNT(*) AS num_bookings
                FROM booking
                GROUP BY name
                ORDER BY num_bookings DESC
                    FETCH FIRST 20 ROWS ONLY""");
        return add(q.executeList().stream().map(item -> new Object[]{item.bookingName, item.numBookings}),
                   "Booking name", "Number of bookings");
    }

    public List<Object[]> rfc03(PersistenceManager pm) {
        class RFC03 {
            String operatorName;
            Double occupationIndex;
        }
        Query<RFC03> q = pm.newQuery(SQL, """
                SELECT os.name, COUNT(*) / os.capacity AS occupation_index
                FROM operator_spec os
                         LEFT JOIN booking b ON os.name = b.name
                WHERE b."end" > SYSDATE
                GROUP BY os.name, os.capacity""");
        return add(q.executeList().stream().map(item -> new Object[]{item.operatorName, item.occupationIndex}),
                   "Operator name", "Occupation Index");
    }

    public List<Object[]> rfc04(PersistenceManager pm, Date[] dateR, boolean[] services) {
        class RFC04 {
            String name;
            Boolean furnished;
            Boolean wifi;
            Boolean kitchenette;
        }
        String furnished = services[0] ? "furnished = 1" : null;
        String wifi = services[1] ? "wifi = 1" : null;
        String kitchenette = services[2] ? "kitchenette = 1" : null;

        String s = Stream.of(furnished, wifi, kitchenette)
                         .filter(Objects::nonNull)
                         .collect(Collectors.joining(" AND "));

        Query<RFC04> q = pm.newQuery(SQL, """
                  SELECT oser.*
                  FROM operator op
                           JOIN operator_service oser ON op.name = oser.name
                           LEFT JOIN booking b ON op.name = b.name
                  WHERE %s
                    AND b."start" > ? AND b."end" < ?""".formatted(s));
        q.setParameters(dateR[0], dateR[1]);
        return add(q.executeList().stream()
                    .map(item -> new Object[]{item.name, item.furnished, item.wifi, item.kitchenette}), "Operator Name",
                   "Furnished", "Wifi", "Kitchenette");
    }

    public List<Object[]> rfc05(PersistenceManager pm) {
        class RFC05 {
            Long nuip;
            String role;
            Long id;
            Date start;
            Date end;
            Long timeSpent;
        }
        Query<RFC05> q = pm.newQuery(SQL, """
                SELECT o.nuip, 'operator' AS role, b.id, b."start", b."end", ROUND(b."end" - b."start") AS time_spent
                FROM booking b
                         LEFT JOIN operator o ON b.name = o.name
                UNION
                SELECT c.nuip, 'client' AS role, b.id, b."start", b."end", ROUND(b."end" - b."start") AS time_spent
                FROM booking b
                         LEFT JOIN client c ON b.nuip = c.nuip""");
        return add(q.executeList().stream()
                    .map(item -> new Object[]{item.nuip, item.role, item.id, item.start, item.end, item.timeSpent}),
                   "nuip", "role", "id", "start", "end", "timeSpent");
    }

    public List<Object[]> rfc06(PersistenceManager pm, long nuip) {
        class RFC06 {
            String role;
            Long id;
            Date start;
            Date end;
            Long timeSpent;
        }
        Query<RFC06> q = pm.newQuery(SQL, """
                SELECT 'operator' AS role, b.id, b."start", b."end", ROUND(b."end" - b."start") AS time_spent, 'operator' AS rol
                FROM booking b
                         LEFT JOIN operator o ON b.name = o.name
                WHERE o.nuip = ?1
                UNION
                SELECT 'client' AS role, b.id, b."start", b."end", ROUND(b."end" - b."start") AS time_spent, 'client' AS rol
                FROM booking b
                         LEFT JOIN client c ON b.nuip = c.nuip
                WHERE c.nuip = ?1""");
        q.setParameters(nuip);
        return add(q.executeList().stream()
                    .map(item -> new Object[]{item.role, item.id, item.start, item.end, item.timeSpent}), "User role", "Booking id", "Booking start", "Booking end", "Booking duration");
    }

    //Iter 3
    public List<Object[]> rfc07(PersistenceManager pm, String unit, String type) {
        class RFC07 {
            Date startDate;
            Integer maxOccupancy;
            Integer maxRevenue;
            Integer minOccupancy;
        }
        Query<RFC07> q = pm.newQuery(SQL, """
                SELECT start_date, MAX(occupancy) AS max_occupancy, SUM(revenue) AS max_revenue, MIN(occupancy) AS min_occupancy
                FROM (SELECT b."start" AS start_date, COUNT(DISTINCT b.id) AS occupancy, SUM(os.capacity * b.cost) AS revenue
                      FROM booking b
                               INNER JOIN operator_spec os ON b.name = os.name
                               INNER JOIN operator o ON o.name = b.name
                      WHERE o.type = ?2
                        AND TRUNC(b."start", ?1) = TRUNC(SYSDATE, ?1) -- time unit aggregation
                      GROUP BY b."start")
                GROUP BY start_date
                ORDER BY start_date""");
        q.setParameters(unit, type);
        return add(q.executeList().stream()
                    .map(item -> new Object[]{item.startDate, item.maxOccupancy, item.minOccupancy, item.maxRevenue}),
                   "Start Date", "Max occupancy", "Min occupancy", "Max revenue");
    }

    public List<Object[]> rfc08(PersistenceManager pm, String operatorName) {
        class RFC08 {
            Long nuip;
            String name;
        }
        Query<RFC08> q = pm.newQuery(SQL, """
                SELECT c.nuip, c.name
                FROM client c
                INNER JOIN booking b ON b.nuip = c.nuip
                INNER JOIN operator_spec os ON os.name = b.name
                WHERE os.name = ?
                GROUP BY c.nuip, c.name
                HAVING COUNT(DISTINCT b.id) >= 3 OR SUM(b."start" - b."end") >= 15""");
        q.setParameters(operatorName);
        return add(q.executeList().stream().map(item -> new Object[]{item.nuip, item.name}), "NUIP", "Name");
    }

    public List<Object[]> rfc09(PersistenceManager pm) {
        class RFC09 {
            Long id;
            String name;
            Date start;
            Date end;
        }
        Query<RFC09> q = pm.newQuery(SQL, """
                SELECT id, l.name, "start", "end"
                FROM (SELECT o.name, b.id, b."start", b."end", LAG(b."end") OVER (PARTITION BY b.name ORDER BY b."start") AS prev_end
                      FROM booking b
                               INNER JOIN operator o ON b.name = o.name
                      WHERE MONTHS_BETWEEN(b."end", b."start") <= 1) l
                WHERE NOT EXISTS(
                        SELECT 1
                        FROM booking b2
                        WHERE b2.name = l.name
                          AND b2."end" < "start"
                          AND MONTHS_BETWEEN("start", prev_end) <= 1
                    )""");
        return add(q.executeList().stream().map(item -> new Object[]{item.id, item.name, item.start, item.end}), "id",
                   "name", "start", "end");
    }

    private static List<Object[]> add(Stream<Object[]> str, String... headers) {
        LinkedList<Object[]> list = str.collect(Collectors.toCollection(LinkedList::new));
        list.addFirst(headers);
        return list;
    }

}
