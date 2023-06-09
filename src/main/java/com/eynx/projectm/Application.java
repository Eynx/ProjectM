package com.eynx.projectm;

import com.eynx.sql.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application
{
	public static final Database database = new Database();
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args)
	{
		database.initialize("Eynx", "ProjectM");
	}
}
