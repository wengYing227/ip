package duke.util;
import java.util.ArrayList;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;


/**
 * Represents a dealer to process a full command.
 */
public class Parser {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    /**
     * Represents a new parser for Duke.
     * @param taskList
     * @param ui
     * @param storage
     */
    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses an input and process it,
     * example of the input command should be like: deadline return book /by 2/12/2019 1800 .
     *
     * @param input a full command
     * @return result content
     */
    public String parse(String input) {
        String lowerCase = input.toLowerCase();
        if (lowerCase.equals("bye")) {
            return commandBye();
        } else if (lowerCase.equals("list")) {
            return commandList();
        } else if (lowerCase.startsWith("deadline")) {
            return commandDeadline(input);
        } else if (lowerCase.startsWith("event")) {
            return commandEvent(input);
        } else if (lowerCase.startsWith("todo")) {
            return commandTodo(input);
        } else if (lowerCase.startsWith("done")) {
            return commandDone(input);
        } else if (lowerCase.startsWith("delete")) {
            return commandDelete(input);
        } else if (lowerCase.startsWith("find")) {
            return findKeyword(input);
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Fetches Task item from TaskList and print through Ui
     *
     * @return result content
     */
    public String commandList() {
        String items = "";
        if (taskList.length() > 0) {
            items = items + "    1. " + taskList.get(0).toString();
        }
        for (int i = 2; i <= taskList.length(); i++) {
            items = items + "\n    " + i + ". " + taskList.get(i - 1).toString();
        }
        return ui.showList(items);
    }

    /**
     * Finds items with keyword given by user.
     * @param input
     * @return result content
     */
    public String findKeyword(String input) {
        try {
            String lowerCase = input.toLowerCase();
            String keyword = lowerCase.substring(5).trim();
            ArrayList<Task> results = new ArrayList<>();
            for (int i = 0; i < taskList.length(); i++) {
                if (taskList.get(i).getDescription().toLowerCase().contains(keyword)) {
                    results.add(taskList.get(i));
                }
            }
            return ui.showFind(results);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! Keyword cannot be empty.");
        }
    }

    private String commandDeadline(String input) {
        int indexOfTime = input.indexOf("/by");
        if (indexOfTime == -1) {
            throw new DukeException("OOPS!!! The timeline of a deadline cannot be empty.");
        }
        String item = input.substring(9, indexOfTime);
        String by = input.substring(indexOfTime + 4);
        Task deadline = new Deadline(item, by);
        taskList.add(deadline);
        storage.add(deadline);
        return ui.showNewTask(deadline);
    }

    private String commandEvent(String input) {
        int indexOfTime = input.indexOf("/at");
        if (indexOfTime == -1) {
            throw new DukeException("OOPS!!! The timeline of a event cannot be empty.");
        }
        String item = input.substring(6, indexOfTime);
        String at = input.substring(indexOfTime + 4);
        Task event = new Event(item, at);
        taskList.add(event);
        storage.add(event);
        return ui.showNewTask(event);
    }

    private String commandTodo(String input) {
        try {
            String item = input.substring(5);
            Task todo = new Todo(item);
            taskList.add(todo);
            storage.add(todo);
            return ui.showNewTask(todo);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
        }
    }

    private String commandDone(String input) {
        try {
            int item = Integer.parseInt(input.substring(5, 6));
            taskList.done(item - 1);
            storage.done(item - 1);
            return ui.showDone(taskList.get(item - 1));
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! The target of finished duke.task cannot be empty.");
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! The format of input is wrong. It should be like 'delete 5'");
        }
    }

    private String commandDelete(String input) {
        try {
            int item = Integer.parseInt(input.substring(7, 8));
            Task task = taskList.get(item - 1);
            taskList.delete(item - 1);
            storage.delete(item - 1);
            return ui.showDelete(task, taskList.length());
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! The target of deleting duke.task cannot be empty.");
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! The format of input is wrong. It should be like 'delete 5'");
        }
    }

    private String commandBye() {
        return "Bye! Hope to see you again!";
    }
}
