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
package fr.utbm.drone.environment.motions;

import java.util.UUID;

import org.joml.Vector3f;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.Influence;


/**
 * Abstract implementation of a motion influence.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class MotionInfluence extends Influence {

	private static final long serialVersionUID = 5211035037875773230L;

	private final DynamicType type;
	private final Vector3f linearInfluence = new Vector3f();
	private float lateralAngularInfluence = 0f;
	private float elevationAngularInfluence = 0f;
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject, Vector3f linearInfluence, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluence);
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param linearInfluenceZ is the linear influence to apply on the object.
	 * @param lateralAngularInfluence is the lateral angular influence to apply on the object.
	 * @param elevationAngularInfluence is the elevation angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject, float linearInfluenceX, float linearInfluenceY, float linearInfluenceZ, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY, linearInfluenceZ);
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject, Vector3f linearInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param linearInfluenceZ is the linear influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject, float linearInfluenceX, float linearInfluenceY, float linearInfluenceZ) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY, linearInfluenceZ);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param lateralAngularInfluence is the lateral angular influence to apply on the object.
	 * @param elevationAngularInfluence is the elevation angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(influencedObject);
		this.type = type;
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 */
	public MotionInfluence(DynamicType type, UUID influencedObject) {
		super(influencedObject);
		this.type = type;
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param lateralAngularInfluence is the lateral angular influence to apply on the object.
	 * @param elevationAngularInfluence is the elevation angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, Vector3f linearInfluence, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluence);
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param linearInfluenceZ is the linear influence to apply on the object.
	 * @param lateralAngularInfluence is the lateral angular influence to apply on the object.
	 * @param elevationAngularInfluence is the elevation angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, float linearInfluenceX, float linearInfluenceY, float linearInfluenceZ, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY, linearInfluenceZ);
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, Vector3f linearInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param linearInfluenceZ is the linear influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, float linearInfluenceX, float linearInfluenceY, float linearInfluenceZ) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY, linearInfluenceZ);
	}

	/**
	 * @return the lateralAngularInfluence
	 */
	public float getLateralAngularInfluence() {
		return lateralAngularInfluence;
	}

	/**
	 * @param lateralAngularInfluence the lateralAngularInfluence to set
	 */
	public void setLateralAngularInfluence(float lateralAngularInfluence) {
		this.lateralAngularInfluence = lateralAngularInfluence;
	}

	/**
	 * @return the elevationAngularInfluence
	 */
	public float getElevationAngularInfluence() {
		return elevationAngularInfluence;
	}

	/**
	 * @param elevationAngularInfluence the elevationAngularInfluence to set
	 */
	public void setElevationAngularInfluence(float elevationAngularInfluence) {
		this.elevationAngularInfluence = elevationAngularInfluence;
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param lateralAngularInfluence is the lateral angular influence to apply on the object.
	 * @param elevationAngularInfluence is the elevation angular influence to apply on the object.
	 */
	public MotionInfluence(DynamicType type, float lateralAngularInfluence, float elevationAngularInfluence) {
		super(null);
		this.type = type;
		setLateralAngularInfluence(lateralAngularInfluence);
		setElevationAngularInfluence(elevationAngularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 */
	public MotionInfluence(DynamicType type) {
		super(null);
		this.type = type;
	}

	/** Set the linear influence.
	 * 
	 * @param l is the linear influence
	 */
	public void setLinarInfluence(Vector3f l) {
		assert(l!=null);
		this.linearInfluence.set(l);
	}
		
	/** Set the linear influence.
	 * 
	 * @param dx is the linear influence
	 * @param dy is the linear influence
	 * @param dz is the linear influence
	 */
	public void setLinarInfluence(float dx, float dy, float dz) {
		this.linearInfluence.set(dx, dy, dz);
	}

	/** Set the angular influence.
	 * 
	 * @param lA the lateral angular
	 * @param eA the elevation angular
	 */
	public void setAngularInfluence(float lA, float eA) {
		this.lateralAngularInfluence = lA;
		this.elevationAngularInfluence = eA;
	}

	/** Replies the linear influence.
	 * 
	 * @return the linear influence
	 */
	public Vector3f getLinearInfluence() {
		return this.linearInfluence;
	}
		
	
	/** Replies the type of the influence.
	 * 
	 * @return the type of the influence.
	 */
	public DynamicType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.linearInfluence + "|(" + this.lateralAngularInfluence + ", " + this.elevationAngularInfluence + ")";
	}

}