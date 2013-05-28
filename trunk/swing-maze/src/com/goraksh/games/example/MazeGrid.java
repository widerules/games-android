package com.goraksh.games.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.JFrame;

/**
 * 
 * @author niteshk
 *
 */
public class MazeGrid extends JFrame {
	
	public MazeGrid() {
		
		 add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);    
		 add(new MazePanel());

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(860, 720);
	        setLocationRelativeTo(null);
	        setTitle("Maze");
	        setVisible(true);
	}

	
	public static void main( String[] args ) {
		new MazeGrid();
	}
}
