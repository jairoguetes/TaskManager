package taskmanager.taskmodel;

import java.time.LocalDate;

public class DeadlineTask extends Task {
    private LocalDate deadline;

    public DeadlineTask(String title, String description, Priority priority,
                        Category category, LocalDate deadline) {
        super(title, description, priority, category);
        this.deadline = deadline;
    }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    @Override
    public void update() {
        if (isDeadlineNear() && getStatus() != Status.COMPLETED) {
            setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public boolean isDue() {
        return LocalDate.now().isAfter(deadline) ||
                LocalDate.now().isEqual(deadline);
    }

    public boolean isDeadlineNear() {
        return LocalDate.now().plusDays(2).isAfter(deadline) &&
                !LocalDate.now().isAfter(deadline);
    }

    @Override
    public String toString() {
        return String.format("DeadlineTask [ID: %s, Title: %s, Priority: %s, Category: %s, Status: %s, Deadline: %s]",
                getId(), getTitle(), getPriority(), getCategory(), getStatus(), deadline);
    }
}