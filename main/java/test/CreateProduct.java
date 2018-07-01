package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class CreateProduct {

	public static void main(String[] args) {

		// Total bolts and machine available in warehouse
		int totalBolts = 15;
		int totalMachines = 7;
		int numberOfWorkers = 3;
		int timeRequiredForCreatingProduct = 1;

		createProducts(totalBolts, totalMachines, numberOfWorkers, timeRequiredForCreatingProduct);
	}

	private static void createProducts(int totalBolts, int totalMachines, int numberOfWorkers,
			int timeRequiredForCreatingProduct) {
		int totalProductsCreated = 0;
		int maxTimeTaken = 0;

		BlockingQueue<Material> blockingQueue = new LinkedBlockingQueue<>();
		for (int i = 0; i < totalMachines; i++) {
			try {
				blockingQueue.put(new Bolt());
				blockingQueue.put(new Machine());
				blockingQueue.put(new Bolt());
				totalProductsCreated ++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Worker worker = new Worker(blockingQueue, timeRequiredForCreatingProduct);
		ExecutorService executor = Executors.newFixedThreadPool(numberOfWorkers);

		for (int i = 1; i <= numberOfWorkers; i++) {
			maxTimeTaken = maxTimeTaken + timeRequiredForCreatingProduct; 
			executor.submit(worker);
		}

		
		double time = totalProductsCreated/(3.0d);
		maxTimeTaken = (int) Math.ceil(time);

		System.out.println("Total products created : " + totalProductsCreated);
		System.out.println("Time taken in mins : " + maxTimeTaken);
		

		System.out.println("Stopped");
	}

}
