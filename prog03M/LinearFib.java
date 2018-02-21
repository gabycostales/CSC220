/*
 * LinearFib.java
 *
 * Created on September 12, 2006, 10:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03M;

/**
 *
 * @author vjm
 */
public class LinearFib implements Fib {	
	/** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
	 */
	public double fib (int n) {
		double a = 0, b = 1;
		for (int i = 0; i < n; i++) {
			double a2 = b;
			b = a + b;
			a = a2;
		}
		return a;
	}

	/** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
	 */
	public double o (int n) {
		return n;
	}
}
