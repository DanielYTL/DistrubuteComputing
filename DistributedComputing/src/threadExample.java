

public class threadExample implements Runnable {
	private int id;

	public threadExample(int i) {
		this.id = i;
	}

	public static void main(String[] args) {
		int times = 5;
		
		for(int i = 0; i < times; i++) {
			Thread t = new Thread(new threadExample(i));
			t.start();
		}

	}

	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println("Thread " + id + " is executing!");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}