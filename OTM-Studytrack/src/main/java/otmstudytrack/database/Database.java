package otmstudytrack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Connection conn;

    public Database(String databaseURI) throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + databaseURI);
        createTables();
    }

    private void createTables() throws SQLException {
        PreparedStatement createCourses = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Course (id integer PRIMARY KEY, name varchar(200))");
        createCourses.execute();
        createCourses.close();

        PreparedStatement createTasks = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "
                + "TaskType (id integer PRIMARY KEY, name varchar(200), course_id integer,"
                + " FOREIGN KEY (course_id) REFERENCES Course(id))");
        createTasks.execute();
        createTasks.close();

        PreparedStatement createEntries = conn.prepareStatement("CREATE TABLE IF NOT EXISTS"
                + " TaskEntry (id integer PRIMARY KEY, date integer, timeSpent integer,"
                + " courseWeek integer, taskType_id integer, "
                + "FOREIGN KEY (taskType_id) REFERENCES TaskType(id))");

    }

    public Connection getConn() throws SQLException {
        return conn;
    }

    public void deleteAllData() throws SQLException {
        PreparedStatement getTables = conn.prepareStatement("SELECT name FROM sqlite_master WHERE type='table'");
        ResultSet tablesRs = getTables.executeQuery();

        List<String> tables = new ArrayList<>();

        while (tablesRs.next()) {
            tables.add(tablesRs.getString("name"));
        }

        for (String table : tables) {
            PreparedStatement dropTable = conn.prepareStatement("DROP TABLE IF EXISTS " + table);
            dropTable.execute();
            dropTable.close();
        }
        
        tablesRs.close();

        createTables();
    }

}
