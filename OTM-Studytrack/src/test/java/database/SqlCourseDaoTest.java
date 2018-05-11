package database;

import java.io.File;
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
        File dbDir = new File("src" + File.separator + "test" + File.separator + "java" + File.separator + "db");
        dbDir.mkdir();
        db = new Database("src" + File.separator + "test" + File.separator + "java" + File.separator + "db" + File.separator + "test.db");
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
        Course toAdd = new Course("OTM", "CS");
        courseDao.addCourse(toAdd);

        Course found = courseDao.findCourseByName("OTM");
        assertEquals(toAdd, found);
    }

    @Test
    public void allCoursesAddedAndFoundCorrectly() throws SQLException {
        Course toAdd1 = new Course("Todari", "Math");
        Course toAdd2 = new Course("Tikape", "CS");

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
        Course toAdd1 = new Course("Tira", "CS");
        Course toAdd2 = new Course("Tira", "CS");
        Course toAdd3 = new Course("Tira", "Wrong");
        courseDao.addCourse(toAdd1);
        courseDao.addCourse(toAdd2);
        courseDao.addCourse(toAdd3);

        List<Course> found = courseDao.findAllCourses();
        assertEquals(1, found.size());
    }

    @Test
    public void courseIsRemovedCorrectly() throws SQLException {
        Course toAdd1 = new Course("Tira", "CS");
        courseDao.addCourse(toAdd1);
        assertEquals(courseDao.findCourseByName("Tira"), toAdd1);
        courseDao.removeCourse(toAdd1);
        assertNull(courseDao.findCourseByName("Tira"));
    }

    @Test
    public void findCourseIdCorrectForNonexistentCourse() throws SQLException {
        assertEquals(-1, courseDao.findCourseID("Tira"));
    }

    @Test
    public void courseIdIsFoundCorrectly() throws SQLException {
        //We know the id of the first added course generated by sqlite3 is 1
        Course toAdd1 = new Course("IGP", "CS");
        courseDao.addCourse(toAdd1);
        assertEquals(1, courseDao.findCourseID(toAdd1.getName()));
    }

    @Test
    public void courseActivitySetCorrectly() throws SQLException {
        Course toAdd1 = new Course("IGP", "CS");
        courseDao.addCourse(toAdd1);
        assertTrue(courseDao.findCourseByName("IGP").isActive());
        courseDao.updateCourseActive(toAdd1, 0);
        assertFalse(courseDao.findCourseByName("IGP").isActive());
        courseDao.updateCourseActive(toAdd1, 1);
        assertTrue(courseDao.findCourseByName("IGP").isActive());
    }

    @Test
    public void activeCoursesFound() throws SQLException {
        Course toAdd1 = new Course("Tira", "CS");
        Course toAdd2 = new Course("OTM", "CS");
        Course toAdd3 = new Course("Todari", "Math");
        courseDao.addCourse(toAdd1);
        courseDao.addCourse(toAdd2);
        courseDao.addCourse(toAdd3);
        courseDao.updateCourseActive(toAdd3, 0);

        Set<Course> correct = new HashSet<>();
        correct.add(toAdd1);
        correct.add(toAdd2);

        Set<Course> found = new HashSet<>();
        found.addAll(courseDao.findAllCoursesByActive(1));

        assertEquals(found, correct);
    }

    @Test
    public void inactiveCoursesFound() throws SQLException {
        Course toAdd1 = new Course("Tira", "CS");
        Course toAdd2 = new Course("OTM", "CS");
        Course toAdd3 = new Course("Todari", "Math");
        courseDao.addCourse(toAdd1);
        courseDao.addCourse(toAdd2);
        courseDao.addCourse(toAdd3);
        courseDao.updateCourseActive(toAdd2, 0);
        courseDao.updateCourseActive(toAdd3, 0);

        Set<Course> correct = new HashSet<>();
        correct.add(toAdd2);
        correct.add(toAdd3);

        Set<Course> found = new HashSet<>();
        found.addAll(courseDao.findAllCoursesByActive(0));

        assertEquals(found, correct);
    }
}
