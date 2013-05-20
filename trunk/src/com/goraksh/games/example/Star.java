package com.goraksh.games.example;

import java.util.Stack;

import javax.swing.JFrame;

public class Star extends JFrame {

    public Star() {

        add(new StarPanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setLocationRelativeTo(null);
        setTitle("Star");
        setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args) {
        //new Star();
        
        Stack<Index> index = new Stack<Index>();
        index.push( new Index( 1 ,2) );
        index.push( new Index( 3, 4 ));
        
        Stack<Index> id = (Stack<Index>) index.clone();
        index.pop();
        index.pop();
        
        System.out.println(id.pop() );
        System.out.println(id.pop() );
        System.out.println(id.pop() );
        
    }
}

class Index {
	int i;
	int j;
	
	public Index(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
}
		