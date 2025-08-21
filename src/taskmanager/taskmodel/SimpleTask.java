package taskmanager.taskmodel;

public class SimpleTask extends Task {

    public SimpleTask(String title, String description, Priority priority, Category category) {
        super(title, description, priority, category);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isDue() {

        return false;
    }

    @Override
    public String toString() {
        return String.format("SimpleTask [ID: %s, Title: %s, Priority: %s, Category: %s, Status: %s]",
                getId(), getTitle(), getPriority(), getCategory(), getStatus());
    }
}
