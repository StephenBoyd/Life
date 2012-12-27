public class Cell{
	private int liveNeighbors;
	private boolean living = false;
	
	public Cell (boolean status){
		if (status== false) living = false;
		else living = true;
	}

	
	public boolean checkLife(){
		if (living == true)
			return true;
		else return false;	
	}


	public void kill(){
		living = false;
	}


	public void birth(){
		living = true;
	}

	public String toString(){
		if (living == true) return "#";
		else return ".";
	}
}
