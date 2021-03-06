/* @(#)AbstractLabelFigure.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.figure;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.annotation.Nullable;
import org.jhotdraw8.collection.Key;
import org.jhotdraw8.collection.ObjectKey;
import org.jhotdraw8.css.CssPoint2D;
import org.jhotdraw8.css.CssRectangle2D;
import org.jhotdraw8.css.CssSize;
import org.jhotdraw8.draw.connector.Connector;
import org.jhotdraw8.draw.connector.RectangleConnector;
import org.jhotdraw8.draw.key.CssPoint2DStyleableMapAccessor;
import org.jhotdraw8.draw.key.CssSizeStyleableFigureKey;
import org.jhotdraw8.draw.key.DirtyBits;
import org.jhotdraw8.draw.key.DirtyMask;
import org.jhotdraw8.draw.locator.RelativeLocator;
import org.jhotdraw8.draw.render.RenderContext;
import org.jhotdraw8.geom.Shapes;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.Objects;

/**
 * A Label that can be placed anywhere on a drawing.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public abstract class AbstractLabelFigure extends AbstractLeafFigure
        implements TextFillableFigure, FillableFigure, StrokableFigure,
        TextFontableFigure, TextLayoutableFigure, ConnectableFigure, PathIterableFigure, ShapeableFigure,
        PaddableFigure {

    public final static CssSizeStyleableFigureKey ORIGIN_X = new CssSizeStyleableFigureKey("originX", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), CssSize.ZERO);
    public final static CssSizeStyleableFigureKey ORIGIN_Y = new CssSizeStyleableFigureKey("originY", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), CssSize.ZERO);
    public final static CssPoint2DStyleableMapAccessor ORIGIN = new CssPoint2DStyleableMapAccessor("origin", ORIGIN_X, ORIGIN_Y);

    @Nonnull
    public final static Key<Bounds> BOUNDS_IN_LOCAL_CACHE_KEY = new ObjectKey<>("boundsInLocal", Bounds.class, null, true, true, null);

    public AbstractLabelFigure() {
        this(0, 0);
    }

    public AbstractLabelFigure(Point2D position) {
        this(position.getX(), position.getY());
    }

    public AbstractLabelFigure(double x, double y) {
        set(ORIGIN, new CssPoint2D(x, y));
        set(FILL, null);
        set(STROKE, null);
    }

    @Nonnull
    @Override
    public Node createNode(RenderContext drawingView) {
        Group g = new Group();
        g.setAutoSizeChildren(false);
        Path p = new Path();
        Text text = new Text();
        g.getChildren().addAll(p, text);
        return g;
    }

    @Override
    public Connector findConnector(@Nonnull Point2D p, Figure prototype) {
        return new RectangleConnector(new RelativeLocator(getBoundsInLocal(), p));
    }

    @Nonnull
    @Override
    public Bounds getBoundsInLocal() {
        Bounds boundsInLocal = getCachedValue(BOUNDS_IN_LOCAL_CACHE_KEY);
        return boundsInLocal == null ? getLayoutBounds() : boundsInLocal;
    }

    @Nonnull
    @Override
    public CssRectangle2D getCssBoundsInLocal() {
        return new CssRectangle2D(getBoundsInLocal());
    }

    /**
     * Returns the bounds of the node for layout calculations. These bounds
     * include the text of the node and the padding.
     *
     * @return the layout bounds
     */
    @Nonnull
    public Bounds getLayoutBounds() {
        Text textNode = new Text();
        updateTextNode(null, textNode);
        Bounds b = textNode.getLayoutBounds();
        Insets i = getStyledNonnull(PADDING).getConvertedValue();

        return new BoundingBox(
                b.getMinX() - i.getLeft(),
                b.getMinY() - i.getTop(),
                b.getWidth() + i.getLeft() + i.getRight(),
                textNode.getBaselineOffset() + i.getTop() + i.getBottom());
    }

    /**
     * Returns the bounds of the text node for layout calculations. These bounds
     * only includes the text - without padding.
     *
     * @param ctx the render context
     * @return the layout bounds of the text
     */
    @Nonnull
    protected Bounds getTextBounds(@Nullable RenderContext ctx) {
        Text textNode = new Text();
        updateTextNode(ctx, textNode);
        Bounds b = textNode.getLayoutBounds();
        return b;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform tx) {
        Text tn = new Text();
        tn.setText(getText(null));
        tn.setX(getStyledNonnull(ORIGIN).getX().getConvertedValue());
        tn.setY(getStyledNonnull(ORIGIN).getY().getConvertedValue());
        tn.setBoundsType(TextBoundsType.VISUAL);
        applyTextFontableFigureProperties(null, tn);
        applyTextLayoutableFigureProperties(null, tn);
        return Shapes.awtShapeFromFX(tn).getPathIterator(tx);
    }

    @Nullable
    protected abstract String getText(RenderContext ctx);


    @Override
    public void layout(@Nonnull RenderContext ctx) {
        Bounds b = getLayoutBounds();
        setCachedValue(BOUNDS_IN_LOCAL_CACHE_KEY, b);
    }

    @Override
    public void reshapeInLocal(@Nonnull CssSize x, @Nonnull CssSize y, @Nonnull CssSize width, @Nonnull CssSize height) {
        Bounds lb = getLayoutBounds();
        Insets i = getStyledNonnull(PADDING).getConvertedValue();
        set(ORIGIN, new CssPoint2D(x.getConvertedValue() + i.getLeft(), y.getConvertedValue() + lb.getHeight() - i.getBottom()));
    }

    @Override
    public void translateInLocal(@Nonnull CssPoint2D delta) {
        set(ORIGIN, getNonnull(ORIGIN).add(delta));
    }

    protected void updateGroupNode(RenderContext ctx, Group node) {

    }

    @Override
    public void updateNode(@Nonnull RenderContext ctx, @Nonnull Node node) {
        Group g = (Group) node;
        Path p = (Path) g.getChildren().get(0);
        Text t = (Text) g.getChildren().get(1);
        updateGroupNode(ctx, g);
        updatePathNode(ctx, p);
        updateTextNode(ctx, t);
    }

    protected void updatePathNode(RenderContext ctx, @Nonnull Path node) {
        applyFillableFigureProperties(ctx, node);
        applyStrokableFigureProperties(ctx, node);
        applyShapeableProperties(ctx, node);
    }

    protected void updateTextNode(RenderContext ctx, @Nonnull Text tn) {
        final String text = getText(ctx);
        if (!Objects.equals(text, tn.getText())) {
            tn.setText(text);
        }
        tn.setX(getStyledNonnull(ORIGIN_X).getConvertedValue());
        tn.setY(getStyledNonnull(ORIGIN_Y).getConvertedValue());
        applyTextFillableFigureProperties(ctx, tn);
        applyTextFontableFigureProperties(ctx, tn);
        applyTextLayoutableFigureProperties(ctx, tn);
    }

}
