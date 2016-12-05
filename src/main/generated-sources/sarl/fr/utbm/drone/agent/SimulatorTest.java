package fr.utbm.drone.agent;

import fr.utbm.drone.agent.DroneAgent;
import fr.utbm.drone.agent.EnvironmentAgent;
import fr.utbm.drone.environment.DroneEnvironment;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.environment.object.DroneBody;
import fr.utbm.drone.events.AgentReady;
import fr.utbm.drone.events.ReadyToStart;
import fr.utbm.drone.events.StartSimulation;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.Skill;
import io.sarl.util.OpenEventSpace;
import io.sarl.util.OpenEventSpaceSpecification;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;
import javax.vecmath.Point3f;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Willy KOUETE
 */
@SarlSpecification("0.4")
@SuppressWarnings("all")
public class SimulatorTest extends Agent {
  protected int waitingAgents = 0;
  
  protected DroneEnvironment env;
  
  protected UUID spaceId;
  
  protected UUID envId;
  
  protected int droneNum = 5;
  
  protected int buildingNum = 20;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.env = ((DroneEnvironment) _get);
    Object _get_1 = occurrence.parameters[1];
    this.spaceId = ((UUID) _get_1);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
    AgentContext _defaultContext = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext();
    OpenEventSpace espace = _defaultContext.<OpenEventSpace>getOrCreateSpaceWithID(this.spaceId, OpenEventSpaceSpecification.class);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
    UUID _spawn = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.spawn(EnvironmentAgent.class, this.env, this.spaceId);
    this.envId = _spawn;
    this.generateObjects();
  }
  
  protected void generateObjects() {
    try {
      Random rd = new Random();
      for (int i = 0; (i <= this.droneNum); i++) {
        float _nextFloat = rd.nextFloat();
        float _width = this.env.getWidth();
        float _multiply = (_nextFloat * _width);
        float _nextFloat_1 = rd.nextFloat();
        float _height = this.env.getHeight();
        float _multiply_1 = (_nextFloat_1 * _height);
        float _nextFloat_2 = rd.nextFloat();
        float _depth = this.env.getDepth();
        float _multiply_2 = (_nextFloat_2 * _depth);
        Point3f _point3f = new Point3f(_multiply, _multiply_1, _multiply_2);
        this.env.generateObjects(ObjectType.DRONE, _point3f);
      }
      for (int i = 0; (i <= this.buildingNum); i++) {
        float _nextFloat = rd.nextFloat();
        float _width = this.env.getWidth();
        float _multiply = (_nextFloat * _width);
        float _nextFloat_1 = rd.nextFloat();
        float _height = this.env.getHeight();
        float _multiply_1 = (_nextFloat_1 * _height);
        float _nextFloat_2 = rd.nextFloat();
        float _depth = this.env.getDepth();
        float _multiply_2 = (_nextFloat_2 * _depth);
        Point3f _point3f = new Point3f(_multiply, _multiply_1, _multiply_2);
        this.env.generateObjects(ObjectType.BUILDING, _point3f);
      }
      float _nextFloat = rd.nextFloat();
      float _width = this.env.getWidth();
      float _multiply = (_nextFloat * _width);
      float _nextFloat_1 = rd.nextFloat();
      float _height = this.env.getHeight();
      float _multiply_1 = (_nextFloat_1 * _height);
      float _nextFloat_2 = rd.nextFloat();
      float _depth = this.env.getDepth();
      float _multiply_2 = (_nextFloat_2 * _depth);
      Point3f _point3f = new Point3f(_multiply, _multiply_1, _multiply_2);
      this.env.generateObjects(ObjectType.TARGET, _point3f);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentReady$1(final AgentReady occurrence) {
    synchronized (this) {
      this.waitingAgents--;
      if ((this.waitingAgents <= 0)) {
        System.out.println("Requiring simulation to start ");
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
        StartSimulation _startSimulation = new StartSimulation();
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_startSimulation);
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
      }
    }
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$AgentReady$1(final AgentReady it, final AgentReady occurrence) {
    boolean _isFromMe = this.isFromMe(occurrence);
    boolean _not = (!_isFromMe);
    return _not;
  }
  
  @SyntheticMember
  private void $behaviorUnit$ReadyToStart$2(final ReadyToStart occurrence) {
    System.out.println("Spawning Agents... ");
    int _dronesNumber = this.env.getDronesNumber();
    this.waitingAgents = _dronesNumber;
    List<Object> agentParameters = CollectionLiterals.<Object>newArrayList(this.spaceId, this.envId);
    Iterable<DroneBody> _droneBodies = this.env.getDroneBodies();
    for (final DroneBody body : _droneBodies) {
      {
        Class<DroneAgent> agentType = DroneAgent.class;
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
        UUID _id = body.getId();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
        AgentContext _defaultContext = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext();
        Object[] _array = agentParameters.toArray();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(agentType, _id, _defaultContext, _array);
      }
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ReadyToStart(final ReadyToStart occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ReadyToStart$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentReady(final AgentReady occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$AgentReady$1(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentReady$1(occurrence));
    }
  }
  
  /**
   * Construct an agent.
   * @param builtinCapacityProvider - provider of the built-in capacities.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Inject
  @SyntheticMember
  public SimulatorTest(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Skill> S $setSkill(final S skill, final Class<? extends Capacity>... capacities) {
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    return super.$setSkill(skill, capacities);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Capacity> S clearSkill(final Class<S> capacity) {
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    return super.clearSkill(capacity);
  }
}
