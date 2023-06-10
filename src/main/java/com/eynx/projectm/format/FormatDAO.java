package com.eynx.projectm.format;

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

public class FormatDAO
{
	private static final Logger log = LoggerFactory.getLogger(FormatDAO.class);

	private final Database database;

	public FormatDAO(Database database)
	{
		this.database = database;
	}

	public List<Format> getFormats()
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Querying the database. Retrieving all of the file formats in the Formats table.");

			Statement query = connection.createStatement();
			ResultSet results = query.executeQuery("SELECT * FROM \"Formats\"");

			List<Format> formats = new ArrayList<>();
			while(results.next())
			{
				formats.add(new Format(results.getInt(1), results.getString(2)));
			}

			return formats;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error while retrieving all of the file formats in the Formats table.");
			e.printStackTrace();
			return null;
		}
	}

	public Format getFormatByID(int id)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Querying the database. Retrieving a file format by its ID from the Formats table.");

			PreparedStatement query = connection.prepareStatement("SELECT * FROM \"Formats\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			ResultSet results = query.executeQuery();

			if(results.next())
			{
				return new Format(results.getInt(1), results.getString(2));
			}
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error retrieving the file format by its ID from the Formats table.");
			e.printStackTrace();
		}

		return null;
	}

	public boolean addFormat(Format format)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Adding a new file format to the Formats table.");

			PreparedStatement query = connection.prepareStatement("INSERT INTO \"Formats\"(\"Extension\") VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, format.getExtension());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The INSERT command somehow updated more than one row in the database.");
			}

			try(ResultSet generatedKeys = query.getGeneratedKeys())
			{
				if(generatedKeys.next())
				{
					format.setID(generatedKeys.getInt(1));
				}
			}
			catch(SQLException e)
			{
				log.error("ERROR: There was an error retrieving the generated keys after inserting the new format into the Formats table.");
				e.printStackTrace();
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error inserting the new file format into the Formats table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateFormat(Format format)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Updating an existing file format in the Formats table.");

			PreparedStatement query = connection.prepareStatement("UPDATE \"Formats\" SET \"Extension\" = ? WHERE \"ID\" = ?");
			query.setString(1, format.getExtension());
			query.setInt(2, format.getID());
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The UPDATE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error updating the file format in the Formats table.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeFormat(int id)
	{
		try(Connection connection = database.getConnection())
		{
			log.info("Updating the database. Removing an existing file format from the Formats table.");

			PreparedStatement query = connection.prepareStatement("DELETE FROM \"Formats\" WHERE \"ID\" = ?");
			query.setInt(1, id);
			int rowsUpdated = query.executeUpdate();

			if(rowsUpdated > 1)
			{
				throw new SecurityException("ERROR: The DELETE command somehow updated more than one row in the database.");
			}

			return rowsUpdated == 1;
		}
		catch(SQLException e)
		{
			log.warn("WARNING: There was an error trying to remove the file format from the Formats table.");
			e.printStackTrace();
			return false;
		}
	}
}
