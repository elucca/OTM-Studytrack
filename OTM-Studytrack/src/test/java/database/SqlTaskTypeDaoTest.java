package database;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.database.*;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

public class SqlTaskTypeDaoTest {

    Database db;
    SqlTaskTypeDao taskDao;
    SqlTaskEntryDao entryDao;

    @Before
    public void setUp() throws SQLException {
        //Creates db for test
        db = new Database("src/test/java/db/testdb.db");
        entryDao = new SqlTaskEntryDao(db);
        taskDao = new SqlTaskTypeDao(db, entryDao);
    }

    @After
    public void tearDown() throws SQLException {
        //Clears database after test
        db.deleteAllData();
        db.closeConnection();
    }

    @Test
    public void taskTypeAddedAndFoundCorrectly() throws SQLException {
        //We assume the course exists and has a courseId of 1
        TaskType task = new TaskType("Exercises", new Course("OTM"));
        taskDao.addTaskType(task, 1);

        TaskType found = taskDao.findTaskType("Exercises", new Course("OTM"), 1);
        assertEquals(task, found);
    }

    @Test
    public void multipleTaskTypesAddedCorrectly() throws SQLException {
        TaskType toAdd1 = new TaskType("TMC", new Course("Tira"));
        TaskType toAdd2 = new TaskType("Laskarit", new Course("Tira"));
        List<TaskType> tasks = new ArrayList<>();
        tasks.add(toAdd1);
        tasks.add(toAdd2);

        taskDao.addTaskTypes(tasks, 1);
        TaskType found1 = taskDao.findTaskType("TMC", new Course("Tira"), 1);
        TaskType found2 = taskDao.findTaskType("Laskarit", new Course("Tira"), 1);
        List<TaskType> foundTasks = new ArrayList<>();
        foundTasks.add(found1);
        foundTasks.add(found2);

        assertTrue(foundTasks.containsAll(tasks));
    }

    @Test
    public void taskTypesOfCourseFoundCorrectly() throws SQLException {
        TaskType toAdd1 = new TaskType("TMC", new Course("Tira"));
        TaskType toAdd2 = new TaskType("Laskarit", new Course("Tira"));
        taskDao.addTaskType(toAdd1, 1);
        taskDao.addTaskType(toAdd2, 1);
        List<TaskType> added = new ArrayList<>();
        added.add(toAdd1);
        added.add(toAdd2);

        assertTrue(added.containsAll(taskDao.findTaskTypesOfACourse(new Course("Tira"), 1)));
    }

    @Test
    public void taskTypeIdFoundCorrectly() throws SQLException {
        //Sqlite will make the first one have an id of 1
        TaskType toAdd1 = new TaskType("TMC", new Course("Tira"));
        taskDao.addTaskType(toAdd1, 1);
        assertEquals(1, taskDao.findTaskTypeId(toAdd1));
    }

    @Test
    public void removeTaskTypeWorks() throws SQLException {
        TaskType toAdd1 = new TaskType("TMC", new Course("Tira"));
        taskDao.addTaskType(toAdd1, 1);

        taskDao.removeTaskType(toAdd1, 1);
        assertEquals(null, taskDao.findTaskType("TMC", new Course("Tira"), 1));
    }

    @Test
    public void removingTaskTypesOfCourseWorks() throws SQLException {
        TaskType toAdd1 = new TaskType("TMC", new Course("Tira"));
        TaskType toAdd2 = new TaskType("Laskarit", new Course("Tira"));
        taskDao.addTaskType(toAdd1, 1);
        taskDao.addTaskType(toAdd2, 1);
        taskDao.removeAllTaskTypesOfCourse(1);
        
        assertEquals(new ArrayList<TaskType>(), taskDao.findTaskTypesOfACourse(new Course("Tira"), 1));
    }

}
