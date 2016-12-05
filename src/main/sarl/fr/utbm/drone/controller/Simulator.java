package fr.utbm.drone.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.utbm.drone.agent.EnvironmentAgent;
import fr.utbm.drone.environment.AbstractEnvObject;
import fr.utbm.drone.gui.DisplayManager;
import fr.utbm.drone.gui.IRenderEngine;
import fr.utbm.drone.gui.Scene;
public class Simulator {
	
	private IRenderEngine simuLogic;
	private DisplayManager graphicEngine;
	
	
	private float imgWidth;
	private float imgHeight;
	private final float envHeight=250f;
	private EnvironmentAgent env;
	
	
	public Simulator(String name, int width, int height) throws Exception{
		this.simuLogic = new Scene();
		this.graphicEngine=new DisplayManager(name, width, height, true, this.simuLogic);
		init();
	}
	
	public void start() throws IOException{
		graphicEngine.start();
	}
	
	private void init() throws Exception{
		BufferedImage image;
		JFileChooser mfChoose= new JFileChooser();
		JOptionPane.showMessageDialog(null,
			    "Please Choose a BitMap For Environment",
			    "Ok",
			    JOptionPane.INFORMATION_MESSAGE);
		int returnVal = mfChoose.showDialog(null, "Bitmap Environment");
		if(returnVal != JFileChooser.APPROVE_OPTION ){
			JOptionPane.showMessageDialog(null,
				    "Default Environment will be generate.",
				    "Ok",
				    JOptionPane.WARNING_MESSAGE);
			image = ImageIO.read(getClass().getResource("/default.bmp"));
		}
		else{
			image = ImageIO.read(mfChoose.getSelectedFile());
		}
		this.imgHeight=image.getHeight();
		this.imgWidth=image.getWidth();
		List<AbstractEnvObject> envItems=new LinkedList<AbstractEnvObject>();
		for (int x =0; x< this.imgHeight;x++){
			for(int y=0; y<this.imgWidth;y++){
				int pix= image.getRGB(x, y);
				if(pix==-1){
				//	envItems.add(null);
				}
			}
		}
	//	this.graphicEngine.updateItems(envItems);
		
		
	}
}
