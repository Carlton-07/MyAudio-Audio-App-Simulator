//Name: Carlton Valerian Gnanamalai
//Id: 501201587

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents; 
	private Map<String, Integer> byTitle; //search function map
	private Map<String, ArrayList<Integer> > byartist; //searcha function map
	private Map<Song.Genre, ArrayList<Integer>> byGenre; //searchg function map

	public AudioContentStore()
	{
		byTitle = new HashMap<String, Integer>();
		byartist = new HashMap<String, ArrayList<Integer> >();
		byGenre = new HashMap<Song.Genre, ArrayList<Integer> >();

		try{
			contents = readContent();         //reading given file
		}catch(IOException exception){ //if there is any IOException then printing an error message
			System.out.println(exception.getMessage());
			System.exit(1);
		}

		////mapping the content by putting their title as the key and an arraylist of indexes 
		
		for(int i=0;i<contents.size();i++){
			byTitle.put(contents.get(i).getTitle(), i);
		}

		///mapping the content by putting their Artist's name as the key and an arraylist of indexes 
		
		for(int i=0;i<contents.size();i++){
			ArrayList<Integer> index = new ArrayList<Integer>();
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){
				Song song = (Song)contents.get(i);
				if(byartist.containsKey(song.getArtist())){ //if the song has an artist whose name is already in the map
					index = byartist.get(song.getArtist()); //getting list of indexes
					index.add(i);
				}else{   //else just adding the index of this song to the empty list
					index.add(i);
					byartist.put(song.getArtist(), index);
				}
			}

			if(contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK")){
				AudioBook a = (AudioBook)contents.get(i);
				if(byartist.containsKey(a.getAuthor())){ //if the audiobook has an author whose name is already in the map
					index = byartist.get(a.getAuthor()); //getting list of indexes
					index.add(i);
				}else{                     //else just adding the index of this audiobook to the empty list
					index.add(i);
					byartist.put(a.getAuthor(), index);
				}
			}
		}

		//mapping the content by putting their genre  as the key and an arraylist of indexes
		for(int i=0;i<contents.size();i++){
			ArrayList<Integer> num = new ArrayList<Integer>();
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){
				Song song = (Song)contents.get(i);
			for(Song.Genre g: Song.Genre.values()){  	//checking the genre of current song
				if(song.getGenre() == g){
					if(byGenre.containsKey(g)){     //if the song has genre which is already in the map
						num = byGenre.get(g); // getting the list of indexes
						num.add(i);
					}else{
						num.add(i); //else just adding the index of this song to the empty list
						byGenre.put(g, num);
					}
				}
			}
		}
	}	
	}

	private ArrayList<AudioContent> readContent() throws IOException  //reads the "store.txt" file and creates objects of song and audiobook accordingly and then adds it to the content arraylist
	{
		ArrayList<AudioContent> content = new ArrayList<AudioContent>();
		Scanner in = new Scanner(new File("store.txt"));
		//strings id, title, year, length, artist, composer, genre. 
		while(in.hasNextLine()){
			String type = in.nextLine();
			if(type.equalsIgnoreCase("SONG")){
				String id = in.nextLine();
				String title = in.nextLine();
				int year = in.nextInt();
				int length = in.nextInt();
				in.nextLine();
				String artist = in.nextLine();
				String composer = in.nextLine();
				String g = in.nextLine();
				int lines = in.nextInt();
				in.nextLine();
				String lyrics = "";
				for(int i=0;i<lines;i++){
					String line = in.nextLine();
					lyrics += line + "\n";
				}
				switch(g){
					case "POP" : 
					Song song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.POP, lyrics);
					content.add(song); break;
					case "ROCK" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.ROCK, lyrics);
					content.add(song); break;
					case "JAZZ" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.JAZZ, lyrics);
					content.add(song); break;
					case "HIPHOP" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.HIPHOP, lyrics);
					content.add(song); break;
					case "RAP" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.RAP, lyrics);
					content.add(song); break;
					case "CLASSICAL" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.CLASSICAL, lyrics);
					content.add(song); break;
					default : break;
				}	
			}
			else if(type.equalsIgnoreCase("AUDIOBOOK")){
				String id = in.nextLine();
				String title = in.nextLine();
				int year = in.nextInt();
				int length = in.nextInt();
				in.nextLine();
				String author = in.nextLine();
				String narrator = in.nextLine();
				int numOfChapters = in.nextInt();
				in.nextLine();
				ArrayList<String> chapterTitles = new ArrayList<>();
				ArrayList<String> chapters = new ArrayList<>();
				for(int i=0;i<numOfChapters;i++){
					String chapter = in.nextLine();
					chapterTitles.add(chapter);
				}
				for(int i=0;i<numOfChapters;i++){
					int numOfLines = in.nextInt();
					in.nextLine();
					String lines = ""; 
					for(int j=0;j<numOfLines;j++){
						lines += in.nextLine() + "\n";
					}
					chapters.add(lines);
				}
				AudioBook audioBook = new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters);
				content.add(audioBook);
			}
		}
		return content;
	}
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}

	public Map<String, Integer> getByTitle() {
		return byTitle;
	}

	public Map<String, ArrayList<Integer>> getByartist() {
		return byartist;
	}

	public Map<Song.Genre, ArrayList<Integer>> getByGenre() {
		return byGenre;
	}
		
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	public void search(String title){    //search the bytitle map for a title given by the user 
		if(byTitle.containsKey(title)){
			for(String key : byTitle.keySet()){
				if(key.equals(title)){
					int index = byTitle.get(key);
					System.out.print(index+1 + ". ");   //print the info of the content that has the same title
					contents.get(index).printInfo();
				}
			}
		}else{
			throw new NoMatchesException("No matches for "+ title);
		}
	}

	public ArrayList<Integer> searchA(String artist){
		ArrayList<Integer> artists = new ArrayList<>(); //search the byartist map for a name of artist given by the user
		if(byartist.containsKey(artist)){
			for(String key : byartist.keySet()){
				if(key.equals(artist)){
					artists = byartist.get(key);
				}
			}
		}else{
			throw new NoMatchesException("No matches for "+ artist);
		}
		return artists;
	}

	public ArrayList<Integer> searchG(Song.Genre genre){ //search the byartist map for a name of artist given by the user
		ArrayList<Integer> sogns_genre = new ArrayList<>();
		if(byGenre.containsKey(genre)){
			for(Song.Genre key : byGenre.keySet()){
				if(key == genre){
					sogns_genre = byGenre.get(key);
				}
			}
		}else{
			throw new NoMatchesException("No matches for "+ genre);
		}
		return sogns_genre;
	}

	public ArrayList<Integer> searchP(String partial){//search the store and every content in the store for a partial string given by the user 
		ArrayList<Integer> matched_index = new ArrayList<Integer>();

		for(int i=0;i<contents.size();i++){
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){
				Song song = (Song)contents.get(i);

				if(song.getArtist().contains(partial) || song.getAudioFile().contains(partial) || song.getTitle().contains(partial) || song.getComposer().contains(partial)){
					matched_index.add(i);
				}
			}
			if(contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK")){
				AudioBook audioBook = (AudioBook)contents.get(i);

				if(audioBook.getAudioFile().contains(partial) || audioBook.getAuthor().contains(partial)){
					matched_index.add(i);
				}else{
					for(int j=0;j<audioBook.getChapterTitles().size();j++){
						if(audioBook.getChapterTitles().get(j).contains(partial) || audioBook.getChapters().get(j).contains(partial)){
							matched_index.add(i);
						}
					}
				}
			}
		}
		return matched_index;
	}

	public class NoMatchesException extends RuntimeException{ //an exception for no matches error
		public NoMatchesException() {}

		public NoMatchesException(String message){
			super(message);
		}
	}
}
