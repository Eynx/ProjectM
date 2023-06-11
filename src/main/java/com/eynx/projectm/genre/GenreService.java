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

	public List<Genre> getGenres()
	{
		return genreDAO.getGenres();
	}

	public Genre getGenreByID(int id)
	{
		return genreDAO.getGenreByID(id);
	}

	public boolean addGenre(Genre Genre)
	{
		return genreDAO.addGenre(Genre);
	}

	public boolean updateGenre(Genre Genre)
	{
		return genreDAO.updateGenre(Genre);
	}

	public boolean removeGenre(int id)
	{
		return genreDAO.removeGenre(id);
	}
}
