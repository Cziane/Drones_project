package fr.utbm.drone.gui;

import java.util.LinkedList;
import java.util.List;

import fr.utbm.drone.environment.AbstractEnvObject;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.gui.graph.items.BuildingItem;
import fr.utbm.drone.gui.graph.items.DroneItem;
import fr.utbm.drone.gui.graph.items.GuiItem;

public class DisplayManager implements Runnable {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private final Window window;

    private final Thread simuLoopThread;

    private final Timer timer;

	private final IRenderEngine simLogic;

    private final MouseInput mouseInput;
    
    private List<AbstractEnvObject> items;
    
    private boolean newItems =false;

    public DisplayManager(String windowTitle, int width, int height, boolean vSync, IRenderEngine simLogic) throws Exception {
    	simuLoopThread = new Thread(this, "SIMU_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        mouseInput = new MouseInput();
        this.simLogic = simLogic;
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
        	simuLoopThread.run();
        } else {
        	simuLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        mouseInput.init(window);
        simLogic.init(window);
    }

    protected void gameLoop() throws Exception {
        float ellapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            ellapsedTime = timer.getEllapsedTime();
            accumulator += ellapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if ( !window.isvSync() ) {
                sync();
            }
        }
    }

    protected void cleanup() {
    	simLogic.cleanup();                
    }
    
    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() {
        mouseInput.input(window);
        simLogic.input(window, mouseInput);
    }
    public void updateItems(List<AbstractEnvObject> items) throws Exception{
    	this.items=items;
    	this.newItems=true;
    }
    
    protected void refreshItems() throws Exception
    {
    	if(newItems){
    		LinkedList<GuiItem> graph= new LinkedList<GuiItem>();
        	for(AbstractEnvObject obj : items)
        	{
        		if(obj.getType()==ObjectType.BUILDING){
        			GuiItem graphObj=new BuildingItem(obj.getBox().getWidth(), obj.getPosition().x, obj.getPosition().y, obj.getPosition().z);
        			graph.add(graphObj);
        			obj.setGraphi(graphObj);
        		}
        		else if(obj.getType()==ObjectType.DRONE){
        			GuiItem graphObj=new DroneItem(obj.getBox().getWidth(), obj.getPosition().x, obj.getPosition().y, obj.getPosition().z);
        			graph.add(graphObj);
        			obj.setGraphi(graphObj);
        			simLogic.setItemToFollow(graphObj);
        		}
        		
        	}
    		simLogic.addItem(graph);
    		newItems=false;
    	}
    }
    protected void update(float interval) throws Exception {
    	simLogic.update(interval, mouseInput);
    	refreshItems();
    }

    protected void render() {
    	simLogic.render(window);
        window.update();
    }
}
