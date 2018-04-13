package otmstudytrack.data.dao;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class FakeTaskEntryDao implements TaskEntryDao {

    private List<TaskEntry> taskEntries;

    public FakeTaskEntryDao() {
        this.taskEntries = new ArrayList<>();
    }

    @Override
    public void addTaskEntry(TaskEntry taskEntry) {
        taskEntries.add(taskEntry);
    }

    @Override
    public TaskEntry findTaskEntry(TaskType taskType, int courseWeek) {
        for (TaskEntry taskEntry : taskEntries) {
            if (taskEntry.getTaskType().equals(taskType)
                    && taskEntry.getCourseWeek() == courseWeek) {
                return taskEntry;
            }
        }
        return null;
    }

    @Override
    public List<TaskEntry> findEntriesOfAType(TaskType taskType) {
        List<TaskEntry> found = new ArrayList<>();

        for (TaskEntry taskEntry : taskEntries) {
            if (taskEntry.getTaskType().getName().equals(taskType.getName())) {
                found.add(taskEntry);
            }
        }

        return found;
    }

    @Override
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

}
