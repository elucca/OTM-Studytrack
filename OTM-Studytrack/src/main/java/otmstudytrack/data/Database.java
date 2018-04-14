package otmstudytrack.data;

import java.sql.*;

public class Database {

    private String databaseURI;

    public Database(String databaseURI) {
        this.databaseURI = databaseURI;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseURI);
    }

    public String getDatabaseURI() {
        return databaseURI;
    }

    public void setDatabaseURI(String databaseURI) {
        this.databaseURI = databaseURI;
    }
    
}
