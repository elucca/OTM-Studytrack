package domain;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.database.*;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.domain.data.*;

public class StudytrackServiceTest {

    StudytrackService service;
    Database db;

    @Before
    public void setUp() throws SQLException {
        //Creates db for test
        File dbDir = new File("src" + File.separator + "test" + File.separator + "java" + File.separator + "db");
        dbDir.mkdir();
        db = new Database("src" + File.separator + "test" + File.separator + "java" + File.separator + "db" + File.separator + "test.db");
        TaskEntryDao entryDao = new SqlTaskEntryDao(db);
        TaskTypeDao taskDao = new SqlTaskTypeDao(db, entryDao);
        CourseDao courseDao = new SqlCourseDao(db, taskDao);
        service = new StudytrackService(db, courseDao, taskDao, entryDao);
    }

    @After
    public void tearDown() throws SQLException {
        db.deleteAllData();
        db.closeConnection();
    }

    @Test
    public void courseAddedAndFound() throws SQLException {
        service.addCourse("OTM", "CS");
        assertEquals(new Course("OTM", "CS"), service.getCourse("OTM"));
    }

    @Test
    public void removeCourseWorks() throws SQLException {
        service.addCourse("OTM", "CS");
        service.removeCourse("OTM");
        assertNull(service.getCourse("OTM"));
    }

    @Test
    public void taskTypeAddedAndFound() throws SQLException {
        service.addCourse("OTM", "CS");
        TaskType toFind = new TaskType("Project", new Course("OTM", "CS"));
        service.addTaskType("Project", "OTM");
        assertEquals(toFind, service.getTaskType("Project", "OTM"));
    }

    @Test
    public void removeTaskTypeWorks() throws SQLException {
        service.addCourse("OTM", "CS");
        service.addTaskType("Project", "OTM");
        service.removeTaskType("OTM", "Project");
        assertNull(service.getTaskType("Project", "OTM"));
    }

    @Test
    public void taskEntryAddedAndFound() throws SQLException {
        service.addCourse("Tira", "CS");
        service.addTaskType("TMC", "Tira");
        service.addTaskEntry(1, "TMC", "Tira", 25, 0);
        assertEquals(new TaskEntry(new Date(), 1, new TaskType("TMC", new Course("Tira", "CS")), Duration.ZERO), service.getTaskEntry("Tira", "TMC", 1));
    }

    @Test
    public void taskEntryNotAddedIfTaskTypeDoesntExist() throws SQLException {
        service.addCourse("Tira", "CS");
        service.addTaskEntry(1, "TMC", "Tira", 25, 0);
        assertNull(service.getTaskEntry("Tira", "TMC", 1));
    }

    @Test
    public void removeTaskEntryWorks() throws SQLException {
        service.addCourse("OTM", "CS");
        service.addTaskType("Project", "OTM");
        service.removeTaskType("OTM", "Project");
        assertEquals(null, service.getTaskType("Project", "OTM"));
    }

    @Test
    public void coursesByActiveStatusFound() throws SQLException {
        service.addCourse("Tira", "CS");
        service.setCourseActive("Tira", false);
        service.addCourse("OTM", "CS");
        ArrayList correct = new ArrayList();
        correct.add(new Course("OTM", "CS"));
        assertEquals(correct, service.getCoursesByActive(true));
    }

    @Test
    public void setCourseInactiveWorks() throws SQLException {
        service.addCourse("Tira", "CS");
        service.setCourseActive("Tira", false);
        ArrayList correct = new ArrayList();
        correct.add(new Course("Tira", "CS"));
        assertEquals(correct, service.getCoursesByActive(false));
    }

    @Test
    public void setCourseActiveWorks() throws SQLException {
        service.addCourse("Tira", "CS");
        service.setCourseActive("Tira", false);
        service.setCourseActive("Tira", true);
        ArrayList correct = new ArrayList();
        correct.add(new Course("Tira", "CS"));
        assertEquals(correct, service.getCoursesByActive(true));
    }

    @Test
    public void timeSpentOnCourseWorks() throws SQLException {
        service.addCourse("Tira", "CS");
        service.addTaskType("TMC", "Tira");
        service.addTaskType("Moodle", "Tira");
        service.addTaskEntry(1, "TMC", "Tira", 1, 0);
        service.addTaskEntry(1, "TMC", "Tira", 1, 0);
        service.addTaskEntry(2, "Moodle", "Tira", 0, 60);
        service.addTaskEntry(3, "Moodle", "Tira", 0, 30);
        Duration timeSpent = Duration.ofSeconds(12600);
        
        assertEquals(timeSpent, service.getTimeSpentOnCourse("Tira"));
    }

    @Test
    public void taskTypesOfCourseFound() throws SQLException {
        service.addCourse("Tira", "CS");
        service.addTaskType("TMC", "Tira");
        service.addTaskType("Moodle", "Tira");
        service.addTaskType("Wrong", "Shouldn't find");
        HashSet<TaskType> correct = new HashSet();
        correct.add(new TaskType("TMC", new Course("Tira", "CS")));
        correct.add(new TaskType("Moodle", new Course("Tira", "CS")));
        
        List<TaskType> found = service.getTaskTypesOfCourse("Tira");
        HashSet<TaskType> foundSet = new HashSet<>();
        foundSet.addAll(found);
        assertEquals(correct, foundSet);
    }

    @Test
    public void removeAllDataWorks() throws SQLException {
        service.addCourse("Tira", "CS");
        service.addTaskType("TMC", "Tira");
        service.addTaskEntry(1, "TMC", "Tira", 25, 0);
        service.removeAllData();
        assertEquals(new ArrayList<Course>(), service.getCourses());
        assertEquals(new ArrayList<TaskType>(), service.getTaskTypesOfCourse("Tira"));
        assertEquals(null, service.getTaskEntry("Tira", "TMC", 1));
    }

    @Test
    public void taskTypeNotAddedIfCourseDoesntExist() throws SQLException {
        service.addTaskType("TMC", "Tira");
        assertEquals(null, service.getTaskType("TMC", "Tira"));
    }

}
