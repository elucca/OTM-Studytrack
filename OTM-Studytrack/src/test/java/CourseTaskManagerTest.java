import domain.CourseTaskManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import otmstudytrack.data.dao.FakeCourseDao;
import otmstudytrack.data.dao.FakeTaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class CourseTaskManagerTest {
    
    CourseTaskManager courseTaskManager;
    
    @Before
    public void setUp() {
        this.courseTaskManager = new CourseTaskManager(new FakeCourseDao(),  new FakeTaskTypeDao());
    }
    
    @Test
    public void addedCourseIsFound() {
        courseTaskManager.addCourse("Tira");
        assertEquals("Tira", courseTaskManager.getCourse("Tira").getName());
        
    }
    
    @Test
    public void getCourseReturnsNullForNonexistentCourse() {
        Course course = courseTaskManager.getCourse("asdfsdf");
        assertEquals(null, course);
    }
    
    @Test
    public void addedTaskTypeIsFound() {
        String courseName = "Tira";
        String taskName = "TMC";
        courseTaskManager.addTaskType(taskName, courseName);
        
        TaskType foundTask = courseTaskManager.getTaskType(taskName, courseName);
        assertEquals(new TaskType(taskName, new Course(courseName)), foundTask);
    }
    
}