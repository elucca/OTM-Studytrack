package logic;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import otmstudytrack.dao.TaskEntryDao;
import otmstudytrack.dao.TaskTypeDao;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class EntryManager {

    private TaskTypeDao taskTypeDao;
    private TaskEntryDao taskEntryDao;

    public EntryManager(TaskTypeDao taskTypeDao, TaskEntryDao taskEntryDao) {
        this.taskTypeDao = taskTypeDao;
        this.taskEntryDao = taskEntryDao;
    }

    public void addTaskEntry(Date date, int courseWeek, TaskType taskType) {
        taskEntryDao.addTaskEntry(new TaskEntry(date, courseWeek, taskType));
    }

    public TaskEntry getTaskEntry(TaskType taskType, int courseWeek) {
        return taskEntryDao.findTaskEntry(taskType, courseWeek);
    }

    public List<TaskEntry> getEntriesOfTaskType(String task, String course) {
        TaskType foundTask = taskTypeDao.findTaskType(task, course);
        List<TaskEntry> foundEntries = taskEntryDao.findEntriesOfAType(foundTask);
        return foundEntries;
    }
     
}
