package com.eynx.projectm.genre;

import com.eynx.sql.Database;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenreController
{
	public static final Logger logger = LoggerFactory.getLogger(GenreController.class);
	public static GenreService genreService;

	public static void initialize(@NotNull Javalin javalin, Database database)
	{
		genreService = new GenreService(database);
		javalin.get("/genres", GenreController::getGenres);
		javalin.post("/genres", GenreController::addGenre);
		javalin.get("/genres/{id}", GenreController::getGenreByID);
		javalin.put("/genres/{id}", GenreController::updateGenre);
		javalin.delete("/genres/{id}", GenreController::removeGenre);
	}

	public static void getGenres(@NotNull Context context)
	{
		context.contentType(ContentType.APPLICATION_JSON);
		context.result(genreService.getGenres().toString());
	}

	public static void getGenreByID(@NotNull Context context)
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

		Genre genre = genreService.getGenreByID(id);
		if(genre != null)
		{
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(genre.toString());
		}
		else
		{
			String message = "Cannot find a genre with the specified ID " + id + ".";
			logger.warn(message);

			context.status(HttpStatus.NOT_FOUND);
			context.result(message);
		}
	}

	public static void addGenre(@NotNull Context context)
	{
		Genre genre = context.bodyAsClass(Genre.class);
		if(genreService.addGenre(genre))
		{
			context.status(HttpStatus.CREATED);
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(genre.toString());
		}
		else
		{
			context.status(HttpStatus.BAD_REQUEST);
			context.result("There was an error trying to create the genre.");
		}
	}

	public static void updateGenre(@NotNull Context context)
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

		Genre genre = context.bodyAsClass(Genre.class);
		genre.setID(id);

		if(genreService.updateGenre(genre))
		{
			context.result("Successfully updated the name of the genre.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a genre with the ID " + id + ".");
		}
	}

	public static void removeGenre(@NotNull Context context)
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

		if(genreService.removeGenre(id))
		{
			context.result("Successfully removed the genre.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a genre with the ID " + id + ".");
		}
	}
}
