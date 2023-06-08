package com.eynx.projectm;

import com.eynx.sql.Database;

public class Application
{
	public static final Database database = new Database();

	public static void main(String[] args)
	{
		database.initialize("Eynx", "ProjectM");
	}
}
