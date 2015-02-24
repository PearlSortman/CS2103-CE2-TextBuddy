/**
 * Name: Pearl Sortman
 * Matric Number: A0135005
 * Course: CS2103 Software Engineering
 * 
 * Assignment: CE2 TextBuddy++
 * 
 * Application Description: ....
 * 
 * 
 **/

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
	public static boolean isSubMethod = false;

	public static final String MESSAGE_WELCOME = "Welcome to TextBuddy. "
			+ file + " is ready for use";
	public static final String MESSAGE_CLEARED = "all content deleted from "
			+ fileName;
	public static final String MESSAGE_ADDED = "";
	public static final String COMMAND_PROMPT = "command: ";
	public static final String ARGUMENT_ERROR = "Please enter a valid argument for the specified command.";
	public static final String COMMAND_ERROR = "Please enter a valid command.";

	public static void main(String[] args) throws IOException { // Takes file
																// name as
																// parameter
																// upon
																// execution in
																// command line
		file = new File(args[0]);
		fileName = args[0];
		checkIfExists();
		initializeReaderWriter();
		printMessage(MESSAGE_WELCOME);

		while (true) { // loops through after completing each command to accept
						// another command
			String[] inputArray = getInputArray();
			String command = inputArray[0];
			executeCommand(command, inputArray);
		}
	}

	/*
	 * Collects user input using java scanner util Adds all input to new Array
	 * Grabs command from first string in input Parses remaining input if any
	 */
	private static String[] getInputArray() {
		printMessage(COMMAND_PROMPT);
		Scanner lines = new Scanner(System.in);
		Scanner tokens = new Scanner(lines.nextLine());
		List<String> input = new ArrayList<String>();
		while (tokens.hasNext()) {
			input.add(tokens.next());
		}
		String[] inputArray = input.toArray(new String[input.size()]);
		return inputArray;
	}

	// Executes given command and checks for argument validity
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
				printMessage(ARGUMENT_ERROR);
			}
		} else if (command.equals("delete")) { // DELETE
			if (inputArray[1] != null) {
				int deleteLine = Integer.parseInt(inputArray[1]);
				delete(deleteLine);
			} else {
				printMessage(ARGUMENT_ERROR);
			}
		} else if (command.equals("search")) { // SEARCH
			String searchString = getInputArgsString(inputArray);
			if (searchString != null) {
				search(searchString);
			} else {
				printMessage(ARGUMENT_ERROR);
			}
		} else if (command.equals("exit")) { // EXIT
			System.exit(0);
		} else { // ANYTHING ELSE
			printMessage(COMMAND_ERROR);
		}
	}

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

	private static void printMessage(String message) {
		System.out.println(message);
	}

	// Search for a word in the file and return the lines containing that word
	private static void search(String searchByString) {

	}

	// Sorts the output lines alphabetically
	@SuppressWarnings("unused")
	private static void sort() throws IOException {
		isSubMethod = true; // ensures print statement in clear() will not
							// execute
		String[] tempArray = getFileAsArray();
		clear();
		Arrays.sort(tempArray);
		for (int i = 0; i < tempArray.length; i++) {
			add(tempArray[i]);
		}
	}

	// If file called does not exist OR text file is a directory it creates new
	private static void checkIfExists() throws FileNotFoundException,
			UnsupportedEncodingException {
		if (!file.exists() || file.isDirectory()) {
			createFile();
		} else {
			// proceed with specified file
		}
	}

	private static void initializeReaderWriter() throws IOException {
		printWriter = new PrintWriter(fileName);
		bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
		bufferedReader = new BufferedReader(new FileReader(fileName));
	}

	private static void createFile() throws FileNotFoundException,
			UnsupportedEncodingException {
		printWriter = new PrintWriter(fileName);
	}

	private static void display() throws IOException {
		bufferedReaderDisplay = new BufferedReader(new BufferedReader(
				new FileReader(fileName)));
		int lineNumber = 1;
		String line;
		if ((line = bufferedReaderDisplay.readLine()) == null) {
			System.out.println(fileName + " is empty");
		} else {
			System.out.println(lineNumber + ". " + line);
			lineNumber++;
			while ((line = bufferedReaderDisplay.readLine()) != null) {
				System.out.println(lineNumber + ". " + line);
				lineNumber++;
			}
		}
	}

	private static void add(String addText) throws IOException {
		try {
			bufferedWriter.append(addText);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			if (!isSubMethod) {
				System.out.println("added to " + fileName + ": \"" + addText
						+ "\"");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static void delete(int deleteLineNum) throws IOException {
		isSubMethod = true; // ensures print statement in clear() will not
							// execute
		String[] stringArr = getFileAsArray();
		clear();
		isSubMethod = true;
		for (int i = 0; i < deleteLineNum - 1; i++) {
			add(stringArr[i]);
		}
		for (int i = deleteLineNum; i < stringArr.length; i++) {
			add(stringArr[i]);
		}
		System.out.println("deleted from " + fileName + " \""
				+ stringArr[deleteLineNum - 1] + "\"");
	}

	@SuppressWarnings("resource")
	private static String[] getFileAsArray() throws IOException {
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

	private static void clear() throws IOException {
		file.delete();
		createFile();
		initializeReaderWriter();
		if (!isSubMethod) {
			printMessage(MESSAGE_CLEARED);
		}
		isSubMethod = false;
	}

}
