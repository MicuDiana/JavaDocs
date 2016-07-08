package ro.teamnet.zth.api.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 08.07.2016.
 */
public class DBManagerTest {

    @Test
    public void testCheckConnectionMethod() {
        assertEquals("Result should be 1", 1, DBManager.checkConnection(DBManager.getConnection()));
    }
}
