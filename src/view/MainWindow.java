/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements KeyListener, ActionListener {

	private DesignArea designArea;
	private Controller ctrl;
	private static final String PLAY = "Play";
	private static final String PLAYSTEP = "PlayStep";
	private static final String PAUSE = "Pause";
	private static final String GRAVITY = "Gravity";
	private static final String BARYCENTER = "Barycenter";
	
	public MainWindow(Controller Controller) {
		// begin-user-code
		// TODO Auto-generated constructor stub
		// end-user-code
		ctrl = Controller;
		
		// Toolbar
		JToolBar toolBar = new JToolBar("Simulation Toolbar");
		{	
			JButton playButton = new JButton(PLAY);
			playButton.setActionCommand(PLAY);
			playButton.setToolTipText("Press to Play");
			playButton.addActionListener(this);
			toolBar.add(playButton);
			
			JButton pauseButton = new JButton(PAUSE);
			pauseButton.setActionCommand(PAUSE);
			pauseButton.setToolTipText("Press to Pause");
			pauseButton.addActionListener(this);
			toolBar.add(pauseButton);
			
			JButton playStepButton = new JButton(PLAYSTEP);
			playStepButton.setActionCommand(PLAYSTEP);
			playStepButton.setToolTipText("Press to Play one Step");
			playStepButton.addActionListener(this);
			toolBar.add(playStepButton);

			JCheckBox gravityButton = new JCheckBox(GRAVITY);
			gravityButton.setActionCommand(GRAVITY);
			gravityButton.setToolTipText("Activate/Deactivate gravity");
			gravityButton.addActionListener(this);
			toolBar.add(gravityButton);
			
			JCheckBox barycenterButton = new JCheckBox(BARYCENTER);
			barycenterButton.setActionCommand(BARYCENTER);
			barycenterButton.setToolTipText("Activate/Deactivate barycenter");
			barycenterButton.addActionListener(this);
			barycenterButton.setSelected(false);
			toolBar.add(barycenterButton);
		}
		toolBar.setFloatable(true);
		toolBar.setRollover(true);
        
		// Design Area
		designArea = new DesignArea(ctrl);
		JScrollPane designScroll = new JScrollPane(designArea);

		// FINAL CONFIGS
        add(toolBar, BorderLayout.PAGE_END);
        add(designScroll, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(800,600));
		
		pack();
		setVisible(true);
		// addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public DesignArea getDesingArea() {
		return designArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
	
			case PLAY:
				ctrl.play();
				break;
				
			case PAUSE:
				ctrl.pause();
				break;
				
			case PLAYSTEP:
				ctrl.playStep();
				break;
				
			case BARYCENTER:
				ctrl.toggleShowBarycenter();
				break;
				
			case GRAVITY:
				ctrl.toggleGravity();
				break;
		}
	}
	
	public void update() {
		designArea.repaint();
	}
}