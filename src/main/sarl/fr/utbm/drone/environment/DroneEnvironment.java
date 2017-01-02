package fr.utbm.drone.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.vecmath.Point3f;

import org.joml.Vector3f;

import fr.utbm.drone.environment.influence.Influence;
import fr.utbm.drone.environment.motions.MotionInfluence;
import fr.utbm.drone.environment.object.Building;
import fr.utbm.drone.environment.object.DroneBody;
import fr.utbm.drone.environment.object.Target;
import fr.utbm.drone.environment.storage.OcTree;
import fr.utbm.drone.maths.Cube3f;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Shape3f;
import fr.utbm.drone.maths.Sphere3f;
import fr.utbm.drone.physics.PhysicEngine;
import fr.utbm.drone.time.TimeManager;

public class DroneEnvironment {
	private final Map<UUID,DroneBody> droneBodies = new TreeMap<UUID,DroneBody>();
	private final List<AbstractEnvObject> allObjects = new ArrayList<>();
	private final TimeManager timeManager;
	private final float width;
	private final float height = 800.0f;
	private final float depth;
	private int dronesNumber = 0;
	private int buildingsNumber = 0;
	private OcTree storage;
	private AtomicBoolean stateChanged = new AtomicBoolean(false);
	private AtomicBoolean perceiving = new AtomicBoolean(true);
	private PhysicEngine physicController;
	
	public DroneEnvironment(TimeManager time, float width, float depth){
		//System.out.print("\nEnvironment: Width: "+width+" Height: "+height+" Depth: "+depth);
		this.width = width;
		this.depth = depth;
		this.timeManager = time;
		this.storage = new OcTree(
				new Cube3f(
						new Point3f(0.0f, 0.0f, 0.0f), 
						new Point3f(width, height, depth)
						)
				);
		this.physicController= new PhysicEngine(width, width, depth);
	}
	
	public void generateObjects(ObjectType type, Point3f pos) throws Exception{
		//Ensure that the Objects position is inside the environment
		if(pos.x > this.width || pos.y > this.height || pos.z > this.depth)
			throw new Exception("\nThe object's position is out side environment bounds!!\n Object position: "+pos.toString()+" \n Environment bound: (Width, Height, Depth) = ("+this.width+", "+this.height+", "+this.depth+")\n");
		AbstractEnvObject obj=null;
		if(type == ObjectType.DRONE){
			obj=this.generateDrones(pos);
			physicController.addItem(obj.getPhysic());
		}
		else if(type == ObjectType.BUILDING){
			obj=this.generateBuildings(pos);
			physicController.addItem(obj.getPhysic());
		}
		else if(type == ObjectType.TARGET){
			obj=this.generateTarget(pos);
		}
	}
	
	private AbstractEnvObject generateDrones(Point3f pos) {
		//for(int i = 0; i< dronesNumber; i++){
			UUID owner = UUID.randomUUID();
			DroneBody dBody= new DroneBody(pos,
					owner,
					new Cube3f(
							new Point3f(pos), 
							new Point3f(
									pos.x+EnvironmentLaws.DRONE_SIZE.x, 
									pos.y+EnvironmentLaws.DRONE_SIZE.y, 
									pos.z+EnvironmentLaws.DRONE_SIZE.z
							)
					),
					EnvironmentLaws.MAX_DRONE_LINEAR_SPEED,
					EnvironmentLaws.MAX_DRONE_LINEAR_ACCELERATION,
					EnvironmentLaws.MAX_DRONE_ANGULAR_SPEED,
					EnvironmentLaws.MAX_DRONE_ANGULAR_ACCELERATION);
			dBody.setFrustum(new Sphere3f(pos, width/4));
			dBody.setName("Drone "+ ++dronesNumber);
			//System.out.print("\nObject Shape: "+dBody.getShape().toString());
			addObject(dBody);
			this.droneBodies.put(owner, dBody);
			
			this.allObjects.add(dBody);
			return dBody;
			
		//}		
	}

	private AbstractEnvObject generateBuildings(Point3f pos) {
		
		//for(int i = 0; i<=  this.buildingsNumber; i++){
			UUID owner = UUID.randomUUID();
			Building b = new Building(
					pos, 
					new Cube3f(
							new Point3f(pos), 
							new Point3f(
									pos.x+EnvironmentLaws.BUILDING_SIZE.x, 
									pos.y+EnvironmentLaws.BUILDING_SIZE.y, 
									pos.z+EnvironmentLaws.BUILDING_SIZE.z
							)
					),
					owner);
			//System.out.print("\nObject Shape: "+b.getShape().toString());
			addObject(b);
			this.allObjects.add(b);
			return b;
		//}
	}

