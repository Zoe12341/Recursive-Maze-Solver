import java.awt.*;
import javax.swing.*;        
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Class that runs a maze display/solution GUI.
 *  Handles drawing calls as a JComponent, and also
 *  callbacks to repeatedly redraw the contents as they change.
 *
 *  YOU WILL NOT NEED TO MODIFY THIS FILE FOR THE ASSIGNMENT
 *
 *  @author Nicholas R. Howe
 *  @version CSC 112, 20 March 2006
 */
public class MazeViewer extends JComponent implements ActionListener {
  /** Size of a maze square in graphic display */
  public static final int BLOCK = 12;

  /** Holds the maze to solve */
  private DisplayableMaze maze;

  /** Window the maze will appear in */
  private JFrame frame;

	/** TImer for callbacks */
	private Timer timer;

	/** Creates a viewer to display the specified maze */
	public MazeViewer(DisplayableMaze maze) {
		super();
		this.maze = maze;
		frame = null;  // not displayed yet
		setPreferredSize(new Dimension(BLOCK*maze.getWidth(),BLOCK*maze.getHeight()));
		setMinimumSize(new Dimension(BLOCK*maze.getWidth(),BLOCK*maze.getHeight()));
    System.out.println("Displaying "+maze.getHeight()+"x"+maze.getWidth()+" maze...");
		openWindow();
	}

	/** Sets up the GUI window */
  private void openWindow() {
    // Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    // Create and set up the window.
    frame = new JFrame("Maze Display");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add components
    createComponents(frame);

    // Display the window.
    frame.pack();
    frame.setVisible(true);

    // Begin animation callback events
    timer = new Timer(25, this);
    timer.setInitialDelay(500);
    timer.start(); 
  }

  private void createComponents(JFrame frame) {
    Container pane = frame.getContentPane();
    pane.add(this);
  }

  /** Draws the maze */
  public void paintComponent(Graphics g) {
    for (int i = 0; i < maze.getHeight(); i++) {
      for (int j = 0; j < maze.getWidth(); j++) {
      g.setColor(maze.getContents(i,j).color);
      g.fillRect(BLOCK*j,BLOCK*i,BLOCK,BLOCK);
      }
    }
    g.setColor(Color.blue);
    MazeLocation start = maze.getStart();
    MazeLocation finish = maze.getFinish();
    g.drawRect(BLOCK*start.getCol()+2,BLOCK*start.getRow()+2,
      BLOCK-4,BLOCK-4);
    //g.drawOval(BLOCK*finish.getCol(),BLOCK*finish.getRow(),BLOCK,BLOCK);
    g.drawOval(BLOCK*finish.getCol()+1,BLOCK*finish.getRow()+1,
      BLOCK-3,BLOCK-3);
    g.drawOval(BLOCK*finish.getCol()+3,BLOCK*finish.getRow()+3,
      BLOCK-7,BLOCK-7);
  }

	/** Timer callback causes the window to be repainted */
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
