// Processor.java
public class Processor {
    private String id;
    private Task currentTask;
    private int taskEndCycle;

    public Processor(String id) {
        this.id = id;
        this.currentTask = null;
        this.taskEndCycle = -1;
    }

    public String getId() {
        return id;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public boolean isIdle() {
        return currentTask == null;
    }

    public void assignTask(Task task, int currentCycle) {
        this.currentTask = task;
        this.taskEndCycle = currentCycle + task.getExecutionTime();
    }

    public Task update(int currentCycle) {
        if (currentTask != null && currentCycle >= taskEndCycle) {
            Task finishedTask = currentTask;
            currentTask = null;
            taskEndCycle = -1;
            return finishedTask;
        }
        return null;
    }
}
