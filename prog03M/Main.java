/*
 * Main.java
 *
 * Created on September 12, 2006, 10:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03M;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
    /** Use this variable to store the result of each call to fib. */
	public static double fibn;

    /** Determine the time in milliseconds it takes to calculate the
        n'th Fibonacci number averaged over ncalls calls.
	@param fib an object that implements the Fib interface
	@param n the index of the Fibonacci number to calculate
	@param ncalls the number of calls to average over
	@return the average time per call
    */
	public static double time (Fib fib, int n, int ncalls) {
	// Get the current time in milliseconds.  This is a static
	// method in the System class.  Actually, it is the time in
	// milliseconds since midnight, January 1, 1970.  What type
	// should you use to store the current time?  Why?
		long start = System.currentTimeMillis();

	// Calculate the n'th Fibonacci number ncalls times.  Each
	// time store the result in fibn.
		for (int i = 0; i < ncalls; i++)
			fibn = fib.fib(n);

	// Get the current time in milliseconds.
		long end = System.currentTimeMillis();

	// Using ncalls and the starting and ending times, calculate
	// the average time per call and return it.  Make sure to use
	// double precision arithmetic for the calculation by casting
	// it to double.
		return (double) (end - start) / ncalls;
	}

    /** Determine the time in milliseconds it takes to to calculate
	the n'th Fibonacci number ACCURATE TO THREE SIGNIFICANT FIGURES.
	@param fib an object that implements the Fib interface
	@param n the index of the Fibonacci number to calculate
	@return the time it it takes to compute the n'th Fibonacci number
    */
	public static double time (Fib fib, int n) {
	// Since the clock is only accurate to the millisecond, we
	// need to use a value of ncalls such that the total time is a
	// second.  First we need to figure that value of ncalls.

	// Starting with ncalls=1, calculate the total time, which is
	// ncalls times the average time.  Use the method
	// time(fib,n,ncalls) method to get the average time.  Keep
	// multiplying ncalls times 10 until the total time is more
	// than a second.
		int ncalls = 1;
		double averageTime = time(fib, n, ncalls);

		while (ncalls * averageTime <= 1000) {
			ncalls *= 10;
			averageTime = time(fib, n, ncalls);
		}

	// Return the average time for that value of ncalls.  As a
	// test, print out ncalls times this average time to make sure
	// it is more than a second.
                // System.println("debug " + (ncalls * averageTime));
		return averageTime;
	}

	private static double oldC = 0;

	private static UserInterface ui = new GUI();

	public static void doExperiments (Fib fib) {
		while (true) {
			String sn = ui.getInfo("Enter n");
			if (sn == null)
				return;
			int n = -1;
			try {
				n = Integer.parseInt(sn);
			} catch (Exception e) {
				ui.sendMessage(sn + " is not an integer.");
				continue;
			}
			if (n < 0) {
				ui.sendMessage(n + " is negative...invalid.");
				continue;
			}

			if (oldC != 0) {
				double estTime = oldC * fib.o(n);
				ui.sendMessage("Estimated time on input " + n + " is " +
						estTime + " milliseconds.");

				if (estTime > 1000 * 60 * 60) {
					ui.sendMessage("Estimated time is more than an hour.\n" +
					"I will ask you if you really want to run it.");
					String choices[] = { "yes", "no" };
					int choice = ui.getCommand(choices);
					if (choice == 1)
						continue;
				}
			}

			double fn = fib.fib(n);
			double newTime = time(fib, n);
			oldC = newTime / fib.o(n);
			ui.sendMessage("fib(" + n + ") = " + fn + " in " +
					newTime + " milliseconds.");
		}
	}

	@SuppressWarnings("unused")
	public static void doExperiments () {
		String[] choices =
		{ "ExponentialFib", "LinearFib", "LogFib", "ConstantFib" };
		int choice = ui.getCommand(choices);
		Fib fib = new ExponentialFib();
		switch (choice) {
		case 0:
			doExperiments(new ExponentialFib());
			break;
		case 1:
			doExperiments(new LinearFib());
			break;
		case 2:
			doExperiments(new LogFib());
			break;
		case 3:
			doExperiments(new ConstantFib());
			break;
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		doExperiments();
	}

}
