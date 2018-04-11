/* @(#)BreadthFirstVertexIterator.java
 * Copyright (c) 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

/**
 * BreadthFirstVertexIterator.
 *
 * @author Werner Randelshofer
 * @version $$Id$$
 * @param <V> the vertex type
 */
public class BreadthFirstVertexIterator<V> implements Iterator<V> {

    private final DirectedGraph<V, ?> graph;
    private final Queue<V> queue;
    private final Predicate<V> visited;

    /**
     * Creates a new instance.
     *
     * @param graph the graph
     * @param root the root vertex
     */
    public BreadthFirstVertexIterator(DirectedGraph<V, ?> graph, V root) {
        if (graph == null) {
            throw new IllegalArgumentException("graph==null");
        }
        if (root == null) {
            throw new IllegalArgumentException("root==null");
        }        this.graph = graph;
        queue = new ArrayDeque<>(16);
        Set<V> vset = new HashSet<>(16);
        visited = vset::add;
        queue.add(root);
        visited.test(root);
    }

    /**
     * Creates a new instance.
     *
     * @param graph the graph
     * @param root the root vertex
     * @param visited a predicate with side effect. The predicate returns true
     * if the specified vertex has been visited, and marks the specified vertex
     * as visited.
     */
    public BreadthFirstVertexIterator(DirectedGraph<V, ?> graph, V root, Predicate<V> visited) {
        if (graph == null) {
            throw new IllegalArgumentException("graph==null");
        }
        if (root == null) {
            throw new IllegalArgumentException("root==null");
        }
        if (visited == null) {
            throw new IllegalArgumentException("visited==null");
        }
        this.graph = graph;
        queue = new ArrayDeque<>(16);
        this.visited = visited;
        queue.add(root);
        visited.test(root);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public V next() {
        V current = queue.remove();
        for (V next : graph.getNextVertices(current)) {
            if (visited.test(next)) {
                queue.add(next);
            }
        }
        return current;
    }

}
