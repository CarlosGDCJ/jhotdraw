/* @(#)LineFigure.java
 * Copyright © by the authors and contributors ofCollection JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.figure;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Transform;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.collection.ImmutableList;
import org.jhotdraw8.css.CssPoint2D;
import org.jhotdraw8.css.CssRectangle2D;
import org.jhotdraw8.css.CssSize;
import org.jhotdraw8.draw.connector.Connector;
import org.jhotdraw8.draw.connector.PathConnector;
import org.jhotdraw8.draw.handle.Handle;
import org.jhotdraw8.draw.handle.HandleType;
import org.jhotdraw8.draw.handle.PolyPointEditHandle;
import org.jhotdraw8.draw.handle.PolygonOutlineHandle;
import org.jhotdraw8.draw.key.Point2DListStyleableFigureKey;
import org.jhotdraw8.draw.locator.RelativeLocator;
import org.jhotdraw8.draw.render.RenderContext;
import org.jhotdraw8.geom.Shapes;
import org.jhotdraw8.geom.Transforms;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * A figure which draws a closed polygon.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class PolygonFigure extends AbstractLeafFigure
        implements StrokableFigure, FillableFigure, HideableFigure, StyleableFigure,
        LockableFigure, CompositableFigure, TransformableFigure, ResizableFigure,
        ConnectableFigure, PathIterableFigure {

    /**
     * The CSS type selector for this object is {@value #TYPE_SELECTOR}.
     */
    public final static String TYPE_SELECTOR = "Polygon";

    public final static Point2DListStyleableFigureKey POINTS = PolylineFigure.POINTS;

    public PolygonFigure() {
        this(0, 0, 1, 1);
    }

    public PolygonFigure(double startX, double startY, double endX, double endY) {
        set(POINTS, ImmutableList.of(new Point2D(startX, startY), new Point2D(endX, endY)));
    }

    public PolygonFigure(Point2D... points) {
        set(POINTS, ImmutableList.of(points));
    }

    @Nonnull
    @Override
    public Bounds getBoundsInLocal() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (Point2D p : getNonnull(POINTS)) {
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    @Nonnull
    public CssRectangle2D getCssBoundsInLocal() {
        return new CssRectangle2D(getBoundsInLocal());
    }

    @Nonnull
    @Override
    public PathIterator getPathIterator(AffineTransform tx) {
        return Shapes.pathIteratorFromPoints(getNonnull(POINTS).asList(), true, PathIterator.WIND_EVEN_ODD, tx);
    }

    @Override
    public void reshapeInLocal(@Nonnull Transform transform) {
        ArrayList<Point2D> newP = getNonnull(POINTS).toArrayList();
        for (int i = 0, n = newP.size(); i < n; i++) {
            newP.set(i, transform.transform(newP.get(i)));
        }
        set(POINTS, ImmutableList.ofCollection(newP));
    }

    @Override
    public void translateInLocal(CssPoint2D t) {
        ArrayList<Point2D> newP = getNonnull(POINTS).toArrayList();
        for (int i = 0, n = newP.size(); i < n; i++) {
            newP.set(i, newP.get(i).add(t.getConvertedValue()));
        }
        set(POINTS, ImmutableList.ofCollection(newP));
    }

    @Nonnull
    @Override
    public Node createNode(RenderContext drawingView) {
        return new Polygon();
    }

    @Override
    public void updateNode(@Nonnull RenderContext ctx, @Nonnull Node node) {
        Polygon lineNode = (Polygon) node;
        applyHideableFigureProperties(ctx, node);
        applyFillableFigureProperties(ctx, lineNode);
        applyStyleableFigureProperties(ctx, node);
        applyStrokableFigureProperties(ctx, lineNode);
        applyTransformableFigureProperties(ctx, node);
        applyCompositableFigureProperties(ctx, lineNode);
        final ImmutableList<Point2D> points = getStyled(POINTS);
        List<Double> list = new ArrayList<>(points.size() * 2);
        for (Point2D p : points) {
            if (p != null) {
                list.add(p.getX());
                list.add(p.getY());
            }
        }
        lineNode.getPoints().setAll(list);
        lineNode.applyCss();
    }

    @Nonnull
    @Override
    public Connector findConnector(@Nonnull Point2D p, Figure prototype) {
        return new PathConnector(new RelativeLocator(getBoundsInLocal(), p));
    }

    @Override
    public void createHandles(HandleType handleType, @Nonnull List<Handle> list) {
        if (handleType == HandleType.SELECT) {
            list.add(new PolygonOutlineHandle(this, POINTS, false, Handle.STYLECLASS_HANDLE_SELECT_OUTLINE));
        /*} else if (handleType == HandleType.MOVE) {
            list.add(new PolygonOutlineHandle(this, POINTS,false, Handle.STYLECLASS_HANDLE_MOVE_OUTLINE));
            for (int i = 0, n = get(POINTS).size(); i < n; i++) {
                list.add(new PolyPointMoveHandle(this, POINTS, i, Handle.STYLECLASS_HANDLE_MOVE));
            }*/
        } else if (handleType == HandleType.POINT) {
            list.add(new PolygonOutlineHandle(this, POINTS, true, Handle.STYLECLASS_HANDLE_POINT_OUTLINE));
            for (int i = 0, n = get(POINTS).size(); i < n; i++) {
                list.add(new PolyPointEditHandle(this, POINTS, i, Handle.STYLECLASS_HANDLE_POINT));
            }
        } else {
            super.createHandles(handleType, list);
        }
    }

    @Override
    public void reshapeInLocal(@Nonnull CssSize x, @Nonnull CssSize y, @Nonnull CssSize width, @Nonnull CssSize height) {
        reshapeInLocal(Transforms.createReshapeTransform(getBoundsInLocal(), x.getConvertedValue(), y.getConvertedValue(), width.getConvertedValue(), height.getConvertedValue()));

    }

    @Nonnull
    @Override
    public String getTypeSelector() {
        return TYPE_SELECTOR;
    }
}
