package taskmanager.filter;

import taskmanager.taskmodel.Task;
import taskmanager.taskmodel.Status;
import java.util.List;
import java.util.stream.Collectors;

public class StatusFilter implements TaskFilter {
    private Status status;

    public StatusFilter(Status status) {
        this.status = status;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}