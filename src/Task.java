// Task.java
public class Task {
    private String id;
    private int creationTime;
    private int executionTime;
    private int priority; // 1 for high, 0 for low

    public Task(String id, int creationTime, int executionTime, int priority) {
        this.id = id;
        this.creationTime = creationTime;
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task " + id + " (Creation: " + creationTime + ", Exec: " + executionTime + ", Priority: " + priority + ")";
    }
}
