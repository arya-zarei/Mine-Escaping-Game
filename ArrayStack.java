/**
 * This class is an ArrayStack class where we have 2 private instance variables top and array. Array is set to 10 and top is set to -1 (for index).
 * We have all the methods of a StackADT and some extra classes. These extras include expand expandCapacity and shrinkCapacity. If the capacity
 * of the array is 75% or greater we make a new array with an increase of 10 more length and add all the elements to the new stack. If the capacity is 
 * 25% or less and the length is 20 or greater we subtract the length by 10 and add all the elements back to the new stack. Additionally, we have a clear method
 * to reset top and erase the array stack and a getCapacity method that returns the array length. 
 * Additionally, in the push, pop, peek, isEmpty, and size method, the top is negative 1 to stay in line with the array but, top + 1 is the actual top.
 * @author aryaz CS1027 Assignment #3
 */
public class ArrayStack <T>
{
	private T[] array; //array holds items in the stack
	private int top; //int for top of the stack
	
	public ArrayStack() //constructor initialized array and top
	{
		array = (T[])(new Object [10]); //array with an original capacity of 10 to hold items
		top = -1; //top = -1 for empty, to be in track with index of positions
	} 
	
	public void push(T element) /**@param element is being pushed on to the top of array stack **/
	{
		expandCapacity(); //if atleast 75% of capacity is being used it will be expanded by +10
		top += 1; //add one to top count for new index
		array[top] = element; //top index of array is now element
	}
	
	public T pop() throws StackException //remove element from top
	{
		if(isEmpty()) //if stack empty
		{ 
			throw new StackException("Stack is empty"); /**@throw exception if index is -1 or less (meaning empty stack) **/
		}
		else //if stack not empty
		{ 
			shrinkCapacity(); //if 25% of under capacity and 20+ available capacity
			T oldTop = array[top]; //value of top to be popped
			top -= 1; //update index of top
			return oldTop; /** @return the value of old top **/
		} 
	}
	
	public T peek () throws StackException /** @returns element from top without removing **/
	{
		if (isEmpty()) 
		{ 
			throw new StackException("Stack is empty"); 
		}
		else 
		{ 
			return array[top]; //return the stack array element at index of top
		}
		
	}
	
	public boolean isEmpty () /**@return boolean if array is empty **/
	{
		return top == -1; /**@return true if top = -1, empty stack **/
	}
	
	public int size () /**@return number of elements in stack **/
	{
		return top + 1; /**@return top + 1 because top is at index value **/
	}
	
	public void clear () //empty array and resets top
	{
		T[] clear = (T[])(new Object[10]);
		array = clear; //array equals new empty array with capacity 10
		top = -1; //top is reset to original value, -1
	}
	
	public int getCapacity () /**@return length of array**/
	{
		return array.length; 
	}
	
	public int getTop ()
	{
		return top; /**@return top index**/
	}
	 
	public String toString () /**@return String of stack **/
	{
		if(isEmpty())
		{
			return "Empty stack."; /**@return message if stack is empty**/
		}
		
		else
		{
			String stackString = "Stack: "; //stack string
			for(int index = top; index > -1; index--)
			{ //for loop to print stack from top index to index 0
				if(index == 0)
				{
					stackString += array[index] + "."; //adds last index
				}
				else
				{
					stackString += array[index] + ", "; //adds all other index
				}
			 }
			 return stackString; /**@return string **/
		 }
	 }
	 
	 private void expandCapacity () 
	 {
		 if( ((double) size() / getCapacity()) >= 0.75 ) //check if capacity should be expanded
		 {
			 T[] larger = (T[])(new Object[array.length + 10]); //expanded new array

				for (int index = 0; index < array.length; index++) //traverse through old array  
				{
					larger[index] = array[index]; //adds all elements to larger array
				}

				array = larger; //updates array to larger array
		 }
	 }
	 
	 private void shrinkCapacity ()
	 {
		if( (getCapacity() >= 20) && ( ((double) size() / getCapacity()) <= 0.25) ) //check if capacity should be shrunk
		{
			T[] smaller = (T[])(new Object[array.length - 10]); //smaller new array
		 
			for (int index = 0; index < smaller.length; index++) //traverse through old array  
			{
				smaller[index] = array[index]; //adds element to new smaller array
			}
			
			array = smaller; //updates array to smaller array
		}
	 }
}