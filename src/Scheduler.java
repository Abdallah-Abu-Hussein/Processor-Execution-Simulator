import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scheduler {
    private Random random = new Random();


    public void schedule(List<Task> waitingTasks, List<Processor> availableProcessors, int currentCycle) {
        for (Processor proc : availableProcessors) {
            if (waitingTasks.isEmpty()) {
                break;
            }
            Task chosenTask = selectTask(waitingTasks);
            if (chosenTask != null) {
                proc.assignTask(chosenTask, currentCycle);
                System.out.println("  Assigned Task " + chosenTask.getId() + " to "
                        + proc.getId() + " (Exec Time: "
                        + chosenTask.getExecutionTime() + ").");
                waitingTasks.remove(chosenTask);
            }
        }
    }

    private Task selectTask(List<Task> tasks) {
        List<Task> highPriority = new ArrayList<>();
        List<Task> lowPriority = new ArrayList<>();

        for (Task t : tasks) {
            if (t.getPriority() == 1) {
                highPriority.add(t);
            } else {
                lowPriority.add(t);
            }
        }

        List<Task> candidateList = !highPriority.isEmpty() ? highPriority : lowPriority;
        if (candidateList.isEmpty()) {
            return null;
        }

        int maxExecutionTime = candidateList.stream().mapToInt(Task::getExecutionTime).max().orElse(0);
        List<Task> maxTasks = new ArrayList<>();
        for (Task t : candidateList) {
            if (t.getExecutionTime() == maxExecutionTime) {
                maxTasks.add(t);
            }
        }

        int index = random.nextInt(maxTasks.size());
        return maxTasks.get(index);
    }
}
