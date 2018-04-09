package otmstudytrack.dao;

import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class FakeTaskTypeDao implements TaskTypeDao {

    private List<TaskType> taskTypes;

    public FakeTaskTypeDao() {
        this.taskTypes = new ArrayList<>();
    }

    @Override
    public void addTaskType(TaskType taskType) {
        taskTypes.add(taskType);
    }

    @Override
    public void addTaskType(List<TaskType> taskTypes) {
        taskTypes.addAll(taskTypes);
    }

    @Override
    public TaskType findTaskType(TaskType taskType) {
        for (TaskType task : taskTypes) {
            if (task.equals(taskType)) {
                return task;
            }
        }
        
        return null;
    }

    @Override
    public List<TaskType> findTasksOfAType(String type) {
        List<TaskType> found = new ArrayList<>();

        for (TaskType taskType : taskTypes) {
            if (taskType.getName().equals(type)) {
                found.add(taskType);
            }
        }

        return found;
    }

    @Override
    public List<TaskType> findTaskTypesOfACourse(Course course) {
        List<TaskType> found = new ArrayList<>();

        for (TaskType taskType : taskTypes) {
            if (taskType.getBelongsToCourse().equals(course)) {
                found.add(taskType);
            }
        }

        return found;
    }

}
