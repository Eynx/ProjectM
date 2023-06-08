package com.eynx.projectm.artist;

import java.text.MessageFormat;

public class Artist
{
	// Directly mirror the layout of the Artists table.
	private int ID;
	private String Name;

	// === Constructors =======================================================

	/**
	 * Default constructor. Construct the artist object without initializing its members.
	 */
	public Artist()
	{
	}

	/**
	 * Name constructor. Construct the artist object and give them a name. Does not initialize their primary key.
	 * @param name The name of the artist.
	 */
	public Artist(String name)
	{
		Name = name;
	}

	/**
	 * Explicit constructor. Construct the artist object and set their primary key and give them a name.
	 * @param id The primary key value of the artist.
	 * @param name The name of the artist.
	 */
	public Artist(int id, String name)
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
		return MessageFormat.format("{\"ID\": {0}, \"Name\": \"{1}\"}", ID, Name);
	}
}
