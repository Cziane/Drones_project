package fr.utbm.drone.controller;

import java.util.UUID;

import io.janusproject.Boot;
import fr.utbm.drone.agents.Simulator;
public class MainProgramm {

	  public static void main(String[] args) throws Exception {
			UUID sID = UUID.randomUUID();
			Boot.setOffline(true);
			Boot.startJanus((Class)null, Simulator.class,sID);
		}
}
