//Game of Life by Stephen Boyd
//my attempt at implementing Conway's Game of Life, begun December 2012.


// Now this requires a file for a starting pattern,
// the first program argument:
// $ java GameOfLife test.txt

// in the input file, periods and spaces are dead cells
// all other characters are live cells.
// test.txt is a good example.



//Uses Cell.java
//Cell Instance methods are checkLife, kill, birth, and toString.
//Class helper methods are gridPrint, gridCopier, generator, and iterator.
//The two grids are 2-dimensional arrays of Cell objects.
//One is the current generation, the other is the next generation,
//   and they alternate roles every generation.


//Rules:
// Any live cell with fewer than two live neighbours dies
// Any live cell with two or three live neighbours lives on
// Any live cell with more than three live neighbours dies
// Any dead cell with exactly three live neighbours becomes a live cell

// In other words:
// Live cells: If live neighbours == 2 or 3, birth. Else, kill.
// Dead cells: If live neighbours == 3, birth. Else, kill.


import java.util.Scanner;
import java.util.ArrayList;
import helpful.*;
import java.io.*;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileReader;


public class GameOfLife {

	static int generations = 0;	

	private static void gridPrint (Cell [][] name){
		StringBuilder matrix = new StringBuilder();
		matrix.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		matrix.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		matrix.append("\n\n\n\n\n\n\n  Generation: "+ generations+"\n");
		for (int x = 0, y = 0; y < name[0].length; x++){
			matrix.append(name[x][y]);
			if (x == (name.length -1)){
				y++;
				x = (-1);
				matrix.append("\n");
			}
		}
		System.out.println(matrix);
	}
 
	private static void gridCopier (Cell [][] source, Cell [][] target){
		for (int x = 0, y = 0; y < source[0].length; x++){
			target[x][y] = new Cell (source[x][y].checkLife());
			if (x == (source.length -1)){
				y++;
				x = (-1);
			}
		}
	}

	//To keep things simple, this skips the grid's perimeter cells.
	private static void generator (Cell [][] source, Cell [][] target){
		for (int x = 1, y = 1; y < (source[0].length - 1); x++){
			boolean nw, n, ne, w, e, sw, s, se;
			nw = source[x - 1][y - 1].checkLife();
			n =  source[x][y -1].checkLife();
			ne = source[x + 1][y - 1].checkLife();
			w =  source[x - 1][y].checkLife();
			e =  source[x + 1][y].checkLife();
			sw = source[x - 1][y + 1].checkLife();
			s =  source[x][y + 1].checkLife();
			se = source[x + 1][y + 1].checkLife();
			int liveNeighbours = 0;
			if (nw == true) liveNeighbours++;
			if (n  == true) liveNeighbours++;
			if (ne == true) liveNeighbours++;
			if (w  == true) liveNeighbours++;
			if (e  == true) liveNeighbours++;
			if (sw == true) liveNeighbours++;
			if (s  == true) liveNeighbours++;
			if (se == true) liveNeighbours++;
			if (source[x][y].checkLife() == true){
				if (liveNeighbours == 2 || liveNeighbours == 3) target[x][y].birth();
				else target[x][y].kill();
			}
			else if (source[x][y].checkLife() == false){
				if (liveNeighbours == 3) target[x][y].birth();
				else target[x][y].kill();
			}
			if (x == (source.length -2)){
				y++;
				x = 0;
			}
		}
		generations++;
	}

	//always use (grid1, grid2) as the parameters.
	private static void iterator (Cell[][] firstMatrix, Cell[][] secondMatrix){
		if ((generations + 2) % 2 == 0) generator(firstMatrix, secondMatrix);
		else generator(secondMatrix, firstMatrix);
	}
 




public static void main (String[] args){
	
	//reads a file, makes and counts a list of lines
	int height = 0;
	int width = 0;
	ArrayList<String> lines = new ArrayList<String>();
	Scanner s = null;
	try {
		do {
			s = new Scanner(new BufferedReader(new FileReader(args[0])));
			s.useDelimiter("\\n");
			for (int i = 0; i < 50; i++){
				lines.add(s.next());
				height++;
				if (s.hasNext() == false){
					s.close();
					break;
				}
			}
			s.close();
			break;	
		} while (s.hasNext());
	} catch (IOException e){
		System.out.println("no file");
	} catch (ArrayIndexOutOfBoundsException e){
	} finally {
		if (s != null) {
		s.close();
		}
	}

	//measures lines to get width, based on shortest line.
	width = 100;
	for (int q = 0; q < lines.size(); q++){
		if (lines.get(q).length() < width)
			width = lines.get(q).length();
	}
	
	//to change the grid size, just change these 2 numbers.
	Cell[][] grid1 = new Cell [width][height];
	System.out.println("Length: " + grid1.length);
	System.out.println("Height: " + grid1[0].length);

	int x;
	int y;
  	
	//initializes 1st generation based on input file
	for (x = 0, y = 0; y < grid1[0].length; x++){
		if (lines.get(y).charAt(x) == '.' || lines.get(y).charAt(x) == ' '){
		grid1[x][y] = new Cell (false);
		}
		else grid1[x][y] = new Cell (true);
		if (x == (grid1.length -1)){
			y++;
			x = (-1);
		}
	}
/*	//this initializes the first generation
	for (x = 0, y = 0; y < grid1[0].length; x++){
		grid1[x][y] = new Cell (false);
		if (x == (grid1.length -1)){
			y++;
			x = (-1);
		}
	}
*/
	Cell[][] grid2 = new Cell [grid1.length][grid1[0].length];
	gridCopier(grid1, grid2);


	while (true){
		StephenIO.prompt("Press enter to iterate. Type \"q\" to quit: ");
		if (StephenIO.readLine().equalsIgnoreCase("q")) return;
		else if ((generations + 2) % 2 == 0) {
			iterator(grid1, grid2);
			gridPrint(grid1);
		}
		else {
			iterator(grid1, grid2);
			gridPrint(grid2);
		}
	}


}
}









