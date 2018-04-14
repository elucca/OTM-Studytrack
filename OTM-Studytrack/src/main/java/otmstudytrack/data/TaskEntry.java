package otmstudytrack.data;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class TaskEntry {
    
    private final Date date;
    private final Duration timeSpent;
    private final int courseWeek;
    private final TaskType taskType;
    
    public TaskEntry(Date date, int courseWeek, TaskType taskType, Duration timeSpent) {
        this.date = date;
        this.courseWeek = courseWeek;
        this.taskType = taskType;
        this.timeSpent = timeSpent;
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskEntry other = (TaskEntry) obj;
        if (this.courseWeek != other.courseWeek) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.timeSpent, other.timeSpent)) {
            return false;
        }
        if (!Objects.equals(this.taskType, other.taskType)) {
            return false;
        }
        return true;
    }
    
}
