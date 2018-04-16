package otmstudytrack.database;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

@Deprecated
public class FakeTaskEntryDao{

    private List<TaskEntry> taskEntries;

    public FakeTaskEntryDao() {
        this.taskEntries = new ArrayList<>();
    }

    public void addTaskEntry(TaskEntry taskEntry) {
        taskEntries.add(taskEntry);
    }

    public TaskEntry findTaskEntry(TaskType taskType, int courseWeek) {
        for (TaskEntry taskEntry : taskEntries) {
            if (taskEntry.getTaskType().equals(taskType)
                    && taskEntry.getCourseWeek() == courseWeek) {
                return taskEntry;
            }
        }
        return null;
    }

    public List<TaskEntry> findEntriesOfAType(TaskType taskType) {
        List<TaskEntry> found = new ArrayList<>();

        for (TaskEntry taskEntry : taskEntries) {
            if (taskEntry.getTaskType().getName().equals(taskType.getName())) {
                found.add(taskEntry);
            }
        }

        return found;
    }

    public List<TaskEntry> findEntriesOfATypeFromCourseWeek(TaskType taskType, int courseWeek) {
        List<TaskEntry> found = new ArrayList<>();

        for (TaskEntry taskEntry : taskEntries) {
            if (taskEntry.getTaskType().getName().equals(taskType.getName())
                    && taskEntry.getTaskType().getBelongsToCourse().equals(courseWeek)) {
                found.add(taskEntry);
            }
        }

        return found;
    }

    public boolean removeTaskEntry(TaskEntry taskEntry) {
        boolean removed = false;
        for (TaskEntry entry : taskEntries) {
            if (entry.equals(taskEntry)) {
                taskEntries.remove(taskEntry);
                return true;
            }
        }
        
        return false;
    }
    
    public void removeAllEntriesOfTaskType(TaskType taskType) {
        for (TaskEntry found : taskEntries) {
            if (found.getTaskType().equals(taskType)) {
                taskEntries.remove(found);
            }
        }
    }

}
