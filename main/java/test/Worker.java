package test;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {

	private BlockingQueue<Material> blockingQueue;
	private int timeRequiredForCreatingProduct;

	public Worker(BlockingQueue<Material> blockingQueue, int timeRequiredForCreatingProduct) {
		this.blockingQueue = blockingQueue;
		this.timeRequiredForCreatingProduct = timeRequiredForCreatingProduct;
	}

	private boolean process() throws InterruptedException {
		Thread.sleep(timeRequiredForCreatingProduct * 1000);
		return true;
	}

	private void readValuesFrom() throws InterruptedException {
		int boltCount = 0;
		int machineCount = 0;

		while (blockingQueue.size() != 0) {
			if (boltCount == 2 && machineCount == 1) {
				return;
			}
			Material token = blockingQueue.peek();
			if (token instanceof Machine) {
				if (machineCount < 1) {
					machineCount++;
					blockingQueue.take();
				}
			} else if (token instanceof Bolt) {
				if (boltCount < 2) {
					boltCount++;
					blockingQueue.take();
				}
			}
		}

	}

	@Override
	public void run() {
		while (blockingQueue.size() != 0) {
			try {
				readValuesFrom();
				process();

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}

		}
	}

}
