package model;

import java.awt.Color;

@SuppressWarnings("serial")
public class MyPolygon implements Cloneable { // It has its own coordinates axes
	
	MyVector[] vertices;

	
	public MyPolygon(MyVector[] vertices) {
		if(vertices.length < 3); 
		this.vertices = vertices.clone();
	}
	
	// Get
	public MyVector[] getVertices() {
		return vertices;
	}
	public MyVector[][] getEdges() {
		int nbVertices = vertices.length;
		MyVector[][] edgesSet = new MyVector[nbVertices][2];
		edgesSet[0][0] = vertices[nbVertices - 1];
		edgesSet[0][1] = vertices[0];
		for (int i = 1 ; i < nbVertices ; i++) {
			edgesSet[i][0] = vertices[i-1];
			edgesSet[i][1] = vertices[i];
		}
		return edgesSet;
	}
	public int getNumberVertices() {
		return vertices.length;
	}
	
	// Set
	public void setVertices(MyVector[] vertices) {
		this.vertices = vertices;
	}
	
	// Calculus Functions
	public double getArea() {
		int nbVertices = vertices.length;
		MyVector[][] edgesSet = getEdges();
		double area2 = 0;
		for (int i = 0 ; i < nbVertices ; i++) {
			area2 += (edgesSet[i][1].getX()+edgesSet[i][0].getX())*(edgesSet[i][1].getY()-edgesSet[i][0].getY());
		}
		return Math.abs(area2/2);
	}
	public MyVector getCentroid() {
		int nbVertices = vertices.length;
		MyVector[][] edgesSet = getEdges();
		double area = getArea();
		// Auxiliary / Accumulators
		double area6X = 0;
		double area6Y = 0;
		double x1, x2, y1, y2;
		// Green's Theorem over lines
		for (int i = 0 ; i < nbVertices ; i++) {
			x1 = edgesSet[i][0].getX();
			x2 = edgesSet[i][1].getX();
			y1 = edgesSet[i][0].getY();
			y2 = edgesSet[i][1].getY();
			area6X += (x1*x1 + x2*x2 + x1*x2)*(y2-y1);
			area6Y += -(y1*y1 + y2*y2 + y1*y2)*(x2-x1);
		}
		return new MyVector(area6X/(6*area) , area6Y/(6*area));
	}
	public double getCenterOfMassInertia() {
		int nbVertices = vertices.length;
		MyVector[][] edgesSet = getEdges();
		// Auxiliary / Accumulators
		double inertia2 = 0;
		double x1, x2, y1, y2;
		// Green's Theorem over lines
		for (int i = 0 ; i < nbVertices ; i++) {
			x1 = edgesSet[i][0].getX();
			x2 = edgesSet[i][1].getX();
			y1 = edgesSet[i][0].getY();
			y2 = edgesSet[i][1].getY();
			inertia2 += (x2+x1)*(x2*x2+x1*x1)*(y2-y1) - (y2+y1)*(y2*y2+y1*y1)*(x2-x1);
		}
		return inertia2/2; // multiply after by Density of Mass
	}

	// Complementary
	public MyPolygon clone() {
		return new MyPolygon(MyVector.clone(vertices));
	}
	public void rotate(double angle) {
		for(int i = 0 ; i < vertices.length ; i++) {
			vertices[i].rotate(angle);
		}
	}
	public void translate(MyVector newOrigin) {
		for(int i = 0 ; i < vertices.length ; i++) {
			vertices[i].translate(newOrigin);
		}
	}
	public void sortVertices() {
		int nbVertices = vertices.length;
		MyVector right = vertices[0];
		MyVector left = vertices[0];
		MyVector[] orderedSet = new MyVector[nbVertices];
		MyVector[] superiorSet = new MyVector[nbVertices];
		MyVector[] inferiorSet = new MyVector[nbVertices-2];
		int nbSuperiorSet = 0;
		int nbInferiorSet = 0;
		
		//  / -> direction of separat
		
		// Look for the rightest and the leftest points
		for (int i = 1 ; i < nbVertices ; i++) { 
			if (vertices[i].getX() < left.getX() || (vertices[i].getX() == left.getX() && vertices[i].getY() < left.getY()))
				left = vertices[i];
			else if(vertices[i].getX() > right.getX() || (vertices[i].getX() == right.getX() && vertices[i].getY() > left.getY()))
				right = vertices[i];
		}

		// Divide all the points in the superior and inferior parts of the plane
		for (int i = 0 ; i < nbVertices ; i++) {
			if ( vertices[i].equals(left) || vertices[i].equals(right) || ((vertices[i].getY() - left.getY()) * (right.getX() - left.getX()) >= (right.getY() - left.getY()) * (vertices[i].getX() - left.getX())))
				superiorSet[nbSuperiorSet++] = vertices[i];
			else
				inferiorSet[nbInferiorSet++] = vertices[i];
		}
		
		MyVector tmp;
		// crescent X 
		int i;
		for (i = 0 ; i < nbSuperiorSet - 1 ; i++) {
			for (int j = nbSuperiorSet - 1 ; j > i ; j--) {
				if(superiorSet[j-1].getX() < superiorSet[j].getX()) {
					tmp = superiorSet[j];
					superiorSet[j] = superiorSet[j-1];
					superiorSet[j-1] = tmp;	
				}
			}
			orderedSet[i] = superiorSet[i];
		}
		orderedSet[i] = superiorSet[i];
		// crescent X, inverse writing 
		for (i = 0 ; i < nbInferiorSet - 1 ; i++) {
			for (int j = nbInferiorSet - 1 ; j > i ; j--) {
				if(inferiorSet[j-1].getX() < inferiorSet[j].getX()) {
					tmp = inferiorSet[j];
					inferiorSet[j] = inferiorSet[j-1];
					inferiorSet[j-1] = tmp;	
				}
			}
			orderedSet[nbVertices - 1 - i] = inferiorSet[i];
		}
		if(nbInferiorSet>0) orderedSet[nbVertices - 1 - i] = inferiorSet[i];
		
		vertices = orderedSet;
	}
	
	// Others
	
	public String toString() {
		String ret = "Form["+getNumberVertices()+" vertices: {";
		for (int i = 0 ; i < getNumberVertices() ; i++) {
			ret += vertices[i].toString();
			if (i < getNumberVertices() - 1)
				ret += ",";
			else ret += "}";
		}
		ret += ", Centroid: "+getCentroid().toString()+", Area: "+getArea()+"]";
		return ret;
	}
	
	public boolean contains(MyVector P) {
		int nbVertices = getNumberVertices();
		
        if (nbVertices <= 2) {
            return false;
        }
        int hits = 0;

        double lastx = vertices[nbVertices - 1].getX();
        double lasty = vertices[nbVertices - 1].getY();
        double curx, cury, x = P.getX() , y = P.getY();

        // Walk the edges of the polygon
        for (int i = 0; i < nbVertices; lastx = curx, lasty = cury, i++) {
            curx = vertices[i].getX();
            cury = vertices[i].getY();

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
