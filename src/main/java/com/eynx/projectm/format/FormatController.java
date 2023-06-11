package com.eynx.projectm.format;

import com.eynx.sql.Database;
import io.javalin.Javalin;
import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatController
{
	public static final Logger logger = LoggerFactory.getLogger(FormatController.class);
	public static FormatService formatService;

	public static void initialize(@NotNull Javalin javalin, Database database)
	{
		formatService = new FormatService(database);
		javalin.get("/formats", FormatController::getFormats);
		javalin.post("/formats", FormatController::addFormat);
		javalin.get("/formats/{id}", FormatController::getFormatByID);
		javalin.put("/formats/{id}", FormatController::updateFormat);
		javalin.delete("/formats/{id}", FormatController::removeFormat);
	}

	public static void getFormats(@NotNull Context context)
	{
		context.contentType(ContentType.APPLICATION_JSON);
		context.result(formatService.getFormats().toString());
	}

	public static void getFormatByID(@NotNull Context context)
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

		Format format = formatService.getFormatByID(id);
		if(format != null)
		{
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(format.toString());
		}
		else
		{
			String message = "Cannot find a file format with the specified ID " + id + ".";
			logger.warn(message);

			context.status(HttpStatus.NOT_FOUND);
			context.result(message);
		}
	}

	public static void addFormat(@NotNull Context context)
	{
		Format format = context.bodyAsClass(Format.class);
		if(formatService.addFormat(format))
		{
			context.status(HttpStatus.CREATED);
			context.contentType(ContentType.APPLICATION_JSON);
			context.result(format.toString());
		}
		else
		{
			context.status(HttpStatus.BAD_REQUEST);
			context.result("There was an error trying to create the file format.");
		}
	}

	public static void updateFormat(@NotNull Context context)
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

		Format format = context.bodyAsClass(Format.class);
		format.setID(id);

		if(formatService.updateFormat(format))
		{
			context.result("Successfully updated the extension of the file format.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a file format with the ID " + id + ".");
		}
	}

	public static void removeFormat(@NotNull Context context)
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

		if(formatService.removeFormat(id))
		{
			context.result("Successfully removed the file format.");
		}
		else
		{
			context.status(HttpStatus.NOT_FOUND);
			context.result("Couldn't find a file format with the ID " + id + ".");
		}
	}
}
