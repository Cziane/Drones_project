/* 
 * $Id$
 * 
 * Copyright (c) 2011-15 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.drone.maths;

/** Mathematic and geometric utilities.
 *
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class MathUtil3 {
	
	/** PI
	 */
	public static final float PI = (float)Math.PI;

	/** E
	 */
	public static final float E = (float)Math.E;

	/** Epsilon value, smallest such that 1.0+EPSILON != 1.0
	 * <p>
	 * Given by the Java3D's implementation of the Matrix3d class.
	 */
	public static final float EPSILON = (float)1.110223024E-16;

	/** 2 * PI
	 */
	public static final float TWO_PI = 2f * PI;

	/**
	 * Compute the signed angle between two vectors.
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return the angle between <code>-PI</code> and <code>PI</code>.
	 */
	public static float signedAngle(float x1, float y1, float z1, float x2, float y2, float z2) {
		float length1 = (float)Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
		float length2 = (float)Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);

		if ((length1 == 0f) || (length2 == 0f))
			return Float.NaN;

		float cx1 = x1;
		float cy1 = y1;
		float cz1 = z1;
		float cx2 = x2;
		float cy2 = y2;
		float cz2 = z2;

		// A and B are normalized
		if (length1 != 1f) {
			cx1 /= length1;
			cy1 /= length1;
			cz1 /= length1;
		}

		if (length2 != 1f) {
			cx2 /= length2;
			cy2 /= length2;
			cz2 /= length2;
		}

		/*
		 * // First method // Angle // A . B = |A|.|B|.cos(theta) = cos(theta) float dot = x1 * x2 + y1 * y2; float angle = Math.acos(dot);
		 * 
		 * // On which side of A, B is located? if ((dot > -1) && (dot < 1)) { dot = MathUtil.determinant(x2, y2, x1, y1); if (dot < 0) angle = -angle; }
		 */

		// Second method
		// A . B = |A|.|B|.cos(theta) = cos(theta)
