package fr.utbm.drone.physics;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

import fr.utbm.drone.environment.EnvironmentLaws;

public class physicBuidling extends physicItem {

	public physicBuidling(Vector3f position) {
		super( EnvironmentLaws.BUILDING_SIZE, EnvironmentLaws.BUILDING_MASS, EnvironmentLaws.BUILDING_INERTIA, new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0,0,0,1),position,1.0f))));
	}

	@Override
	public void setUpImpulsion(Vector3f imp){
		imp.y=imp.y*EnvironmentLaws.BUILDING_MASS;
		this.myBody.applyCentralImpulse(imp);
	}

}
