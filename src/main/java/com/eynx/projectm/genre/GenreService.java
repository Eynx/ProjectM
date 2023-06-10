package com.eynx.projectm.genre;

import com.eynx.sql.Database;

import java.util.List;

public class GenreService
{
	private final GenreDAO genreDAO;

	public GenreService(Database database)
	{
		this.genreDAO = new GenreDAO(database);
	}

	public List<Genre> getAlbums()
	{
		return genreDAO.getGenres();
	}

	public Genre getAlbumByID(int id)
	{
		return genreDAO.getGenreByID(id);
	}

	public boolean addAlbum(Genre Genre)
	{
		return genreDAO.addGenre(Genre);
	}

	public boolean updateAlbum(Genre Genre)
	{
		return genreDAO.updateGenre(Genre);
	}

	public boolean removeAlbum(int id)
	{
		return genreDAO.removeGenre(id);
	}
}
