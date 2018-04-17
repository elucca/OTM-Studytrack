package otmstudytrack.main;

import otmstudytrack.database.SqlTaskTypeDao;
import otmstudytrack.database.SqlCourseDao;
import otmstudytrack.database.SqlTaskEntryDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.UI.TextUI;
import otmstudytrack.database.Database;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Initializes the program

        //Init db
        Database db = new Database("db/studytrack.db"); //URI should later come from config file
        SqlTaskEntryDao entryDao = new SqlTaskEntryDao(db);
        SqlTaskTypeDao taskDao = new SqlTaskTypeDao(db, entryDao);
        SqlCourseDao courseDao = new SqlCourseDao(db, taskDao);

        db.deleteAllData();

        //Init logic
        StudytrackService service = new StudytrackService(courseDao, taskDao, entryDao);

        //Init UI
        Scanner reader = new Scanner(System.in);
        TextUI textUI = new TextUI(reader, service);
        textUI.start();
    }

}
