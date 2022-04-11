import java.util.Scanner;

/**
 *  MazeSolver solves a maze from user inputs in console
 *
 *  @author  Zoe Buck
 *  @version CSC 212, March 2022
 */ 

public class MazeSolver{
  
  /** 
    *  Sets up and solves a maze using the maze class
    *
    *  @param String[] args user input of filename
    */
  public static void main(String [] args) {
    //when entering the file name as a string
    if (args.length > 0) {
      String mazeName = args[0];
      
      Maze mazes = new Maze(mazeName);
      MazeViewer viewer = new MazeViewer(mazes);

      }
    //when the user uses "<" to directly connect to the file. I was struggling with, and could not figure out how to implement this part. 
    if(args.length == 0){
      Scanner file = new Scanner(System.in);
      while (file.hasNextLine()) {
        String line = file.nextLine();
      //default maze
      Maze mazes = new Maze("maze 1");
      MazeViewer viewer = new MazeViewer(mazes);
      }
    }
  }
}
 