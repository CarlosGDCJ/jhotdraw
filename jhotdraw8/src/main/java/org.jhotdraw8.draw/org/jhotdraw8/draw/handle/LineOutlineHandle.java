/* @(#)BoundsInLocalHandle.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.handle;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Transform;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.annotation.Nullable;
import org.jhotdraw8.css.CssColor;
import org.jhotdraw8.css.Paintable;
import org.jhotdraw8.draw.DrawingView;
import org.jhotdraw8.draw.figure.Figure;
import org.jhotdraw8.draw.figure.LineConnectionFigure;
import org.jhotdraw8.geom.Transforms;

/**
 * Draws the {@code wireframe} of a {@code LineFigure}, but does not provide any
 * interactions.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class LineOutlineHandle extends AbstractHandle {
    private Group node;
    private Polyline polyline2;
    private Polyline polyline1;
    private double[] points;
    private String styleclass;

    public LineOutlineHandle(Figure figure) {
        this(figure, STYLECLASS_HANDLE_MOVE_OUTLINE);
    }

    public LineOutlineHandle(Figure figure, String styleclass) {
        super(figure);
        node = new Group();
        points = new double[4];
        polyline1 = new Polyline(points);
        polyline2 = new Polyline(points);
        node.getChildren().addAll(polyline1, polyline2);
        this.styleclass = styleclass;
    }

    @Override
    public boolean contains(DrawingView dv, double x, double y, double tolerance) {
        return false;
    }

    @Nullable
    @Override
    public Cursor getCursor() {
        return null;
    }

    @Override
    public Node getNode(DrawingView view) {
        CssColor color = view.getHandleColor();
        polyline1.setStroke(Color.WHITE);
        polyline1.setStrokeWidth(3);
        polyline2.setStroke(Paintable.getPaint(color));
        return node;
    }



    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void updateNode(@Nonnull DrawingView view) {
        Figure f = getOwner();
        Transform t = Transforms.concat(view.getWorldToView(), f.getLocalToWorld());
        Bounds b = getOwner().getBoundsInLocal();
        points[0] = f.getNonnull(LineConnectionFigure.START).getX().getConvertedValue();
        points[1] = f.getNonnull(LineConnectionFigure.START).getY().getConvertedValue();
        points[2] = f.getNonnull(LineConnectionFigure.END).getX().getConvertedValue();
        points[3] = f.getNonnull(LineConnectionFigure.END).getY().getConvertedValue();

        t.transform2DPoints(points, 0, points, 0, 2);
        ObservableList<Double> pp1 = polyline1.getPoints();
        ObservableList<Double> pp2 = polyline2.getPoints();
        for (int i = 0; i < points.length; i++) {
            pp1.set(i, points[i]);
            pp2.set(i, points[i]);
        }
    }

}
