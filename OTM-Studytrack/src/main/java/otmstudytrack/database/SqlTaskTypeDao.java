package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import otmstudytrack.domain.data.Course;
import otmstudytrack.domain.data.TaskType;

public class SqlTaskTypeDao {

    private Database db;
    private SqlTaskEntryDao entryDao;

    public SqlTaskTypeDao(Database db, SqlTaskEntryDao entryDao) {
        this.db = db;
        this.entryDao = entryDao;
    }

    public void addTaskType(TaskType taskType, int courseId) throws SQLException {
        PreparedStatement taskStmt = db.getConn().prepareStatement("INSERT INTO TaskType (name, course_id) VALUES (?, ?)");
        taskStmt.setString(1, taskType.getName());
        taskStmt.setInt(2, courseId);
        taskStmt.execute();
        taskStmt.close();
    }

    public void addTaskTypes(List<TaskType> taskTypes, int courseId) throws SQLException {
        for (TaskType taskType : taskTypes) {
            addTaskType(taskType, courseId);
        }
    }

    public TaskType findTaskType(String name, Course course, int courseId) throws SQLException {
        PreparedStatement taskStmt = db.getConn().prepareStatement("SELECT * FROM TaskType WHERE TaskType.name = ?"
                + "AND TaskType.course_id = ?");
        taskStmt.setString(1, name);
        taskStmt.setInt(2, courseId);
        ResultSet taskRs = taskStmt.executeQuery();
        taskStmt.close();

        if (taskRs.next()) {
            String foundName = taskRs.getString("name");
            TaskType foundTaskType = new TaskType(foundName, course);
            int foundId = taskRs.getInt("id");
            foundTaskType.addEntries(entryDao.findEntriesOfAType(foundTaskType, foundId));
            taskRs.close();
            return foundTaskType;
        }

        return null;
    }

    public List<TaskType> findTaskTypesOfACourse(Course course, int courseId) throws SQLException {
        PreparedStatement taskStmt = db.getConn().prepareStatement("SELECT * FROM TaskType WHERE TaskType.course_id = ?");
        taskStmt.setInt(1, courseId);
        ResultSet tasksRs = taskStmt.executeQuery();
        taskStmt.close();

        List<TaskType> foundTasks = new ArrayList<>();

        while (tasksRs.next()) {
            foundTasks.add(new TaskType(tasksRs.getString("name"), course));
        }
        tasksRs.close();

        return foundTasks;
    }
    
    public int findTaskTypeId(TaskType taskType) throws SQLException {
        PreparedStatement idStmt = db.getConn().prepareStatement("SELECT id FROM TaskType WHERE TaskType.name = ?");
        idStmt.setString(1, taskType.getName());
        ResultSet idRs = idStmt.executeQuery();
        
        if (idRs.next()) {
            idRs.close();
            return idRs.getInt("id");
        }
        idRs.close();
        
        return -1;
    }

    public void removeTaskType(TaskType taskType, int courseId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskType WHERE TaskType.name = ? AND TaskType.course_id = ?");
        removeStmt.setString(1, taskType.getName());
        removeStmt.setInt(2, courseId);
        removeStmt.close();
    }

    public void removeAllTaskTypesOfCourse(int courseId) throws SQLException {
        PreparedStatement removeStmt = db.getConn().prepareStatement("DELETE FROM TaskType WHERE TaskType.course_id = ?");
        removeStmt.setInt(1, courseId);
        removeStmt.close();
    }

}
