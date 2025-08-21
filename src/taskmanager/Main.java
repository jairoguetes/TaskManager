package taskmanager;

import taskmanager.taskmodel.*;
import taskmanager.filter.*;
import taskmanager.notification.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TaskManager taskManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        taskManager = new TaskManager();
        scanner = new Scanner(System.in);

        // Add notifiers
        taskManager.addNotifier(new ConsoleNotifier());

        System.out.println("Welcome to TaskMaster!");

        boolean running = true;
        while (running) {
            showMenu();
            int option = getOption();
            running = processOption(option);
        }

        taskManager.shutdown();
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add new task");
        System.out.println("2. View all tasks");
        System.out.println("3. Filter tasks");
        System.out.println("4. Mark task as completed");
        System.out.println("5. Delete task");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private static int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean processOption(int option) {
        switch (option) {
            case 1:
                addTask();
                return true;
            case 2:
                viewAllTasks();
                return true;
            case 3:
                filterTasks();
                return true;
            case 4:
                completeTask();
                return true;
            case 5:
                deleteTask();
                return true;
            case 6:
                System.out.println("Thank you for using TaskMaster Pro!");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    private static void addTask() {
        System.out.println("\n=== ADD NEW TASK ===");
        System.out.println("1. Simple Task");
        System.out.println("2. Deadline Task");
        System.out.println("3. Recurring Task");
        System.out.print("Select task type: ");

        int type = getOption();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        // priority
        Priority priority = selectPriority();

        // category
        Category category = selectCategory();

        Task newTask = null;

        switch (type) {
            case 1:
                newTask = new SimpleTask(title, description, priority, category);
                break;

            case 2:
                System.out.print("Deadline (YYYY-MM-DD): ");
                String dateStr = scanner.nextLine();
                LocalDate deadline = LocalDate.parse(dateStr);
                newTask = new DeadlineTask(title, description, priority, category, deadline);
                break;

            case 3:
                System.out.println("Recurrence type:");
                System.out.println("1. Daily");
                System.out.println("2. Weekly");
                System.out.println("3. Monthly");
                System.out.println("4. Yearly");
                System.out.print("Select: ");
                int recurrenceOption = getOption();

                RecurringTask.Recurrence recurrence = RecurringTask.Recurrence.DAILY;
                switch (recurrenceOption) {
                    case 1: recurrence = RecurringTask.Recurrence.DAILY; break;
                    case 2: recurrence = RecurringTask.Recurrence.WEEKLY; break;
                    case 3: recurrence = RecurringTask.Recurrence.MONTHLY; break;
                    case 4: recurrence = RecurringTask.Recurrence.YEARLY; break;
                }

                System.out.print("Start date (YYYY-MM-DD): ");
                String startStr = scanner.nextLine();
                LocalDate startDate = LocalDate.parse(startStr);

                newTask = new RecurringTask(title, description, priority, category, recurrence, startDate);
                break;

            default:
                System.out.println("Invalid task type.");
                return;
        }

        taskManager.addTask(newTask);
        System.out.println("Task added successfully!");
    }

    private static Priority selectPriority() {
        System.out.println("Priority:");
        System.out.println("1. Low");
        System.out.println("2. Medium");
        System.out.println("3. High");
        System.out.println("4. Urgent");
        System.out.print("Select: ");

        int option = getOption();
        switch (option) {
            case 1: return Priority.LOW;
            case 2: return Priority.MEDIUM;
            case 3: return Priority.HIGH;
            case 4: return Priority.URGENT;
            default: return Priority.MEDIUM;
        }
    }

    private static Category selectCategory() {
        System.out.println("Category:");
        System.out.println("1. Work");
        System.out.println("2. Personal");
        System.out.println("3. Study");
        System.out.println("4. Health");
        System.out.println("5. Finance");
        System.out.println("6. Other");
        System.out.print("Select: ");

        int option = getOption();
        switch (option) {
            case 1: return Category.WORK;
            case 2: return Category.PERSONAL;
            case 3: return Category.STUDY;
            case 4: return Category.HEALTH;
            case 5: return Category.FINANCE;
            default: return Category.OTHER;
        }
    }

    private static void viewAllTasks() {
        System.out.println("\n=== ALL TASKS ===");
        List<Task> tasks = taskManager.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void filterTasks() {
        System.out.println("\n=== FILTER TASKS ===");
        System.out.println("1. By category");
        System.out.println("2. By priority");
        System.out.println("3. By status");
        System.out.print("Select: ");

        int option = getOption();
        TaskFilter filter = null;

        switch (option) {
            case 1:
                Category category = selectCategory();
                filter = new CategoryFilter(category);
                break;

            case 2:
                Priority priority = selectPriority();
                filter = new PriorityFilter(priority);
                break;

            case 3:
                Status status = selectStatus();
                filter = new StatusFilter(status);
                break;

            default:
                System.out.println("Invalid option.");
                return;
        }

        taskManager.addFilter(filter);
        List<Task> filteredTasks = taskManager.getFilteredTasks();

        System.out.println("\n=== FILTERED TASKS ===");
        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks match the filter.");
        } else {
            for (int i = 0; i < filteredTasks.size(); i++) {
                System.out.println((i + 1) + ". " + filteredTasks.get(i));
            }
        }

        taskManager.clearFilters();
    }

    private static Status selectStatus() {
        System.out.println("Status:");
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        System.out.println("4. Cancelled");
        System.out.print("Select: ");

        int option = getOption();
        switch (option) {
            case 1: return Status.PENDING;
            case 2: return Status.IN_PROGRESS;
            case 3: return Status.COMPLETED;
            case 4: return Status.CANCELLED;
            default: return Status.PENDING;
        }
    }

    private static void completeTask() {
        viewAllTasks();
        System.out.print("Task number to complete: ");

        int number = getOption();
        List<Task> tasks = taskManager.getAllTasks();

        if (number > 0 && number <= tasks.size()) {
            Task task = tasks.get(number - 1);
            task.complete();
            System.out.println("Task completed: " + task.getTitle());
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void deleteTask() {
        viewAllTasks();
        System.out.print("Task number to delete: ");

        int number = getOption();
        List<Task> tasks = taskManager.getAllTasks();

        if (number > 0 && number <= tasks.size()) {
            Task task = tasks.get(number - 1);
            taskManager.removeTask(task.getId());
            System.out.println("Task deleted: " + task.getTitle());
        } else {
            System.out.println("Invalid task number.");
        }
    }
}