package otmstudytrack.domain.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {

    private String name;
    private List<TaskType> taskTypes;

    public Course(String name) {
        this.name = name;
        this.taskTypes = new ArrayList<>();
    }

    public void addTaskType(TaskType taskType) {
        this.taskTypes.add(taskType);
    }

    public void addTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes.addAll(taskTypes);
    }

    public String getName() {
        return name;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return name;
    }

}
