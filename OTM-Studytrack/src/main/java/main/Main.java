package main;

import java.util.Scanner;
import logic.CourseTaskManager;
import logic.EntryManager;
import logic.TaskService;
import otmstudytrack.UI.TextUI;
import otmstudytrack.dao.CourseDao;
import otmstudytrack.dao.FakeCourseDao;
import otmstudytrack.dao.FakeTaskEntryDao;
import otmstudytrack.dao.FakeTaskTypeDao;
import otmstudytrack.dao.TaskEntryDao;
import otmstudytrack.dao.TaskTypeDao;

public class Main {
    
    public static void main(String[] args) {
        //Initializes the program
        
        //Init fake daos
        TaskEntryDao entryDao = new FakeTaskEntryDao();
        TaskTypeDao taskDao = new FakeTaskTypeDao();
        CourseDao courseDao = new FakeCourseDao();
        
        //Init logic
        CourseTaskManager courseTaskManager = new CourseTaskManager(courseDao, taskDao);
        EntryManager entryManager = new EntryManager(taskDao, entryDao);
        TaskService service = new TaskService(courseTaskManager, entryManager);
        
        //Init UI
        Scanner reader = new Scanner(System.in);
        TextUI textUI = new TextUI(reader, service);
        textUI.start();
    }

}