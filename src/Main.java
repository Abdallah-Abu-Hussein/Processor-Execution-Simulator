public class Main{
    public static void main(String[] args) {

        int numProcessors = 2;
        int totalCycles = 10;
        String filePath = "/home/mrt/my_learning/Processor Execution Simulator/Input.txt";

        Simulator simulator = new Simulator(numProcessors, totalCycles, filePath);
        simulator.run();
    }
}
