package com.goraksh.games.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.goraksh.games.maze.BackTrackingMaze;


/**
 * 
 * @author niteshk
 *
 */
public class MazePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121284753677557600L;

	public static int[][] actual = new int[][] {
		{ 0, 0, 0, 0, 1, 0, 0, 0 },
		{ 0, 1, 1, 0, 1, 0, 1, 1 },
		{ 0, 1, 1, 0, 1, 0, 0, 1 },
		{ 0, 1, 0, 0, 1, 1, 0, 1 },
		{ 0, 1, 1, 0, 0, 1, 0, 1 },
		{ 0, 1, 0, 1, 0, 1, 0, 1 }, 
		{ 0, 1, 0, 0, 0, 1, 0, 1 },
		{ 1, 1, 1, 0, 1, 0, 0, 1 }, 
		{ 1, 1, 1, 0, 0, 0, 1, 1 } };

	private int[][] exitpath;
	
	private Dimension dim;

	private int[][] maze = actual;// BackTrackingMaze.maze;
	// for maze, movement along WIDTH is movement along Y , and movement along
	// HEIGHT is movement along X
	private int X;
	private int Y;

	private int countExitPathNodes;

	private int currentCount;
	private HashSet<String> flyImageSet;
	private Timer timer;
	private List<BackTrackingMaze.Index> orderOfExitPath;
	private Color BLOCKAGE = Color.gray;
	private Color ALLOWED = Color.green;
	//public static final String BUG_IMAGE = "bug.jpg";
	public static final String BUG_IMAGE = "walk-1.jpg";

	public MazePanel() {
		
		flyImageSet = new HashSet<>();
	
		X = maze.length;
		Y = maze[0].length;

		int rows = X;
		int cols = Y;

		dim = new Dimension(rows, cols);

		BackTrackingMaze bm = new BackTrackingMaze();
		bm.move(0, 0, maze, -1, -1);
		this.exitpath = bm.getExitMaze();
		this.countExitPathNodes = bm.getExitNodeCount();
		this.orderOfExitPath = bm.getOrderOfPath();
	
		this.currentCount = countExitPathNodes > 0 ? 0 : 0;

		System.out.println("Grid width:" + dim.width + " ,Grid Height: "
				+ dim.height);
		setLayout(new GridLayout(rows, cols));

		addLabels();
		
		timer = new Timer();
	    timer.scheduleAtFixedRate(new RefreshImage(this), 100, 500);
		//RefreshImage re = new RefreshImage(this);
		//re.doo();

	}
	
	public int incrementCurrentCount() {
		if ( currentCount == countExitPathNodes )
			return currentCount;
		return ++currentCount;
	}
	
	public int getCurrentCount() {
		return this.currentCount;
	}

	public int[][] getExitPath() {
		return this.getExitPath();
	}

	private void addLabels() {
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				JLabel label = getLabel(i, j);
				// label.setBorder( new BevelBorder(BevelBorder.LOWERED));
				add(label, BorderLayout.CENTER);
			}
		}
	}

	 //public void paint( Graphics g ) {
		 //paintComponent(g);
	 //}

	/**
	 * Paints the components of this panel
	 * In this case it paints the number of label + flyimages
	 */
	public void paintComponent(Graphics g) {
				System.out.println("Calling paint component with current-count: "
				+ currentCount);
		
		fillImageMap();
		Component[] comps = getComponents();
		for (Component comp : comps) {
			if (comp instanceof GridLabel) {
				((GridLabel) comp).dopaintComponent(g, this.getImageSet() );
			}
		}
	}
	
	public HashSet<String> getImageSet() {
		return this.flyImageSet;
	}
	
	/**
	 * Add the number of images to images set. Number of images added is equal to the number of movement of the flyimage
	 * 
	 */
	public void fillImageMap() {
		if ( getCurrentCount() == 0 )
			return;
		
		flyImageSet.clear();
		Dimension[] imgaeDims = getImageArray( getCurrentCount() );
		
		for( Dimension dim: imgaeDims) 
		flyImageSet.add( (int)dim.getWidth() + "" +  (int)dim.getHeight() );
	
	}
	
	/**
	 *Array of images to be drawn
	 * 
	 * @param currentCount
	 * @return
	 */
	private Dimension[] getImageArray( int currentCount) {
		System.out.println("Geting image array for current count = "
				+ currentCount);
		Dimension[] dim = calculateFromOrderOfExitPath(currentCount);
		return dim;
	}

	/**
	 * Order of movement from start to the exit
	 * 
	 * @param count
	 * @return
	 */
	private Dimension[] calculateFromOrderOfExitPath(int count) {
		Dimension[] dim = new Dimension[count];
		for( int m = 0; m < count; m++ ) {
			BackTrackingMaze.Index ij = orderOfExitPath.get(m);
			dim[m] = new Dimension(ij.j, ij.i );
		}
		return dim;
	}
	
	/**
	 * Each grid is a label
	 * 
	 * @param mazeX
	 * @param mazeY
	 * @return
	 */
	private JLabel getLabel(int mazeX, int mazeY) {
		Color color = ALLOWED;
		if (maze[mazeX][mazeY] == 1) 
			color = BLOCKAGE;
		
		return new GridLabel("", color);
	}	
}

