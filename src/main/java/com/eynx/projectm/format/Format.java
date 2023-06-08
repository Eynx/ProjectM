package com.eynx.projectm.format;

import java.text.MessageFormat;

public class Format
{
	// Directly mirror the layout of the Formats table.
	private int ID;
	private String Extension;

	// === Constructors =======================================================

	/**
	 * Default constructor. Constructs the format object without initializing its members.
	 */
	public Format()
	{
	}

	/**
	 * Extension name constructor. Construct the format object and name its extension.
	 * Does not initialize its primary key.
	 * @param extension The name of the file extension.
	 */
	public Format(String extension)
	{
		Extension = extension;
	}

	/**
	 * Explicit constructor. Construct the format object and set its primary key and set its extension.
	 * @param ID The primary key of the format.
	 * @param extension The name of the file extension.
	 */
	public Format(int ID, String extension)
	{
		this.ID = ID;
		Extension = extension;
	}

	// === Getters/Setters ====================================================

	public int getID()
	{
		return ID;
	}
	public void setID(int id)
	{
		this.ID = id;
	}

	public String getExtension()
	{
		return Extension;
	}
	public void setExtension(String extension)
	{
		this.Extension = extension;
	}

	// === Overrides ==========================================================

	@Override
	public String toString()
	{
		return MessageFormat.format("{\"ID\": {0},\"Extension\": \"{1}\"}", ID, Extension);
	}
}
