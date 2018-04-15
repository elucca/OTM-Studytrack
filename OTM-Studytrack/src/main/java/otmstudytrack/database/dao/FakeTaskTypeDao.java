package otmstudytrack.database.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class FakeTaskTypeDao implements TaskTypeDao {

    private List<TaskType> taskTypes;
    private TaskEntryDao entryDao;

    public FakeTaskTypeDao(TaskEntryDao entryDao) {
        this.entryDao = entryDao;
        this.taskTypes = new ArrayList<>();
    }

    @Override
    public void addTaskType(TaskType taskType) {
        taskTypes.add(taskType);
    }

    @Override
    public void addTaskTypes(List<TaskType> taskTypes) {
        taskTypes.addAll(taskTypes);
    }

    @Override
    public TaskType findTaskType(TaskType taskType) throws SQLException {
        TaskType foundTaskType = null;

        for (TaskType task : taskTypes) {
            if (task.equals(taskType)) {
                foundTaskType = task;
            }
        }

        if (foundTaskType == null) {
            return null;
        }

        foundTaskType.addEntries(entryDao.findEntriesOfAType(foundTaskType));
        return foundTaskType;
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

    @Override
    public boolean removeTaskType(TaskType taskType) throws SQLException {
        for (TaskType found : taskTypes) {
            if (found.equals(taskType)) {
                entryDao.removeAllEntriesOfTaskType(found);
                taskTypes.remove(found);
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void removeAllTaskTypesOfCourse(Course course) throws SQLException {
        for (TaskType found : taskTypes) {
            if (found.getBelongsToCourse().equals(course)) {
                taskTypes.remove(found);
                entryDao.removeAllEntriesOfTaskType(found);
            }
        }
    }

}
