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

}
