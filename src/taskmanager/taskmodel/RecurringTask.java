package taskmanager.taskmodel;

import java.time.LocalDate;

public class RecurringTask extends Task {
    public enum Recurrence {
        DAILY, WEEKLY, MONTHLY, YEARLY
    }

    private Recurrence recurrence;
    private LocalDate nextOccurrence;

    public RecurringTask(String title, String description, Priority priority,
                         Category category, Recurrence recurrence, LocalDate startDate) {
        super(title, description, priority, category);
        this.recurrence = recurrence;
        this.nextOccurrence = startDate;
    }

    public Recurrence getRecurrence() { return recurrence; }
    public void setRecurrence(Recurrence recurrence) { this.recurrence = recurrence; }

    public LocalDate getNextOccurrence() { return nextOccurrence; }
    public void setNextOccurrence(LocalDate nextOccurrence) { this.nextOccurrence = nextOccurrence; }

    @Override
    public void update() {
        if (LocalDate.now().isAfter(nextOccurrence) || LocalDate.now().isEqual(nextOccurrence)) {

            setStatus(Status.PENDING);

            switch (recurrence) {
                case DAILY:
                    nextOccurrence = nextOccurrence.plusDays(1);
                    break;
                case WEEKLY:
                    nextOccurrence = nextOccurrence.plusWeeks(1);
                    break;
                case MONTHLY:
                    nextOccurrence = nextOccurrence.plusMonths(1);
                    break;
                case YEARLY:
                    nextOccurrence = nextOccurrence.plusYears(1);
                    break;
            }
        }
    }

    @Override
    public boolean isDue() {
        return LocalDate.now().isAfter(nextOccurrence) ||
                LocalDate.now().isEqual(nextOccurrence);
    }

    @Override
    public String toString() {
        return String.format("RecurringTask [ID: %s, Title: %s, Priority: %s, Category: %s, Status: %s, Next: %s, Recurrence: %s]",
                getId(), getTitle(), getPriority(), getCategory(), getStatus(), nextOccurrence, recurrence);
    }
}