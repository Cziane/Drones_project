package fr.utbm.drone.agent;

import com.google.common.base.Objects;
import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.environment.Percept;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.events.AgentDead;
import fr.utbm.drone.events.AgentReady;
import fr.utbm.drone.events.InfluenceEmited;
import fr.utbm.drone.events.PerceptionEvent;
import fr.utbm.drone.events.StopSimulation;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import fr.utbm.drone.util.AddressUUIDScope;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
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
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author perso
 */
@SarlSpecification("0.4")
@SuppressWarnings("all")
public class DroneAgent extends Agent {
  protected Point3f target = new Point3f(230, 50f, 30.0f);
  
  protected Address myAdr;
  
  protected OpenEventSpace espace;
  
  protected UUID envId;
  
  protected Percept body;
  
  protected boolean agonize = false;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    UUID spaceId = ((UUID) _get);
    Object _get_1 = occurrence.parameters[1];
    this.envId = ((UUID) _get_1);
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
    System.out.println("Drone initialized! ");
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
    AgentReady _agentReady = new AgentReady();
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_agentReady);
  }
  
  @SyntheticMember
  private void $behaviorUnit$PerceptionEvent$1(final PerceptionEvent occurrence) {
    this.body = occurrence.body;
    Vector3f vr = new Vector3f(0.0f, 0.0f, 0.0f);
    List<Percept> perceptions = occurrence.perceptions;
    String _name = this.body.getName();
    String _plus = ("---------" + _name);
    String _plus_1 = (_plus + "----------\n");
    String _plus_2 = (_plus_1 + 
      "Current Position: ");
    javax.vecmath.Point3f _position = this.body.getPosition();
    String _plus_3 = (_plus_2 + _position);
    String _plus_4 = (_plus_3 + "\n");
    String msg = (_plus_4 + 
      "Perceptions: \n");
    for (final Percept o : perceptions) {
      boolean _notEquals = (!Objects.equal(o, occurrence.body));
      if (_notEquals) {
        if ((Objects.equal(o.getType(), ObjectType.BUILDING) || Objects.equal(o.getType(), ObjectType.DRONE))) {
          javax.vecmath.Point3f _position_1 = o.getPosition();
          float x = _position_1.x;
          javax.vecmath.Point3f _position_2 = o.getPosition();
          float y = _position_2.y;
          javax.vecmath.Point3f _position_3 = o.getPosition();
          float z = _position_3.z;
          float _x = this.body.getX();
          float _minus = (_x - x);
          float _y = this.body.getY();
          float _minus_1 = (_y - y);
          float _z = this.body.getZ();
          float _minus_2 = (_z - z);
          Vector3f repForce = new Vector3f(_minus, _minus_1, _minus_2);
          float _length = repForce.length();
          float _divide = (1 / _length);
          float _plus_5 = (_divide + 1);
          repForce.setLength(_plus_5);
          vr.operator_add(repForce);
        } else {
          Serializable _type = o.getType();
          boolean _equals = Objects.equal(_type, ObjectType.TARGET);
          if (_equals) {
            javax.vecmath.Point3f _position_4 = o.getPosition();
            javax.vecmath.Point3f _position_5 = o.getPosition();
            javax.vecmath.Point3f _position_6 = o.getPosition();
            Point3f _point3f = new Point3f(_position_4.x, _position_5.y, _position_6.z);
            this.target = _point3f;
          }
        }
      }
    }
    float _x_1 = this.target.getX();
    float _x_2 = this.body.getX();
    float _minus_3 = (_x_1 - _x_2);
    float _y_1 = this.target.getY();
    float _y_2 = this.body.getY();
    float _minus_4 = (_y_1 - _y_2);
    float _z_1 = this.target.getZ();
    float _z_2 = this.body.getZ();
    float _minus_5 = (_z_1 - _z_2);
    Vector3f va = new Vector3f(_minus_3, _minus_4, _minus_5);
    float _length_1 = va.length();
    float _plus_6 = (_length_1 + 1);
    va.setLength(_plus_6);
    float _y_3 = vr.getY();
    double _multiply = (0.7 * _y_3);
    float _y_4 = va.getY();
    double _multiply_1 = (0.3 * _y_4);
    double y_motion = (_multiply + _multiply_1);
    float _y_5 = vr.getY();
    double _multiply_2 = (0.7 * _y_5);
    float _y_6 = va.getY();
    double _multiply_3 = (0.3 * _y_6);
    double _plus_7 = (_multiply_2 + _multiply_3);
    boolean _lessThan = (_plus_7 < 0);
    if (_lessThan) {
      y_motion = 0.5f;
    }
    float _x_3 = vr.getX();
    double _multiply_4 = (0.7 * _x_3);
    float _x_4 = va.getX();
    double _multiply_5 = (0.3 * _x_4);
    double _plus_8 = (_multiply_4 + _multiply_5);
    float _z_3 = vr.getZ();
    double _multiply_6 = (0.7 * _z_3);
    float _z_4 = va.getZ();
    double _multiply_7 = (0.3 * _z_4);
    double _plus_9 = (_multiply_6 + _multiply_7);
    final Vector3f total = new Vector3f(_plus_8, y_motion, _plus_9);
    org.joml.Vector3f _direction = this.body.getDirection();
    org.joml.Vector3f _direction_1 = this.body.getDirection();
    org.joml.Vector3f _direction_2 = this.body.getDirection();
    final Vector3f direction = new Vector3f(_direction.x, _direction_1.y, _direction_2.z);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      it.setLinear(total);
    };
    BehaviourOutput b = ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
    float _x_5 = direction.getX();
    float _y_7 = direction.getY();
    float _z_5 = direction.getZ();
    float _x_6 = total.getX();
    float _y_8 = total.getY();
    float _z_6 = total.getZ();
    Orientation3f angle = Orientation3f.getOrientation(_x_5, _y_7, _z_5, _x_6, _y_8, _z_6);
    float _lateralAngle = angle.getLateralAngle();
    float _abs = Math.abs(_lateralAngle);
    boolean _lessEqualsThan = (_abs <= 0.05f);
    if (_lessEqualsThan) {
      angle.setLateralAngle(0.0f);
    }
    float _elevationAngle = angle.getElevationAngle();
    float _abs_1 = Math.abs(_elevationAngle);
    boolean _lessEqualsThan_1 = (_abs_1 <= 0.005f);
    if (_lessEqualsThan_1) {
      angle.setElevationAngle(0.0f);
    }
    b.setAngular(angle);
    InfluenceEmited inf = new InfluenceEmited(b);
    inf.setSource(this.myAdr);
    UUID _iD = this.body.getID();
    inf.emiter = _iD;
    inf.msg = msg;
    AddressUUIDScope _addressUUIDScope = new AddressUUIDScope(this.envId);
    this.espace.emit(inf, _addressUUIDScope);
  }
  
  @SyntheticMember
  private void $behaviorUnit$StopSimulation$2(final StopSimulation occurrence) {
    String _name = this.body.getName();
    String _plus = (_name + " Dying... ");
    System.out.println(_plus);
    AgentDead event = new AgentDead();
    event.setSource(this.myAdr);
    AddressUUIDScope _addressUUIDScope = new AddressUUIDScope(this.envId);
    this.espace.emit(event, _addressUUIDScope);
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
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
  private void $guardEvaluator$StopSimulation(final StopSimulation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StopSimulation$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PerceptionEvent(final PerceptionEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PerceptionEvent$1(occurrence));
  }
  
  /**
   * Construct an agent.
   * @param builtinCapacityProvider - provider of the built-in capacities.
   * @param parentID - identifier of the parent. It is the identifier of the parent agent and the enclosing contect, at the same time.
   * @param agentID - identifier of the agent. If <code>null</code> the agent identifier will be computed randomly.
   */
  @Inject
  @SyntheticMember
  public DroneAgent(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
    super(builtinCapacityProvider, parentID, agentID);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Skill> S $setSkill(final S skill, final Class<? extends Capacity>... capacities) {
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = null;
    return super.$setSkill(skill, capacities);
  }
  
  @SyntheticMember
  @Override
  protected <S extends Capacity> S clearSkill(final Class<S> capacity) {
    this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = null;
    this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = null;
    this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = null;
    return super.clearSkill(capacity);
  }
}
