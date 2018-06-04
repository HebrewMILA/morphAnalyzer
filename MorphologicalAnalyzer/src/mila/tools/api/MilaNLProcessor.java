package mila.tools.api;

import java.io.InputStream;
import java.io.OutputStream;

public interface MilaNLProcessor {
	public void process(InputStream in, OutputStream out) throws MilaException;

	public void setSettings(MilaSettings settings);
}
