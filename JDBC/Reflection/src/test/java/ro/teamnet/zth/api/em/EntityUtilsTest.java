package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.appl.domain.Department;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 07.07.2016.
 */
public class EntityUtilsTest {

    @Test
    public void testGetTableNameMethod() {
        String tableName = EntityUtils.getTableName(Department.class);
        assertEquals("Table name should be departments!", "DEPARTMENTS", tableName);
    }

    @Test
    public void testGetColumnsMethod() {
        assertEquals("Department columnInfo list size should be 3", 3, EntityUtils.getColumns(Department.class).size());
    }

    @Test
    public void testCastFromSqlTypeMethod() {
        assertEquals("Casted type should be Integer", Integer.class, EntityUtils.castFromSqlType(new BigDecimal(100), Integer.class).getClass());
    }

    @Test
    public void testGetFieldsByIdAnnotationMethod() {
        assertEquals("Field list size with id annotation should be 1", 1, EntityUtils.getFieldsByAnnotations(Department.class, Id.class).size());
    }

    @Test
    public void testGetFieldsByColumnAnnotationMethod() {
        assertEquals("Field list size with column annotation should be 2", 2, EntityUtils.getFieldsByAnnotations(Department.class, Column.class).size());
    }

    @Test
    public void testGetSqlValueMethod() {
        try {
            assertEquals("Class of field with Id annotation should be Long", Long.class, EntityUtils.getSqlValue(new Department()).getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
