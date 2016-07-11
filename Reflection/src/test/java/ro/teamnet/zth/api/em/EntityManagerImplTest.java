package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.domain.Location;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by user on 08.07.2016.
 */
public class EntityManagerImplTest {

    EntityManagerImpl manager = new EntityManagerImpl();

    @Test
    public void testFindAllMethod() {
        assertEquals("List size should be 19", 19, manager.findAll(Job.class).size());
    }

    @Test
    public void testFindByIdMethod() {
        Department expected = new Department();
        expected.setDepartmentName("Marketing");
        expected.setId((long) 20);
        expected.setLocation(1800);

        assertEquals("test", expected, manager.findById(Department.class, (long) 200));
    }

    @Test
    public void testGetNextValMethod() {

        assertEquals("test", (long) 207, manager.getNextIdVal("EMPLOYEES", "employee_id").longValue());
    }

    @Test
    public void testInsertMethod() {

        Location toBeInserted = new Location();
        toBeInserted.setCity("'Kek'");
        toBeInserted.setPostalCode("'kek'");
        toBeInserted.setStateProvince("'kek'");
        toBeInserted.setStreetAddress("'kek'");

        assertNotNull(manager.insert(toBeInserted));
    }

    @Test
    public void testDeleteMethod() {
        Location toBeInserted = new Location();
        toBeInserted.setId((long) 3201);

        manager.delete(toBeInserted);
    }

    @Test
    public void testUpdateMethod() {
        Location toBeUpdated = new Location();
        toBeUpdated.setId(3202);
        toBeUpdated.setCity("'Lol'");
        toBeUpdated.setPostalCode("'Lol'");
        toBeUpdated.setStateProvince("'Lol'");
        toBeUpdated.setStreetAddress("'Lol'");
        manager.update(toBeUpdated);

    }

    @Test
    public void testFindByParamsMethod() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("phone_number", "'011.44.1344.129268'");
        params.put("salary", (long) 10000);

        assertEquals("test", 1, manager.findByParams(Employee.class, params).size());
    }
}
