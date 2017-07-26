/* @(#)SimpleBezierFigure.java
 * Copyright (c) 2015 by the authors and contributors of JHotDraw.
 * You may only use this file in compliance with the accompanying license terms.
 */
package org.jhotdraw8.draw.figure;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.transform.Transform;
import org.jhotdraw8.collection.ImmutableObservableList;
import org.jhotdraw8.draw.connector.Connector;
import org.jhotdraw8.draw.connector.PathConnector;
import org.jhotdraw8.draw.handle.BezierControlPointEditHandle;
import org.jhotdraw8.draw.handle.BezierNodeEditHandle;
import org.jhotdraw8.draw.handle.BezierNodeMoveHandle;
import org.jhotdraw8.draw.handle.BezierNodeTangentHandle;
import org.jhotdraw8.draw.handle.BezierOutlineHandle;
import org.jhotdraw8.draw.handle.Handle;
import org.jhotdraw8.draw.handle.HandleType;
import org.jhotdraw8.draw.key.BezierNodeListStyleableFigureKey;
import org.jhotdraw8.draw.key.BooleanStyleableFigureKey;
import org.jhotdraw8.draw.key.DirtyBits;
import org.jhotdraw8.draw.key.DirtyMask;
import org.jhotdraw8.draw.locator.RelativeLocator;
import org.jhotdraw8.draw.render.RenderContext;
import org.jhotdraw8.geom.BezierNode;
import org.jhotdraw8.geom.BezierNodePath;
import org.jhotdraw8.geom.BezierPath;
import org.jhotdraw8.geom.Shapes;

/**
 * A {@link Figure} which draws a {@link BezierPath}.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SimpleBezierFigure extends AbstractLeafFigure
        implements StrokeableFigure, FillableFigure, TransformableFigure, HideableFigure,
        StyleableFigure, LockableFigure, CompositableFigure, ResizableFigure, ConnectableFigure,
        PathIterableFigure {

    public final static BezierNodeListStyleableFigureKey PATH = new BezierNodeListStyleableFigureKey("path", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT, DirtyBits.LAYOUT_OBSERVERS), ImmutableObservableList.emptyList());
    public final static BooleanStyleableFigureKey CLOSED = new BooleanStyleableFigureKey("closed", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT_OBSERVERS), false);
    /**
     * The CSS type selector for this object is {@value #TYPE_SELECTOR}.
     */
    public final static String TYPE_SELECTOR = "Bezier";

    @Override
    public Node createNode(RenderContext ctx) {
        return new Path();
    }

    @Override
    public Connector findConnector(Point2D p, Figure prototype) {
        return new PathConnector(new RelativeLocator(getBoundsInLocal(), p));
    }

    @Override
    public Bounds getBoundsInLocal() {
        // XXX should be cached
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (BezierNode p : get(PATH)) {
            minX = Math.min(minX, p.getMinX());
            minY = Math.min(minY, p.getMinY());
            maxX = Math.max(maxX, p.getMaxX());
            maxY = Math.max(maxY, p.getMaxY());
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    public int getNodeCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PathIterator getPathIterator(AffineTransform tx) {
        return new BezierNodePath(getStyled(PATH), getStyled(CLOSED), getStyled(FILL_RULE)).getPathIterator(tx);
    }

    public Point2D getPoint(int index, int coord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Point2D getPointOnPath(float f, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTypeSelector() {
        return TYPE_SELECTOR;
    }

    @Override
    public void reshapeInLocal(Transform transform) {
        ArrayList<BezierNode> newP = new ArrayList<>(get(PATH));
        for (int i = 0, n = newP.size(); i < n; i++) {
            newP.set(i, newP.get(i).transform(transform));
        }
        set(PATH, new ImmutableObservableList<>(newP));
    }

    @Override
    public void updateNode(RenderContext ctx, Node node) {
        Path pathNode = (Path) node;

        applyHideableFigureProperties(node);
        applyStyleableFigureProperties(ctx, node);
        applyStrokeableFigureProperties(pathNode);
        applyFillableFigureProperties(pathNode);
        applyTransformableFigureProperties(node);
        applyCompositableFigureProperties(pathNode);
        pathNode.setFillRule(getStyled(FILL_RULE));
        final List<PathElement> elements = Shapes.fxPathElementsFromAWT(new BezierNodePath(getStyled(PATH), getStyled(CLOSED), getStyled(FILL_RULE)).getPathIterator(null));
        /*        if (getStyled(CLOSED)) {
            elements.add(new ClosePath());
        }*/
        if (!pathNode.getElements().equals(elements)) {
            pathNode.getElements().setAll(elements);
        }

    }

    @Override
    public void createHandles(HandleType handleType, List<Handle> list) {
        if (handleType == HandleType.SELECT) {
            list.add(new BezierOutlineHandle(this, PATH, Handle.STYLECLASS_HANDLE_SELECT_OUTLINE));
        } else if (handleType == HandleType.MOVE) {
            list.add(new BezierOutlineHandle(this, PATH, Handle.STYLECLASS_HANDLE_MOVE_OUTLINE));
            for (int i = 0, n = get(PATH).size(); i < n; i++) {
                list.add(new BezierNodeMoveHandle(this, PATH, i, Handle.STYLECLASS_HANDLE_MOVE));
            }
        } else if (handleType == HandleType.POINT) {
            list.add(new BezierOutlineHandle(this, PATH, Handle.STYLECLASS_HANDLE_POINT_OUTLINE));
            for (int i = 0, n = get(PATH).size(); i < n; i++) {
                list.add(new BezierNodeTangentHandle(this, PATH, i, Handle.STYLECLASS_HANDLE_CONTROL_POINT_OUTLINE));
                list.add(new BezierControlPointEditHandle(this, PATH, i, BezierNode.C1_MASK, Handle.STYLECLASS_HANDLE_CONTROL_POINT));
                list.add(new BezierControlPointEditHandle(this, PATH, i, BezierNode.C2_MASK, Handle.STYLECLASS_HANDLE_CONTROL_POINT));
                list.add(new BezierNodeEditHandle(this, PATH, i, Handle.STYLECLASS_HANDLE_POINT));
            }
        } else {
            super.createHandles(handleType, list);
        }
    }

}