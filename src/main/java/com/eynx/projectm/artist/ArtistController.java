package com.eynx.projectm.artist;

import com.eynx.sql.Database;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtistController
{
	public static final Logger logger = LoggerFactory.getLogger(ArtistController.class);
	public static ArtistService artistService;

	public static void initialize(@NotNull Javalin javalin, Database database)
	{
		artistService = new ArtistService(database);
		javalin.get("/artists", ArtistController::getArtists);
		javalin.post("/artists", ArtistController::addArtist);
		javalin.get("/artists/{id}", ArtistController::getArtistByID);
		javalin.put("/artists/{id}", ArtistController::updateArtist);
		javalin.delete("/artists/{id}", ArtistController::removeArtist);
	}

	public static void getArtists(@NotNull Context context)
	{
		context.contentType(ContentType.APPLICATION_JSON);
		context.result(artistService.getArtists().toString());
	}

	public static void getArtistByID(@NotNull Context context)
	{
		int id;
		try { id = Integer.parseInt(context.pathParam("id")); }
		catch(NumberFormatException e)
		{
			String message = "Unable to determine the ID in the request.";
			logger.warn(message);
			context.status(HttpStatus.BAD_REQUEST);
			context.result(message);
			return;
		}

		Artist artist = artistService.getArtistByID(id);
		if(artist != null)
		{
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(artist.toString());
		}
		else
		{
			String message = "Cannot find an artist with the specified ID " + id + ".";
			logger.warn(message);

			context.status(HttpStatus.NOT_FOUND);
			context.result(message);
		}
	}

	public static void addArtist(@NotNull Context context)
	{
		Artist artist = context.bodyAsClass(Artist.class);
		if(artistService.addArtist(artist))
		{
			context.status(HttpStatus.CREATED);
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(artist.toString());
		}
		else
		{
			context.status(HttpStatus.BAD_REQUEST);
			context.result("There was an error trying to create the artist.");
		}
	}

	public static void updateArtist(@NotNull Context context)
	{
		int id;
		try { id = Integer.parseInt(context.pathParam("id")); }
		catch(NumberFormatException e)
		{
			String message = "Unable to determine the ID in the request.";
			logger.warn(message);
			context.status(HttpStatus.BAD_REQUEST);
			context.result(message);
			return;
		}

		Artist artist = context.bodyAsClass(Artist.class);
		artist.setID(id);

		if(artistService.updateArtist(artist))
		{
			context.result("Successfully updated the name of artist.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find an artist with the ID " + id + ".");
		}
	}

	public static void removeArtist(@NotNull Context context)
	{
		int id;
		try { id = Integer.parseInt(context.pathParam("id")); }
		catch(NumberFormatException e)
		{
			String message = "Unable to determine the ID in the request.";
			logger.warn(message);
			context.status(HttpStatus.BAD_REQUEST);
			context.result(message);
			return;
		}

		if(artistService.removeArtist(id))
		{
			context.result("Successfully removed the artist.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find an artist with the ID " + id + ".");
		}
	}
}
