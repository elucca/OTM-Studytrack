package otmstudytrack.domain.data;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

/**
 * A class whose instances represent a single, weekly entry on a given task type
 * related to a particular course.
 */
public class TaskEntry {
    
    private Date date;
    private Duration timeSpent;
    private final int courseWeek;
    private final TaskType taskType;
    
    /**
     * Constructs a TaskEntry object related to the provided TaskType and course
     * week. The information on which course the entry is related to is contained
     * in the TaskType object. TaskTypes are considered equal for each TaskType
     * for a given course week, such that in the data storage mechanisms only one
     * entry ever exists for a given combination of TaskType and course week.
     * 
     * @param date the date of the entry
     * @param courseWeek the week of the course the entry relates to, with the
     * minimum value being 1 (first week of a course), and the maximum being arbitrary
     * @param taskType the TaskType this entry relates to
     * @param timeSpent the time spent working on the task specified to the
     * accuracy of minutes using a DUration object.
     */
    public TaskEntry(Date date, int courseWeek, TaskType taskType, Duration timeSpent) {
        this.date = date;
        this.courseWeek = courseWeek;
        this.taskType = taskType;
        this.timeSpent = timeSpent;
    }

    /**
     * Returns the date the entry was entered into the program.
     * 
     * @return a Date object containing the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the time spent on the task the entry relates to, to the accuracy
     * of minutes.
     * 
     * @return the Duration object stored in the TaskEntry's timeSpent field,
     * representing the amount of time spent on the task
     */
    public Duration getTimeSpent() {
        return timeSpent;
    }

    /**
     * Returns the course week the entry relates to.
     * 
     * @return the course week, as an integer
     */
    public int getCourseWeek() {
        return courseWeek;
    }

    /**
     * Returns the task type the entry relates to.
     * @return The TaskType object stored in the TaskEntry's taskType field
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Returns the hash code value for this TaskEntry. The hash code of two TaskEntry
     * objects will be equal if their taskType and courseWeek fields are equal.
     * 
     * @return the hash value for this TaskEntry
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.courseWeek;
        hash = 23 * hash + Objects.hashCode(this.taskType);
        return hash;
    }

    /**
     * Compares the provided object with this TaskEntry for equality. Two TaskEntry
     * objects are considered equal when their taskType and courseWeek fields are
     * equal.
     * 
     * @param obj the object to be compared to this TaskEntry
     * @return true if the objects are equal
     */
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
        if (!Objects.equals(this.taskType, other.taskType)) {
            return false;
        }
        return true;
    }


    

    
}
