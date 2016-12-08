package fr.utbm.drone.gui;

import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import fr.utbm.drone.gui.graph.PointLight;
import fr.utbm.drone.gui.graph.items.Camera;
import fr.utbm.drone.gui.graph.items.GuiItem;
import fr.utbm.drone.gui.graph.items.Skybox;

public class Scene implements IRenderEngine {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private List<GuiItem> GuiItems;
    
    private GuiItem  itemFollow;

    private Vector3f ambientLight;

    private PointLight pointLight;
    
    private Skybox my_skybox;

    private static final float CAMERA_POS_STEP = 1.05f;

    public Scene() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        
        GuiItems = new LinkedList<GuiItem>();
        this.my_skybox=new Skybox();

        ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
        Vector3f lightColour = new Vector3f(20, 20, 20);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        float lightPos = pointLight.getPosition().z;
        if (window.isKeyPressed(GLFW_KEY_N)) {
            this.pointLight.getPosition().z = lightPos + 0.1f;
        } else if (window.isKeyPressed(GLFW_KEY_M)) {
            this.pointLight.getPosition().z = lightPos - 0.1f;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera position
    	if(itemFollow!=null){
    		camera.setPosition(itemFollow.getPosition().x-10,itemFollow.getPosition().y+15, itemFollow.getPosition().z);
    	}
        

        // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, GuiItems, my_skybox , ambientLight, pointLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GuiItem GuiItem : GuiItems) {
            GuiItem.getMesh().cleanUp();
        }
    }
    
 @Override
public void setCamera(Vector3f pos) {
	// TODO Auto-generated method stub
	 this.camera.setPosition(pos.x, pos.y, pos.z);
}
    
    @Override
    public void setItemToFollow(GuiItem fo) {
    	this.itemFollow=fo;
    	this.setCamera(fo.getPosition());
    	
    }

	@Override
	public void addItem(List<GuiItem> items) {
		this.GuiItems=items;
		
	}

}
