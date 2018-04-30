package otmstudytrack.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import otmstudytrack.database.SqlTaskTypeDao;
import otmstudytrack.database.SqlCourseDao;
import otmstudytrack.database.SqlTaskEntryDao;
import java.sql.SQLException;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.ui.TextUI;
import otmstudytrack.database.Database;

public class Main {

    public static void main(String[] args) throws SQLException, UnsupportedEncodingException, URISyntaxException {
        //Initializes the program

        //Init db
        //Database file URI should later come from config file
        File dbDir = new File("db");
        dbDir.mkdir();

        Database db = new Database("db" + File.separator + "studytrack.db");
        SqlTaskEntryDao entryDao = new SqlTaskEntryDao(db);
        SqlTaskTypeDao taskDao = new SqlTaskTypeDao(db, entryDao);
        SqlCourseDao courseDao = new SqlCourseDao(db, taskDao);

        //Init logic
        StudytrackService service = new StudytrackService(db, courseDao, taskDao, entryDao);

        //Init UI
        Scanner reader = new Scanner(System.in);
        TextUI textUI = new TextUI(reader, service);
        textUI.start();
    }

}
