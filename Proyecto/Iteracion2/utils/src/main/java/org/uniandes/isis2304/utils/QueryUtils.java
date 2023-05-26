package org.uniandes.isis2304.utils;

import org.uniandes.isis2304.core.PK;
import org.uniandes.isis2304.core.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

public final class QueryUtils {
    static String[] reserved = {"start", "end", "size"};

    public static Map<String, String> build(Class<?> clazz) {
        String table = clazz.getAnnotation(Table.class).value();

        StringJoiner where = new StringJoiner(" AND ");
        StringJoiner insertA = new StringJoiner(", ");
        StringJoiner insertB = new StringJoiner(", ");
        for (Field field : clazz.getDeclaredFields()) {
            String fName = clean(field.getName());
            if (field.isAnnotationPresent(PK.class)) where.add(fName + "= ?");
            insertA.add(fName);
            insertB.add("?");

        }
        return Map.of(
                "table", table,
                "where", "WHERE " + where,
                "insert", "(" + insertA + ") VALUES (" + insertB + ")"
        );
    }

    public static String clean(String str) {
        StringBuilder result = new StringBuilder();
        boolean quotes = Arrays.asList(reserved).contains(str);
        if (quotes) result.append("\"");
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0 && !Character.isUpperCase(str.charAt(i - 1))) result.append('_');
                result.append(Character.toLowerCase(c));
            } else result.append(c);
        }
        if (quotes) result.append("\"");
        return result.toString();
    }

}