package database;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.database.*;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

public class SqlTaskEntryDaoTest {

    Database db;
    SqlTaskEntryDao entryDao;

    @Before
    public void setUp() throws SQLException {
        //Creates db for test
        File dbDir = new File("src" + File.separator + "test" + File.separator + "java" + File.separator + "db");
        dbDir.mkdir();
        db = new Database("src" + File.separator + "test" + File.separator + "java" + File.separator + "db" + File.separator + "test.db");
        entryDao = new SqlTaskEntryDao(db);
    }

    @After
    public void tearDown() throws SQLException {
        //Clears database after test
        db.deleteAllData();
        db.closeConnection();
    }

    @Test
    public void entryAddedAndFoundCorrectly() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskEntry toAdd = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        entryDao.addTaskEntry(toAdd, 1);

        assertEquals(toAdd, entryDao.findTaskEntry(taskOfEntry, 1, 2));
    }
    
    @Test
    public void nonexistentEntryReturnsNull() throws SQLException {
        assertNull(entryDao.findTaskEntry(new TaskType("Doesn't", new Course("exist", ",fool")), 2, 3));
    }
    
    @Test
    public void duplicateEntryNotAdded() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskEntry toAdd = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        entryDao.addTaskEntry(toAdd, 1);
        
        assertFalse(entryDao.addTaskEntry(toAdd, 1));
    }
    
    @Test
    public void timeAppendedToExistingEntry() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskEntry toAdd = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ofSeconds(20));
        entryDao.addTaskEntry(toAdd, 1);
        entryDao.addTaskEntry(toAdd, 1);
        
        assertEquals(40, entryDao.findTaskEntry(taskOfEntry, 1, 2).getTimeSpent().getSeconds(), 1);
    }

    @Test
    public void entriesOfTaskTypeFoundCorrectly() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskType wrongTask = new TaskType("Laskarit", new Course("Tira", "CS"));
        TaskEntry toAdd1 = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        TaskEntry toAdd2 = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        TaskEntry toAdd3 = new TaskEntry(new Date(), 2, wrongTask, Duration.ZERO);
        entryDao.addTaskEntry(toAdd1, 1);
        entryDao.addTaskEntry(toAdd2, 1);
        entryDao.addTaskEntry(toAdd3, 1);
        
        Set<TaskEntry> correctEntries = new HashSet<>();
        correctEntries.add(toAdd1);
        correctEntries.add(toAdd2);

        Set<TaskEntry> foundEntries = new HashSet<>(entryDao.findEntriesOfAType(taskOfEntry, 1));

        assertEquals(correctEntries, foundEntries);
    }

    @Test
    public void removingEntryWorks() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskEntry toAdd = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        entryDao.addTaskEntry(toAdd, 1);
        entryDao.removeTaskEntry(toAdd, 1, 1);

        assertNull(entryDao.findTaskEntry(taskOfEntry, 1, 2));
    }

    @Test
    public void removingAllEntriesOfTaskWorks() throws SQLException {
        TaskType taskOfEntry = new TaskType("TMC", new Course("Tira", "CS"));
        TaskEntry toAdd1 = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        TaskEntry toAdd2 = new TaskEntry(new Date(), 2, taskOfEntry, Duration.ZERO);
        entryDao.addTaskEntry(toAdd1, 1);
        entryDao.addTaskEntry(toAdd2, 1);

        entryDao.removeAllEntriesOfTaskType(1);
        assertEquals(new ArrayList<TaskEntry>(), entryDao.findEntriesOfAType(taskOfEntry, 1));
    }

}
