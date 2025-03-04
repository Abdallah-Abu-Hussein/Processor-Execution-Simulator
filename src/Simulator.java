import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Simulator {
    private List<Processor> processors;
    private List<Task> allTasks;
    private List<Task> waitingTasks;
    private int totalCycles;
    private Scheduler scheduler;

    public Simulator(int numProcessors, int totalCycles, String filePath) {
        this.totalCycles = totalCycles;
        processors = new ArrayList<>();
        for (int i = 0; i < numProcessors; i++) {
            processors.add(new Processor("P" + (i + 1)));
        }
        allTasks = readTasksFromFile(filePath);
        waitingTasks = new ArrayList<>();
        scheduler = new Scheduler();
    }

    private List<Task> readTasksFromFile(String filePath) {
        List<Task> tasks = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            if (!scanner.hasNextLine()) {
                System.err.println("Empty task file: " + filePath);
                scanner.close();
                return tasks;
            }
            int numberOfTasks = Integer.parseInt(scanner.nextLine().trim());
            int taskCounter = 1;
            while (scanner.hasNextLine() && taskCounter <= numberOfTasks) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+");
                if (parts.length < 3) {
                    System.err.println("Invalid task entry: " + line);
                    continue;
                }
                int creationTime = Integer.parseInt(parts[0]);
                int executionTime = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                Task task = new Task("T" + taskCounter, creationTime, executionTime, priority);
                tasks.add(task);
                taskCounter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Task file not found: " + filePath);
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in task file.");
            System.exit(1);
        }
        return tasks;
    }

    public void run() {
        System.out.println("Starting simulation for " + totalCycles + " cycles.");
        for (int cycle = 1; cycle <= totalCycles; cycle++) {
            System.out.println("Cycle " + cycle + ":");

            // 1. Task Creation: add tasks scheduled for this cycle.
            for (Task task : allTasks) {
                if (task.getCreationTime() == cycle) {
                    System.out.println("  Task " + task.getId() + " created (Exec Time: "
                            + task.getExecutionTime() + ", Priority: " + task.getPriority() + ").");
                    waitingTasks.add(task);
                }
            }

            List<Processor> availableProcessors = new ArrayList<>();
            for (Processor proc : processors) {
                Task finishedTask = proc.update(cycle);
                if (finishedTask != null) {
                    System.out.println("  Task " + finishedTask.getId() + " completed on " + proc.getId() + ".");
                    availableProcessors.add(proc);
                } else if (proc.isIdle()) {
                    availableProcessors.add(proc);
                }
            }

            scheduler.schedule(waitingTasks, availableProcessors, cycle);

            System.out.println("-------------------------------");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Simulation finished.");
    }
}
