package com.eynx.projectm;

import com.eynx.projectm.album.AlbumController;
import com.eynx.projectm.artist.ArtistController;
import com.eynx.projectm.format.FormatController;
import com.eynx.projectm.genre.GenreController;
import com.eynx.projectm.song.SongController;
import com.eynx.sql.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.json.JsonMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class Application
{
	public Database database;
	public Javalin javalin;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public void initialize()
	{
		log.info("\n\n---\tStarting ProjectM backend server instance.\n");

		// Setup the JDBC database connection.
		database = new Database();
		database.initialize("Eynx", "ProjectM");

		// Prepare the Javalin config objects.
		Gson gson = new GsonBuilder().create();
		JsonMapper gsonMapper = new JsonMapper() {
			@NotNull
			@Override
			public String toJsonString(@NotNull Object obj, @NotNull Type type) {
				return gson.toJson(obj, type);
			}

			@NotNull
			@Override
			public <T> T fromJsonString(@NotNull String json, @NotNull Type targetType) {
				return gson.fromJson(json, targetType);
			}
		};

		// Setup the Javalin server.
		javalin = Javalin.create(config -> config.jsonMapper(gsonMapper));
		// Initialize the controllers to populate the handlers.
		AlbumController.initialize(javalin, database);
		ArtistController.initialize(javalin, database);
		FormatController.initialize(javalin, database);
		GenreController.initialize(javalin, database);
		SongController.initialize(javalin, database);
		// Start the Javalin server thread.
		javalin.start(8080);
	}

	public void shutdown()
	{
		log.info("Shutting down ProjectM backend server...\n");
	};

	public static void main(String[] args)
	{
		Application application = new Application();
		application.initialize();
	}
}
