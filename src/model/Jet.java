package model;

public class Jet {

	private MyVector vectorForce;
	protected boolean relativeRotationBoolean;
	private double absoluteRotation;  // only when boolean deactivated
	private MyVector relativePosition;

	// Constructors
	public Jet(MyVector vectorForce, MyVector relativePosition){
		
		this.relativePosition = relativePosition;
		this.vectorForce = vectorForce;
		this.relativeRotationBoolean = true;
		
	}

	// Set Methods
	public void setIntensity(double intensity) {
		vectorForce.setRadius(intensity);
	}
	public void setRelativeRotation() {
		relativeRotationBoolean = true;
	}
	public void setAbsoluteRotation() {
		relativeRotationBoolean = false;
	}
	
	// Get Methods
	public double getIntensity() {
		return vectorForce.getRadius();
	}
	public double getRelativeAngle() {
		return vectorForce.getAngle();
	}
	public MyVector getRelativePosition() {
		return relativePosition;
	}
	public MyVector getVectorForce() {
		return vectorForce.clone();	
	}
	

	public void rotate(double angle) {
		vectorForce.rotate(angle);
	}
/*
	public double add(Jet f) {
		double torque = MyVector.crossProduct(relativePosition , vectorForce) + MyVector.crossProduct(f.relativePosition , f.vectorForce);
		MyVector forceTmp = MyVector.sum(f.getVectorForce(), this.getVectorForce());
		MyVector radius = new MyVector(1,0);
		if(torque > 0) {
			radius.setRadius(torque/forceTmp.getRadius());
			radius.setAngle(forceTmp.getAngle() - Math.PI/2);
		} else {
			radius.setRadius(-torque/forceTmp.getRadius());
			radius.setAngle(forceTmp.getAngle() + Math.PI/2); 
		}	
		this.vectorForce.setLocation(forceTmp);
		this.relativePosition.setLocation(radius);
		return torque;
	}
*/

}