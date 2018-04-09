package otmstudytrack.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskType {

    private String name;
    private List<TaskEntry> entries;
    private Course belongsToCourse;

    public TaskType(String name, Course course) {
        this.name = name;
        this.entries = new ArrayList<>();
        this.belongsToCourse = course;
    }

    public void addEntry(TaskEntry entry) {
        if (!entries.contains(entry)) {
            this.entries.add(entry);
        }
    }

    public String getName() {
        return name;
    }

    public List<TaskEntry> getEntries() {
        return entries;
    }

    public Course getBelongsToCourse() {
        return belongsToCourse;
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
        final TaskType other = (TaskType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.belongsToCourse, other.belongsToCourse)) {
            return false;
        }
        return true;
    }
    
    

}
