package duke.util;

import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    public void print(String... strings) {
        System.out.println("_________________________________________________");
        for(String string: strings) {
            System.out.println("\n" + "    " + string);
        }
        System.out.println("_________________________________________________");
    }

    public void showLine() {
        System.out.println("_________________________________________________");
    }

    public void showWelcome() {
        String words1 = "Hello! Welcome! This is Duke~";
        String words2 = "What can I help you today?";
        print(words1, words2);
    }

    public void showGoodBye() {
        String words = "Bye! Hope to see you again!";
        print(words);
    }

    public void showList(String input) {
        String words = "Here is your duke.task list:";
        print(words, input);
    }

    public void showDone(Task task) {
        String words = " Nice! I've marked this duke.task as done:";
        print(words, task.toString());
    }

    public void showDelete(Task task, int num) {
        String words1 = "Noted. I've removed this duke.task:";
        String words2 = "Now you have " + num + " tasks in the list.";
        print(words1, task.toString(), words2);
    }

    public void showNewTask(Task task) {
        String words = "added:";
        print(words, task.toString());
    }

    public void showFind(ArrayList<Task> results) {
        String words = "Here are the matching tasks in your list:";
        for(int i = 1; i <= results.size(); i++) {
            words = words + "\n    " + i + ". " + results.get(i-1);
        }
        print(words);
    }

    public String readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();
        return command;
    }
}
