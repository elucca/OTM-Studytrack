package otmstudytrack.database;

import java.sql.SQLException;
import java.util.List;
import otmstudytrack.domain.data.TaskEntry;
import otmstudytrack.domain.data.TaskType;

public interface TaskEntryDao {

    public boolean addTaskEntry(TaskEntry taskEntry, int taskTypeId) throws SQLException;

    public TaskEntry findTaskEntry(TaskType taskType, int taskTypeId, int courseWeek) throws SQLException;

    public List<TaskEntry> findEntriesOfAType(TaskType taskType, int taskTypeId) throws SQLException;

    public void removeTaskEntry(TaskEntry taskEntry, int taskTypeId, int courseWeek) throws SQLException;

    public void removeAllEntriesOfTaskType(int taskTypeId) throws SQLException;
}
