package com.eynx.projectm.song;

import com.eynx.projectm.album.Album;
import com.eynx.projectm.artist.Artist;
import com.eynx.projectm.format.Format;
import com.eynx.projectm.genre.Genre;

import java.text.MessageFormat;

public class Song
{
	// Directly mirror the layout of the Songs table.
	private int ID;
	private String title;
	private Artist artist;
	private Album album;
	private Genre genre;
	private Format format;

	// === Constructors =======================================================

	/**
	 * Default constructor. Construct the song object without initializing its members.
	 */
	public Song()
	{
	}

	/**
	 * Song Title constructor. Construct the song object and give it a name.
	 * Does not initialize any of the other members.
	 * @param title The name of the song.
	 */
	public Song(String title)
	{
		this.title = title;
	}

	/**
	 * Explicit constructor without ID. Construct the song object with its name, artist, album, genre, and file format.
	 * Does not initialize the song's ID.
	 * @param title The name of the song.
	 * @param artistID Reference to the name of the artist.
	 * @param albumID Reference to the name of the song's album.
	 * @param genreID Reference to the genre of the song.
	 * @param formatID Reference to the file format of the song.
	 */
	public Song(String title, Artist artist, Album album, Genre genre, Format format)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.format = format;
	}

	/**
	 * Explicit constructor. Construct the song object with its ID, name, artist, album, genre, and file format.
	 * @param ID The ID of the song.
	 * @param title The name of the song.
	 * @param artistID Reference to the name of the artist.
	 * @param albumID Reference to the name of the song's album.
	 * @param genreID Reference to the genre of the song.
	 * @param formatID Reference to the file format of the song.
	 */
	public Song(int ID, String title, Artist artist, Album album, Genre genre, Format format)
	{
		this.ID = ID;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.format = format;
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

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}

	public Artist getArtistID()
	{
		return artist;
	}
	public void setArtistID(Artist artist)
	{
		this.artist = artist;
	}

	public Album getAlbumID()
	{
		return album;
	}
	public void setAlbumID(Album album)
	{
		this.album = album;
	}

	public Genre getGenreID()
	{
		return genre;
	}
	public void setGenreID(Genre genre)
	{
		this.genre = genre;
	}

	public Format getFormatID()
	{
		return format;
	}
	public void setFormatID(Format format)
	{
		this.format = format;
	}

	// === Overrides ==========================================================

	@Override
	public String toString()
	{
		return MessageFormat.format("{\"ID\": {0}, \"Title\": \"{1}\", \"artist\": {2}, \"album\": {3}, \"genre\": {4}, \"format\": {5}}", ID, title, artist, album, genre, format);
	}
}
