/* @(#)Locator.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.locator;

import javafx.geometry.Point2D;
import javax.annotation.Nonnull;
import org.jhotdraw8.draw.figure.Figure;

/**
 * A <em>locator</em> encapsulates a strategy for locating a point on a
 * {@link Figure}.
 *
 * @design.pattern Locator Strategy, Strategy. {@link Locator} encapsulates a
 * strategy for locating a point on a {@link Figure}.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public interface Locator {

    /**
     * Locates a position on the provided figure.
     *
     * @param owner provided figure
     * @return a point on the figure in local coordinates.
     */
    @Nonnull
    public Point2D locate(@Nonnull Figure owner);

    /**
     * Locates a position on the provided figure relative to the dependent
     * figure.
     *
     * @param owner provided figure
     * @param dependent dependent figure
     * @return a point on the figure in local coordinates.
     */
    @Nonnull
    public Point2D locate(@Nonnull Figure owner, @Nonnull Figure dependent);
}
