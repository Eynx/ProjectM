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

	public List<Song> getSongs()
	{
		return songDAO.getSongs();
	}

	public Song getSongByID(int id)
	{
		return songDAO.getSongByID(id);
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
		return songDAO.removeSong(id);
	}
}
