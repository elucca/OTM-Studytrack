package otmstudytrack.database;

import java.sql.SQLException;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

public interface TaskTypeDao {

    public void addTaskType(TaskType taskType, int courseId) throws SQLException;

    public void addTaskTypes(List<TaskType> taskTypes, int courseId) throws SQLException;

    public TaskType findTaskType(String name, Course course, int courseId) throws SQLException;

    public List<TaskType> findTaskTypesOfACourse(Course course, int courseId) throws SQLException;

    public int findTaskTypeId(TaskType taskType) throws SQLException;

    public void removeTaskType(TaskType taskType, int courseId) throws SQLException;

    public void removeAllTaskTypesOfCourse(int courseId) throws SQLException;
}
