/*****************************************************
 * 
 * Name: Pearl Sortman
 * Matric Number: A0135005
 * Course: CS2103 Software Engineering
 * Tutorial Group: T13
 * 
 * Assignment: CE2 TextBuddy++
 * 
 * Application Description:
 * Text editor supports the following functions:
 * 	add (String)
 * 	delete (int)
 * 	clear
 * 	sort
 * 	search (String)
 * 	exit
 * 
 ******************************************************/

package main;

import java.io.*;
import java.util.*;

public class CE2 {

	public static PrintWriter printWriter = null;
	public static BufferedWriter bufferedWriter = null;
	public static BufferedReader bufferedReader = null;
	public static BufferedReader bufferedReaderDisplay = null;

	public static File file;
	public static String fileName;

	// controls execution of printMessage in add() and clear()
	public static boolean isSubMethod = false;

	public static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	public static final String MESSAGE_CLEARED = "all content deleted from %1$s";
	public static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	public static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
	public static final String MESSAGE_SORTED = "%1$s has been sorted alphabetically";
	public static final String MESSAGE_EMPTY = "%1$s is empty";
	public static final String ARGUMENT_ERROR = "Please enter a valid argument for the %1$s command.";
	public static final String COMMAND_ERROR = "Please enter a valid command.";

	/*
	 * upon execution, takes file name as parameter in command line loops
	 * continuously after each command to accept another proceeds till exit
	 * command entered
	 */
	public static void main(String[] args) throws IOException {
		file = new File(args[0]);
		fileName = args[0];
		checkIfExists();
		initializeReaderWriter();
		printMessage(MESSAGE_WELCOME, fileName, null);
		while (true) {
			String[] inputArray = getInputArray();
			String command = inputArray[0];
			executeCommand(command, inputArray);
			isSubMethod = false;
		}
	}

	/*
	 * collects user input using java scanner util 
	 * adds all input to new Array
	 * grabs command from first string in input
	 * parses remaining input, if any
	 * assumes the input will never be completely blank
	 */
	private static String[] getInputArray() {
		System.out.print("command: ");
		Scanner lines = new Scanner(System.in);
		Scanner tokens = new Scanner(lines.nextLine());
		List<String> input = new ArrayList<String>();
		while (tokens.hasNext()) {
			input.add(tokens.next());
		}
		String[] inputArray = input.toArray(new String[input.size()]);
		return inputArray;
	}

	/*
	 * parses user input returning as an array command will always be at 0 index
	 */
	private static String getInputArgsString(String[] inputArray) {
		if (inputArray[1] != null) {
			String inputArgsString = inputArray[1];
			for (int i = 2; i < inputArray.length; i++) {
				inputArgsString = inputArgsString + " " + inputArray[i];
			}
			return inputArgsString;
		} else {
			return null;
		}
	}

	@SuppressWarnings("resource")
	public static String[] getFileAsArray() throws IOException {
		BufferedReader tempBufferedReader = new BufferedReader(new FileReader(
				fileName));
		String lineInFile;
		List<String> listOfLines = new ArrayList<String>();
		while ((lineInFile = tempBufferedReader.readLine()) != null) {
			listOfLines.add(lineInFile);
		}
		String[] arrayOfLines = listOfLines.toArray(new String[0]);
		return arrayOfLines;
	}

	/*
	 * executes given command and checks for argument validity
	 */
	private static void executeCommand(String command, String[] inputArray)
			throws IOException {
		if (command.equals("display")) { // DISPLAY
			display();
		} else if (command.equals("clear")) { // CLEAR
			clear();
		} else if (command.equals("sort")) { // SORT
			sort();
		} else if (command.equals("add")) { // ADD
			String addText = getInputArgsString(inputArray);
			if (addText != null) {
				add(addText);
			} else {
				printMessage(ARGUMENT_ERROR, "add", null);
			}
		} else if (command.equals("delete")) { // DELETE
			if (inputArray[1] != null) {
				int deleteLine = Integer.parseInt(inputArray[1]);
				delete(deleteLine);
			} else {
				printMessage(ARGUMENT_ERROR, "delete", null);
			}
		} else if (command.equals("search")) { // SEARCH
			String searchString = getInputArgsString(inputArray);
			if (searchString != null) {
				search(searchString);
			} else {
				printMessage(ARGUMENT_ERROR, "search", null);
			}
		} else if (command.equals("exit")) { // EXIT
			System.exit(0);
		} else { // ANYTHING ELSE
			printMessage(COMMAND_ERROR, null, null);
		}
	}

