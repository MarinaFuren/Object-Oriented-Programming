package oop.ex5.filescript;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the manager of the program.
 * Includes the main, reads the command line, call to the parser
 * to parse the commands, goes over the sections and prints
 * the files and the warnings (if has). Catch the type one exceptions.
 *
 */
public class MyFileScript {
	private static int[] warnings;
	private static ArrayList<File> fileArray;
	private static final String ERROR_MSG = "ERROR";
	private static final String WARNING_MSG = "Warning in line ";


	/**
	 * The main, the manager of the program.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			// reset the members
			File[] sourceDir = null;
			File commandFile = null;
			String sourceDirPath = null;
			String commandFilePath = null;
			
			//reading the command line
			sourceDirPath = args[0];
			commandFilePath = args[1];
			commandFile = new File(commandFilePath);
			File directory = new File(sourceDirPath);
			
			//check if the input valid
			if (!directory.exists() || !directory.isDirectory()){
				throw new BadInputError();
			}
			if (!commandFile.exists() || !commandFile.isFile()){
				throw new BadInputError();
			}
			
			sourceDir = directory.listFiles();
			Parser parser = new Parser();
			ArrayList<Section> sections = parser.parseFile(commandFile);
			for (Section newSection: sections){
				fileArray = newSection.filterOrderSection(sourceDir);
				warnings = newSection.getWarnings();
				
				//check if need to print warnings
				if (warnings != null){
					if (warnings[0] != 0){
						System.out.println(WARNING_MSG + warnings[0]);
					} 
					if (warnings[1] != 0){
						System.out.println(WARNING_MSG + warnings[1]);
					}
				}
				for (File newFile: fileArray){
					System.out.println(newFile.getName());
				}
			}
			
		}
		catch (BadInputError error) {
			System.err.println(ERROR_MSG);
			return;
		}
		catch (IOException error){
			System.err.println(ERROR_MSG);
			return;
		}
	}

}
