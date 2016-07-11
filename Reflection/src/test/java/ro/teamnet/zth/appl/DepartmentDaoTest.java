package ro.teamnet.zth.appl;

import org.junit.Test;
import ro.teamnet.zth.appl.dao.DepartmentDao;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Job;
import ro.teamnet.zth.appl.domain.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDaoTest {

    DepartmentDao departmentDao = new DepartmentDao();

    @Test
    public void testFindByIdMethod() {
        Department expected = new Department();
        expected.setDepartmentName("Marketing");
        expected.setId((long) 20);
        expected.setLocation(1800);

        assertEquals("test", expected, departmentDao.findById((long) 20));
    }

    @Test
    public void testFindAllMethod() {
        assertEquals("List size should be 27", 27, departmentDao.findAll().size());
    }

    @Test
    public void testInsertMethod() {

        Department toBeInserted = new Department();
        toBeInserted.setDepartmentName("'LOL'");
        toBeInserted.setLocation(1100);

        assertNotNull(departmentDao.insert(toBeInserted));
    }

    @Test
    public void testDeleteMethod() {
        Department toBeDeleted = new Department();
        toBeDeleted.setId((long) 271);

        departmentDao.delete(toBeDeleted);
    }

    @Test
    public void testUpdateMethod() {
        Department toBeUpdated = new Department();
        toBeUpdated.setId((long) 270);
        toBeUpdated.setDepartmentName("'UPDATED  '");
        toBeUpdated.setLocation(1100);
        departmentDao.update(toBeUpdated);

    }




}
