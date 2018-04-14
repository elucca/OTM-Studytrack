package otmstudytrack.data.dao;

import java.util.List;
import otmstudytrack.data.Course;
import otmstudytrack.data.TaskType;

public class SqlTaskTypeDao implements TaskTypeDao {

    private Database db;

    public SqlTaskTypeDao(Database db) {
        this.db = db;
    }

    @Override
    public void addTaskType(TaskType taskType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTaskTypes(List<TaskType> taskTypes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskType findTaskType(TaskType taskType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskType> findTasksOfAType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskType> findTaskTypesOfACourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeTaskType(TaskType taskType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeAllTaskTypesOfCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
