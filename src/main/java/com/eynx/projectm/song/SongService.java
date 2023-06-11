package com.eynx.projectm.song;

import com.eynx.sql.Database;

import java.util.List;

public class SongService
{
	private final SongDAO songDAO;

	public SongService(Database database)
	{
		this.songDAO = new SongDAO(database);
	}
	public SongService(SongDAO songDAO)
	{
		this.songDAO = songDAO;
	}

	public List<Song> getSongs()
	{
		return songDAO.getSongs();
	}

	public Song getSongByID(int id)
	{
		// Verify the ID is a valid SQL index.
		if(id > 0) {
			return songDAO.getSongByID(id);
		} else {
			return null;
		}
	}

	public boolean addSong(Song Song)
	{
		return songDAO.addSong(Song);
	}

	public boolean updateSong(Song Song)
	{
		return songDAO.updateSong(Song);
	}

	public boolean removeSong(int id)
	{
		// Verify the ID is a valid SQL index.
		if(id > 0) {
			return songDAO.removeSong(id);
		} else {
			return false;
		}
	}
}
