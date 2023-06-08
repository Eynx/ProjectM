package com.eynx.projectm.album;

import java.text.MessageFormat;

public class Album
{
	// Directly mirror the layout of the Albums table.
	private int ID;
	private String Name;

	// === Constructors =======================================================

	/**
	 * Default constructor. Construct the album object without initializing its members.
	 */
	public Album()
	{
	}

	/**
	 * Name constructor. Construct the album object and give it a name. Does not initialize its primary key.
	 * @param name The name of the album.
	 */
	public Album(String name)
	{
		Name = name;
	}

	/**
	 * Explicit constructor. Construct the album object and set its primary key and give it a name.
	 * @param id The primary key value of the album.
	 * @param name The name of the album.
	 */
	public Album(int id, String name)
	{
		this.ID = id;
		Name = name;
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

	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		this.Name = name;
	}

	// === Overrides ==========================================================

	@Override
	public String toString()
	{
		return MessageFormat.format("'{'\"ID\": {0}, \"Name\": \"{1}\"'}'", ID, Name);
	}
}
