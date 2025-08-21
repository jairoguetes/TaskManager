package taskmanager.filter;

import taskmanager.taskmodel.Task;
import java.util.List;

public interface TaskFilter {
    List<Task> filter(List<Task> tasks);
}