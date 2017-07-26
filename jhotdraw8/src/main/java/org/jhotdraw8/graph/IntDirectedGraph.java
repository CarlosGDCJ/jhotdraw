/* @(#)IntDoubleVertexGraphModel.java
 * Copyright (c) 2017 by the authors and contributors of JHotDraw.
 * You may only use this file in compliance with the accompanying license terms.
 */

package org.jhotdraw8.graph;

/**
 * A facade for a directed graph where the vertices
 * are integers from  {@code 0} to {@code vertexCount - 1}.
 *
 * @author Werner Randelshofer
 * @version $$Id$$
 */
public interface IntDirectedGraph {
    /** Returns the number of vertices {@code V}.
     * @return vertex count
     */
    int getVertexCount();
   
    
    /** Returns the number of next vertices of v.
     * 
     * @param v a vertex
     * @return the number of next vertices of v.
     */
    int getNextCount(int v);
    
    /** Returns the i-th next vertex of v.
     * 
     * @param v a vertex
     * @return i the index
     * @return the i-th next vertex of v
     */
     int getNext(int v, int i);
    
}