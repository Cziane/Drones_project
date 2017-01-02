package fr.utbm.drone.agent;

import fr.utbm.drone.environment.DroneEnvironment;
import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.Percept;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.MotionInfluence;
import fr.utbm.drone.environment.object.DroneBody;
import fr.utbm.drone.events.AgentDead;
import fr.utbm.drone.events.InfluenceEmited;
import fr.utbm.drone.events.PerceptionEvent;
import fr.utbm.drone.events.ReadyToStart;
import fr.utbm.drone.events.StartSimulation;
import fr.utbm.drone.events.StopSimulation;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Vector3f;
import fr.utbm.drone.time.TimeManager;
import fr.utbm.drone.time.TimePercept;
import fr.utbm.drone.util.AddressUUIDScope;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Skill;
import io.sarl.util.OpenEventSpace;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Willy KOUETE
 * 
 * This agent handles all the environment targetted event and requires the environment to performs the related action
 * Its initialization comes from the simulator it self
 */
@SarlSpecification("0.4")
@SuppressWarnings("all")
public class EnvironmentAgent extends Agent {
  protected DroneEnvironment environment;
  
  protected OpenEventSpace espace;
  
  protected Address myAdr;
  
  protected int leftSimulationLoop = 500000000;
  
  protected int leftAgent = 0;
  
  protected int droneNum = 5;
  
  protected int buildingNum = 20;
  
  protected boolean running = true;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.environment = ((DroneEnvironment) _get);
    Object _get_1 = occurrence.parameters[1];
    UUID spaceId = ((UUID) _get_1);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
    AgentContext _defaultContext = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext();
    OpenEventSpace _space = _defaultContext.<OpenEventSpace>getSpace(spaceId);
    this.espace = _space;
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
    EventListener _asEventListener = _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.asEventListener();
    this.espace.register(_asEventListener);
    UUID _iD = this.getID();
    Address _address = this.espace.getAddress(_iD);
    this.myAdr = _address;
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
    ReadyToStart _readyToStart = new ReadyToStart();
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_readyToStart);
  }
  
  @SyntheticMember
  private void $behaviorUnit$StartSimulation$1(final StartSimulation occurrence) {
    System.out.println("Starting simulation! ");
    Iterable<DroneBody> _droneBodies = this.environment.getDroneBodies();
    int _size = IterableExtensions.size(_droneBodies);
    this.leftAgent = _size;
    this.run();
  }
  
  @SyntheticMember
  private void $behaviorUnit$InfluenceEmited$2(final InfluenceEmited occurrence) {
    if (this.running) {
      BehaviourOutput bOut = occurrence.output;
      UUID emiter = occurrence.emiter;
      this.manageInfluence(bOut, emiter);
      this.run();
    }
  }
  
  protected void manageInfluence(final BehaviourOutput bo, final UUID e) {
    DynamicType _type = bo.getType();
    Vector3f _linear = bo.getLinear();
    float _x = _linear.getX();
    Vector3f _linear_1 = bo.getLinear();
    float _y = _linear_1.getY();
    Vector3f _linear_2 = bo.getLinear();
    float _z = _linear_2.getZ();
    org.joml.Vector3f _vector3f = new org.joml.Vector3f(_x, _y, _z);
    Orientation3f _angular = bo.getAngular();
    float _lateralAngle = _angular.getLateralAngle();
    Orientation3f _angular_1 = bo.getAngular();
    float _elevationAngle = _angular_1.getElevationAngle();
    MotionInfluence mi = new MotionInfluence(_type, e, _vector3f, _lateralAngle, _elevationAngle);
    DroneBody _droneBodyFor = this.environment.getDroneBodyFor(e);
    _droneBodyFor.influence(mi);
  }
  
  protected void run() {
    try {
      int nbL = 0;
      synchronized (this) {
        if ((this.leftSimulationLoop <= 0)) {
          this.running = false;
          this.stopSimulation();
        }
        int _minusMinus = this.leftSimulationLoop--;
        nbL = _minusMinus;
      }
      if (this.running) {
        this.environment.simulate();
        TimeManager _timeManager = this.environment.getTimeManager();
        float _simulationDelay = _timeManager.getSimulationDelay();
        long delay = ((long) _simulationDelay);
        if ((delay > 0)) {
          Thread.sleep(delay);
        }
        this.notifyAgentsOrDie();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected void stopSimulation() {
    StopSimulation event = new StopSimulation();
    event.setSource(this.myAdr);
    this.espace.emit(event);
  }
  
  protected void notifyAgentsOrDie() {
    boolean run = false;
    TimeManager _timeManager = this.environment.getTimeManager();
    float _currentTime = _timeManager.getCurrentTime();
    TimeManager _timeManager_1 = this.environment.getTimeManager();
    float _lastStepDuration = _timeManager_1.getLastStepDuration();
    final TimePercept timePercept = new TimePercept(_currentTime, _lastStepDuration);
    Iterable<DroneBody> _droneBodies = this.environment.getDroneBodies();
    for (final DroneBody body : _droneBodies) {
      {
        run = true;
        List<Percept> _perceptions = body.getPerceptions();
        Percept _percept = new Percept(body);
        PerceptionEvent event = new PerceptionEvent(_perceptions, _percept, timePercept);
        event.setSource(this.myAdr);
        UUID _id = body.getId();
        AddressUUIDScope _addressUUIDScope = new AddressUUIDScope(_id);
        this.espace.emit(event, _addressUUIDScope);
      }
    }
    if ((!run)) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentDead$3(final AgentDead occurrence) {
    this.leftAgent--;
    if ((this.leftAgent <= 0)) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
    }
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @Inline(value = "$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS")
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class);
    }
    return this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @Inline(value = "$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE")
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class);
    }
    return this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @Inline(value = "$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES")
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = getSkill(Schedules.class);
    }
    return this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  }
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @Inline(value = "$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS")
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = getSkill(Behaviors.class);
    }
    return this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$InfluenceEmited(final InfluenceEmited occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$InfluenceEmited$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentDead(final AgentDead occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentDead$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartSimulation(final StartSimulation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartSimulation$1(occurrence));
  }
  
  /**
   * Construct an agent.
   * @param builtinCapacityProvider - provider of the built-in capacities.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Inject
  @SyntheticMember
  public EnvironmentAgent(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Skill> S $setSkill(final S skill, final Class<? extends Capacity>... capacities) {
    this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = null;
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    return super.$setSkill(skill, capacities);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Capacity> S clearSkill(final Class<S> capacity) {
    this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = null;
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    return super.clearSkill(capacity);
  }
}
