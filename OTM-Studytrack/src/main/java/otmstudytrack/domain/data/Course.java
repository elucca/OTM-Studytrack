package otmstudytrack.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class whose instances represent a particular course.
 */
public class Course {

    private String name;
    private String subject;
    private boolean active;

    private List<TaskType> taskTypes;

    /**
     * Constructs a course with the given name and subject. It will be set as
     * active on instantiation.
     *
     * @param name the name of the course
     * @param subject the subject of the course (for instance, mathematics)
     */
    public Course(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.active = true;
        this.taskTypes = new ArrayList<>();
    }

    /**
     * Adds a TaskType to this course, representing a type of regular task that
     * is part of the course.
     *
     * @param taskType the TaskType to be added
     */
    public void addTaskType(TaskType taskType) {
        this.taskTypes.add(taskType);
    }

    /**
     * Adds all TaskTypes contained in the provided list to the course, each
     * representing a type of regular task that is part of the course.
     *
     * @param taskTypes a List containing the TaskTypes to be added
     */
    public void addTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes.addAll(taskTypes);
    }

    /**
     * Returns the name of this course.
     *
     * @return the name field of the Course
     */
    public String getName() {
        return name;
    }

    /**
     * Returns all task types of the course.
     *
     * @return an ArrayList containing all TaskTypes related to the course
     */
    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    /**
     * Returns the subject of the course.
     *
     * @return the subject of the course
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Returns whether the course is active or inactive.
     *
     * @return true if the course is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the activity status of the course.
     *
     * @param active the value specifying whether the course is to be set to
     * active (true) or inactive (false)
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the hash code value for this Course. The hash code of two Course
     * objects will be equal if their name and subject fields are equal.
     *
     * @return the hash value for this Course
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.subject);
        return hash;
    }

    /**
     * Compares the provided object with this Course for equality. Two Course
     * objects are considered equal when their name and subject fields are
     * equal.
     *
     * @param obj the object to be compared to this Course
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this course which includes the name
     * and subject fields.
     *
     * @return the string representation of the Course
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Subject: " + subject;
    }

}
