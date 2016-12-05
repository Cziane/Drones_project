package fr.utbm.drone.physics;

import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import fr.utbm.drone.environment.AbstractEnvObject;

public class PhysicEngine {
	private DynamicsWorld physicWorld;
	
	public PhysicEngine(float width, float height, float depth) {
		BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();
        physicWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        physicWorld.setGravity(new Vector3f(0, -9.81f,0 ));
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(0,0,0), 0.25f);
        MotionState groundMotionState= new DefaultMotionState(new Transform (new Matrix4f(new Quat4f(0,0,0,1.0f),new Vector3f(0,0,0), 1.0f)));
        RigidBodyConstructionInfo groundRbInf= new RigidBodyConstructionInfo(0, groundMotionState, groundShape,new Vector3f(0,0,0));
        groundRbInf.restitution=0.25f;
        RigidBody groundRb = new RigidBody(groundRbInf);
        physicWorld.addRigidBody(groundRb);
        CollisionShape skyboxShape = new BoxShape(new Vector3f(230.0f,230.0f,230.0f));
        
		
	}
	

	public void addItem(physicItem obj)
	{
		this.physicWorld.addRigidBody(obj.getBody());
	}
	
	public void removeItem(physicItem obj)
	{
		this.physicWorld.removeRigidBody(obj.getBody());
	}
	
	public void stepSimulation(float timeStep, int maxSubSteps){
		physicWorld.stepSimulation(timeStep, maxSubSteps);
		
	}
}
