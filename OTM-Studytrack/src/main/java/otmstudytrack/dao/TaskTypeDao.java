package otmstudytrack.dao;

import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public interface TaskTypeDao {
    
    public void addTaskType(TaskType taskType);
    
    public void addTaskType(List<TaskType> taskTypes);
    
    public List<TaskType> findTasksOfAType(String type);
    
    public List<TaskType> findTaskTypesOfACourse(Course course);

}
