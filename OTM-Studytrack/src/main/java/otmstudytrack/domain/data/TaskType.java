package otmstudytrack.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class whose instances represent a type of recurring task which makes up
 * part of the coursework of a course, for example "programming exercises".
 */
public class TaskType {

    private String name;
    private Course belongsToCourse;
    private List<TaskEntry> entries;

    /**
     * Constructs a task type with the provided name related to the provided
     * course.
     *
     * @param name the name of the task type
     * @param course the course the TaskType relates to
     */
    public TaskType(String name, Course course) {
        this.name = name;
        this.entries = new ArrayList<>();
        this.belongsToCourse = course;
    }

    /**
     * Adds an entry (a TaskEntry object) to this TaskType.
     *
     * @param entry the entry to be added
     */
    public void addEntry(TaskEntry entry) {
        if (!entries.contains(entry)) {
            this.entries.add(entry);
        }
    }

    /**
     * Adds the contents of a List containing entries (TaskEntry objects) to
     * this TaskType.
     *
     * @param entries the entries to be added
     */
    public void addEntries(List<TaskEntry> entries) {
        this.entries.addAll(entries);
    }

    /**
     * Returns the name of this TaskType.
     *
     * @return the name of this TaskType
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a List containing the TaskEntries of this TaskType.
     *
     * @return the TaskEntries of the TaskType
     */
    public List<TaskEntry> getEntries() {
        return entries;
    }

    /**
     * Returns the Course which this TaskType relates to.
     *
     * @return the course of the task type
     */
    public Course getBelongsToCourse() {
        return belongsToCourse;
    }

    /**
     * Returns the hash code value for this TaskType. The hash value of two
     * TaskTypes objects will be equal if their name and belongsToCourse fields
     * are equal.
     *
     * @return the hash value for this TaskType
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.belongsToCourse);
        return hash;
    }

    /**
     * Compares the provided object with this TaskType for equality. Two
     * TaskType objects are considered equal when their name and belongsToCourse
     * fields are equal.
     *
     * @param obj the object to be compared to this TaskType
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
        final TaskType other = (TaskType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.belongsToCourse, other.belongsToCourse)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this TaskType, including its name and
     * the course it relates to.
     *
     * @return a string representation of the TaskType
     */
    @Override
    public String toString() {
        return "TaskType{" + "name=" + name + ", belongsToCourse=" + belongsToCourse + '}';
    }

}
