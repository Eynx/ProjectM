package com.eynx.projectm.song;

import com.eynx.sql.Database;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongController
{
	public static final Logger logger = LoggerFactory.getLogger(SongController.class);
	public static SongService songService;

	public static void initialize(@NotNull Javalin javalin, Database database)
	{
		songService = new SongService(database);
		javalin.get("/songs", SongController::getSongs);
		javalin.post("/songs", SongController::addSong);
		javalin.get("/songs/{id}", SongController::getSongByID);
		javalin.put("/songs/{id}", SongController::updateSong);
		javalin.delete("/songs/{id}", SongController::removeSong);
	}

	public static void getSongs(@NotNull Context context)
	{
		context.contentType(ContentType.APPLICATION_JSON);
		context.result(songService.getSongs().toString());
	}

	public static void getSongByID(@NotNull Context context)
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

		Song song = songService.getSongByID(id);
		if(song != null)
		{
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(song.toString());
		}
		else
		{
			String message = "Cannot find a song with the specified ID " + id + ".";
			logger.warn(message);

			context.status(HttpStatus.NOT_FOUND);
			context.result(message);
		}
	}

	public static void addSong(@NotNull Context context)
	{
		Song song = context.bodyAsClass(Song.class);
		if(songService.addSong(song))
		{
			context.status(HttpStatus.CREATED);
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(song.toString());
		}
		else
		{
			context.status(HttpStatus.BAD_REQUEST);
			context.result("There was an error trying to add the song.");
		}
	}

	public static void updateSong(@NotNull Context context)
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

		Song song = context.bodyAsClass(Song.class);
		song.setID(id);

		if(songService.updateSong(song))
		{
			context.result("Successfully updated the song.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a song with the ID " + id + ".");
		}
	}

	public static void removeSong(@NotNull Context context)
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

		if(songService.removeSong(id))
		{
			context.result("Successfully removed the song.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a song with the ID " + id + ".");
		}
	}
}
