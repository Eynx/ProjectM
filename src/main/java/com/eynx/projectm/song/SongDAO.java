package com.eynx.projectm.song;

import com.eynx.projectm.Application;
import com.eynx.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO
{
	private static final Logger log = LoggerFactory.getLogger(SongDAO.class);

	public List<Song> getSongs()
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving all of the songs in the Songs table.");

			Statement query = connection.createStatement();
			ResultSet results = query.executeQuery("SELECT * FROM \"Songs\"");

			List<Song> songs = new ArrayList<>();
			while(results.next())
			{
				songs.add(new Song(results.getInt(1), results.getString(2), results.getInt(3), results.getInt(4), results.getInt(5), results.getInt(6)));
			}

			return songs;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error while retrieving all of the song albums in the Albums table.");
			e.printStackTrace();
			return null;
		}
	}

	public Song getSongByID(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving a song by its ID from the Songs table.");

			PreparedStatement query = connection.prepareStatement("SELECT * FROM \"Songs\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			ResultSet results = query.executeQuery();

			if(results.next())
			{
				return new Song(results.getInt(1), results.getString(2), results.getInt(3), results.getInt(4), results.getInt(5), results.getInt(6));
			}
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error retrieving the song by its ID from the Songs table.");
			e.printStackTrace();
		}

		return null;
	}

	public boolean addSong(Song song)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Adding a new song to the Songs table.");

			PreparedStatement query = connection.prepareStatement("INSERT INTO \"Songs\"(\"Title\", \"Artist\", \"Album\", \"Genre\", \"Format\") VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, song.getTitle());
			query.setInt(2, song.getArtist().getID());
			query.setInt(3, song.getAlbum().getID());
			query.setInt(4, song.getGenre().getID());
			query.setInt(5, song.getFormat().getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The INSERT command somehow updated more than one row in the database.");
			}

			try(ResultSet generatedKeys = query.getGeneratedKeys())
			{
				if(generatedKeys.next())
				{
					song.setID(generatedKeys.getInt(1));
				}
			}
			catch(SQLException e)
			{
				log.error("ERROR: There was an error retrieving the generated keys after inserting the new song into the Songs table.");
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

	public boolean updateSong(Song song)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Updating an existing song in the Songs table.");

			PreparedStatement query = connection.prepareStatement("UPDATE \"Songs\" SET \"Name\" = ?, \"Title\"= ?, \"Artist\"= ?, \"Album\" = ?, \"Genre\" = ?, \"Format\" = ? WHERE \"ID\" = ?");
			query.setString(1, song.getTitle());
			query.setInt(2, song.getArtist().getID());
			query.setInt(3, song.getAlbum().getID());
			query.setInt(4, song.getGenre().getID());
			query.setInt(5, song.getFormat().getID());
			query.setInt(6, song.getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error updating the song in the Songs table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeSong(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Removing an existing song from the Songs table.");

			PreparedStatement query = connection.prepareStatement("DELETE FROM \"Songs\" WHERE \"ID\" = ?");
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
			log.warn("WARNING: There was an error trying to remove the song from the Songs table.");
			e.printStackTrace();
			return false;
		}
	}
}