	private AbstractEnvObject generateTarget(Point3f pos) {
		
		//for(int i = 0; i<=  this.buildingsNumber; i++){
			UUID owner = UUID.randomUUID();
			Target t = new Target(
					pos, 
					new Cube3f(
							new Point3f(pos), 
							new Point3f(
									pos.x+EnvironmentLaws.BUILDING_SIZE.x, 
									pos.y+EnvironmentLaws.BUILDING_SIZE.y, 
									pos.z+EnvironmentLaws.BUILDING_SIZE.z
							)
					),
					owner);
			t.setPosition(pos);
			//System.out.print("\nObject Shape: "+b.getShape().toString());
			addObject(t);
			this.allObjects.add(t);
			return t;
		//}
	}

	public void addObject(AbstractEnvObject obj){
		this.storage.addObject(obj);
	}

	protected void onAgentBodyCreated(AbstractAgentBody body) {
		// TODO Auto-generated method stub

	}

	protected void onAgentBodyDestroyed(AbstractAgentBody body) {
		// TODO Auto-generated method stub
	}

	protected List<Influence> computeEndogenousBehaviorInfluences() {
		// TODO Auto-generated method stub
		return null;
	}

	public  List<Percept> computePerceptionsFor(AbstractAgentBody agent) {
		Shape3f<?> f = agent.getFrustum();
		List<AbstractEnvObject> perceived = (List<AbstractEnvObject>) storage.findPerceivedObjects(f);
		List<Percept> perceptions = new ArrayList<>();
		//System.out.println(perceived.size());
		for(AbstractEnvObject o:perceived){
			perceptions.add(new Percept(o));
		}
		return perceptions;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @return the dronesNumber
	 */
	public int getDronesNumber() {
		return dronesNumber;
	}
	
	public DroneBody getDroneBodyFor(UUID droneId) {
		return this.droneBodies.get(droneId);
	}

	/**
	 * @return All the objects in the storage, with a readonly format
	 */
	public List<AbstractEnvObject> getAllObjects() {
		return allObjects;
	}

	/**
	 * @return the timeManager
	 */
	public TimeManager getTimeManager() {
		return timeManager;
	}

	public void applyInfluences(Collection<MotionInfluence> motionInfluences, Collection<Influence> otherInfluences,
			TimeManager timeManager) {
		List<MoveAction> moves = new ArrayList<>();
		for(MotionInfluence mi:motionInfluences){
			DroneBody concerned = getDroneBodyFor(mi.getEmitter());
			//TODO: Compute physic output motion
			Vector3f li = mi.getLinearInfluence();
			Orientation3f ai = new Orientation3f(
					mi.getLateralAngularInfluence(),
					mi.getElevationAngularInfluence()
					);
			//TODO: Store the move result in a MoveAction object and push it in the moves List
			moves.add(new MoveAction(concerned, li, ai));
		}
		
			//TODO: Manage collision avoidance
		for(MoveAction m:moves){
			// Set the move at 0 if there is a presumed collision
			for(MoveAction om:moves){
				if(m != om 
						&& 
					m.getBody()
						.getShape()
						.translate(m.getDirection())
						.intersects(
								om.getBody()
								.getShape()
								.translate(om.getDirection())
								)
						){
					m.setDirection(new Vector3f(0.0f, 0.0f, 0.0f));
				}
			}
			m.performAction();
		}
	}
	

	/**
	 * @return the droneBodies
	 */
	public Iterable<DroneBody> getDroneBodies() {
		return Collections.unmodifiableCollection(droneBodies.values());
	}

	public void simulate() {
		
		Collection<MotionInfluence> motionInfluences = new ArrayList<>();
		Collection<Influence> otherInfluences = new ArrayList<>();
		for(DroneBody body : this.droneBodies.values()) {
			MotionInfluence mi = body.consumeMotionInfluence();
			if (mi != null) {
				motionInfluences.add(mi);
			}
			
		}

		if (!motionInfluences.isEmpty() || !otherInfluences.isEmpty()) {
			applyInfluences(
					motionInfluences,
					otherInfluences,
					this.timeManager);
			//System.out.println("\n Environment::Simulate(): Calling ApplyInfluence()... ");
		}

		notifyEnvironmentChange();

		this.timeManager.increment();
		
		for(DroneBody body:getDroneBodies()){
			  List<Percept> perceptions = computePerceptionsFor(body);
			  body.setPerceptions(perceptions);
		}
		applyPhysic();
	}
	
	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector3f computeSteeringTranslation(MobileObject obj, Vector3f move, TimeManager clock) {
		if (obj instanceof AbstractDynamicObject) {
			AbstractDynamicObject o = (AbstractDynamicObject)obj;
			return o.computeSteeringTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected final Vector3f computeKinematicTranslation(MobileObject obj, Vector3f move, TimeManager clock) {
		if (obj instanceof AbstractDynamicObject) {
			AbstractDynamicObject o = (AbstractDynamicObject)obj;
			return o.computeKinematicTranslation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final Orientation3f computeKinematicRotation(MobileObject obj, Orientation3f move, TimeManager clock) {
		if (obj instanceof AbstractDynamicObject) {
			AbstractDynamicObject o = (AbstractDynamicObject)obj;
			return o.computeKinematicRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param obj is the object to move.
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected final Orientation3f computeSteeringRotation(MobileObject obj, Orientation3f move, TimeManager clock) {
		if (obj instanceof AbstractDynamicObject) {
			AbstractDynamicObject o = (AbstractDynamicObject)obj;
			return o.computeSteeringRotation(move, clock);
		}
		throw new IllegalArgumentException("obj"); //$NON-NLS-1$
	}

	private void notifyEnvironmentChange() {
		/*synchronized(this.stateChanged){
			this.stateChanged.set(true);
		}*/
	}
	public void applyPhysic(){
		physicController.stepSimulation(timeManager.getCurrentTime()/1000, 5);
		for(AbstractEnvObject obj :allObjects){
			obj.applyTransform();
			if((obj instanceof DroneBody)){
				obj.getPhysic().getBody().applyGravity();
			}
		}
	}

	private class MoveAction{
		private DroneBody body;
		private Vector3f direction = new Vector3f();
		private Orientation3f orientation;
		/**
		 * @param body
		 * @param direction
		 * @param orientation
		 */
		public MoveAction(DroneBody body, Vector3f direction, Orientation3f orientation) {
			super();
			this.body = body;
			this.direction = direction;
			this.orientation = orientation;
		}
		/**
		 * @param body
		 * @param orientation
		 */
		public MoveAction(DroneBody body, Orientation3f orientation) {
			this(body, new Vector3f(0.0f, 0.0f, 0.0f), orientation);
		}
		/**
		 * @param body
		 * @param direction
		 */
		public MoveAction(DroneBody body, Vector3f direction) {
			this(body, direction, new Orientation3f());
		}
		/**
		 * @param body
		 */
		public MoveAction(DroneBody body) {
			this(body, new Vector3f(0.0f, 0.0f, 0.0f), new Orientation3f());
		}
		
		/** Move the given object.
		 * 
		 * 
		 */
		public void performAction(){
			if (body instanceof AbstractDynamicObject) {
				AbstractDynamicObject o = body;
				float duration = timeManager.getLastStepDuration();
				o.move(direction);
				//o.rotate(orientation, duration);
				storage.update(body);
				//System.out.println("\n : Accorded position: "+o.getPosition());
				notifyEnvironmentChange();
			}
			else {
				throw new IllegalArgumentException("obj"); //$NON-NLS-1$
			}
		}
		/**
		 * @return the body
		 */
		public DroneBody getBody() {
			return body;
		}
		/**
		 * @param body the body to set
		 */
		public void setBody(DroneBody body) {
			this.body = body;
		}
		/**
		 * @return the direction
		 */
		public Vector3f getDirection() {
			return direction;
		}
		/**
		 * @param direction the direction to set
		 */
		public void setDirection(Vector3f direction) {
			this.direction = direction;
		}
		/**
		 * @return the orientation
		 */
		public Orientation3f getOrientation() {
			return orientation;
		}
		/**
		 * @param orientation the orientation to set
		 */
		public void setOrientation(Orientation3f orientation) {
			this.orientation = orientation;
		}
		
	}
}
