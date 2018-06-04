package mila.tools;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

public interface Tool {
	public void processFile(File input, File output) throws IOException, JAXBException;
}
