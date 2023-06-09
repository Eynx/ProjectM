package com.eynx.projectm.genre;

import java.util.List;

public class GenreService
{
	private final GenreDAO genreDAO = new GenreDAO();

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
