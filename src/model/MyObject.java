package model;

import java.awt.Color;
import java.util.ArrayList;

public class MyObject {
	private MyPolygon polygon;
	private MyVector position; // relative to the background
	private MyVector velocity; // relative to the background

	private double rotation;
	private ArrayList<Jet> listForces;
	private Color color;
	
	private double density; // real variable
	private double friction; // µ
	
	private double area; // stocks
	private double inertia;
	private double mass;
	
	private int collisionDomain;

	public MyObject(MyVector[] vertices, Color color) {
		polygon = new MyPolygon(MyVector.clone(vertices)); 
		position = polygon.getCentroid();
		polygon.translate(position);
		
		this.color = color;
		
		velocity = new MyVector(0 , 0);
		density = 1;
		rotation = 0;

		collisionDomain = 1;
		
		calcInertia();
		calcArea();
		calcMass();
	}
	public MyObject(MyVector[] vertices) {
		this(vertices , new Color(
				(float)(Math.random()/1.4) , 
				(float)(Math.random()/1.4) , 
				(float)(Math.random()/1.4) , 
				(float)(Math.random()/1.2)) 
				);
	}
	
	public void calcInertia(){
		inertia = polygon.getCenterOfMassInertia()*density;
	}
	public void calcArea() {
		area = polygon.getArea();
	}
	public void calcMass(){
		mass = density*polygon.getArea();
	}
	
	public Color getColor() {
		return color;
	}
	public MyVector getPosition(){
		return position.clone();
	}	
	public MyVector getVelocity() {
		return velocity.clone();
	}
	public double getRotation(){
		return rotation;
	}
	public double getMass(){
		return mass;
	}
	public double getDensity(){
		return density;
	}
	public double getArea() {
		return area;
	}
	public double getInertia(){
		return inertia;
	}
	public double getParallelAxisInertia(MyVector P) {
		return inertia + mass*MyVector.dist2(position , P);
	}
	public MyPolygon getPolygon() {
		// It takes a clone. This protects the polygon against external modifications
		return polygon.clone();
	}
	/**
	 * Get a copy of the polygon e
	 */
	public MyPolygon getAbsolutePolygon() {
		MyPolygon absolutePolygon = getPolygon();
		absolutePolygon.rotate(rotation);
		absolutePolygon.translate(position.opposite());
		return absolutePolygon;
	}

	public void insertColisionDomain(int domain) {
		collisionDomain |= (1 << domain);
	}
	public void removeColisionDomain(int domain) {
		collisionDomain &= ~(1 << domain);
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setPosition(MyVector position){
		if(position != null)
			this.position = position;
		else 
			this.position = new MyVector(0,0);
	}
	public void setVelocity(MyVector velocity){
		if(velocity != null)
			this.velocity = velocity;
		else 
			this.velocity = new MyVector(0,0);
	}
	public void setRotation(double rotation){
		while(rotation >= Math.PI)
			rotation -= 2*Math.PI;
		while(rotation < Math.PI)
			rotation += 2*Math.PI;
		this.rotation = rotation;
	}
	public boolean setMass(double mass){
		if(mass<=0) return false;
		this.mass = mass;
		this.density = mass/area;
		return true;
	}
	public boolean setDensity(double density){
		if(density<=0) return false;
		this.density = density;
		this.mass = density * area;
		return true;
	}
	public void rotate(double variation) {
		variation += rotation;
		setRotation(variation);
	}
	
	public void addForce(Jet F) {
		listForces.add(F);
	}
	/*
	 * public MyVector calcTranslational() {
		Jet resultant = new Jet(0.0, 0.0);
		for(Jet f : listForces) {
			resultant.add(f);
		}
		return resultant;
	}
	*/
	public String toString() {
		return "Structure, Position: "+position.toString()+", Density: "+density+" kg/m^2, Rotation: "+rotation+" rad";
	}

	public void translate(MyVector variation) {
		setPosition( position.plus(variation) );
	}

	public boolean contains(MyVector P) {
		int nbVertices = polygon.getNumberVertices();
		
        if (nbVertices <= 2) {
            return false;
        }
        int hits = 0;

        double lastx = polygon.vertices[nbVertices - 1].getX();
        double lasty = polygon.vertices[nbVertices - 1].getY();
        double curx, cury, x = P.getX() , y = P.getY();

        // Walk the edges of the polygon
        for (int i = 0; i < nbVertices; lastx = curx, lasty = cury, i++) {
            curx = polygon.vertices[i].getX();
            cury = polygon.vertices[i].getY();

            if (cury == lasty) {
                continue;
            }

            double leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0); // true if it's odd
    }


}