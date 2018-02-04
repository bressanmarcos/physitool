package model;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import view.MainWindow;


public class Engine extends Thread {
	
	private MainWindow Observer;
	private ArrayList<MyObject> listObjects;
	private Color backgroundColor;
	
	private MyVector gravityConstant;
	private double f;		 // frequency of simulation
	private long t_wait; 	// speed of simulation
	
	public boolean showBarycenter;
	private boolean hasGravity;
	private boolean isPlaying;
	
	private MyVector pressedMouse; // Temporary
	private MyObject selectedObject;
	
	
	public Engine() {
		listObjects = new ArrayList<MyObject>();
		backgroundColor = new Color(1f,1f,1f);

		gravityConstant = new MyVector(0, -9.8);
		f = 60;
		t_wait = 10;
		
		hasGravity = true;
		showBarycenter = false;
		isPlaying = true;
	}
	public void addObserver(MainWindow Observer) {
		this.Observer = Observer;
	}
	
	/**
	 * it obtains corrected values of shapes (absolute values) to be shown in Frame
	 * @return
	 */
	public ArrayList<MyObject> getList() {
		return listObjects;
	}

	// Simulation Control
	public void run() {
		while(true) {
			if(isPlaying) {
				playStep();
				try {
					Thread.sleep(t_wait);
				} catch (InterruptedException e) {
					System.err.println("erro");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void playStep() {
		stepSimObjects();
		Observer.update();
	}
	public boolean play() {
		isPlaying = true;
		return true;
	}
	public boolean pause() {
		isPlaying = false;
		return true;
	}
	
	// MyObjects
	public MyObject addObject(MyVector[] points) {
		MyObject newObj = new MyObject(points);
		listObjects.add(newObj);
		return newObj;
	}
	public void addObject(MyObject obj) {
		listObjects.add(obj);
	}
	/**
	 * It realizes all the possible moves of the objects in scene.
	 * It should be able to detect intersections (using collision domains)
	 * , rotate objects, control velocities and displacements
	 * distribute energies
	 */
	private void stepSimObjects() {
		for (MyObject s : listObjects) {
			MyVector addAcc = new MyVector(0, 0);
			MyVector[] sVertices = s.getAbsolutePolygon().getVertices();
			int i;
			for(i = 0 ; i < sVertices.length ; i++) {
				if(sVertices[i].getY() < 0.0) {

					addAcc = s.getVelocity().times( -2 * f /* *s.getMass()*/ ).plus(gravityConstant.opposite());
					addAcc.setX(0); // applies only in Y
					
					break;
				}
			}
			
			s.setVelocity( s.getVelocity().plus(gravityConstant.plus(addAcc).times(1/f)) );
			s.setPosition( s.getPosition().plus(s.getVelocity().times(1/f)) );

		}	
	}
	/** System.out.println();
	 * 
	 * @param myVector
	 * @param option
	 * @return
	 * 
	 * It tests automatically if the saved object is not null
	 */
	private boolean moveSelectedObject(MyVector newPosition , int option) {
		if(selectedObject != null) {
			switch (option) {
				case MouseEvent.BUTTON1:
					selectedObject.setVelocity( null );
					selectedObject.setPosition( selectedObject.getPosition().plus(newPosition).minus(pressedMouse)  );
					break;
				case MouseEvent.BUTTON2:
					break;
				case MouseEvent.BUTTON3:
					selectedObject.setVelocity( null );
					selectedObject.rotate(MyVector.sub(newPosition , selectedObject.getPosition()).getAngle() - MyVector.sub(pressedMouse , selectedObject.getPosition()).getAngle());
					break;
			}
			return true;
		}
		return false;
	}
	public MyObject detectObject(MyVector P) {
		MyObject returned = null;
		for(MyObject s : listObjects)
			if(s.getAbsolutePolygon().contains(P))
				returned = s;
		return returned;
	}

	
	// Options
	public void hasGravity(boolean state) {
		hasGravity = state;
	}
	public boolean hasGravity() {
		return hasGravity;
	}
	
	public void showBarycenter(boolean state) {
		showBarycenter = state;
		Observer.update();
	}
	public boolean showBarycenter() {
		return showBarycenter;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	

	
	// Actions
	public void mousePressed(MyVector position , int button) {
		selectedObject = detectObject(position);
		pressedMouse = position;
	}
	public void mousedReleased(MyVector position , int button) {
		moveSelectedObject(new MyVector(position.getX(), position.getY()), button);
		selectedObject = null;
		Observer.update();
	}
	public void mouseDragged(MyVector position , int button) {
		moveSelectedObject(position, button);
		Observer.update();
	}

}
