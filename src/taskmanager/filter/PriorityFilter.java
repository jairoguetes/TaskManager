package taskmanager.filter;

import taskmanager.taskmodel.Task;
import taskmanager.taskmodel.Priority;
import java.util.List;
import java.util.stream.Collectors;

public class PriorityFilter implements TaskFilter {
    private Priority priority;

    public PriorityFilter(Priority priority) {
        this.priority = priority;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getPriority().equals(priority))
                .collect(Collectors.toList());
    }
}