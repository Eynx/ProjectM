package com.eynx.projectm.artist;

import java.util.List;

public class ArtistService
{
	private final ArtistDAO artistDAO = new ArtistDAO();

	public List<Artist> getArtists()
	{
		return artistDAO.getArtists();
	}

	public Artist getArtistByID(int id)
	{
		return artistDAO.getArtistByID(id);
	}

	public boolean addArtist(Artist artist)
	{
		return artistDAO.addArtist(artist);
	}

	public boolean updateArtist(Artist artist)
	{
		return artistDAO.updateArtist(artist);
	}

	public boolean removeArtist(int id)
	{
		return artistDAO.removeArtist(id);
	}
}
