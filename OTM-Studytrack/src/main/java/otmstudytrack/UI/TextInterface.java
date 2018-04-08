package otmstudytrack.UI;

import java.util.Scanner;
import logic.TaskService;

public class TextInterface {
    
    private Scanner reader;
    private TaskService taskService;
    
    public TextInterface(Scanner reader, TaskService taskService) {
        this.reader = reader;
        this.taskService = taskService;
    }

}