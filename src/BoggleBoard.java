import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Keeps track of a Boggle Board information including the position of
 * characters, the words found on it, and the maximum possible score for a given
 * Boggle board
 * @author Sherilyn
 * @version March 15, 2015
 */
public class BoggleBoard
{
	// Creates the board
	private char[][] grid;

	/**
	 * Reads in a file and constructs a Boggle board with the given file name
	 * @param file the file that the board is created upon
	 */
	public BoggleBoard(String file)
	{
		try
		{
			// Reads in the given file
			Scanner inFile = new Scanner(new File(file));
			// Creates an ArrayList to store the information in the given file
			ArrayList<String> lines = new ArrayList<String>();

			// Adds lines of characters from the file to the ArrayList
			while (inFile.hasNextLine())
				lines.add(inFile.nextLine());
			// Creates a new 2D Array from the ArrayList
			grid = new char[lines.size()][];
			int row = 0;
			for (String nextLine : lines)
			{
				// Converts the Strings within each line to characters and adds
				// them to the board
				grid[row] = nextLine.toCharArray();
				row++;
			}
			inFile.close();// Closes the Scanner
		}
		// If the file is not found by the BoggleBoard class
		catch (FileNotFoundException exp)
		{
			// Displays the board as "BAD FILE NAME"
			grid = new char[][] { { 'B', 'A', 'D' },
					{ 'F', 'I', 'L', 'E' },
					{ 'N', 'A', 'M', 'E' } };
		}
	}

	/**
	 * Searches to see if a word is on the Boggle board
	 * @param word the word to search for on the board
	 * @return whether the word is found on the board
	 */
	public boolean search(String word)
	{
		// Goes through every row and column to look for the first character of
		// the given word
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[0].length; col++)
			{
				if (grid[row][col] == word.charAt(0))
				{
					// If the character at the position is the same as the first
					// character of the given word, then search for the entire
					// word starting at this position by calling searchAround
					if (searchAround(row, col, word))
						// If the word is on the board, returns true
						return true;
				}
			}
		}
		// Returns false if the word is not on the board
		return false;
	}

	/**
	 * Recursively checks if a word is found on the Boggle board by checking
	 * each letter from all eight possible directions starting on the indicated
	 * spot
	 * @param row the row of the starting position
	 * @param col the column of the starting position
	 * @param wordLeft the word left after taking away the first letter from the
	 *            original word
	 * @return returns if the word is found on the board
	 */
	private boolean searchAround(int row, int col, String wordLeft)
	{
		if (row >= grid.length || row < 0 || col >= grid[row].length
				|| col < 0)
			// Returns false if the starting position is out of the board
			return false;

		if (wordLeft.charAt(0) != grid[row][col])
			// Returns false if the character at the starting position is not
			// the same as the first letter of wordLeft
			return false;

		if (wordLeft.length() == 1)
			// Returns true if the length of wordLeft is 1
			return true;

		// Marks the character at the starting position by making it lower case
		// The character is being marked since each character at a certain spot
		// should only be checked once in one word
		grid[row][col] = Character.toLowerCase(grid[row][col]);

		// Recursively checks for a word by going through all eight possible
		// directions
		wordLeft = wordLeft.substring(1);
		boolean isWordOn = (searchAround(row + 1, col + 1, wordLeft)
				|| searchAround(row + 1, col, wordLeft)
				|| searchAround(row + 1, col - 1, wordLeft)
				|| searchAround(row, col + 1, wordLeft)
				|| searchAround(row, col - 1, wordLeft)
				|| searchAround(row - 1, col - 1, wordLeft)
				|| searchAround(row - 1, col, wordLeft)
				|| searchAround(row - 1, col + 1, wordLeft));

		// Remarks the character at the starting position by making it upper
		// case
		// The character is being remarked since it is needed when checking for
		// a different word
		grid[row][col] = Character.toUpperCase(grid[row][col]);

		// Returns whether the given word is found on the board
		return isWordOn;
	}

	/**
	 * Calculates the maximum score that a Boggle board can have given a list of
	 * valid words. Scores are calculated based on the provided rules
	 * @param wordList the list of words being searched for on the Boggle board
	 * @return the calculated maximum score
	 */
	public int getMaxScore(List<String> wordList)
	{
		// Declares and initializes the score variable
		int score = 0;

		// Goes through every word on the word list and checks if it's on the
		// Boggle board
		for (String nextWord : wordList)
		{
			// If the word is on the Boggle board
			if (search(nextWord))
			{
				// Finds the length of the word for the purpose of calculating
				// score
				int length = nextWord.length();

				// Adds points onto the score according to the provided rules
				if (length >= 8)
					score += 11;
				else if (length == 7)
					score += 5;
				else if (length == 6)
					score += 3;
				else if (length == 5)
					score += 2;
				else if (length >= 3)
				{
					// If the grid is 4x4 or the word length is 4, then add 1 to
					// the score
					if (grid.length == 4 || length == 4)
						score += 1;
				}

			}
		}
		return score;// Returns the calculated maximum score
	}

	/**
	 * Converts the Boggle board to a String for formatted output
	 * @return the String version of the Boggle board
	 */
	public String toString()
	{
		// Creates a StringBuilder with a length of 25 since the board can only
		// contain up to 25 letters
		StringBuilder board = new StringBuilder(25);

		// Goes through every row and column for each letter on the board
		for (int row = 0; row < grid.length; row++)
		{
			for (int col = 0; col < grid[0].length; col++)
			{
				// Adds the character at this position to the StringBuilder
				board.append(grid[row][col]);
				board.append(" ");
			}
			// Switches to the next line
			board.append("\n");
		}
		// Returns the converted board
		return board.toString();
	}
}
