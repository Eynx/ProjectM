package com.eynx.projectm.genre;

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

public class GenreDAO
{
	private static final Logger log = LoggerFactory.getLogger(GenreDAO.class);

	public List<Genre> getGenres()
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving all of the song genres in the Genres table.");

			Statement query = connection.createStatement();
			ResultSet results = query.executeQuery("SELECT * FROM \"Genres\"");

			List<Genre> genres = new ArrayList<>();
			while(results.next())
			{
				genres.add(new Genre(results.getInt(1), results.getString(2)));
			}

			return genres;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error while retrieving all of the song genres in the Genres table.");
			e.printStackTrace();
			return null;
		}
	}

	public Genre getGenreByID(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Querying the database. Retrieving a song genre by its ID from the Genres table.");

			PreparedStatement query = connection.prepareStatement("SELECT * FROM \"Genres\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			ResultSet results = query.executeQuery();

			if(results.next())
			{
				return new Genre(results.getInt(1), results.getString(2));
			}
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error retrieving the song genre by its ID from the Genres table.");
			e.printStackTrace();
		}

		return null;
	}

	public boolean addGenre(Genre genre)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Adding a new song genre to the Genres table.");

			PreparedStatement query = connection.prepareStatement("INSERT INTO \"Genres\"(\"Name\") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, genre.getName());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The INSERT command somehow updated more than one row in the database.");
			}

			try(ResultSet generatedKeys = query.getGeneratedKeys())
			{
				if(generatedKeys.next())
				{
					genre.setID(generatedKeys.getInt(1));
				}
			}
			catch(SQLException e)
			{
				log.error("ERROR: There was an error retrieving the generated keys after inserting the new genre into the Genres table.");
				e.printStackTrace();
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error inserting the new song genre into the Genres table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateGenre(Genre genre)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Updating an existing song genre in the Genres table.");

			PreparedStatement query = connection.prepareStatement("UPDATE \"Genres\" SET \"Name\" = ? WHERE \"ID\" = ?");
			query.setString(1, genre.getName());
			query.setInt(2, genre.getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error updating the song genre in the Genres table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeGenre(int id)
	{
		try(Connection connection = Application.database.getConnection())
		{
			log.info("Updating the database. Removing an existing song genre from the Genres table.");

			PreparedStatement query = connection.prepareStatement("DELETE FROM \"Genres\" WHERE \"ID\" = ?");
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
			log.warn("WARNING: There was an error trying to remove the song genre from the Genres table.");
			e.printStackTrace();
			return false;
		}
	}
}
