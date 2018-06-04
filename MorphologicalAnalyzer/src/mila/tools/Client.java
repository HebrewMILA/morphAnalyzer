package mila.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		String input = args[0];
		String output = args[1];
		String mySocket = args[2];
		String host = args[3];

		int socket = Integer.parseInt(mySocket);
		File in = new File(input);
		File out = new File(output);
		if (in.isDirectory() && out.isDirectory()) {
			// if we want to process a directory recursively - process a full
			// corpus
			client.processDirectoryNoDataLoad(input, output, socket, host);
			// if we want to process a single input file, output file
		} else if (!in.isDirectory() && !out.isDirectory()) {
			client.process(input, output, socket, host);
		} else {
			System.out.println("input parameters must include input & output file or input & output directory");
		}
		System.exit(0);
	}

	PrintWriter out = null;
	BufferedReader sin = null;

	private void analyzeDirectory(File inputDirectory, String outputDirectory, final int pos, int socket, String host) {
		if (inputDirectory.isDirectory()) {
			// create correspond directory for xml

			String out = outputDirectory + inputDirectory.getAbsolutePath().substring(pos);
			// System.out.println(out);
			if (!(new File(out)).exists()) {
				if ((new File(out)).mkdir())
					System.out.println("Success creating directory: " + out);
				else
					System.out.println("Error in creation of directory: " + out);
			}
			// call for analysis of each file/dir under the currect directory
			File[] files = inputDirectory.listFiles();
			Arrays.sort(files);
			for (int i = 0; i < files.length; i++)
				analyzeDirectory(files[i], outputDirectory, pos, socket, host);

		} else { // file to be analyzed
			String inputFile = inputDirectory.getAbsolutePath();
			String outputFile = outputDirectory + inputFile.substring(pos);
			outputFile = outputFile.replaceAll(".txt", ".xml");
			try {
				process(inputFile, outputFile, socket, host);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private Socket connectServer(int socket, String host) {
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(host, socket);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:yeda.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: yeda.");
			System.exit(1);
		}
		return clientSocket;
	}

	public String getServerResponse(Socket clientSocket) {
		String outputAnalysis = "";
		String lineFromServer = "";
		try {
			sin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF8"));
			StringBuffer fromServer = new StringBuffer();
			lineFromServer = sin.readLine();
			while (lineFromServer == null) {
				lineFromServer = sin.readLine();
			}
			fromServer.append(lineFromServer);
			if (!lineFromServer.endsWith("#*"))
				do {
					lineFromServer = sin.readLine();
					if (lineFromServer != null)
						fromServer.append(lineFromServer);
				} while (lineFromServer != null && !lineFromServer.endsWith("#*"));

			String fromServerStr = fromServer.toString();
			outputAnalysis = (fromServerStr).substring(0, fromServerStr.length() - 2);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: yeda.");
			System.exit(1);
		}
		return outputAnalysis;
	}

	public void process(String inputFile, String outputFile, int socket, String host) {
		try {
			System.out.println("inputFile=" + inputFile);
			System.out.println("outputFile=" + outputFile);
			Socket clientSocket = null;
			String morphlogicalAnalyzerOutput = "";

			clientSocket = connectServer(socket, host);
			readInputFile(inputFile);
			String taggedAnalysis = morphlogicalAnalyzerOutput = getServerResponse(clientSocket);
			if (morphlogicalAnalyzerOutput.equals("")) {
				System.out.println("No Analysis to file=" + inputFile);
				return;
			} else {
				FileOutputStream out = null;
				OutputStreamWriter pOut = null;
				try {
					out = new FileOutputStream(outputFile);

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				try {
					pOut = new OutputStreamWriter(out, "UTF8");
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				pOut.write(taggedAnalysis);
				pOut.close();
			}

			out.close();
			sin.close();
			clientSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:yeda.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: yeda.");
			System.exit(1);
		}

	}

	public void processDirectoryNoDataLoad(String inputDirectory, String outputDirectory, int socket, String host) {
		File in = new File(inputDirectory);
		int pos = (in.isDirectory() ? in.getAbsolutePath().length() : in.getParent().length());
		analyzeDirectory(in, outputDirectory, pos, socket, host);
	}

	public void readInputFile(String inputFile) {
		// StringBuffer contents = new StringBuffer();
		try {
			File file = new File(inputFile);
			Scanner scanner = new Scanner(file, "UTF-8");
			scanner.useDelimiter(System.lineSeparator());
			String line = "";
			while (scanner.hasNext()) {
				line = scanner.next();
				// BOM handling - skip BOM and continue processing
				if (!line.equals("") && line.charAt(0) == 0xFEFF) {
					line = line.substring(1);
				}
				out.println(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.println("Bye");
	}
}
