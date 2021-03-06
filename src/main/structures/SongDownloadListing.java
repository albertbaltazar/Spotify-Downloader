package main.structures;


public class SongDownloadListing implements Comparable<SongDownloadListing> {
	public SongInfo song;
	public String listingID;
	public DownloadRequest request;
	
	public SongDownloadListing(SongInfo song, String listingID, DownloadRequest req) {
		this.song = song;
		this.listingID = listingID;
		this.request = req;
	}

	public String toString() {
		String temp = "{" + listingID  + ", " + getURLString() + "}\n";
		return temp;
	}
	//currently this is useless as i am only adding obj to the heap that pass reject test.
	//if it passes the reject test compareTo will always return 0
	@Override
	public int compareTo(SongDownloadListing o) {
		assert(song == o.song);
		int points = 0;
		int otherPoints = 0;
		if (!shouldReject()) points+=1;
		if (!o.shouldReject()) otherPoints+=1;
		return points - otherPoints;
	}
	
	protected int getAbsPoints() {
		int points = 0;
		String[] badWords = {"remix", "cover", "live", "mash", "dj", "mix", "edit", "customized", "customised", "instrumental", "rework"};
		String matchString = (listingID+" "+getURLString()).toLowerCase().replaceAll("\\(", " LP ").replaceAll("\\)", " RP ");
		boolean titleMatch = matchString.matches(".*?"+song.title.toLowerCase()+".*?");
		boolean artistMatch = matchString.matches(".*?"+song.artist.toLowerCase()+".*?");
		boolean doesntHaveBadWord = true;
		for(int i=0; i<badWords.length; i++) {
			if (song.title.toLowerCase().matches(".*?"+badWords[i]+".*?")) {
				continue; //if looking for a badWord, then fine
			}
			if (matchString.matches(".*?"+badWords[i]+".*?")) {
				doesntHaveBadWord = false;
				break;
			}
		}
		if(titleMatch) 			points+=32;
		if(artistMatch) 		points+=16;
		if(doesntHaveBadWord) 	points+=8;

		return points;
	}
	
	//call to see whether to add or not.
	public boolean shouldReject() {
		boolean reject = false;
		String[] badWords = {"remix", "cover", "live", "mash", "dj", "mix", "edit", "customized", "customised", "instrumental", "rework"};
		String matchString = (listingID+" "+getURLString()).toLowerCase().replaceAll("\\(", " LP ").replaceAll("\\)", " RP ");
		boolean titleMatch = matchString.matches(".*?"+song.title.toLowerCase().replaceAll("\\(", " LP ").replaceAll("\\)", " RP ")+".*?");
		boolean artistMatch = matchString.matches(".*?"+song.artist.toLowerCase().replaceAll("\\(", " LP ").replaceAll("\\)", " RP ")+".*?");
		boolean doesntHaveBadWord = true;
		for(int i=0; i<badWords.length; i++) {
			if (song.title.toLowerCase().matches(".*?"+badWords[i]+".*?")) {
				continue; //if looking for a badWord, then fine
			}
			if (matchString.matches(".*?"+badWords[i]+".*?")) {
				doesntHaveBadWord = false;
				break;
			}
		}
		if(!titleMatch
				|| !artistMatch 
				|| !doesntHaveBadWord) reject = true;
		return reject;
	}

	private String getURLString() {
		return request.httpRequest.getURI().toString();
	}
	
}

//rank download listings
/*
1. song title			32
2. artist				16
3. NO {live, remix}		8
4. bitrate				4
5. size					2




*/