package main.song_downloaders;

import main.structures.SongInfo;

public class SingleSongDownloader_Concurrent extends SingleSongDownloader {
	MultiSongDownloader_Queue sq;
	public SingleSongDownloader_Concurrent(MultiSongDownloader_Queue sq, SongInfo song) {
		super(song);
		this.sq = sq;
	}
	@Override
	public void run() {
		populateListingSources();
		boolean success = downloadSongToFile("music/" + song.artist + "-" + song.album + "-" + song.title + ".mp3");
		if (success) sq.successfulDownload(song);
		else sq.failedDownload(song.toString());
	}

}
