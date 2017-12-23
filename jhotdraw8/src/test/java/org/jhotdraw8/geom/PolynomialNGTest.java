/* @(#)PolynomialNGTest.java
 * Copyright (c) 2017 by the authors and contributors of JHotDraw.
 * You may only use this file in compliance with the accompanying license terms.
 */
package org.jhotdraw8.geom;

import static java.lang.Math.sqrt;
import java.util.Arrays;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * PolynomialNGTest.
 *
 * @author Werner Randelshofer
 * @version $$Id$$
 */
public class PolynomialNGTest {

    public PolynomialNGTest() {
    }

    @DataProvider
    public Object[][] polynomials() {
        return new Object[][]{
            {new Polynomial(1, -1.61900826446281, 0.882231404958678, -0.163453828290417), new double[]{0.635379615995478}},
            {new Polynomial(false, new double[]{2330400.0, -1.49088E7, 3.76128E7, -4.6464E7, 2.3232E7}), new double[]{0.405180683762359,
                0.722769898622671}},
            {new Polynomial(1, 6, -5, -10, -3), new double[]{(-7 - sqrt(37)) / 2, (-7 + sqrt(37)) / 2, (1 - sqrt(5)) / 2, (1 + sqrt(5)) / 2}},
            {new Polynomial(5), new double[]{}},
            {new Polynomial(2, 1), new double[]{-0.5}},
            {new Polynomial(-3, 0, 2, 0, 5), new double[]{1.2909944487358056, -1.2909944487358056}},};
    }

    @DataProvider
    public Object[][] polynomialsInInterval() {
        return new Object[][]{
            {new Polynomial(1, -1.61900826446281, 0.882231404958678, -0.163453828290417), 0.0, 1.0, new double[]{0.635379615995478}},
            {new Polynomial(5), -5.0, 5.0, new double[]{}},
            {new Polynomial(2, 1), -5.0, 5.0, new double[]{-0.5}},
            {new Polynomial(-3, 0, 2, 0, 5), -5.0, 5.0, new double[]{1.2909944487358056, -1.2909944487358056}},
            {new Polynomial(false, 2330400.0, -1.49088E7, 3.76128E7, -4.6464E7, 2.3232E7), 0.0, 1.0, new double[]{0.405180683762359,
                0.722769898622671}},
            {new Polynomial(false, -288000.0, 2330400.0, -7454400.0, 1.25376E7, -1.1616E7, 4646400.0), -5.0, 5.0, new double[]{0.327910033575923,
                0.838094098688656,
                0.5}},};
    }

    @Test(dataProvider = "polynomials")
    public void testGetRoots(Polynomial instance, double[] expected) {
        System.out.println("getRoots");
        System.out.println(instance);
        Arrays.sort(expected);
        double[] actual = instance.getRoots();
        Arrays.sort(actual);
        System.out.println("  expected: " + Arrays.toString(expected));
        System.out.println("  actual    : " + Arrays.toString(actual));
        Arrays.sort(actual);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(actual[i], expected[i], 1e-6, "root #" + i);
        }
    }

    @Test(dataProvider = "polynomialsInInterval")
    public void testGetRootsInInterval(Polynomial instance, double from, double to, double[] expected) {
        System.out.println("getRootsInInterval");
        System.out.println(instance);
        Arrays.sort(expected);
        double[] actual = instance.getRootsInInterval(from, to);
        Arrays.sort(actual);
        System.out.println("  expected: " + Arrays.toString(expected));
        System.out.println("  actual    : " + Arrays.toString(actual));
        for (int i = 0; i < expected.length; i++) {
            assertEquals(actual[i], expected[i], 1e-6, "root #" + i);
        }
    }

    public static void main(String[] args) {
        PolynomialNGTest test = new PolynomialNGTest();
        for (Object[] a : test.polynomials()) {
            test.testGetRoots((Polynomial) a[0], (double[]) a[1]);
        }
        for (Object[] a : test.polynomialsInInterval()) {
            test.testGetRootsInInterval((Polynomial) a[0], (double) a[1], (double) a[2], (double[]) a[3]);
        }
    }

}
