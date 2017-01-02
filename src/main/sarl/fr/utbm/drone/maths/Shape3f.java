/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.utbm.drone.maths;

import java.io.Serializable;

import javax.vecmath.Point3f;
import org.eclipse.xtext.xbase.lib.Pure;
import org.joml.Vector3f;

/** 3D shape.
 *
 * @param <ST> is the type of the shape implementation.
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class Shape3f<ST extends Shape3f<? super ST>> implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 110789738572592611L;

	/** Replies if this shape is empty.
	 * The semantic associated to the state "empty"
	 * depends on the implemented shape. See the
	 * subclasses for details.
	 *
	 * @return <code>true</code> if the shape is empty;
	 * <code>false</code> otherwise.
	 */
	@Pure
	public abstract	boolean isEmpty();

	/** Clone this shape.
	 *
	 * @return the clone.
	 */
	@Override
	@Pure
	public abstract	ST clone();

	/** Reset this shape to be equivalent to
	 * an just-created instance of this shape type.
	 */
	public abstract void clear();

	/** Replies if the given point is inside this shape.
	 *
	 * @param pt the point to search for.
	 * @return <code>true</code> if the given shape is intersecting this
	 *     shape, otherwise <code>false</code>.
	 */
	@Pure
	public abstract boolean contains(Point3f pt);

	/** Replies the point on the shape that is closest to the given point.
	 *
	 * @param pt the reference point.
	 * @return the closest point on the shape; or the point itself
	 *     if it is inside the shape.
	 */
	@Pure
	public abstract Point3f getClosestPointTo(Point3f pt);

	/** Replies the point on the shape that is farthest to the given point.
	 *
	 * @param pt the reference point.
	 * @return the farthest point on the shape.
	 */
	@Pure
	public abstract Point3f getFarthestPointTo(Point3f pt);

	/** Set this shape with the attributes of the given shape.
	 *
	 * @param shape the shape.
	 */
	public abstract void set(ST shape);

	public abstract boolean contains(Shape3f<?> box);
	
	public abstract boolean intersects(Shape3f<?> s);

	public abstract Cube3f getBox();
	
	public abstract void setPosition(Point3f nCenter);

	public abstract ST translate(Vector3f r);

}
