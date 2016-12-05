package fr.utbm.drone.physics;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public abstract class physicItem {
	
	protected RigidBody myBody;
	
	protected physicItem(Vector3f size,float mass, Vector3f inertia,MotionState itemMotionState){
		CollisionShape itemShape = new BoxShape(size);
        RigidBodyConstructionInfo itemRbInf= new RigidBodyConstructionInfo(mass, itemMotionState, itemShape,inertia);
        itemRbInf.restitution=0.25f;
        this.myBody = new RigidBody(itemRbInf);
	}
	
	public void setRotation(Vector3f angl){
		this.myBody.setAngularVelocity(angl);
	}
	
	public void setLinearMotion(Vector3f mot){
		//this.myBody.setWorldTransform(new Transform(new Matrix4f(new rotateuat4f(),mot, 1.0f)));
		this.myBody.setLinearVelocity(mot);
	}
	
	public abstract void setUpImpulsion(Vector3f imp);
	
	
	public RigidBody getBody(){
		return this.myBody;
	}
	
	public Vector3f getPosition(){
		return this.myBody.getWorldTransform(new Transform()).origin;
	}
	
	public Vector3f getAngular(){
		Quat4f rotate=new Quat4f();
		rotate=this.myBody.getWorldTransform(new Transform()).getRotation(rotate);
		float roll = (float)Math.atan2(2*((rotate.x*rotate.y)+(rotate.z*rotate.w)),1-(2*((rotate.y*rotate.y)+(rotate.z*rotate.z))));
		float pitch =(float) Math.asin(2*(rotate.x*rotate.z-(rotate.w*rotate.y)));
		float yaw = (float)Math.atan2(2*((rotate.x*rotate.w)+(rotate.y*rotate.z)),1-(2*((rotate.z*rotate.z)+(rotate.w*rotate.w))));
		return new Vector3f(roll,pitch,yaw);
	}
	
	
	
	
}
