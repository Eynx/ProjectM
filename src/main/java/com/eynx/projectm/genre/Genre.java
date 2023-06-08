package com.eynx.projectm.genre;

import java.text.MessageFormat;

public class Genre
{
	// Directly mirror the layout of the Genres table.
	private int ID;
	private String Name;

	// === Constructors =======================================================

	/**
	 * Default constructor. Construct the genre object without initializing its members.
 	 */
	public Genre()
	{
	}

	/**
	 * Name constructor. Construct the genre object and give it a name. Does not initialize its primary key.
	 * @param name The name of the genre.
	 */
	public Genre(String name)
	{
		Name = name;
	}

	/**
	 * Explicit constructor. Construct the genre object and set its primary key and give it a name.
	 * @param id The primary key value of the genre.
	 * @param name The name of the genre.
	 */
	public Genre(int id, String name)
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
