package otmstudytrack.data;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class TaskEntry {
    
    private Date date;
    private Duration timeSpent;
    private int courseWeek;
    private TaskType taskType;
    
    public TaskEntry(Date date, int courseWeek, TaskType taskType, Duration timeSpent) {
        this.timeSpent = Duration.ZERO;
        this.courseWeek = courseWeek;
        this.taskType = taskType;
        this.timeSpent = timeSpent;
    }
    
    public void addTimeSpent(Duration timeSpent) {
        this.timeSpent = this.timeSpent.plus(timeSpent);
    }

    public Date getDate() {
        return date;
    }

    public Duration getTimeSpent() {
        return timeSpent;
    }

    public int getCourseWeek() {
        return courseWeek;
    }

    public TaskType getTaskType() {
        return taskType;
    }
    
}
