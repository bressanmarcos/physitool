/**
 * 
 */
package control;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.*;
import view.*;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author borgesbressa_mar
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class Controller {

	private Engine engine;
	
	public Controller(Engine engine) {
		this.engine = engine;
	}

	public ArrayList<MyObject> getDesign() {
		return engine.getList();
	}
	public Color getBackgroundColor() {
		return engine.getBackgroundColor() ;
	}

	public void playStep() {
		engine.playStep();
	}

	public boolean play() {
		engine.play();
		return true;
	}

	public boolean pause() {
		engine.pause();
		return true;
	}


	
	public void mousePressed(MyVector position , int button) {
		engine.mousePressed(position, button);
	}

	public void mouseReleased(MyVector position , int button) {
		engine.mousedReleased(position , button);
	}

	public void mouseDragged(MyVector position , int button) {
		engine.mouseDragged(position, button);
	}

	public void toggleShowBarycenter() {
		engine.showBarycenter(!engine.showBarycenter());
	}
	
	public boolean showBarycenter() {
		return engine.showBarycenter();
	}


	public void toggleGravity() {
		// TODO Auto-generated method stub
		engine.hasGravity(!engine.hasGravity());
	}






}