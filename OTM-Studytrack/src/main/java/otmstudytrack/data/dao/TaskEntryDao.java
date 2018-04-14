package otmstudytrack.data.dao;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public interface TaskEntryDao {
    
    public void addTaskEntry(TaskEntry taskEntry) throws SQLException;
    
    public TaskEntry findTaskEntry(TaskType taskType, int courseWeek) throws SQLException;
    
    public List<TaskEntry> findEntriesOfAType(TaskType taskType) throws SQLException;
    
    public List<TaskEntry> findEntriesOfATypeFromCourseWeek(TaskType taskType, int courseWeek) throws SQLException;
    
    public boolean removeTaskEntry(TaskEntry taskEntry) throws SQLException;
    
    public void removeAllEntriesOfTaskType(TaskType taskType) throws SQLException;
    
}
