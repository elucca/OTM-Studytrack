package otmstudytrack.data.dao;

import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public interface TaskTypeDao {

    public void addTaskType(TaskType taskType);

    public void addTaskTypes(List<TaskType> taskTypes);

    public TaskType findTaskType(TaskType taskType);

    public List<TaskType> findTasksOfAType(String type);

    public List<TaskType> findTaskTypesOfACourse(Course course);
    
    public boolean removeTaskType(TaskType taskType);
    
    public void removeAllTaskTypesOfCourse(Course course);

}
