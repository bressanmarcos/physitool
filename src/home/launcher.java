package home;

import model.*;

import java.awt.Dimension;

import control.*;
import view.*;

public class launcher {

	public static void main(String[] args) {
		//

		Engine engine = new Engine();
		Controller Controller = new Controller(engine);
		MainWindow Fenetre = new MainWindow(Controller);
		Fenetre.setSize(new Dimension(800, 600));
		engine.addObserver(Fenetre);

		MyObject a = engine.addObject(new MyVector[] {new MyVector(200, 200), new MyVector(400, 200), new MyVector(400, 400), new MyVector(200, 400)});
		//MyObject b = engine.addObject(new MyVector[] {new MyVector(500, 500), new MyVector(600, 500), new MyVector(600, 600), new MyVector(500, 600)});
		engine.start();
		
		//a.setVelocity(a.getVelocity().plus(new MyVector(1, 0)));
		//b.setVelocity(a.getVelocity().plus(new MyVector(1, 0)));
	}


}
