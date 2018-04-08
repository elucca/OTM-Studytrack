package otmstudytrack.dao;

import java.util.List;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public interface TaskEntryDao {
    
    public void addTaskEntry(TaskEntry taskEntry);
    
    public List<TaskEntry> findEntriesOfAType(TaskType taskType);
    
    public List<TaskEntry> findEntriesOfATypeFromCourseWeek(TaskType taskType, int courseWeek);
    
}
