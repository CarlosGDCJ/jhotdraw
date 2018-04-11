/* @(#)BreadthFirstVertexSpliteratorTest.java
 * Copyright (c) 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 * BreadthFirstVertexSpliteratorTest.
 *
 * @author Werner Randelshofer
 * @version $$Id$$
 */
public class BreadthFirstVertexSpliteratorTest {

    private DirectedGraph<Integer, Double> createGraph() {
        DirectedGraphBuilder<Integer, Double> builder = new DirectedGraphBuilder<>();
        builder.addVertex(1);
        builder.addVertex(2);
        builder.addVertex(3);
        builder.addVertex(4);
        builder.addVertex(5);
        builder.addVertex(6);

        builder.addBidiArrow(1, 2, 7.0);
        builder.addArrow(1, 3, 9.0);
        builder.addBidiArrow(1, 6, 14.0);
        builder.addArrow(2, 3, 10.0);
        builder.addArrow(2, 4, 15.0);
        builder.addArrow(3, 4, 11.0);
        builder.addArrow(3, 6, 2.0);
        builder.addArrow(4, 5, 6.0);
        builder.addBidiArrow(5, 6, 9.0);
        return builder;
    }

    public Object[][] anyPathProvider() {
        return new Object[][]{
            {1, 5, Arrays.asList(1, 2, 3, 6, 4, 5)},
            {1, 4, Arrays.asList(1, 2, 3, 6, 4)},
            {2, 6, Arrays.asList(2, 1, 3, 4, 6)}
        };
    }

    @Test
    public void testCreateGraph() {
        final DirectedGraph<Integer, Double> graph = createGraph();

        final String expected
                = "1 -> 2, 3, 6.\n"
                + "2 -> 1, 3, 4.\n"
                + "3 -> 4, 6.\n"
                + "4 -> 5.\n"
                + "5 -> 6.\n"
                + "6 -> 1, 5.";

        final String actual = DirectedGraphs.dumpAsAdjacencyMap(graph);
        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testIterateWithAnyPathProvider() throws Exception {
        for (Object[] args : anyPathProvider()) {
            testIterate((Integer) args[0], (Integer) args[1], (List<Integer>) args[2]);
        }
    }

    public void testIterate(Integer start, Integer goal, List<Integer> expResult) throws Exception {
        System.out.println("testIterate start:" + start + " goal:" + goal + " expResult:" + expResult);
        DirectedGraph<Integer, Double> graph = createGraph();
        BreadthFirstVertexSpliterator<Integer> instance = new BreadthFirstVertexSpliterator<>(graph, start);
        List<Integer> result = new ArrayList<>();
        while (instance.hasNext()) {
            final Integer next = instance.next();
            result.add(next);
            if (next.equals( goal)) {
                break;
            }
        }
        System.out.println("actual:" + result);
        assertEquals(expResult, result);
    }

    @Test
    @Ignore
    public void testForEachRemainingWithAnyPathProvider() throws Exception {
        for (Object[] args : anyPathProvider()) {
            testForEachRemaining((Integer) args[0], (Integer) args[1], (List<Integer>) args[2]);
        }
    }

    public void testForEachRemaining(Integer start, Integer goal, List<Integer> expResult) throws Exception {
        System.out.println("testForEachRemaining start:" + start + " goal:" + goal + " expResult:" + expResult);
        DirectedGraph<Integer, Double> graph = createGraph();
        BreadthFirstVertexSpliterator<Integer> instance = new BreadthFirstVertexSpliterator<>(graph, start);
        List<Integer> result = new ArrayList<>();
        // FIXME we need java 9
        instance/*.takeWhile(vertex->!vertex.equals(goal))*/.forEachRemaining(result::add);
        System.out.println("actual:" + result);
        assertEquals(expResult, result);
    }
}