//Game of Life by Stephen Boyd
//my attempt at implementing Conway's Game of Life, written December 2012.

//Uses Cell.java
//Cell Instance methods are checkLife, kill, birth, and toString.
//Class helper methods are gridPrint, gridCopier, generator, and iterator.
//The two grids are 2-dimensional arrays of Cell objects.
//One is the current generation, the other is the next generation,
//   and they alternate roles every generation.

//generator computes the next generation's positions
//   and writes to the other grid (matrix).
//iterator determines which grid writes to the other.

//Indentation is screwed up because of multiple editors.

//Rules:
// Any live cell with fewer than two live neighbours dies
// Any live cell with two or three live neighbours lives on
// Any live cell with more than three live neighbours dies
// Any dead cell with exactly three live neighbours becomes a live cell

// In other words:
// Live cells: If live neighbours == 2 or 3, birth. Else, kill.
// Dead cells: If live neighbours == 3, birth. Else, kill.


import jpb.*;

public class GameOfLife {

	static int generations = 0;	

	private static void gridPrint (Cell [][] name){
		int i;
		for (i = 0; i < 10; i++) System.out.println("\n \n \n \n \n");
		System.out.println("Generation: " + generations);
		for (int x = 0, y = 0; y < name[0].length; x++){
			System.out.print(name[x][y]);
			if (x == (name.length -1)){
				y++;
				x = (-1);
				System.out.println();
			}
		}
		System.out.println();
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
 //The redundant variables are for readability.
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
if (n == true) liveNeighbours++;
if (ne == true) liveNeighbours++;
if (w == true) liveNeighbours++;
if (e == true) liveNeighbours++;
if (sw == true) liveNeighbours++;
if (s == true) liveNeighbours++;
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
  //to change the grid size, just change these 2 numbers.
  Cell[][] grid1 = new Cell [40][20];
  System.out.println("Length: " + grid1.length);
  System.out.println("Height: " + grid1[0].length);

  int x;
  int y;
  
//this initializes the first generation
  for (x = 0, y = 0; y < grid1[0].length; x++){
   grid1[x][y] = new Cell (false);
   if (x == (grid1.length -1)){
    y++;
    x = (-1);
   }
  }
  
  Cell[][] grid2 = new Cell [grid1.length][grid1[0].length];
  gridCopier(grid1, grid2);


//These are starting patterns, known to do cool repeating things.
//The best part of this program is experimenting with different patterns.

  //glider
  grid1[3][2].birth();
  grid1[4][3].birth();
  grid1[5][3].birth();
  grid1[3][4].birth();
  grid1[4][4].birth();  

  //lightweight spaceship
  grid1[4][14].birth();
  grid1[5][14].birth();
  grid1[6][14].birth();
  grid1[7][14].birth();
  grid1[3][15].birth();
  grid1[7][15].birth();
  grid1[7][16].birth();
  grid1[3][17].birth();
  grid1[6][17].birth();

  //ten cell row
  grid1[19][7].birth();
  grid1[20][7].birth();
  grid1[21][7].birth();
  grid1[22][7].birth();
  grid1[23][7].birth();
  grid1[24][7].birth();
  grid1[25][7].birth();
  grid1[26][7].birth();
  grid1[27][7].birth();
  grid1[28][7].birth();

  gridCopier(grid1, grid2);
    
  System.out.println();
  System.out.println(" Grid 1: ");
  gridPrint(grid1);
  
  System.out.println();
  System.out.println(" Grid 2: ");
  gridPrint(grid2);


	boolean loop = true;
	while (loop == true){

		
		SimpleIO.prompt("Press enter to iterate. Type \"q\" to quit: ");
		if (SimpleIO.readLine().equalsIgnoreCase("q")) return;
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