	/*
	 * if file called does not exist OR text file is a directory it creates new
	 */
	private static void checkIfExists() throws FileNotFoundException,
			UnsupportedEncodingException {
		if (!file.exists() || file.isDirectory()) {
			createFile();
		}
	}

	private static void createFile() throws FileNotFoundException,
			UnsupportedEncodingException {
		printWriter = new PrintWriter(fileName);
	}

	private static void initializeReaderWriter() throws IOException {
		printWriter = new PrintWriter(fileName);
		bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
		bufferedReader = new BufferedReader(new FileReader(fileName));
	}

	/*
	 * prints file in current state (results after latest command has executed)
	 * handles printing of line numbers checks if content is empty
	 */
	private static void display() throws IOException {
		if (checkIfEmpty()) {
			printMessage(MESSAGE_EMPTY, fileName, null);
		} else {
			String[] tempArray = getFileAsArray();
			int lineNumber = 1;
			for (int i = 0; i < tempArray.length; i++) {
				System.out.println(lineNumber + ". " + tempArray[i]);
				lineNumber++;
			}
		}
	}

	private static void add(String addText) throws IOException {
		bufferedWriter.append(addText);
		bufferedWriter.newLine();
		bufferedWriter.flush();
		if (!isSubMethod) {
			printMessage(MESSAGE_ADDED, fileName, addText);
		}
	}

	/*
	 * checks to ensure line to delete exists in file checks to ensure file not
	 * empty saves current file as array, storing each line as seperate index
	 * clears current file re-adds all lines up until the deleted line is
	 * reached skips deleted line re-adds all lines following deleted line
	 */
	private static void delete(int deleteLineNum) throws IOException {
		String[] stringArr = getFileAsArray();
		if (checkIfEmpty()) {
			printMessage(MESSAGE_EMPTY, fileName, null);
		} else if (deleteLineNum > stringArr.length) {
			printMessage(ARGUMENT_ERROR, "delete", null);
		} else {
			isSubMethod = true;
			clear();
			for (int i = 0; i < deleteLineNum - 1; i++) {
				add(stringArr[i]);
			}
			for (int i = deleteLineNum; i < stringArr.length; i++) {
				add(stringArr[i]);
			}
			printMessage(MESSAGE_DELETED, fileName,
					stringArr[deleteLineNum - 1]);
			isSubMethod = false;
		}
	}

	/*
	 * clears file by deleting current and creating new with same name only
	 * prints message if clear command was executed by user as direct command
	 */
	private static void clear() throws IOException {
		file.delete();
		createFile();
		initializeReaderWriter();
		if (!isSubMethod) {
			printMessage(MESSAGE_CLEARED, fileName, null);
		}
	}

	/*
	 * search for a word in the file and return the lines containing that word
	 */
	public static String[] search(String searchByString) throws IOException {
		// TODO
		String[] filteredArray = new String[100];
		return filteredArray;
	}

	/*
	 * sorts the output lines alphabetically store current file as array, one
	 * index per line clear file sort array re-add all lines in sorted order
	 */
	public static String[] sort() throws IOException {
		// TODO
		String[] sortedArray = new String[100];
		return sortedArray;
	}

	private static Boolean checkIfEmpty() throws IOException {
		if ((getFileAsArray()).length < 1) {
			return true; // file is empty
		}
		return false; // file has content
	}

	/*
	 * printMessage will customize output strings as defined as final class
	 * variables
	 */
	private static void printMessage(String message, String unique1,
			String unique2) {
		if (unique1 != null && unique2 != null) {
			System.out.println(String.format(message, unique1, unique2));
		} else if (unique1 != null && unique2 == null) {
			System.out.println(String.format(message, unique1));
		} else {
			System.out.println(message);
		}
	}

}
