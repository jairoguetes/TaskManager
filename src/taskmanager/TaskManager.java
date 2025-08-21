package taskmanager;

import taskmanager.taskmodel.Task;
import taskmanager.taskmodel.DeadlineTask;
import taskmanager.filter.TaskFilter;
import taskmanager.taskmodel.Status;
import taskmanager.notification.Notifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskManager {
    private List<Task> tasks;
    private List<TaskFilter> filters;
    private List<Notifier> notifiers;
    private Timer timer;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.filters = new ArrayList<>();
        this.notifiers = new ArrayList<>();
        this.timer = new Timer(true);

        // Schedule periodic task checking
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkTasks();
            }
        }, 0, 60000);
    }

    // Methods to manage tasks
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added: " + task.getTitle());
    }

    public void removeTask(String taskId) {
        tasks.removeIf(task -> task.getId().equals(taskId));
        System.out.println("Task removed: " + taskId);
    }

    public Task findTaskById(String taskId) {
        return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Methods to manage filters
    public void addFilter(TaskFilter filter) {
        filters.add(filter);
    }

    public void clearFilters() {
        filters.clear();
    }

    // Methods to manage notifiers
    public void addNotifier(Notifier notifier) {
        notifiers.add(notifier);
    }

    // Method to apply multiple filters
    public List<Task> getFilteredTasks() {
        List<Task> filteredTasks = new ArrayList<>(tasks);

        for (TaskFilter filter : filters) {
            filteredTasks = filter.filter(filteredTasks);
        }

        return filteredTasks;
    }

    // Method to check and update tasks
    private void checkTasks() {
        for (Task task : tasks) {
            task.update();

            if (task.isDue() && task.getStatus() != Status.COMPLETED) {
                notifyAll("Task '" + task.getTitle() + "' is overdue.");
            }

            if (task instanceof DeadlineTask) {
                DeadlineTask deadlineTask = (DeadlineTask) task;
                if (deadlineTask.isDeadlineNear() && deadlineTask.getStatus() != Status.COMPLETED) {
                    notifyAll("Task '" + task.getTitle() + "' is due soon.");
                }
            }
        }
    }

    // Method to notify
    private void notifyAll(String message) {
        for (Notifier notifier : notifiers) {
            notifier.send(message);
        }
    }

    // Method to shutdown TaskManager and release resources
    public void shutdown() {
        timer.cancel();
    }
}