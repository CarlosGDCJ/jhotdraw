/* @(#)LineConnectingFigure.java
 * Copyright (c) 2017 by the authors and contributors of JHotDraw.
 * You may only use this file in compliance with the accompanying license terms.
 */

package org.jhotdraw8.draw.figure;

import javafx.geometry.Point2D;
import org.jhotdraw8.draw.connector.Connector;
import org.jhotdraw8.draw.key.DirtyBits;
import org.jhotdraw8.draw.key.DirtyMask;
import org.jhotdraw8.draw.key.DoubleStyleableFigureKey;
import org.jhotdraw8.draw.key.Point2DStyleableMapAccessor;
import org.jhotdraw8.draw.key.SimpleFigureKey;

/**
 * LineConnectingFigure.
 *
 * @author Werner Randelshofer
 * @version $$Id$$
 */
public interface LineConnectingFigure extends ConnectingFigure {
   /**
     * The end position of the line.
     */
    public static Point2DStyleableMapAccessor END = LineFigure.END;
    /**
     * The end connector.
     */
    public static SimpleFigureKey<Connector> END_CONNECTOR = new SimpleFigureKey<>("endConnector", Connector.class, DirtyMask.of(DirtyBits.STATE, DirtyBits.LAYOUT_SUBJECT, DirtyBits.LAYOUT, DirtyBits.LAYOUT_OBSERVERS, DirtyBits.TRANSFORM), null);
    /**
     * The end target.
     */
    public static SimpleFigureKey<Figure> END_TARGET = new SimpleFigureKey<>("endTarget", Figure.class, DirtyMask.of(DirtyBits.STATE, DirtyBits.LAYOUT_SUBJECT, DirtyBits.LAYOUT, DirtyBits.LAYOUT_OBSERVERS, DirtyBits.TRANSFORM), null);
    /**
     * The end position of the line.
     */
    public static DoubleStyleableFigureKey END_X = LineFigure.END_X;
    /**
     * The end position of the line.
     */
    public static DoubleStyleableFigureKey END_Y = LineFigure.END_Y;
    /**
     * The start position of the line.
     */
    public static Point2DStyleableMapAccessor START = LineFigure.START;
    /**
     * The start connector.
     */
    public static SimpleFigureKey<Connector> START_CONNECTOR = new SimpleFigureKey<>("startConnector", Connector.class, DirtyMask.of(DirtyBits.STATE, DirtyBits.LAYOUT_SUBJECT, DirtyBits.LAYOUT, DirtyBits.LAYOUT_OBSERVERS, DirtyBits.TRANSFORM), null);
    /**
     * The start target.
     */
    public static SimpleFigureKey<Figure> START_TARGET = new SimpleFigureKey<>("startTarget", Figure.class, DirtyMask.of(DirtyBits.STATE, DirtyBits.LAYOUT_SUBJECT, DirtyBits.LAYOUT, DirtyBits.LAYOUT_OBSERVERS, DirtyBits.TRANSFORM), null);
    /**
     * The start position of the line.
     */
    public static DoubleStyleableFigureKey START_X = LineFigure.START_X;
    /**
     * The start position of the line.
     */
    public static DoubleStyleableFigureKey START_Y = LineFigure.START_Y;
        default boolean isStartConnected() {
        return get(START_CONNECTOR)!=null&&get(START_TARGET)!=null;
    }
    default boolean isEndConnected() {
        return get(END_CONNECTOR)!=null&&get(END_TARGET)!=null;
    }
    default Point2D getStartTargetPoint() {
        if (isStartConnected()) {
        return worldToLocal(get(START_CONNECTOR).getPositionInWorld(this, get(START_TARGET)));
        }else{
            return get(START);
        }
    }
    default Point2D getEndTargetPoint() {
        if (isEndConnected()) {
        return worldToLocal(get(END_CONNECTOR).getPositionInWorld(this, get(END_TARGET)));
        }else{
            return get(END);
        }
    }
}