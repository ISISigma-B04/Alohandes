package org.uniandes.isis2304.persistence;

import org.uniandes.isis2304.utils.QueryUtils;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@SuppressWarnings({"unchecked", "SqlNoDataSourceInspection"}) public class SQLHandler<Entity> {
    protected final static String SQL = AlohandesPersistence.SQL;

    private final String table;
    private final String insert;
    private final String wherePK;

    public SQLHandler(Class<Entity> entity) {
        Map<String, String> build = QueryUtils.build(entity);
        this.table = build.get("table");
        this.wherePK = build.get("where");
        this.insert = build.get("insert");
    }

    private static void cleanBooleans(Object[] values) {
        IntStream.range(0, values.length).forEach(i -> {if (values[i] instanceof Boolean b) values[i] = b ? 1 : 0;});
    }

    /** Create an entity in the DB */
    public long create(PersistenceManager pm,
                       Object... values) {
        assert values != null && values.length >= 1;
        cleanBooleans(values);
        Query<Long> q = pm.newQuery(SQL, "INSERT INTO " + table + insert);
        return q.setParameters(values).executeUnique();
    }

    /** Retrieve a single entity(Using its PKs) from the DB */
    public Entity retrieveByPK(PersistenceManager pm,
                               Object... keys) {
        assert keys != null && keys.length >= 1;
        Query<Entity> q = pm.newQuery(SQL, "SELECT * FROM " + table + " " + wherePK);
        return q.setParameters(keys).executeUnique();
    }

    /** Retrieve all entities from the DB */
    public List<Entity> retrieveAll(PersistenceManager pm) {
        Query<Entity> q = pm.newQuery(SQL, "SELECT * FROM " + table);
        return q.executeList();
    }

    /** Delete a single entity(Using its PKs) from the DB */
    public long deleteByPK(PersistenceManager pm,
                           Object... keys) {
        assert keys != null && keys.length >= 1;
        Query<Long> q = pm.newQuery(SQL, "DELETE FROM " + table + " " + wherePK);
        return q.setParameters(keys).executeUnique();
    }
}