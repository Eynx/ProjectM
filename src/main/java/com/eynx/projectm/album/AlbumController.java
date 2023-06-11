package com.eynx.projectm.album;

import com.eynx.sql.Database;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlbumController
{
	public static final Logger logger = LoggerFactory.getLogger(AlbumController.class);
	public static AlbumService albumService;

	public static void initialize(@NotNull Javalin javalin, Database database)
	{
		albumService = new AlbumService(database);
		javalin.get("/albums", AlbumController::getAlbums);
		javalin.post("/albums", AlbumController::addAlbum);
		javalin.get("/albums/{id}", AlbumController::getAlbumByID);
		javalin.put("/albums/{id}", AlbumController::updateAlbum);
		javalin.delete("/albums/{id}", AlbumController::removeAlbum);
	}

	public static void getAlbums(@NotNull Context context)
	{
		context.contentType(ContentType.APPLICATION_JSON);
		context.result(albumService.getAlbums().toString());
	}

	public static void getAlbumByID(@NotNull Context context)
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

		Album album = albumService.getAlbumByID(id);
		if(album != null)
		{
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(album.toString());
		}
		else
		{
			String message = "Cannot find an album with the specified ID " + id + ".";
			logger.warn(message);

			context.status(HttpStatus.NOT_FOUND);
			context.result(message);
		}
	}

	public static void addAlbum(@NotNull Context context)
	{
		Album album = context.bodyAsClass(Album.class);
		if(albumService.addAlbum(album))
		{
			context.status(HttpStatus.CREATED);
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(album.toString());
		}
		else
		{
			context.status(HttpStatus.BAD_REQUEST);
			context.result("There was an error trying to create the album.");
		}
	}

	public static void updateAlbum(@NotNull Context context)
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

		Album album = context.bodyAsClass(Album.class);
		album.setID(id);

		if(albumService.updateAlbum(album))
		{
			context.result("Successfully updated the name of album.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find an album with the ID " + id + ".");
		}
	}

	public static void removeAlbum(@NotNull Context context)
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

		if(albumService.removeAlbum(id))
		{
			context.result("Successfully removed the album.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find an album with the ID " + id + ".");
		}
	}
}
