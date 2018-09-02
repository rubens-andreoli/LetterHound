package org.scribble;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.StringTokenizer;

public class Scribble implements Shape, Serializable {
  
    protected double[] points = new double[64]; // The scribble data
    protected int numPoints = 0; // The current number of points

    double maxX = Double.NEGATIVE_INFINITY; // The bounding box

    double maxY = Double.NEGATIVE_INFINITY;

    double minX = Double.POSITIVE_INFINITY;

    double minY = Double.POSITIVE_INFINITY;

    /**
     * Begin a new polyline at (x,y). Note the use of Double.NaN in the points
     * array to mark the beginning of a new polyline
     */
    public void moveto(double x, double y) {
      if (numPoints + 3 > points.length)
        reallocate();
      // Mark this as the beginning of a new line
      points[numPoints++] = Double.NaN;
      // The rest of this method is just like lineto();
      lineto(x, y);
    }

    /**
     * Add the point (x,y) to the end of the current polyline
     */
    public void lineto(double x, double y) {
      if (numPoints + 2 > points.length)
        reallocate();
      points[numPoints++] = x;
      points[numPoints++] = y;

      // See if the point enlarges our bounding box
      if (x > maxX)
        maxX = x;
      if (x < minX)
        minX = x;
      if (y > maxY)
        maxY = y;
      if (y < minY)
        minY = y;
    }

    /**
     * Append the Scribble s to this Scribble
     */
    public void append(Scribble s) {
      int n = numPoints + s.numPoints;
      double[] newpoints = new double[n];
      System.arraycopy(points, 0, newpoints, 0, numPoints);
      System.arraycopy(s.points, 0, newpoints, numPoints, s.numPoints);
      points = newpoints;
      numPoints = n;
      minX = Math.min(minX, s.minX);
      maxX = Math.max(maxX, s.maxX);
      minY = Math.min(minY, s.minY);
      maxY = Math.max(maxY, s.maxY);
    }

    /**
     * Translate the coordinates of all points in the Scribble by x,y
     */
    public void translate(double x, double y) {
      for (int i = 0; i < numPoints; i++) {
        if (Double.isNaN(points[i]))
          continue;
        points[i++] += x;
        points[i] += y;
      }
      minX += x;
      maxX += x;
      minY += y;
      maxY += y;
    }

    /** An internal method to make more room in the data array */
    protected void reallocate() {
      double[] newpoints = new double[points.length * 2];
      System.arraycopy(points, 0, newpoints, 0, numPoints);
      points = newpoints;
    }

    /** Convert the scribble data to a textual format */
    public String toString() {
      StringBuffer b = new StringBuffer();
      for (int i = 0; i < numPoints; i++) {
        if (Double.isNaN(points[i])) {
          b.append("m ");
        } else {
          b.append(points[i]);
          b.append(' ');
        }
      }
      return b.toString();
    }

    /**
     * Create a new Scribble object and initialize it by parsing a string of
     * coordinate data in the format produced by toString()
     */
    public static Scribble parse(String s) throws NumberFormatException {
      StringTokenizer st = new StringTokenizer(s);
      Scribble scribble = new Scribble();
      while (st.hasMoreTokens()) {
        String t = st.nextToken();
        if (t.charAt(0) == 'm') {
          scribble.moveto(Double.parseDouble(st.nextToken()), Double
              .parseDouble(st.nextToken()));
        } else {
          scribble.lineto(Double.parseDouble(t), Double.parseDouble(st
              .nextToken()));
        }
      }
      return scribble;
    }

    // ========= The following methods implement the Shape interface ========

    /** Return the bounding box of the Shape */
    public Rectangle getBounds() {
      return new Rectangle((int) (minX - 0.5f), (int) (minY - 0.5f),
          (int) (maxX - minX + 0.5f), (int) (maxY - minY + 0.5f));
    }

    /** Return the bounding box of the Shape */
    public Rectangle2D getBounds2D() {
      return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }

    /** Our shape is an open curve, so it never contains anything */
    public boolean contains(Point2D p) {
      return false;
    }

    public boolean contains(Rectangle2D r) {
      return false;
    }

    public boolean contains(double x, double y) {
      return false;
    }

    public boolean contains(double x, double y, double w, double h) {
      return false;
    }

    /**
     * Determine if the scribble intersects the specified rectangle by testing
     * each line segment individually
     */
    public boolean intersects(Rectangle2D r) {
      if (numPoints < 4)
        return false;
      int i = 0;
      double x1, y1, x2 = 0.0, y2 = 0.0;
      while (i < numPoints) {
        if (Double.isNaN(points[i])) { // If we're beginning a new line
          i++; // Skip the NaN
          x2 = points[i++];
          y2 = points[i++];
        } else {
          x1 = x2;
          y1 = y2;
          x2 = points[i++];
          y2 = points[i++];
          if (r.intersectsLine(x1, y1, x2, y2))
            return true;
        }
      }

      return false;
    }

    /** Test for intersection by invoking the method above */
    public boolean intersects(double x, double y, double w, double h) {
      return intersects(new Rectangle2D.Double(x, y, w, h));
    }

    /**
     * Return a PathIterator object that tells Java2D how to draw this scribble
     */
    public PathIterator getPathIterator(AffineTransform at) {
      return new ScribbleIterator(at);
    }

    /**
     * Return a PathIterator that doesn't include curves. Ours never does.
     */
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
      return getPathIterator(at);
    }

    /**
     * This inner class implements the PathIterator interface to describe the
     * shape of a scribble. Since a Scribble is composed of arbitrary movetos
     * and linetos, we simply return their coordinates
     */
    public class ScribbleIterator implements PathIterator {
      protected int i = 0; // Position in array

      protected AffineTransform transform;

      public ScribbleIterator(AffineTransform transform) {
        this.transform = transform;
      }

      /** How to determine insideness and outsideness for this shape */
      public int getWindingRule() {
        return PathIterator.WIND_NON_ZERO;
      }

      /** Have we reached the end of the scribble path yet? */
      public boolean isDone() {
        return i >= numPoints;
      }

      /** Move on to the next segment of the path */
      public void next() {
        if (Double.isNaN(points[i]))
          i += 3;
        else
          i += 2;
      }

      /**
       * Get the coordinates of the current moveto or lineto as floats
       */
      public int currentSegment(float[] coords) {
        int retval;
        if (Double.isNaN(points[i])) { // If its a moveto
          coords[0] = (float) points[i + 1];
          coords[1] = (float) points[i + 2];
          retval = SEG_MOVETO;
        } else {
          coords[0] = (float) points[i];
          coords[1] = (float) points[i + 1];
          retval = SEG_LINETO;
        }

        // If a transform was specified, use it on the coordinates
        if (transform != null)
          transform.transform(coords, 0, coords, 0, 1);

        return retval;
      }

      /**
       * Get the coordinates of the current moveto or lineto as doubles
       */
      public int currentSegment(double[] coords) {
        int retval;
        if (Double.isNaN(points[i])) {
          coords[0] = points[i + 1];
          coords[1] = points[i + 2];
          retval = SEG_MOVETO;
        } else {
          coords[0] = points[i];
          coords[1] = points[i + 1];
          retval = SEG_LINETO;
        }
        if (transform != null)
          transform.transform(coords, 0, coords, 0, 1);
        return retval;
      }
    }
}           
         