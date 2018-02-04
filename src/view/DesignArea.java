/**
 * 
 */
package view;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputListener;

import control.*;
import model.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author borgesbressa_mar
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class DesignArea extends JPanel implements MouseInputListener {
	
	private Controller ctrl;
//	ArrayList<MyVector> pointsToShow = new ArrayList<MyVector>();
	private int width;
	private int height;
	
	private JPopupMenu context;

	public DesignArea(Controller ctrl) {
		this(ctrl, 800, 600);
	}
	public DesignArea(Controller ctrl, int width, int height) {
		this.ctrl = ctrl;
		addMouseListener(this);
		addMouseMotionListener(this);
		this.width = width;
		this.height = height;
		
		context = new JPopupMenu ();
		add(context);
	}
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void paint(Graphics g) {
		// Background
		g.setColor(new Color(1f, 1f, 1f));
		g.fillRect(0 , 0 , width , height);
		
		// get absolute positioned figures
		for( MyObject o : ctrl.getDesign()) {
			MyVector[] vertices = o.getAbsolutePolygon().getVertices();
			int[] xPoints = new int[vertices.length];
			int[] yPoints = new int[vertices.length];
			for (int i = 0 ; i < vertices.length ; i++) {
				xPoints[i] = (int) vertices[i].getX();
				yPoints[i] = (int) vertices[i].getY();
				g.setColor(Color.GRAY);
				g.drawString(new MyVector(xPoints[i], yPoints[i]).toString() , xPoints[i] , yPoints[i]);
			}
			g.setColor(o.getColor());
			g.fillPolygon(xPoints , yPoints , vertices.length);
			
			// SHow Barycenter
			if (ctrl.showBarycenter()) {
				MyVector position = o.getPosition();
				g.setColor(Color.BLACK);
				g.drawString(new MyVector((int)position.getX(), (int)position.getY()).toString() , (int) position.getX() , (int) position.getY());
				g.fillOval((int)(position.getX()-5), (int)(position.getY()-5), 10 , 10);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		context.show();
	}
	public void mousePressed(MouseEvent e) {
		ctrl.mousePressed(new MyVector(e.getX(), e.getY()), e.getButton());
	}
	public void mouseReleased(MouseEvent e) {
		ctrl.mouseReleased(new MyVector(e.getX(), e.getY()), e.getButton());
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {
		ctrl.mouseDragged(new MyVector(e.getX(), e.getY()), e.getButton());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}	
		
/*
			FontMetrics frc = g.getFontMetrics();
			Rectangle2D rec=frc.getStringBounds(nb+"", g);
			g.drawString(nb+"", x, y);				

	}*/
	
