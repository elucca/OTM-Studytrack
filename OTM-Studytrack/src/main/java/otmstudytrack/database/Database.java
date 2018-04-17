package otmstudytrack.database;

import java.sql.*;

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

}
