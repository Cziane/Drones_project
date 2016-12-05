/**
 * 
 */
package fr.utbm.drone.controller;

import java.util.UUID;

import fr.utbm.drone.agent.SimulatorTest;
import fr.utbm.drone.environment.DroneEnvironment;
import fr.utbm.drone.time.StepTimeManager;
import io.janusproject.Boot;

/**
 * @author perso
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DroneEnvironment env = new DroneEnvironment(new StepTimeManager(500), 500, 600);
		UUID sID = UUID.randomUUID();
		Boot.startJanus(
				(Class) null,
				SimulatorTest.class,
				env,
				sID);
	}

}
