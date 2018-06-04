/*
 * Created on 10/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mila.lexicon.utils;

/**
 * @author daliabo
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrefixRec {
	private String surface = "";
	private String function = "";

	private String transliterated = "";

	/**
	 * @return Returns the function.
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @return Returns the surface.
	 */
	public String getSurface() {
		return surface;
	}

	/**
	 * @return Returns the transliterated.
	 */
	public String getTransliterated() {
		return transliterated;
	}

	/**
	 * @param function
	 *           The function to set.
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @param surface
	 *           The surface to set.
	 */
	public void setSurface(String surface) {
		this.surface = surface;
	}

	/**
	 * @param transliterated
	 *           The transliterated to set.
	 */
	public void setTransliterated(String transliterated) {
		this.transliterated = transliterated;
	}
}
