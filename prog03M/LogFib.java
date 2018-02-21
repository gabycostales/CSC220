/*
 * LogFib.java
 *
 * Created on September 12, 2006, 10:23 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03M;

/**
 *
 * @author vjm
 */
public class LogFib extends PowerFib {
    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double o (int n) {
        return Math.log(n);
    }

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
	if (n <= 0)
	    return 1;
	double y = pow(x, n / 2);
	if (n % 2 == 0)
	    return y * y;
	else
	    return x * y * y;
    }
}
