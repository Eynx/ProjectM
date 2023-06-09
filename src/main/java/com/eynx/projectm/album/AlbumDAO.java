package com.eynx.projectm.album;

import com.eynx.projectm.Application;
import com.eynx.projectm.artist.Artist;
import com.eynx.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO
{
	private static final Logger log = LoggerFactory.getLogger(AlbumDAO.class);

	public List<Album> getAlbums()
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving all of the song albums in the Albums table.");

			Statement query = connection.createStatement();
			ResultSet results = query.executeQuery("SELECT * FROM \"Albums\"");

			List<Album> albums = new ArrayList<>();
			while(results.next())
			{
				albums.add(new Album(results.getInt(1), results.getString(2)));
			}

			return albums;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error while retrieving all of the song albums in the Albums table.");
			e.printStackTrace();
			return null;
		}
	}

	public Album getAlbumByID(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving a song album by its ID from the Albums table.");

			PreparedStatement query = connection.prepareStatement("SELECT * FROM \"Albums\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			ResultSet results = query.executeQuery();

			if(results.next())
			{
				return new Album(results.getInt(1), results.getString(2));
			}
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error retrieving the song album by its ID from the Albums table.");
			e.printStackTrace();
		}

		return null;
	}

	public boolean addAlbum(Album album)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Adding a new song album to the Artists table.");

			PreparedStatement query = connection.prepareStatement("INSERT INTO \"Albums\"(\"Name\") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, album.getName());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The INSERT command somehow updated more than one row in the database.");
			}

			try(ResultSet generatedKeys = query.getGeneratedKeys())
			{
				if(generatedKeys.next())
				{
					album.setID(generatedKeys.getInt(1));
				}
			}
			catch(SQLException e)
			{
				log.error("ERROR: There was an error retrieving the generated keys after inserting the new album into the Albums table.");
				e.printStackTrace();
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error inserting the new song album into the Albums table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateAlbum(Album album)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Updating an existing song album in the Albums table.");

			PreparedStatement query = connection.prepareStatement("UPDATE \"Albums\" SET \"Name\" = ? WHERE \"ID\" = ?");
			query.setString(1, album.getName());
			query.setInt(2, album.getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error updating the song album in the Albums table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeAlbum(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Removing an existing song album from the Albums table.");

			PreparedStatement query = connection.prepareStatement("DELETE FROM \"Albums\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error trying to remove the song album from the Albums table.");
			e.printStackTrace();
			return false;
		}
	}
}
