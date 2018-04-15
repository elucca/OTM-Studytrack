package otmstudytrack.database.dao;

import java.sql.*;
import java.util.List;
import otmstudytrack.data.TaskEntry;
import otmstudytrack.data.TaskType;
import otmstudytrack.database.Database;

public class SqlTaskEntryDao implements TaskEntryDao {

    private Database db;

    public SqlTaskEntryDao(Database db) {
        this.db = db;
    }

    @Override
    public void addTaskEntry(TaskEntry taskEntry) throws SQLException {
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
