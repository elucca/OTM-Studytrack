package database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.database.*;
import otmstudytrack.domain.data.Course;

public class SqlCourseDaoTest {

    Database db;
    SqlTaskEntryDao entryDao;
    SqlTaskTypeDao taskDao;
    SqlCourseDao courseDao;

    @Before
    public void setUp() throws SQLException, IOException {
        //Creates db for test
        db = new Database("src/test/java/db/testdb.db");
        entryDao = new SqlTaskEntryDao(db);
        taskDao = new SqlTaskTypeDao(db, entryDao);
        courseDao = new SqlCourseDao(db, taskDao);
    }

    @After
    public void tearDown() throws SQLException {
        //Clears database after test
        db.deleteAllData();
        db.closeConnection();
    }

    @Test
    public void courseIsAddedAndFoundCorrectlyBasedOnName() throws SQLException {
        //Needs to also test tasktypes!
        Course toAdd = new Course("OTM");
        courseDao.addCourse(toAdd);

        Course found = courseDao.findCourse("OTM");
        assertEquals(toAdd, found);
    }

    @Test
    public void allCoursesAddedAndFoundCorrectly() throws SQLException {
        Course toAdd1 = new Course("Todari");
        Course toAdd2 = new Course("Tikape");

        Set addedCourses = new HashSet();
        addedCourses.add(toAdd1);
        addedCourses.add(toAdd2);

        courseDao.addCourse(toAdd1);
        courseDao.addCourse(toAdd2);

        List<Course> found = courseDao.findAllCourses();
        Set<Course> foundSet = new HashSet<>();
        foundSet.addAll(found);

        assertEquals(addedCourses, foundSet);
    }

    @Test
    public void duplicateCoursesNotAdded() throws SQLException {
        Course toAdd1 = new Course("Tira");
        Course toAdd2 = new Course("Tira");
        courseDao.addCourse(toAdd1);
        courseDao.addCourse(toAdd2);

        List<Course> found = courseDao.findAllCourses();
        assertEquals(1, found.size());
    }

    @Test
    public void courseIsRemovedCorrectly() throws SQLException {
        Course toAdd1 = new Course("Tira");
        courseDao.addCourse(toAdd1);
        assertEquals(courseDao.findCourse("Tira"), toAdd1);
        courseDao.removeCourse(new Course("Tira"));
        assertNull(courseDao.findCourse("Tira"));
    }

    @Test
    public void findCourseIdReturnsMinusOneForNonexistentCourse() throws SQLException {
        assertEquals(-1, courseDao.findCourseID(new Course("Tira")));
    }

    @Test
    public void courseIdIsFoundCorrectly() throws SQLException {
        //We know the id of the first added course generated by sqlite3 is 1
        Course toAdd1 = new Course("IGP");
        courseDao.addCourse(toAdd1);
        assertEquals(1, courseDao.findCourseID(new Course("IGP")));
    }
}