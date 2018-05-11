package otmstudytrack.main;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.ui.TextUI;
import otmstudytrack.database.*;

public class Main {

    public static void main(String[] args) {
        //Initializes the program

        //Init db
        //Database file URI should later come from config file
        File dbDir = new File("db");
        dbDir.mkdir();

        try {
            Database db = new Database("db" + File.separator + "studytrack.db");
            TaskEntryDao entryDao = new SqlTaskEntryDao(db);
            TaskTypeDao taskDao = new SqlTaskTypeDao(db, entryDao);
            CourseDao courseDao = new SqlCourseDao(db, taskDao);

            //Init logic
            StudytrackService service = new StudytrackService(db, courseDao, taskDao, entryDao);

            //Init UI
            Scanner reader = new Scanner(System.in);
            TextUI textUI = new TextUI(reader, service);
            textUI.start();
        } catch (SQLException sqlException) {
            System.out.println("Error initializing database. Exiting program.");
        }
    }

}
