package main;

import java.util.Scanner;
import otmstudytrack.domain.StudytrackService;
import otmstudytrack.UI.TextUI;
import otmstudytrack.data.dao.CourseDao;
import otmstudytrack.data.dao.FakeCourseDao;
import otmstudytrack.data.dao.FakeTaskEntryDao;
import otmstudytrack.data.dao.FakeTaskTypeDao;
import otmstudytrack.data.dao.TaskEntryDao;
import otmstudytrack.data.dao.TaskTypeDao;

public class Main {
    
    public static void main(String[] args) {
        //Initializes the program
        
        //Init daos
        TaskEntryDao entryDao = new FakeTaskEntryDao();
        TaskTypeDao taskDao = new FakeTaskTypeDao(entryDao);
        CourseDao courseDao = new FakeCourseDao(taskDao);
        
        //Init logic
        StudytrackService service = new StudytrackService(courseDao, taskDao, entryDao);
        
        //Init UI
        Scanner reader = new Scanner(System.in);
        TextUI textUI = new TextUI(reader, service);
        textUI.start();
    }

}