package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtils {
    public EntityUtils() throws UnsupportedOperationException {}

    public static String getTableName(Class entity){
        Table annotation = (Table) entity.getAnnotation(Table.class);
        return annotation.name();
    }

    public static ArrayList<ColumnInfo> getColumns(Class entity){
        ArrayList<ColumnInfo> columnsList = new ArrayList<ColumnInfo>();

        for(Field field : entity.getDeclaredFields()) {

            ColumnInfo newColumn = new ColumnInfo();
            newColumn.setColumnName(field.getName());
            newColumn.setColumnType(field.getType());

            Column columnAnnotation = (Column) field.getAnnotation(Column.class);

            if(columnAnnotation == null){
                Id idAnnotation = (Id) field.getAnnotation(Id.class);
                newColumn.setId(true);
                newColumn.setDbName(idAnnotation.name());
            }
            else{
                newColumn.setDbName(columnAnnotation.name());
            }
            columnsList.add(newColumn);
        }

        return columnsList;
    }

    public static Object castFromSqlType(Object value, Class wantedType) {

     //  if(value.getClass() == BigDecimal.class && wantedType == Integer.class)
           return ((BigDecimal) value).intValue();
//        if(value.getClass() == BigDecimal.class && wantedType == Long.class)
//            return ((BigDecimal) value).longValue();
//        if(value.getClass() == BigDecimal.class && wantedType == Float.class)
//            return ((BigDecimal) value).floatValue();
//        if(value.getClass() == BigDecimal.class && wantedType == Double.class)
//            return ((BigDecimal) value).doubleValue();
//      //  if(value.getClass() != BigDecimal.class)
//            return value;


    }

    public static ArrayList<Field> getFieldsByAnnotations(Class clazz, Class annotation) {
        ArrayList<Field> myList = new ArrayList<Field>();

        for(Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(annotation))
                myList.add(field);
        }
        return  myList;
    }

    public static Object getSqlValue(Object object){

        if(object.getClass().isAnnotationPresent(Table.class)){
            for(Field field : object.getClass().getDeclaredFields()) {
                if(field.isAnnotationPresent(Id.class))
                    field.setAccessible(true);

            }
        }

        return object;


    }
}
