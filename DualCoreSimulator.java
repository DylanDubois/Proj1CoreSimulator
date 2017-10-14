package dualcoresimulator;
import java.util.ArrayList;
import java.util.Random;
/**
* This is the main file of the simulator. Iterates through the total number of cycles, creating and terminating jobs when necessary.
* @author Dylan Dubois
* @since 09-24-2017
* Course: CS3102.01
* Programming Project #: 1
* Instructor: Dr. Duncan
* @see Heap.java, DualCoreSimulator.java, PCB.java, HeapAPI.java, HeapException.java
*/
public class DualCoreSimulator {
	public static void main(String[] args) throws HeapException {
		ArrayList<Heap<PCB>> cpuCores = new ArrayList<Heap<PCB>>();
		cpuCores.add((new Heap<PCB>()));
		cpuCores.add((new Heap<PCB>()));
		double[] averages = new double[6]; // [# of CPU1 Jobs, # of CPU2 Jobs, CPU1 Wait Times, CPU2 WAit Times, 
			// # of Started Jobs CPU1, # of Started Jobs CPU2]
		Random rand = new Random(System.currentTimeMillis());
		int totalCycles = Integer.parseInt(args[1]), heapId = 1;
		double probability = Double.parseDouble(args[0]);
		for (int cycle = 1; cycle <= totalCycles; cycle++) {
			System.out.printf("*** Cycle #: %d\n", cycle);
			System.out.printf("CPU 1 (%d) CPU 2 (%d)\n", cpuCores.get(0).size(), cpuCores.get(1).size());
			for (int i = 0; i < cpuCores.size(); i++) {
				if (cpuCores.get(i).isEmpty())
					System.out.printf("CPU %d: idle\n", i + 1);
				else {
					if (cycle - cpuCores.get(i).peek().getStart() == cpuCores.get(i).peek().getLength()) {
						System.out.printf("CPU %d: Process #%d has just terminated.\n", i + 1, (cpuCores.get(i).peek()).getPid());
						cpuCores.get(i).remove();
						averages[i] += 1.0;
					} else {
						System.out.printf("CPU %d: Process #%d is executing.\n", i + 1, (cpuCores.get(i).peek()).getPid());
					}
					if (!cpuCores.get(i).isEmpty() && !(cpuCores.get(i).peek()).isExecuting()) {
						cpuCores.get(i).peek().execute();
						cpuCores.get(i).peek().setStart(cycle);
						cpuCores.get(i).peek().setWait(cpuCores.get(i).peek().getStart() - cpuCores.get(i).peek().getArrival());
						averages[2+i] += cpuCores.get(i).peek().getWait() * 1.0;
						averages[4+i] += 1.0;
					}
				}
			}
			int length, priority;
			double q = rand.nextDouble();
			if (q <= probability * probability) {
				System.out.printf("Two new jobs this cycle.\n");
				if (cpuCores.get(0).size() > cpuCores.get(1).size()) {
					for (int i = 0; i < 2; i++) {
						length = rand.nextInt(100) + 1;
						priority = rand.nextInt(39) - 20;
					    cpuCores.get(1).insert((new PCB(heapId, priority, 0, cycle, length)));
						System.out.printf("CPU 2: Adding job with pid #%d and priority %d and length %d.\n",
								heapId, priority, length);
						heapId++;
					}
				} else if (cpuCores.get(1).size() > cpuCores.get(0).size()) {
					for (int i = 0; i < 2; i++) {
						length = rand.nextInt(100) + 1;
						priority = rand.nextInt(39) - 20;
						cpuCores.get(0).insert((new PCB(heapId, priority, 0, cycle, length)));
						System.out.printf("CPU 1: Adding job with pid #%d and priority %d and length %d.\n",
								heapId, priority, length);
						heapId++;
					}
				} else {
					for (int i = 0; i < 2; i++) {
						length = rand.nextInt(100) + 1;
						priority = rand.nextInt(39) - 20;
						cpuCores.get(i).insert((new PCB(heapId, priority, 0, cycle, length)));
						System.out.printf("CPU %d: Adding job with pid #%d and priority %d and length %d.\n", i + 1,
								heapId, priority, length);
						heapId++;
					}
				}
			} else if (q <= probability) {
				System.out.printf("One new job this cycle.\n");
				if (cpuCores.get(0).size() > cpuCores.get(1).size()) {
					length = rand.nextInt(100) + 1;
					priority = rand.nextInt(39) - 20;
					cpuCores.get(1).insert((new PCB(heapId, priority, 0, cycle, length)));
					System.out.printf("CPU 2: Adding job with pid #%d and priority %d and length %d.\n",
							heapId, priority, length);
					heapId++;
				} else {
					length = rand.nextInt(100) + 1;
					priority = rand.nextInt(39) - 20;
					cpuCores.get(0).insert((new PCB(heapId, priority, 0, cycle, length)));
					System.out.printf("CPU 1: Adding job with pid #%d and priority %d and length %d.\n",
							heapId, priority, length);
					heapId++;
				}
			} else {
				System.out.printf("No new job this cycle.\n");
			}
		}
		// Decided to format the averages with 3 decimal places because my outputs were just 0s past the 3 decimal place.
		System.out.printf("\nCPU 1: average throughput is %.3f per cycle.\n", averages[0] / totalCycles);
		System.out.printf("CPU 1: average wait time is %.14f.\n", averages[2] / averages[4]);
		System.out.printf("CPU 2: average throughput is %.3f per cycle.\n", averages[1] / totalCycles);
		System.out.printf("CPU 2: average wait time is %.14f.\n", averages[3] / averages[5]);
		System.out.printf("overall average throughput is %.3f per cycle.\n", (averages[0] + averages[1]) / totalCycles);
		System.out.printf("overall average wait time is %.14f.\n", (averages[2] + averages[3]) / (averages[4] + averages[5]));
		}

}