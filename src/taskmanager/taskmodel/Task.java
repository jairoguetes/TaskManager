package taskmanager.taskmodel;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Task {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private Status status;
    private LocalDate creationDate;

    public Task(String title, String description, Priority priority, Category category) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.status = Status.PENDING;
        this.creationDate = LocalDate.now();
    }

    // Getters y setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getCreationDate() { return creationDate; }

    public abstract void update();
    public abstract boolean isDue();

    public void complete() {
        this.status = Status.COMPLETED;
    }

    @Override
    public String toString() {
        return String.format("Task [ID: %s, Title: %s, Priority: %s, Category: %s, Status: %s]",
                id, title, priority, category, status);
    }
}
