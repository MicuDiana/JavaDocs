package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.annotations.Table;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Created by user on 07.07.2016.
 */
public class EntityUtils {

    private EntityUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getTableName(Class entity) {

        return ((Table) entity.getAnnotation(Table.class)).name();
    }

    public static LinkedList<ColumnInfo> getColumns(Class entity) {

        LinkedList<ColumnInfo> columnInfos = new LinkedList<>();

        for (Field field : entity.getDeclaredFields()) {

            ColumnInfo newColumnInfo = new ColumnInfo();
            Annotation annotation = field.getAnnotation(Column.class);

            if (annotation == null) {
                newColumnInfo.setDbName(field.getAnnotation(Id.class).name());
                newColumnInfo.setId(true);
            } else {
                newColumnInfo.setDbName(((Column) annotation).name());
            }

            newColumnInfo.setColumnName(field.getName());
            newColumnInfo.setColumnType(field.getType());
            columnInfos.add(newColumnInfo);
        }
        return columnInfos;
    }

    public static Object castFromSqlType(Object value, Class wantedType) {

        if (value.getClass() == BigDecimal.class && wantedType == Integer.class) {
            return ((BigDecimal) value).intValue();
        }
        if (value.getClass() == BigDecimal.class && wantedType == Long.class) {
            return ((BigDecimal) value).longValue();
        }
        if (value.getClass() == BigDecimal.class && wantedType == Float.class) {
            return ((BigDecimal) value).floatValue();
        }
        if (value.getClass() == BigDecimal.class && wantedType == Double.class) {
            return ((BigDecimal) value).doubleValue();
        }
        return value;
    }

    public static LinkedList<Field> getFieldsByAnnotations(Class clazz, Class annotation) {

        LinkedList<Field> fields = new LinkedList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static Object getSqlValue(Object object) throws IllegalAccessException {

        if (object.getClass().isAnnotationPresent(Table.class)) {

            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return field.get(object);
                }
            }
        }
        return object;
    }
}
