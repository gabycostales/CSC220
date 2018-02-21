/*
 * ConstantFib.java
 *
 * Created on September 12, 2006, 10:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03M;

/**
 *
 * @author vjm
 */
public class ConstantFib extends PowerFib {
    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double o (int n) {
        return 1;
    }

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
	return Math.pow(x, n);
    }
}
