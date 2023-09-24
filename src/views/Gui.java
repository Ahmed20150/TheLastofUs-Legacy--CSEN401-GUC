package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame implements ActionListener{
	private JButton startGame;
	private JButton exit;
	private JButton back;
	private JButton right;
	private JButton left;
	private JButton hero;
	
	private JButton [][] map;
	
	private JButton attack;
	private JButton useSpecial;
	private JButton cure;
	private JButton move;
	private JButton endTurn;
	
	
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	
	
	public Gui(){
		p1= new JPanel();
		p2= new JPanel();
		p3= new JPanel();
		p4= new JPanel();
		
		//startGame button
		startGame= new JButton("Start Game");
		startGame.setBounds(20, 100, 200, 50);
		startGame.addActionListener(this);
		p1.add(startGame);
		
		//Left Button
		left= new JButton("left");
		left.setBounds(200, 500, 200, 50);
		left.addActionListener(this);
		p2.add(left);
		
		//Right button
		right= new JButton("right");
		right.setBounds(1400, 500, 200, 50);
		right.addActionListener(this);
		p2.add(right);
			
		//Exit button
		exit= new JButton("Exit");
		exit.setBounds(100, 290, 200, 50);
		exit.addActionListener(this);
		p1.add(exit);
		
		//back Button
		back= new JButton("Back");
		back.setBounds(0, 250, 250, 50);
		back.addActionListener(this);
		back.setBackground(Color.RED);
		p2.add(back);
		
		//attack Button
		attack= new JButton("Attack");
		attack.setBounds(100, 290, 200, 50);
		attack.addActionListener(this);
		p3.add(attack);
		
//		//useSpecial Button
//		useSpecial= new JButton("Use Special");
//		useSpecial.setBounds();
//		useSpecial.addActionListener(this);
//		p3.add(useSpecial);
//		
//		//cure Button
//		cure= new JButton("Cure");
//		cure.setBounds();
//		cure.addActionListener(this);
//		p3.add(cure);
//		
//		//move Button
//		move= new JButton("Move");
//		move.setBounds();
//		move.addActionListener(this);
//		p3.add(move);
//		
//		//endTurn Button
//		endTurn= new JButton("End Turn");
//		endTurn.setBounds();
//		endTurn.addActionListener(this);
//		p3.add(endTurn);
//		
		//Image Button
		ImageIcon image = new ImageIcon("C:/Users/gchehata/Desktop/GUC/Sem 4/Game (CSEN401)/Project PDFS(Instructions)/Milestone 3/joel.jpg");
		hero= new JButton(image);
		hero.setBounds(800,500,400,400);
		p2.add(hero);
		hero.addActionListener(this);
		
		//Panel 1(Main Menu)
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		p1.setLayout(null);
		p1.setVisible(true);
		p1.setSize(5000, 5000);
		p1.add(startGame);
		p1.add(exit);
		
		//Panel 2 (Hero select Screen)
		p2.setLayout(null);
		p2.setVisible(false);
		p2.setSize(5000, 5000);
		
		//Panel 3 (Game screen)
		
			//Panel 4(Map)
			p4= new JPanel(new GridLayout(15,15));
			p4.setVisible(true);
			p4.setBackground(Color.PINK);
			p4.setSize(500,500);
			p4.setBounds(27,70,1850,800);
		
		p3= new JPanel(null);
//		p3.add(p4);
		p3.setVisible(false);
		p3.setSize(5000,5000);
		map = new JButton[15][15];
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map.length;j++){
				map[i][j]= new JButton();
				p4.add(map[i][j]);
			}
		}
		
		this.setTitle("The Last of Us: Legacy");
		this.getContentPane().setLayout(null);
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.setVisible(true);
		this.setSize(5000, 5000);
		
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== exit){
			this.dispose();
		}
		else if(e.getSource()==startGame){
		p1.setVisible(false);
		p2.setVisible(true);	
		}
		else if(e.getSource()==back){
			p1.setVisible(true);
			p2.setVisible(false);
		}
		else if(e.getSource()==hero){
			p1.setVisible(false);
			p2.setVisible(false);
			p3.setVisible(true);
		}
		
	}
	
	public static void main(String [] args){
		Gui g= new Gui();
	}
}

