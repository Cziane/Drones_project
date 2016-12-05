package fr.utbm.drone.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.Transform;

import fr.utbm.drone.environment.EnvironmentLaws;

public class PhysicDrone extends physicItem {

	public PhysicDrone(Vector3f position) {
		super( EnvironmentLaws.DRONE_SIZE, EnvironmentLaws.DRONE_MASS, EnvironmentLaws.DRONE_INERTIA, new KinematicMotionState(new Transform(new Matrix4f(new Quat4f(0,0,0,1),position,1.0f))));
	}
	@Override
	public void setUpImpulsion(Vector3f imp){
		imp.y=imp.y*EnvironmentLaws.DRONE_MASS;
		this.myBody.applyCentralImpulse(imp);
	}
}
