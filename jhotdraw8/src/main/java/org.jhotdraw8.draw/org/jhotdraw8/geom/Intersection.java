/* @(#)Intersection.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 *
 * This class is a based on:
 *
 *  Polynomial.js by Kevin Lindsey.
 * Copyright (C) 2002, Kevin Lindsey.
 *
 * MgcPolynomial.cpp by David Eberly.
 * Copyright (c) 2000-2003 Magic Software, Inc.
 */
package org.jhotdraw8.geom;

import javafx.geometry.Point2D;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.annotation.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes the result of an intersection test.
 * <p>
 * This class is a port of Intersection.js by Kevin Lindsey. Part of
 * Intersection.js is based on MgcPolynomial.cpp written by David Eberly, Magic
 * Software. Inc.
 * <p>
 * References:
 * <p>
 * <a href="http://www.kevlindev.com/gui/index.htm">Intersection.js</a>,
 * Copyright (c) 2002, Kevin Lindsey.
 * <p>
 * <a href="http://www.magic-software.com">MgcPolynomial.cpp </a> Copyright
 * 2000-2003 (c) David Eberly. Magic Software, Inc.
 * <p>
 * <a href="http://pomax.github.io/bezierinfo/">A Primer on Bézier Curves</a>,
 * Copyright ©-2016 Mike "Pomax" Kamermansy.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class Intersection {

    public static class IntersectionPoint {
        /**
         * The value of the argument 't' of the first parametric function at the intersection.
         */
        final double t1;
        /**
         * The value of the argument 't' of the second parametric function at the intersection.
         */
        final double t2;
        /**
         * The point of intersection.
         */
        final Point2D point;
        /**
         * The tangent vector at the intersection of the first parametric function.
         * This vector is not normalized.
         */
        final Point2D tangent1;
        /**
         * The tangent vector at the intersection of the second parametric function.
         * This vector is not normalized.
         */
        final Point2D tangent2;

        public IntersectionPoint(Point2D point, double t1) {
            this(point, t1, new Point2D(1, 0), 0, new Point2D(0, -1));
        }

        public IntersectionPoint(Point2D point, double t1, Point2D tangent1, double t2, Point2D tangent2) {
            this.point = point;
            this.t1 = t1;
            this.tangent1 = tangent1;
            this.t2 = t2;
            this.tangent2 = tangent2;
        }

        public double getT1() {
            return t1;
        }

        public Point2D getPoint() {
            return point;
        }

        public Point2D getTangent1() {
            return tangent1;
        }

        public double getT2() {
            return t2;
        }

        public Point2D getTangent2() {
            return tangent2;
        }
    }


    @Nonnull
    private final List<IntersectionPoint> intersections;
    private final Status status;

    public Intersection(@Nonnull List<IntersectionPoint> intersections) {
        this(intersections.isEmpty() ? Status.NO_INTERSECTION : Status.INTERSECTION, intersections);
    }

    public Intersection(Status status) {
        this(status, Collections.emptyList());
    }

    public Intersection(Status status, @Nonnull List<IntersectionPoint> intersections) {
        if (status == Status.INTERSECTION && intersections.isEmpty()
                || status != Status.INTERSECTION && !intersections.isEmpty()) {
            throw new IllegalArgumentException("status=" + status + " intersections=" + intersections);
        }
        intersections.sort(Comparator.comparingDouble(IntersectionPoint::getT1));

        this.intersections = Collections.unmodifiableList(intersections);
        this.status = status;
    }

    @Nonnull
    public List<IntersectionPoint> getIntersections() {
        return intersections;
    }

    public Point2D getLastPoint() {
        return intersections.get(intersections.size() - 1).getPoint();
    }

    public double getLastT() {
        return intersections.get(intersections.size() - 1).getT1();
    }

    @Nullable
    public IntersectionPoint getLastIntersectionPoint() {
        return intersections.isEmpty() ? null : intersections.get(intersections.size() - 1);
    }

    public double getFirstT() {
        return intersections.get(0).getT1();
    }

    public List<Point2D> getPoints() {
        return intersections.stream().map(IntersectionPoint::getPoint).collect(Collectors.toList());
    }

    public Status getStatus() {
        return status;
    }

    public List<Double> getTs() {
        return intersections.stream().map(IntersectionPoint::getT1).collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return intersections.isEmpty();
    }

    public int size() {
        return intersections.size();
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Intersection{").append(status).append(", points=");
        boolean first = true;
        for (Point2D p : getPoints()) {
            if (first) {
                first = false;
            } else {
                b.append(' ');
            }
            b.append(p.getX()).append(',').append(p.getY());
        }
        b.append(", ts=").append(getTs()).append('}');
        return b.toString();
    }

    public enum Status {
        INTERSECTION,
        NO_INTERSECTION,
        NO_INTERSECTION_INSIDE,
        NO_INTERSECTION_OUTSIDE,
        NO_INTERSECTION_TANGENT,
        NO_INTERSECTION_COINCIDENT,
        NO_INTERSECTION_PARALLEL
    }
}
