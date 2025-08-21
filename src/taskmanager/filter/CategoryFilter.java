package taskmanager.filter;

import taskmanager.taskmodel.Task;
import taskmanager.taskmodel.Category;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TaskFilter {
    private Category category;

    public CategoryFilter(Category category) {
        this.category = category;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}