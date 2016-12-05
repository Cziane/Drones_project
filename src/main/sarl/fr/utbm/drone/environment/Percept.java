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
package fr.utbm.drone.environment;

import java.io.Serializable;
import java.util.UUID;

import javax.vecmath.Point3f;

import org.joml.Vector3f;

import com.google.common.base.Objects;

import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Shape3f;

/**
 * Defined a perception unit.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Percept implements MobileObject, Serializable {

	private static final long serialVersionUID = -6553882246151660857L;

	private final UUID bodyId;
	private final UUID objectId;
	private Shape3f<?> shape;
	private Point3f position;
	private final Orientation3f angle;
	private final Serializable type;
	private final float maxLinearSpeed;
	private final float maxLinearAcceleration;
	private Vector3f currentLinearMotion;
	private final float maxAngularSpeed;
	private final float maxAngularAcceleration;
	private float currentElevationAngularSpeed = 0;
	private float currentLateralAngularSpeed = 0;
	private final String name;
	
	/**
	 * @param perceivedObject is the perceived object.
	 */
	public Percept(AbstractEnvObject perceivedObject) {
		this(perceivedObject, perceivedObject.getType());
	}
	
	/**
	 * @param perceivedObject is the perceived object.
	 * @param type the type of the object.
	 */
	public Percept(AbstractEnvObject perceivedObject, Serializable type) {
		this.objectId = perceivedObject.getId();
		this.bodyId = perceivedObject.getId();
		this.name = perceivedObject.getName();
		this.shape = perceivedObject.getShape();
		this.position = (Point3f) perceivedObject.getPosition().clone();
		if (type == null) {
			type = perceivedObject.getType();
			if (type == null) {
				type = perceivedObject.getClass().getName();
			}
		}
		this.type = type;
		if (perceivedObject instanceof MobileObject) {
			MobileObject mo = (MobileObject) perceivedObject;
			this.angle = mo.getAngle();
			this.maxAngularAcceleration = mo.getMaxAngularAcceleration();
			this.maxAngularSpeed = mo.getMaxAngularSpeed();
			this.maxLinearAcceleration = mo.getMaxLinearAcceleration();
			this.maxLinearSpeed = mo.getMaxLinearSpeed();
			this.currentElevationAngularSpeed = mo.getCurrentElevationAngularSpeed();
			this.currentLateralAngularSpeed = mo.getCurrentLateralAngularSpeed();
			this.currentLinearMotion = new Vector3f(mo.getCurrentLinearMotion());
		} else {
			this.angle = new Orientation3f();
			this.maxAngularAcceleration = 0f;
			this.maxAngularSpeed = 0f;
			this.maxLinearAcceleration = 0f;
			this.maxLinearSpeed = 0f;
			this.currentElevationAngularSpeed = 0f;
			this.currentLateralAngularSpeed = 0f;
			this.currentLinearMotion = new Vector3f();
		}
	}
	
	@Override
	public Percept clone() {
		try {
			Percept clone = (Percept) super.clone();
			clone.currentLinearMotion = new Vector3f(this.currentLinearMotion);
			clone.position = new Point3f(this.position);
			clone.shape = this.shape.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Percept) {
			Percept p = (Percept) obj;
			return Objects.equal(this.bodyId, p.bodyId)
				&& Objects.equal(this.objectId, p.objectId);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.bodyId, this.objectId);
	}
	
	public int compareTo(MobileObject o) {
		if (o instanceof Percept) {
			if (this.bodyId == ((Percept) o).getBodyID()) {
				return 0;
			}
			if (this.objectId ==((Percept)o).getID()) {
				return 0;
			}
		}
		return Integer.MAX_VALUE;
	}

	/** Replies the id of the body.
	 *
	 * @return the id of the body.
	 */
	public UUID getBodyID() {
		return this.bodyId;
	}

	/** Replies the id of the body.
	 *
	 * @return the id of the body.
	 */
	public UUID getID() {
		return this.objectId;
	}

	public Shape3f<?> getShape() {
		return this.shape;
	}

	public float getX() {
		return this.position.getX();
	}

	public float getY() {
		return this.position.getY();
	}

	public float getZ() {
		return this.position.getZ();
	}
	
	public Point3f getPosition() {
		return this.position;
	}

	@Override
	public Orientation3f getAngle() {
		return this.angle;
	}

	@Override
	public Vector3f getDirection() {
		return this.angle.getDirection();
	}

	@Override
	public float getMaxLinearSpeed() {
		return this.maxLinearSpeed;
	}

	@Override
	public float getMaxAngularSpeed() {
		return this.maxAngularSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return this.maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return this.maxAngularAcceleration;
	}

	@Override
	public float getCurrentElevationAngularSpeed() {
		return this.currentElevationAngularSpeed;
	}

	@Override
	public float getCurrentLateralAngularSpeed() {
		return this.currentLateralAngularSpeed;
	}
	
	@Override
	public float getCurrentLinearSpeed() {
		if (this.currentLinearMotion == null) {
			return 0;
		}
		return this.currentLinearMotion.length();
	}

	@Override
	public Vector3f getCurrentLinearMotion() {
		if (this.currentLinearMotion == null) {
			return new Vector3f();
		}
		return this.currentLinearMotion;
	}

	public Serializable getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		String msg = "";
		msg+="Type: "+this.type+"\n";
		msg+="Position: "+this.position.toString()+"\n";
		msg+="Shape: "+this.shape.toString()+"\n";
		/*if (this.name != null) {
			return this.name;
		}*/
		return msg;
	}
		
}