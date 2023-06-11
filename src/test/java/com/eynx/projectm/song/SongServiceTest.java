package com.eynx.projectm.song;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SongServiceTest
{
	static SongDAO songDAO;
	static SongService songService;
	static Song expectedSong;

	@BeforeAll
	public static void initialize()
	{
		songDAO = mock(SongDAO.class);
		songService = new SongService(songDAO);
		expectedSong = new Song(1, "Lorem Ipsum", 1, 2, 3, 4);

		when(songDAO.getSongByID(1)).thenReturn(expectedSong);
		when(songDAO.getSongByID(2)).thenReturn(null);
		when(songDAO.removeSong(1)).thenReturn(true);
		when(songDAO.removeSong(2)).thenReturn(false);
	}

	@Test
	public void getSongByZero()
	{
		assertNull(songService.getSongByID(0));
	}

	@Test
	public void getSongByNegativeValue()
	{
		assertNull(songService.getSongByID(-1));
	}

	@Test
	public void getSongByIDSuccess()
	{
		assertEquals(expectedSong, songService.getSongByID(1));
	}

	@Test
	public void getSongByIDFailed()
	{
		assertNull(songService.getSongByID(2));
	}

	@Test
	public void removeSongZero()
	{
		assertFalse(songService.removeSong(0));
	}

	@Test
	public void removeSongNegativeValue()
	{
		assertFalse(songService.removeSong(-1));
	}

	@Test
	public void removeSongSuccess()
	{
		assertTrue(songService.removeSong(1));
	}

	@Test
	public void removeSongFailed()
	{
		assertFalse(songService.removeSong(2));
	}
}
