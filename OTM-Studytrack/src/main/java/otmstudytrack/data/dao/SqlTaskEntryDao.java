package otmstudytrack.data.dao;

import java.util.List;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;

public class SqlTaskEntryDao implements TaskEntryDao {

    @Override
    public void addTaskEntry(TaskEntry taskEntry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskEntry findTaskEntry(TaskType taskType, int courseWeek) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskEntry> findEntriesOfAType(TaskType taskType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskEntry> findEntriesOfATypeFromCourseWeek(TaskType taskType, int courseWeek) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeTaskEntry(TaskEntry taskEntry) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAllEntriesOfTaskType(TaskType taskType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}