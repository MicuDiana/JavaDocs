package ro.teamnet.zth.appl;

import org.junit.Test;
import ro.teamnet.zth.appl.dao.LocationDao;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Employee;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.domain.Location;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by user on 11.07.2016.
 */
public class LocationDaoTest {

    LocationDao locationDao = new LocationDao();

    @Test
    public void testFindAllMethod() {

        assertEquals("List size should be x", 26, locationDao.findAll().size());
    }

    @Test
    public void testFindByIdMethod() {
        Location expected = new Location();
        expected.setId(1500);
        expected.setStreetAddress("2011 Interiors Blvd");
        expected.setStateProvince("California");
        expected.setCity("South San Francisco");
        expected.setPostalCode("99236");

        assertEquals("test", expected, locationDao.findById((long) 1500));
    }

    @Test
    public void testGetNextValMethod() {

        assertEquals("test",  3206 , locationDao.getNextIdVal().longValue());
    }

    @Test
    public void testInsertMethod() {

        Location toBeInserted = new Location();
        toBeInserted.setCity("'Kek'");
        toBeInserted.setPostalCode("'kek'");
        toBeInserted.setStateProvince("'kek'");
        toBeInserted.setStreetAddress("'kek'");

        assertNotNull(locationDao.insert(toBeInserted));
    }

    @Test
    public void testFindByParamsMethod() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("location_id", 1200);

        assertEquals("test", 1, locationDao.findByParams(params).size());
    }
}