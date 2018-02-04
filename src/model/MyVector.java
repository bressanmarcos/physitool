package model;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MyVector extends Point2D.Double {
	
	public MyVector() {
		super(0, 0);
	}
	public MyVector(double x, double y) {
		super(x, y);
	}
	// Access functions : Set
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setPolar(double radius, double angle) {
		x = radius * Math.cos(angle);
		y = radius * Math.sin(angle);
	}

	public boolean setRadius(double radius) {
		if (radius<0) return false;
		double ratio = radius / getRadius();
		x *= ratio;
		y *= ratio;
		return true;
	}
	public void setAngle(double angle) {
		if (angle != 0.0) {
			double radius = getRadius();
			x = radius * Math.cos(angle);
			y = radius * Math.sin(angle);
		}

	}
	
	// Access functions : Get
	/*
	 * 
	 */
	public double distSegment(MyVector[] seg) {
		return Math.sqrt( (seg[0].dot(seg[1]))*(seg[0].dot(seg[1]))/((seg[1].dot(seg[1]))) - (seg[0].dot(seg[0])));
	}
	
	public double getRadius(){
		return Math.sqrt(x*x + y*y);
	}
	public double getAngle(){
		return Math.atan2(y, x);
	}
	
	// Modify the point
	public void rotate(double angle) {
		angle += getAngle();
		setAngle(angle);
	}
	public void rotate(double angle, MyVector P) {
		MyVector difference = sub(this, P);
		difference.rotate(angle);
		x += difference.getX();
		y += difference.getY();
	}
	public void translate(MyVector newOrigin) {
		x -= newOrigin.getX();
		y -= newOrigin.getY();
	}
	
	// Mathematics
	public double dot(MyVector vector) {
		return x * vector.getX() + y * vector.getY();
	}
	public double cross(MyVector vector) {
		return x * vector.getY() - y * vector.getX();
	}
	public MyVector times(double constant) {
		return new MyVector(x * constant, y * constant);
	}
	public MyVector plus(MyVector vector) {
		return new MyVector(x + vector.getX(), y + vector.getY());
	}
	public MyVector minus(MyVector vector) {
		return new MyVector(x - vector.getX(), y - vector.getY());
	}

	// Static
	public static double norm(MyVector A) {
		return A.getRadius();
	}
	public static double dotProduct(MyVector A, MyVector B) {
		return A.getX()*B.getX() + A.getY()*B.getY();
	}
	public static double crossProduct(MyVector A, MyVector B) {
		return A.getX()*B.getY() - A.getY()+B.getX();
	}
	public static MyVector sum(MyVector A, MyVector B) {
		MyVector P = new MyVector();
		P.setLocation(A.getX() + B.getX() , A.getY() + B.getY());
		return P;
	}

	public static MyVector sub(MyVector A, MyVector B) {
		MyVector P = new MyVector();
		P.setLocation(A.getX() - B.getX() , A.getY() - B.getY());
		return P;
	}
	public static double dist(MyVector A, MyVector B) {
		return norm(sub(A, B));
	}
	public static double dist2(MyVector A, MyVector B) {
		MyVector subs = sub(A, B);
		return subs.getX()*subs.getX() + subs.getY()*subs.getY();
	}
	public static MyVector[] clone(MyVector[] coordinatesArray) {
		MyVector[] Clone = new MyVector[coordinatesArray.length];
		for (int i = 0 ; i < coordinatesArray.length ; i++) {
			Clone[i] = coordinatesArray[i].clone();
		}
		return Clone;
	}
	
	// Tests
	public boolean equivalent(MyVector P) {
		return (this.getX() == P.getX() && this.getY() == P.getY());
	}
	
	// Others
	@Override
    public String toString() {
        return "("+x+", "+y+")";
    }
	public MyVector clone() {
		return new MyVector(x, y);
	}
	public MyVector opposite() {
		// TODO Auto-generated method stub
		return new MyVector(-x, -y);
	}


}