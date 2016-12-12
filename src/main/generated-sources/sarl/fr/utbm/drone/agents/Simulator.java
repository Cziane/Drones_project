package fr.utbm.drone.agents;

import fr.utbm.drone.agent.DroneAgent;
import fr.utbm.drone.agent.EnvironmentAgent;
import fr.utbm.drone.environment.AbstractEnvObject;
import fr.utbm.drone.environment.DroneEnvironment;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.environment.object.DroneBody;
import fr.utbm.drone.events.AgentReady;
import fr.utbm.drone.events.ReadyToStart;
import fr.utbm.drone.events.StartSimulation;
import fr.utbm.drone.gui.DisplayManager;
import fr.utbm.drone.gui.IRenderEngine;
import fr.utbm.drone.gui.Scene;
import fr.utbm.drone.time.StepTimeManager;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
public class Simulator extends Agent {
  protected int waitingAgents = 0;
  
  protected DroneEnvironment env;
  
  protected UUID spaceId;
  
  protected UUID envId;
  
  protected IRenderEngine simuLogic;
  
  protected DisplayManager graphicEngine;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.init();
      Object _get = occurrence.parameters[0];
      this.spaceId = ((UUID) _get);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
      AgentContext _defaultContext = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext();
      OpenEventSpace espace = _defaultContext.<OpenEventSpace>getOrCreateSpaceWithID(this.spaceId, OpenEventSpaceSpecification.class);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
      UUID _spawn = _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.spawn(EnvironmentAgent.class, this.env, this.spaceId);
      this.envId = _spawn;
      Scene _scene = new Scene();
      this.simuLogic = _scene;
      DisplayManager _displayManager = new DisplayManager("Drone Simulator", 800, 600, true, this.simuLogic);
      this.graphicEngine = _displayManager;
      this.graphicEngine.start();
      List<AbstractEnvObject> _allObjects = this.env.getAllObjects();
      this.graphicEngine.updateItems(_allObjects);
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
  
  protected void init() {
    try {
      BufferedImage image = null;
      JFileChooser mfChoose = new JFileChooser();
      JOptionPane.showMessageDialog(null, 
        "Please Choose a BitMap For Environment", 
        "Ok", 
        JOptionPane.INFORMATION_MESSAGE);
      int returnVal = mfChoose.showDialog(null, "Bitmap Environment");
      if ((returnVal != JFileChooser.APPROVE_OPTION)) {
        JOptionPane.showMessageDialog(null, 
          "Default Environment will be generate.", 
          "Ok", 
          JOptionPane.WARNING_MESSAGE);
        Class<? extends Simulator> _class = this.getClass();
        URL _resource = _class.getResource("/default.bmp");
        BufferedImage _read = ImageIO.read(_resource);
        image = _read;
      } else {
        File _selectedFile = mfChoose.getSelectedFile();
        BufferedImage _read_1 = ImageIO.read(_selectedFile);
        image = _read_1;
      }
      StepTimeManager _stepTimeManager = new StepTimeManager(500);
      int _width = image.getWidth();
      int _multiply = (_width * 16);
      int _height = image.getHeight();
      int _multiply_1 = (_height * 16);
      DroneEnvironment _droneEnvironment = new DroneEnvironment(_stepTimeManager, _multiply, _multiply_1);
      this.env = _droneEnvironment;
      for (int x = 0; (x < image.getHeight()); x++) {
        for (int z = 0; (z < image.getWidth()); z++) {
          {
            int pix = image.getRGB(x, z);
            if ((pix == (-1))) {
              Point3f _point3f = new Point3f((x * 16), 0, (z * 16));
              this.env.generateObjects(ObjectType.BUILDING, _point3f);
            }
          }
        }
      }
      Point3f _point3f = new Point3f(16, 100, 16);
      this.env.generateObjects(ObjectType.DRONE, _point3f);
      Point3f _point3f_1 = new Point3f(230, 50f, 30.0f);
      this.env.generateObjects(ObjectType.TARGET, _point3f_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
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
  public Simulator(final BuiltinCapacitiesProvider builtinCapacityProvider, final UUID parentID, final UUID agentID) {
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
