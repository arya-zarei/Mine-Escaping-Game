/**
 * For the MineEscape class we have 3 private instance variables a map to load the game map, numGold to keep count of gold total 
 * and numKeys to store 3 key color amounts. The Constructor initialized the map, sets gold to 0 and numKeys to an array of length 3.
 * The private helper method findNextCell uses the MapCell object current to find the next cell. Using a separate for loop for going
 * down the hierarchy of next cells from exit, key cell or gold cell, floor to locked cell. Also the for loop goes through index from low to high.
 * The first cell that is found from the any loop is returned. If in all of the four loops there is no cell returned then null will be returned to the 
 * findEscapePath method. The findEscapePath algorithm is copied from the pseudocode given. It initializes a stack and pushed the start onto the stack and 
 * creates a String variable to be returned as it is a String method. While current is not empty and running is true(exit not found).
 * Check if cell is a key cell or a gold cell, and if the cell is beside a lava cell and resets gold. Then sends current into findNextCell to retrieve next cell, 
 * checks if next is null to remove current from stack and mark current out of stack. If next is not null then next is added to stack and so is string of next. 
 * Then check if next is a locked door (last in hierarchy). After loop is exited if the exit is found, the string for the path with the gold total is returned or 
 * string "No solution found" is returned.
 * @author aryaz CS1027 Assignment #3
 */
public class MineEscape 
{
	private Map map; //current mine map
	private int numGold; //count of gold
	private int[] numKeys; //count of all key colors holding
	
	public MineEscape(String filename) /** constructor with a @param filename String **/
	{
		try 
		{
			this.map = new Map (filename); //initialize map variable using filename
			this.numGold = 0; //initialize numGold to 0
			this.numKeys = new int[3]; //array numKeys with 3 cells
		} 
		catch (Exception e) //exception catch for map filename
		{
			System.out.println(e.getMessage()); /** @returns exception message **/
		}
	}
	
	private MapCell findNextCell(MapCell cell) /**@param cell called from findEscapePath to @return next cell for findEscapePath **/
	{
		MapCell current = null; //current MapCell object
		
		for (int i = 0; i < 4; i++) //traverse through for loop for all 4 index
		{
			current = cell.getNeighbour(i); //get current for specific index
				
			if (current != null && !current.isWall() && !current.isMarked() && !current.isLava()) //check if current is not null, a wall, marked, and lava
			{ 
				if (current.isExit()) //if current is an exit cell
				{
					return current; /**@return exit cell**/
				}
			}
		}
		
		for (int i = 0; i < 4; i++) //next level of hierarchy: gold and keys 
		{
			current = cell.getNeighbour(i);
			
			if (current != null && !current.isWall() && !current.isMarked() && !current.isLava())
			{
				if (current.isKeyCell() || current.isGoldCell()) //if current is a key cell or gold cell
				{
					return current; /**@return key or gold cell**/
				}
			}
		}
		
		for (int i = 0; i < 4; i++) //next level of hierarchy: floor
		{
			current = cell.getNeighbour(i);
			
			if (current != null && !current.isWall() && !current.isMarked() && !current.isLava())
			{
				if (current.isFloor()) //if current is a floor cell
				{
					return current; /**@return floor cell**/
				}
			}
		}
		
		for (int i = 0; i < 4; i++) //last level of hierarchy: locked cell
		{
			current = cell.getNeighbour(i);
			if (current != null && !current.isWall() && !current.isMarked() && !current.isLava())
			{
				if (current.isLockCell()) //if current is a lock cell
				{
						
					if ( (current.isRed() && numKeys[0] > 0) || (current.isGreen() && numKeys[1] > 0) || (current.isBlue() && numKeys[2] > 0) )
					{//check all 3 door colors to find the correct door color and then check if a key of that color exists
						return current; /**@return lock cell door**/
					}
				}
			}
		}
		return null; /**@return null if nothing above has been return, meaning there is no next cell**/
	}
	
	public String findEscapePath()
	{
	    ArrayStack<MapCell> arrayPath = new ArrayStack<>(); //initialize array stack for path
	    String escapePath = "Path: "; //string for escape path
	    boolean running = true; //set running boolean to true
	    MapCell top; //top of path
	    
	    
	    arrayPath.push(map.getStart()); //first cell push onto stack
	    arrayPath.peek().markInStack();//mark start cell in stack
	    escapePath += arrayPath.peek().getID() + " "; //add first cell to string
	    
	    while(!arrayPath.isEmpty() && running) //while loop to find path if stack is not empty and running remains true
	    {
	    	top = arrayPath.peek(); //top of path
	    	
	    	if(top.isExit()) //if top is exit 
	    	{
	    		running = false; // set running to false
	    		break; // end loop
	    	}
	    	
	    	else if (top.isKeyCell()) //check if top is a key cell
	    	{
	    		if(top.isRed()) //if key is red 
	    		{
	    			numKeys[0]+=1; //add key to index 0 for all red keys
	    		}
	    		if(top.isGreen()) //if key is green
	    		{
	    			numKeys[1]+=1; //add key to index 1 for all green keys
	    		}
	    		if(top.isBlue()) //if key is blue
	    		{
	    			numKeys[2]+=1; //add key to index 2 for all blue keys
	    		}
	    		top.changeToFloor(); //update top to basic floor cell
	    	}
	    	
	    	else if (top.isGoldCell()) //if top is gold cell
	    	{
	    		numGold++; //add one gold to gold count
	    		top.changeToFloor();
	    	}
	    	
	    	for(int index = 0; index < 4; index++) //check if neighbor cell is lava
	    	{
	    		if (top.getNeighbour(index) != null)
	    		{	if(top.getNeighbour(index).isLava()) //if any neighbor cell is lava
		    		{
		    			numGold = 0; //reset gold count to 0
		    		}
	    		}
	    	}
	    	
	    	MapCell next = findNextCell(top); /** next is the findNextCell using @paramter top **/
	    	
	    	if (next == null) //if next is empty
	    	{
	    		top = arrayPath.pop(); //remove top from path
	    		top.markOutStack(); //set top to markOutStack since top is no longer needed to find path
	    	}
	    	else //if next has a value
	    	{
	    		escapePath += next.getID() + " "; //add next and space to path string
	    		arrayPath.push(next); //push next on to stack (new top)
	    		next.markInStack(); //mark next as in stack
	    		
	    		if(next.isLockCell()) //if cell is a locked door
	    		{ //key availability is checked in findNextCell
	    			if(next.isRed()) //check all 3 door colors
	    			{
	    				numKeys[0] -=1; //update key color index by - 1 as key is being used to unlock door
	    			}
	    			else if(next.isGreen())
	    			{
	    				numKeys[1] -=1;
	    			}
	    			else if(next.isBlue())
	    			{
	    				numKeys[2] -=1;
	    			}
	    			next.changeToFloor(); //change next to basic floor cell
	    		}	    	
	    	}
	    }
	    
	    if(!running) //if not running meaning exit has been found
	    {
	    	escapePath += numGold + "G"; //add gold to final string 
	    	return escapePath; /** @return string escape path**/
	    }
	    else
	    {
	    	return "No solution found"; /**@return no solution as exit has not been found**/
	    }
	}
}