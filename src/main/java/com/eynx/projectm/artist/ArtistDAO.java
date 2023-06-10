package com.eynx.projectm.artist;

import com.eynx.sql.Connection;
import com.eynx.sql.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO
{
	private static final Logger log = LoggerFactory.getLogger(ArtistDAO.class);

	private final Database database;

	public ArtistDAO(Database database)
	{
		this.database = database;
	}

	public List<Artist> getArtists()
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Querying the database. Retrieving all of the song artists in the Artists table.");

			Statement query = connection.createStatement();
			ResultSet results = query.executeQuery("SELECT * FROM \"Artists\"");

			List<Artist> artists = new ArrayList<>();
			while(results.next())
			{
				artists.add(new Artist(results.getInt(1), results.getString(2)));
			}

			return artists;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error while retrieving all of the song artists in the Artists table.");
			e.printStackTrace();
			return null;
		}
	}

	public Artist getArtistByID(int id)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Querying the database. Retrieving a song artist by their ID from the Artists table.");

			PreparedStatement query = connection.prepareStatement("SELECT * FROM \"Artists\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			ResultSet results = query.executeQuery();

			if(results.next())
			{
				return new Artist(results.getInt(1), results.getString(2));
			}
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error retrieving the song artist by their ID from the Artists table.");
			e.printStackTrace();
		}

		return null;
	}

	public boolean addArtist(Artist artist)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Adding a new song artist to the Artists table.");

			PreparedStatement query = connection.prepareStatement("INSERT INTO \"Artists\"(\"Name\") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, artist.getName());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The INSERT command somehow updated more than one row in the database.");
			}

			try(ResultSet generatedKeys = query.getGeneratedKeys())
			{
				if(generatedKeys.next())
				{
					artist.setID(generatedKeys.getInt(1));
				}
			}
			catch(SQLException e)
			{
				log.error("ERROR: There was an error retrieving the generated keys after inserting the new artist into the Artists table.");
				e.printStackTrace();
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error inserting the new song artist into the Artists table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateArtist(Artist artist)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Updating an existing song artist in the Artists table.");

			PreparedStatement query = connection.prepareStatement("UPDATE \"Artists\" SET \"Name\" = ? WHERE \"ID\" = ?");
			query.setString(1, artist.getName());
			query.setInt(2, artist.getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error updating the song artist in the Artists table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeArtist(int id)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Removing an existing song artist from the Artists table.");

			PreparedStatement query = connection.prepareStatement("DELETE FROM \"Artists\" WHERE \"ID\" = ?");
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
			log.warn("WARNING: There was an error trying to remove the song artist from the Artists table.");
			e.printStackTrace();
			return false;
		}
	}
}
