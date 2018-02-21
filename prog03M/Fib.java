/*
 * Fib.java
 *
 * Created on September 12, 2006, 10:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03M;

/**
 *
 * @author vjm
 */
public interface Fib {
    /** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
    */
    double fib (int n);

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    double o (int n);
}
