package com.eynx.projectm.album;

import com.eynx.sql.Database;

import java.util.List;

public class AlbumService
{
	private final AlbumDAO albumDAO;

	public AlbumService(Database database)
	{
		this.albumDAO = new AlbumDAO(database);
	}

	public List<Album> getAlbums()
	{
		return albumDAO.getAlbums();
	}

	public Album getAlbumByID(int id)
	{
		return albumDAO.getAlbumByID(id);
	}

	public boolean addAlbum(Album album)
	{
		return albumDAO.addAlbum(album);
	}

	public boolean updateAlbum(Album album)
	{
		return albumDAO.updateAlbum(album);
	}

	public boolean removeAlbum(int id)
	{
		return albumDAO.removeAlbum(id);
	}
}
