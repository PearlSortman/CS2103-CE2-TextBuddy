package main;

import java.io.*;
import java.util.*;

public class CE2 {

	public static PrintWriter printWriter = null;
	public static BufferedWriter bufferedWriter = null;
	public static BufferedReader bufferedReader = null;
	public static BufferedReader bufferedReaderDisplay = null;

	public static File textFile;
	public static String fileName, line;

	public static boolean isSubMethod = false;
	public static final String MESSAGE_WELCOME = "Welcome to TextBuddy. " + textFile + " is ready for use";

	public static void main(String[] args) throws IOException {

		/*
		 * Takes file name as parameter upon execution in command line
		 */
		textFile = new File(args[0]);
		fileName = args[0];
		checkIfExists();
		initializeReaderWriter();

		System.out.println("Welcome to TextBuddy. " + textFile
				+ " is ready for use");

		/*
		 * loops through after completing each command to accept another command
		 */
		while (true) {
			/*
			 * Collects user input using java scanner util Adds all input to new
			 * Array Grabs command from first string in input Parses remaining
			 * input if any
			 */
			System.out.print("command: ");
			Scanner lines = new Scanner(System.in);
			Scanner tokens = new Scanner(lines.nextLine());
			List<String> input = new ArrayList<String>();
			while (tokens.hasNext()) {
				input.add(tokens.next());
			}
			String[] inputArray = input.toArray(new String[input.size()]);
			String command = inputArray[0];

			/*
			 * Determines which command is being called and delegates to
			 * appropriate method
			 */
			if (command.equals("display")) {
				display();
			} else if (command.equals("add")) {
				if (inputArray[1] != null) {
					String addText = inputArray[1];
					for (int i = 2; i < inputArray.length; i++) {
						addText = addText + " " + inputArray[i];
					}
					add(addText);
				} else {
					System.out
							.println("Please enter a valid argument for add command.");
				}

			} else if (command.equals("clear")) {
				clear();
			} else if (command.equals("delete")) {
				if (inputArray[1] != null) {
					String deleteLine = inputArray[1];
					delete(deleteLine);
				} else {
					System.out
							.println("Please enter a valid argument for delete command.");
				}

			} else if (command.equals("exit")) {
				System.exit(0);
			} else {
				System.out.println("Please enter a valid command.");
			}
		}
	}
	
	private static void printMessage(String message) {
		System.out.println(message);
	}

	/*
	 * if file called does not exist OR text file is a directory it creates new
	 */
	private static void checkIfExists() throws FileNotFoundException,
			UnsupportedEncodingException {
		if (!textFile.exists() || textFile.isDirectory()) {
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
			// APPEND MODE SET HERE
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

	@SuppressWarnings("resource")
	private static void delete(String deleteLine) throws IOException {
		isSubMethod = true;
		int deleteLineNum = Integer.parseInt(deleteLine);
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String str;

		List<String> list = new ArrayList<String>();
		while ((str = in.readLine()) != null) {
			list.add(str);
		}

		String[] stringArr = list.toArray(new String[0]);
		clear();
		for (int i = 0; i < deleteLineNum - 1; i++) {
			add(stringArr[i]);
		}
		for (int i = deleteLineNum; i < stringArr.length; i++) {
			add(stringArr[i]);
		}
		System.out.println("deleted from " + fileName + " \""
				+ stringArr[deleteLineNum - 1] + "\"");
		isSubMethod = false;
	}

	private static void clear() throws IOException {
		textFile.delete();
		createFile();
		initializeReaderWriter();
		if (!isSubMethod) {
			System.out.println("all content deleted from " + fileName);
		}
	}

}
