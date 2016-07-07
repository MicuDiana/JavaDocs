package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.appl.domain.Department;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtilsTest {

    @Test
    public void testGetTableNameMethod() {
        String tableName = EntityUtils.getTableName(Department.class);
        assertEquals("Table name should be departments!", "DEPARTMENTS", tableName);
    }

    @Test

    public void testgetColumns() {
        ArrayList<ColumnInfo> columnInfos = EntityUtils.getColumns(Department.class);
        assertEquals("Number should be:", 3, columnInfos.size());

    }

    @Test
    public void testcastFromSqlType() {
        assertEquals("Wrong cast", Integer.class, EntityUtils.castFromSqlType(new BigDecimal(30), Integer.class.getClass()));
    }

    @Test
    public void testgetFieldsByAnnotations(){

        assertEquals("Something",1, EntityUtils.getFieldsByAnnotations(Department.class, Id.class).size() );

    }

    @Test
    public void testgetSqlValue() {
        assertEquals("Get Fields List size", 2, EntityUtils.getFieldsByAnnotations(Department.class, Column.class).size());

    }

}
