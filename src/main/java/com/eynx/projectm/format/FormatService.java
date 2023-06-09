package com.eynx.projectm.format;

import java.util.List;

public class FormatService
{
	private final FormatDAO formatDAO = new FormatDAO();

	public List<Format> getAlbums()
	{
		return formatDAO.getFormats();
	}

	public Format getAlbumByID(int id)
	{
		return formatDAO.getFormatByID(id);
	}

	public boolean addAlbum(Format Format)
	{
		return formatDAO.addFormat(Format);
	}

	public boolean updateAlbum(Format Format)
	{
		return formatDAO.updateFormat(Format);
	}

	public boolean removeAlbum(int id)
	{
		return formatDAO.removeFormat(id);
	}
}
