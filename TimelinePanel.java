package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class TimelinePanel extends JPanel implements ActionListener{
	Timer timer;
	List<int[]> shapeIntVariables = new ArrayList<int[]>();
	List<Color> shapeColorVariables = new ArrayList<Color>();
	
	private Graphics myGraphics;
	public TimelinePanel(){
		timer= new Timer(5,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Video", 315, 40);
		g.drawString("Audio", 315, 170);
		for(int i=0;i<shapeIntVariables.size();i++){
			g.setColor(shapeColorVariables.get(i));
			g.fillRect(shapeIntVariables.get(i)[0], shapeIntVariables.get(i)[1], shapeIntVariables.get(i)[2], shapeIntVariables.get(i)[3]);
		}


	}

	public void addToProject(int[] variable,Color c){
		shapeIntVariables.add(variable);
		shapeColorVariables.add(c);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
