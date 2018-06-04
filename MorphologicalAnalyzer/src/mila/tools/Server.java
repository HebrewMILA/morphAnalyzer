package mila.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;

import mila.mw.MWXMLMorphAnalyzer;

public class Server {

	public static String inputStream2String(InputStream in) throws IOException {
		try {
			StringBuffer buffer = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(in, "UTF8");
			Reader r = new BufferedReader(isr);
			int ch;
			while ((ch = r.read()) > -1) {
				buffer.append((char) ch);
			}
			r.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java  -cp morphAnalyzer.jar Server <port Number>");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = null;
		try {
			String dinflectionsFile = "dataFiles/dinflections.data";
			String dprefixesFile = "dataFiles/dprefixes.data";
			String gimatriasFile = "dataFiles/gimatria.data";
			String dmwinflectionsFile = "dataFiles/dmwinflections.data";
			String dmwe1File = "dataFiles/dmwe1.data";
			String dmwe2File = "dataFiles/dmwe2.data";
			String dmwe3File = "dataFiles/dmwe3.data";
			String dmwe4File = "dataFiles/dmwe4.data";
			// XMLMorphAnalyzer a = new XMLMorphAnalyzer(); // EDIT 21.7.11
			// (yossi)
			MWXMLMorphAnalyzer a = new MWXMLMorphAnalyzer();
			// a.dataLoad(dinflectionsFile,dprefixesFile, gimatriasFile); //
			// EDIT 21.7.11 (yossi)
			MWXMLMorphAnalyzer.dataLoad(dinflectionsFile, dprefixesFile, gimatriasFile, dmwinflectionsFile, dmwe1File,
					dmwe2File, dmwe3File, dmwe4File);

			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Could not listen on port " + port);
				System.exit(-1);
			}
			while (true) {
				ClientWorker w;
				try {
					// server.accept returns a client connection
					w = new ClientWorker(serverSocket.accept(), a);
					Thread t = new Thread(w);
					t.start();
				} catch (IOException e) {
					System.out.println("Accept failed: " + port);
					System.exit(-1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
