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

	public List<Song> getAlbums()
	{
		return songDAO.getSongs();
	}

	public Song getAlbumByID(int id)
	{
		return songDAO.getSongByID(id);
	}

	public boolean addAlbum(Song Song)
	{
		return songDAO.addSong(Song);
	}

	public boolean updateAlbum(Song Song)
	{
		return songDAO.updateSong(Song);
	}

	public boolean removeAlbum(int id)
	{
		return songDAO.removeSong(id);
	}
}