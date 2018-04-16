package otmstudytrack.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

@Deprecated
public class FakeTaskTypeDao {

    private List<TaskType> taskTypes;
    private FakeTaskEntryDao entryDao;

    public FakeTaskTypeDao(FakeTaskEntryDao entryDao) {
        this.entryDao = entryDao;
        this.taskTypes = new ArrayList<>();
    }

    public void addTaskType(TaskType taskType) {
        taskTypes.add(taskType);
    }

    public void addTaskTypes(List<TaskType> taskTypes) {
        taskTypes.addAll(taskTypes);
    }

    public TaskType findTaskType(String name, Course course) throws SQLException {
        TaskType foundTaskType = null;

        for (TaskType task : taskTypes) {
            if (task.getName().equals(name)) {
                foundTaskType = task;
            }
        }

        if (foundTaskType == null) {
            return null;
        }

        foundTaskType.addEntries(entryDao.findEntriesOfAType(foundTaskType));
        return foundTaskType;
    }

    public List<TaskType> findTaskTypesOfACourse(Course course) {
        List<TaskType> found = new ArrayList<>();

        for (TaskType taskType : taskTypes) {
            if (taskType.getBelongsToCourse().equals(course)) {
                found.add(taskType);
            }
        }

        return found;
    }

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

    public void removeAllTaskTypesOfCourse(Course course) throws SQLException {
        for (TaskType found : taskTypes) {
            if (found.getBelongsToCourse().equals(course)) {
                taskTypes.remove(found);
                entryDao.removeAllEntriesOfTaskType(found);
            }
        }
    }

}
