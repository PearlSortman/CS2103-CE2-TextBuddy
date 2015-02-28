package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import main.CE2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CE2ClassTest {

	// creates the test data
	@Parameters
	public static String[] sortedFile() {
		String[] sortedFile = new String[10];
		sortedFile[0] = "aaaaa";
		sortedFile[1] = "bbbbb";
		sortedFile[2] = "ccccc";
		sortedFile[3] = "ddddd";
		sortedFile[4] = "eeeee";
		sortedFile[5] = "fffff";
		return sortedFile;
	}

	@Parameters
	public static String[] filteredFile() {
		String[] filteredFile = new String[10];
		filteredFile[0] = "sample 1";
		filteredFile[1] = "more sample stuff";
		filteredFile[2] = "sample sample samplezzzz";
		filteredFile[3] = "this is a sample";
		return filteredFile;
	}

	@Test
	public void testSearchMethodReturnsFilteredResults() throws IOException {
		String[] expected = filteredFile();
		String[] actual = CE2.search("sample");
		for (int i=0; i<expected.length-1; i++) {
			assertEquals("Filtered result arrays content is identical.", expected[i], actual[i]);
		}
	}

	@Test
	public void testSortMethodReturnsSortedResults() throws IOException {
		String[] expected = sortedFile();
		String[] actual = CE2.sort();
		for (int i=0; i<expected.length-1; i++) {
			assertEquals("Sorted result arrays content is identical.", expected[i], actual[i]);
		}
	}
}
