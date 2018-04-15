package otmstudytrack.database.dao;

import java.sql.SQLException;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public interface TaskTypeDao {

    public void addTaskType(TaskType taskType) throws SQLException;

    public void addTaskTypes(List<TaskType> taskTypes) throws SQLException;

    public TaskType findTaskType(TaskType taskType) throws SQLException;

    public List<TaskType> findTasksOfAType(String type) throws SQLException;

    public List<TaskType> findTaskTypesOfACourse(Course course) throws SQLException;
    
    public boolean removeTaskType(TaskType taskType) throws SQLException;
    
    public void removeAllTaskTypesOfCourse(Course course) throws SQLException;

}
