package com.eynx.projectm.format;

import com.eynx.sql.Database;

import java.util.List;

public class FormatService
{
	private final FormatDAO formatDAO;

	public FormatService(Database database)
	{
		this.formatDAO = new FormatDAO(database);
	}

	public List<Format> getFormats()
	{
		return formatDAO.getFormats();
	}

	public Format getFormatByID(int id)
	{
		return formatDAO.getFormatByID(id);
	}

	public boolean addFormat(Format Format)
	{
		return formatDAO.addFormat(Format);
	}

	public boolean updateFormat(Format Format)
	{
		return formatDAO.updateFormat(Format);
	}

	public boolean removeFormat(int id)
	{
		return formatDAO.removeFormat(id);
	}
}