//		float cos = cx1 * cx2 + cy1 * cy2 + cz1 * cz2;//juste ?
//		// A x B = |A|.|B|.sin(theta).N = sin(theta) (where N is the unit vector perpendicular to plane AB)
//		float sin = cx1 * cy2 - cy1 * cx2 ; //?
//
//		float angle = (float)Math.atan2(sin, cos);
//
//		return angle;
		Vector3f v1 = new Vector3f(cx1, cy1, cz1);
		Vector3f v2 = new Vector3f(cx2, cy2, cz2);
		float angle = (float) Math.acos(v1.dot(v2));
		Vector3f cross = v1.cross(v2);
		float dir = v2.dot(cross.cross(v1));
		if (dir < 0) { // Or > 0
		  angle = -angle;
		}
		return angle;
	}

	/** Clamp the given angle in radians to {@code [0;2PI)}.
	 * 
	 * @param radians is the angle to clamp
	 * @return the angle in {@code [0;2PI)} range.
	 */
	public static float clampRadian(float radians) {
		return clampRadian(radians, 0f, TWO_PI);
	}
	
	/** Clamp the given angle in radians.
	 * 
	 * @param radians is the angle to clamp
	 * @param min is the min value of the range.
	 * @param max is the max value of the range.
	 * @return the angle in the given range.
	 */
	public static float clampRadian(float radians, float min, float max) {
		float r = radians;
		while (r<min) r += TWO_PI;
		while (r>=max) r -= TWO_PI;
		return r;
	}

	/** Clamp the given value to the given range.
	 * <p>
	 * If the value is outside the {@code [min;max]}
	 * range, it is clamp to the nearest bounding value
	 * <var>min</var> or <var>max</var>.
	 * 
	 * @param v is the value to clamp.
	 * @param min is the min value of the range.
	 * @param max is the max value of the range.
	 * @return the value in {@code [min;max]} range.
	 */
	public static float clamp(float v, float min, float max) {
		if (min<max) {
			if (v<min) return min;
			if (v>max) return max;
		}
		else {
			if (v>min) return min;
			if (v<max) return max;
		}
		return v;
	}

	/** Replies the min value in the given values.
	 *
	 * @return the min value.
	 */
	public static float min(float... values) {
		float min = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (min > values[i]) {
				min = values[i];
			}
		}
		return min;
	}

	/** Replies the max value in the given values.
	 *
	 * @return the max value.
	 */
	public static float max(float... values) {
		float max = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (max < values[i]) {
				max = values[i];
			}
		}
		return max;
	}

	/**
	 * Replies the projection a point on a segment.
	 * 
	 * @param p is the coordinate of the point to project
	 * @param s1 is the x-coordinate of the first line point.
	 * @param s2 is the x-coordinate of the second line point.
	 * @return the projection of the specified point on the line. If 
	 * equal to {@code 0}, the projection is equal to the first segment point. 
	 * If equal to {@code 1}, the projection is equal to the second segment point. 
	 * If inside {@code ]0;1[}, the projection is between the two segment points. 
	 * If inside {@code ]-inf;0[}, the projection is outside on the side of the 
	 * first segment point. If inside {@code ]1;+inf[}, the projection is 
	 * outside on the side of the second segment point.
	 */
	public static float projectsPointOnLine(Point3f p, Point3f s1, Point3f s2) {
		float r_numerator = (p.getX()-s1.getX())*(s2.getX()-s1.getX()) 
				+ (p.getY()-s1.getY())*(s2.getY()-s1.getY() + (p.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
		float r_denomenator = (s2.getX()-s1.getX())*(s2.getX()-s1.getX()) + (s2.getY()-s1.getY())*(s2.getY()-s1.getY() + (s2.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
		return r_numerator / r_denomenator;
	}
	
	private static float determinant(Tuple3f<?> a, Tuple3f<?> b, Tuple3f<?> c) {
		return a.getX()*(b.getY()*c.getZ() - b.getZ()*c.getY())
				- a.getY()*(b.getX()*c.getZ() - b.getZ()*c.getX())
				+ a.getZ()*(b.getX()*c.getY() - b.getY()*c.getX());
	
	}
	/**
	 * Replies one position factor for the intersection point between two lines.
	 * <p>
	 * Let line equations for L1 and L2:<br>
	 * <code>L1: P1 + factor1 * (P2-P1)</code><br>
	 * <code>L2: P3 + factor2 * (P4-P3)</code><br>
	 * If lines are intersecting, then<br>
	 * <code>P1 + factor1 * (P2-P1) = P3 + factor2 * (P4-P3)</code>
	 * <p>
	 * This function computes and replies <code>factor1</code>.
	 * 
	 * @param p1
	 *            is the coordinates of the first point of the first segment.
	 * @param p2
	 *            is the coordinates of the second point of the first segment.
	 * @param p3
	 *            is the coordinates of the first point of the second segment.
	 * @param p4
	 *            is the coordinates of the second point of the second segment.
	 * @return <code>factor1</code> or {@link Float#NaN} if no intersection.
	 */
	public static float getSegmentSegmentIntersectionFactor(Point3f p1, Point3f p2, Point3f p3, Point3f p4) {
		Vector3f v1 = p2.operator_minus(p1);
		Vector3f v2 = p4.operator_minus(p3);

//		// determinant is zero when parallel = det(L1,L2)
//		float det = determinant(v1, v2);
//		if (det == 0f) return Float.NaN;
		
		Vector3f n1 = v1.cross(v2);
		Vector3f n2 = p3.operator_minus(p1).cross(v2);
		
//		float divx = n1.getX()/n2.getX();
//		float divy = n1.getY()/n2.getY();
//		float divz = n1.getZ()/n2.getZ();
		
		if (n1.cross(n2).equals(new Vector3f(0, 0, 0))){
			return n2.getX()/n1.getX();
		}
		return Float.NaN;
		// Given line equations:
		// Pa = P1 + ua (P2-P1), and
		// Pb = P3 + ub (P4-P3)
		// and
		// V = (P1-P3)
		// then
		// ua = det(L2,V) / det(L1,L2)
		// ub = det(L1,V) / det(L1,L2)
//		Vector3f v3 = p1.operator_minus(p3);
//		float u = determinant(v1, v3) / det;
//		if (u < 0. || u > 1.) return Float.NaN;
//		u = determinant(v2, v3) / det;
//		return (u < 0. || u > 1.) ? Float.NaN : u;
	}
	
	/** Compute the distance between a point and a segment.
	 *
	 * @param p position of the point.
	 * @param s1 position of the first point of the segment.
	 * @param s2 position of the second point of the segment.
	 * @return the distance beetween the point and the segment.
	 */
	public static final float distancePointToSegment(Point3f p, Point3f s1, Point3f s2) {
//		float r_denominator = (s2.getX()-s1.getX())*(s2.getX()-s1.getX()) 
//				+ (s2.getY()-s1.getY())*(s2.getY()-s1.getY() 
//						+ (s2.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
//		if (r_denominator==0f) return p.distance(s1);
//		float r_numerator = (p.getX()-s1.getX())*(s2.getX()-s1.getX()) 
//				+ (p.getY()-s1.getY())*(s2.getY()-s1.getY() 
//						+ (p.getZ()-s1.getZ())*(s2.getZ()-s1.getZ()));
//		float ratio = r_numerator / r_denominator;
//
//		if (ratio<=0.) {
//			return (float)Math.sqrt((p.getX()-s1.getX())*(p.getX()-s1.getX()) 
//					+ (p.getY()-s1.getY())*(p.getY()-s1.getY()) 
//					+ (p.getZ()-s1.getZ())*(p.getZ()-s1.getZ()));
//		}
//
//		if (ratio>=1f) {
//			return (float)Math.sqrt((p.getX()-s2.getX())*(p.getX()-s2.getX()) 
//					+ (p.getY()-s2.getY())*(p.getY()-s2.getY()) 
//					+ (p.getZ()-s2.getZ())*(p.getZ()-s2.getZ()));
//		}
//
//		float s =  ((s1.getY()-p.getY())*(s2.getX()-s1.getX())
//				-(s1.getX()-p.getX())*(s2.getY()-s1.getY()) );
//		return (float)(Math.abs(s) * Math.sqrt(r_denominator));
		
		Vector3f s = new Vector3f(s2.getX()-s1.getX(), s2.getY()-s1.getY(), s2.getZ()-s1.getZ());
		Vector3f p_s1 = new Vector3f(s1.getX()-p.getX(), s1.getY()-p.getY(), s1.getZ()-p.getZ());
		
		Vector3f num = p_s1.cross(s);
		return num.length()/s.length();
	}
	
	/** Compute the distance between two segments.
	 *
	 * @param p position of the point.
	 * @param s1 position of the first point of the segment.
	 * @param s2 position of the second point of the segment.
	 * @return the distance beetween the segments.
	 */
	public static final float distanceSegmentToSegment(Point3f s1, Point3f s2, Point3f s3, Point3f s4) {
		float f = getSegmentSegmentIntersectionFactor(s1, s2, s3, s4);
		if (!Float.isNaN(f)) {
			return 0f;
		}
		float d1 = distancePointToSegment(s1, s3, s4);
		float d2 = distancePointToSegment(s2, s3, s4);
		float d3 = distancePointToSegment(s3, s1, s2);
		float d4 = distancePointToSegment(s4, s1, s2);
		return MathUtil.min(d1, d2, d3, d4);
	}

}
