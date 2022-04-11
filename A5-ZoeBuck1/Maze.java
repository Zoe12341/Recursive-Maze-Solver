
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException; 

/**
 *  Maze holds MazeConetents in an array and solves the maze using recursion
 *
 *  @author  Zoe Buck
 *  @version CSC 212, March 2022
 */


public class Maze implements DisplayableMaze {
  
  /** nested array of MazeContents */
  private MazeContents[][] myArray; 

  /** height of maze grid */
  private int height;

  /** name of file with maze */
  private String fileName;

  /** the MazeContents at a given MazeLocation */
  private MazeContents myConts;

  /** width of maze grid */
  private int width;
  
  /** The maze location that is the start of the maze */
  private MazeLocation mazeStart;

  /** The maze location that is the end of the maze */
  private MazeLocation mazeEnd;

  /** 
    *  Constructor for the Maze class, sets up the maze. Initializes fileName
    *
    *  @param String fileName name of the maze file
    */
  public Maze(String fileName){
    this.fileName = fileName;

    fillContent();
    boolean solvable = solver(getStart());
    if(solvable){
      System.out.println("Your maze is solvable!");
    }
    else{
      System.out.println("Your maze isn't solvable!");
    }
  }



    /** @return height of maze grid */
    public int getHeight() {
      Scanner file = null;
      try{
        file = new Scanner(new File(this.fileName));
      }
      catch(FileNotFoundException e){
        System.exit(1);
      }
      this.height = 0; 
        while (file.hasNextLine()) {
          file.nextLine();
          this.height++;
        }
      file.close();
      return this.height;
    }

    /** @return width of maze grid */
    public int getWidth() {
      Scanner file = null;
      try{
        file = new Scanner(new File (this.fileName));
      }
      catch(FileNotFoundException e){
        System.exit(1);
      }
      if (file.hasNextLine()) {
        String line = file.nextLine();
        List<Character> wordsToCheck = mazeGrid(line);
        this.width = wordsToCheck.size();
        }
      file.close();
      return this.width;
    }

    /** 
    *  Makes a list of characters by splitting up a string
    *
    *  @param int i, int j the row and column numbers respectively
    *  @return MazeContents contents of maze grid at row i, column j
    */
    public MazeContents getContents(int i, int j) {
      if(i > getHeight() || i < 0 || j < 0 || j>getWidth()){
        return MazeContents.WALL;
      }
      return myArray[i][j];
    }

    /** @return location of maze start point */
    public MazeLocation getStart() {
      return this.mazeStart;
      
    }

    /** @return location of maze finish point */
    public MazeLocation getFinish() {
      //System.out.println(this.mazeEnd);
      return this.mazeEnd;      
    }
  
  /** 
    *  Makes a list of characters by splitting up a string
    *
    *  @param String line
    *  @return List<Character> a list of characters
    */
  public static List<Character> mazeGrid(String line){
    List<Character> chars = new ArrayList<>();
    for (char ch : line.toCharArray()){
      chars.add(ch);
    }
    //System.out.println(chars.size());
    return chars; 
  }

  /** 
    *  Builds a maze visualization based off a nested array of maze contents
    */
  public void fillContent(){
    myArray = new MazeContents[getHeight()][getWidth()];
    Scanner file = null;
    try{
      file = new Scanner(new File (this.fileName));
      }
    catch(FileNotFoundException e){
      System.exit(1);
    }
    int height2 = 0; 
      while (file.hasNextLine()) {
        String line = file.nextLine();
        List<Character> myContents = mazeGrid(line);
        for(int i = 0; i <= myContents.size()-1; i++) {
          if (myContents.get(i) == '#'){
            myArray[height2][i] = MazeContents.WALL;
          }
          else if (myContents.get(i) == 'S'){
            this.mazeStart = new MazeLocation(height2,i);
          }
          else if (myContents.get(i) == 'F'){
            this.mazeEnd = new MazeLocation(height2,i);
          }
          if(myContents.get(i) != '#'){
            myArray[height2][i] = MazeContents.OPEN;
          }
        }
        height2++;
      }
      file.close();
    
  }

  /** 
    *  Solves a maze using recursion
    *
    *  @param currentloc a location in the maze
    *  @return T/F whether the maze can be solved or not
    */
  public boolean solver(MazeLocation currentLoc){
    try { Thread.sleep(9);	} catch (InterruptedException e) {};
    myConts = getContents(currentLoc.getRow(), currentLoc.getCol());
    if(currentLoc.equals(getFinish())){
      myConts = MazeContents.PATH;
      myArray[currentLoc.getRow()][currentLoc.getCol()] = MazeContents.PATH;
      return true;
    }
    else if(myConts != MazeContents.OPEN && myConts != MazeContents.PATH) {
      //mark as something?
      return false;
    }
    else{
      myArray[currentLoc.getRow()][currentLoc.getCol()] = MazeContents.VISITED;
      MazeLocation ML = new MazeLocation(currentLoc.getRow(), currentLoc.getCol());
      if(solver(ML.neighbor(MazeDirection.NORTH))|| solver(ML.neighbor(MazeDirection.SOUTH))|| solver(ML.neighbor(MazeDirection.EAST))|| solver(ML.neighbor(MazeDirection.WEST))){
        myArray[currentLoc.getRow()][currentLoc.getCol()] = MazeContents.PATH;
        return true;
      }
      else{
        myArray[currentLoc.getRow()][currentLoc.getCol()] = MazeContents.DEAD_END;
        myConts = MazeContents.DEAD_END; 
        return false;
      }
    }
  }  
}