/**
 * Grids demostrating a block of movement
 * 
 * @author niteshk
 *
 */
class GridLabel extends JLabel {

	private Color color;
	private Image image;
	private Image flyImage;

	public GridLabel(String text, Color color, String imagePath) {
		super(text);
		this.color = color;

		setBorder(new BevelBorder(BevelBorder.LOWERED));

		if (imagePath != null) {
			ImageIcon ii = new ImageIcon(imagePath);
			image = ii.getImage();
		}
	}

	public GridLabel(String text, Color color) {
		super(text);
		this.color = color;

		setBorder(new BevelBorder(BevelBorder.LOWERED));
		//ImageIcon ii = new ImageIcon(Constants.STAR_PNG);
		ImageIcon ii = new ImageIcon( ClassLoader.getSystemResource( MazePanel.BUG_IMAGE ));
		flyImage = ii.getImage();
	}
	
	private Image getImageIcon( String imageUrl ) {
		ImageIcon ii = new ImageIcon( ClassLoader.getSystemResource( imageUrl ));
		return ii.getImage();
	}

	private Color getColor() {
		return this.color;
	}

  public void dopaintComponent(Graphics g,  HashSet<String> imageDimensionSet ) {
		g.setColor(getColor());

		System.out.println("Painting this grid label: " + this.getX() + "::" + this.getY());
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		int[] coordinates = contains(imageDimensionSet, this.getX(), this.getY(), this.getWidth(), this.getHeight() );
	
		if ( coordinates != null ) 
				addImage(g, this.getX(), this.getY(), this.getWidth() / 2,
					this.getHeight() / 2, coordinates );
		
	}
	
	private int[] contains( HashSet<String> imageDimensionSet, int x, int y, int width, int height  ) {
		int jIndex = x/width;
		int iIndex = y/height;
		
		
		boolean isContains = imageDimensionSet.contains( jIndex + "" + iIndex  );
		System.out.println("Checking  if this current label is contained in image set " + x + "::" + y + " Label Index(i,j) : " + iIndex + "::" + jIndex + " Looking for key(width:height)=" + ( jIndex + "" + iIndex ) + ". isContained ? " + isContains);
	  return isContains ? 	new int[] { iIndex, jIndex } : null;
	}

	private void addImage(Graphics g, int x, int y, int width, int height, int[] coordinates ) {
		System.out.println("Toggling fly image for(i,j) " + coordinates[0] + "::" + coordinates[1] + " ,for image total distance: "  + ( coordinates[0] + coordinates[1]) );
		g.drawImage(getFlyImage(coordinates[0] + coordinates[1]), x, y, width, height, null);
	}

	
	private Image getFlyImage(int totalDistance) {
		//return this.flyImage;
		ImageToggler toggler = ImageToggler.getInstance();
		return getImageIcon( toggler.toggleAndGet(totalDistance) );
	}

}

class RefreshImage extends TimerTask {

	private MazePanel targetPanel;
	
	public RefreshImage( MazePanel panel ) {
		this.targetPanel = panel;
	}
	
    public void run() {
       targetPanel.incrementCurrentCount();
       targetPanel.fillImageMap();
       targetPanel.repaint();
    }
    
    /**
     * For test purpose
     */
    public void doo() {
    	 targetPanel.incrementCurrentCount();
    	 targetPanel.incrementCurrentCount();
    	// targetPanel.incrementCurrentCount();
         targetPanel.fillImageMap();
         //targetPanel.repaint();
    }
}
