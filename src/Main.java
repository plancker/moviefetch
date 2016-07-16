import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    static String[] param = {"title",
            "year",
            "rated",
            "released",
            "runtime",
            "genre",
            "directors",
            "writers",
            "actors",
            "plot",
        "language",
            "country",
            "awards",
            "poster",
            "metaScore",
        "imdB rating",
    "imdBVotes",
            "imdBiD",
            "type",
            "response"
    };
    static int paramLength = param.length;


    public static void main(String[] args) {

        /*List<String> movies = linesInFile("flicks.txt");
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter s for sorting by imdB Rating and f for filtering by any parameter");
        String input = reader.next();
        if(input.toLowerCase() == "f"){
            filterMovies(movies, fetchFlickData(movies));
        }*/


        Map query = new HashMap<>(paramLength);
        for(int i = 0; i < paramLength; i++){
            query.put(param[i], null);
        }
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter search parameters in this format: title:blah, actor:blah, director:blah, writer:blah, genre:blah...");
        System.out.println("When done press enter");
        String queryString = reader.next();
        String[] queryArray = queryString.split(", ");
        for(int i =0; i<queryArray.length; i++){
            String[] keyValuePair = queryArray[i].split(":");
            if(query.containsKey(keyValuePair[0])){
                query.put(keyValuePair[0], keyValuePair[1]);
            }
        }
        System.out.println(query);

    }

    static List<Flick> fetchFlickData(List<String> movies){
        System.out.println("Fetching data for your flicks.");
        List<Flick> flickList = new ArrayList<>();
        int moviesFetched = 0;
        int moviesSize = movies.size();
        for(String movie : movies) {
            String url = "http://www.omdbapi.com/?t="+ movie.replaceAll(" ", "+") +"&y=&plot=short&r=json";
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = null;
            try {
                response = client.execute(request);
                String json = EntityUtils.toString(response.getEntity(), "UTF-8");
                Gson gson = new Gson();
                Flick flick = gson.fromJson(json, Flick.class);
                flickList.add(flick);
                moviesFetched = moviesFetched+1;
                System.out.println(moviesFetched+"/"+moviesSize);

            } catch (IOException | java.lang.IllegalStateException e) {
                System.out.println(e.getCause());
            }
        }

        return flickList;
    }

    static void filterMovies(List movies, List<Flick> flickList){
        Map query = new HashMap<>(paramLength);
        for(int i = 0; i < paramLength; i++){
            query.put(param[i], null);
        }
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter search parameters in this format: title:blah, actor:blah, director:blah, writer:blah, genre:blah...");
        System.out.println("When done press enter");
        String queryString = reader.next();
        String[] queryArray = queryString.split(", ");
        for(int i =0; i<queryArray.length; i++){
            String[] keyValuePair = queryArray[i].split(":");
            if(query.containsKey(keyValuePair[0])){
                query.put(keyValuePair[0], keyValuePair[1]);
            }
        }
        System.out.println(query);

        //Set queryKeySet = query.keySet();
        //for(flick: flickList){
        //    Collection queryValues = query.values();
        //    for(queryVal :queryValues){
        //        if(queryVal == )
        //    }
        //}
    }

    static int simScore(Flick queryFlick, Flick inDriveFlick){
        int score = 0;

        if(queryFlick.getTitle() == inDriveFlick.getTitle()){
            score = score + 100;
        }

        if(queryFlick.getActors() == inDriveFlick.getActors()){
            score = score + 10;
        }

        if(queryFlick.getDirector() == inDriveFlick.getDirector()){
            score = score + 50;
        }

        if(queryFlick.getYear() == inDriveFlick.getYear()){
            score = score + 1;
        }

        if(queryFlick.getWriter() == inDriveFlick.getWriter()){
            score = score + 10;
        }

        if(queryFlick.getGenre() == inDriveFlick.getGenre()){
            score = score + 5;
        }

        return score;
    }

    private static List linesInFile(String filename) {
        List<String> lines = new ArrayList<String>();
        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader bf = new BufferedReader(filereader);
            String line = bf.readLine();
            String editedLine = line.replaceAll(" ", "+");
            while(!(editedLine == null)){
                lines.add(editedLine.toLowerCase());
                line = bf.readLine();
                if(!(line == null)){
                    editedLine = line.replaceAll(" ", "+");
                }
                else{
                    editedLine = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List filesInDirectory(String directoryPath){
        List fileNames = new ArrayList<>();
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }
}

class Flick {
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Year")
    @Expose
    private String year;
    @SerializedName("Rated")
    @Expose
    private String rated;
    @SerializedName("Released")
    @Expose
    private String released;
    @SerializedName("Runtime")
    @Expose
    private String runtime;
    @SerializedName("Genre")
    @Expose
    private String genre;
    @SerializedName("Director")
    @Expose
    private String director;
    @SerializedName("Writer")
    @Expose
    private String writer;
    @SerializedName("Actors")
    @Expose
    private String actors;
    @SerializedName("Plot")
    @Expose
    private String plot;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Awards")
    @Expose
    private String awards;
    @SerializedName("Poster")
    @Expose
    private String poster;
    @SerializedName("Metascore")
    @Expose
    private String metascore;
    @SerializedName("imdbRating")
    @Expose
    private String imdbRating;
    @SerializedName("imdbVotes")
    @Expose
    private String imdbVotes;
    @SerializedName("imdbID")
    @Expose
    private String imdbID;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The year
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     * The Year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     *
     * @return
     * The rated
     */
    public String getRated() {
        return rated;
    }

    /**
     *
     * @param rated
     * The Rated
     */
    public void setRated(String rated) {
        this.rated = rated;
    }

    /**
     *
     * @return
     * The released
     */
    public String getReleased() {
        return released;
    }

    /**
     *
     * @param released
     * The Released
     */
    public void setReleased(String released) {
        this.released = released;
    }

    /**
     *
     * @return
     * The runtime
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     *
     * @param runtime
     * The Runtime
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     *
     * @return
     * The genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     * The Genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     * The director
     */
    public String getDirector() {
        return director;
    }

    /**
     *
     * @param director
     * The Director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     *
     * @return
     * The writer
     */
    public String getWriter() {
        return writer;
    }

    /**
     *
     * @param writer
     * The Writer
     */
    public void setWriter(String writer) {
        this.writer = writer;
    }

    /**
     *
     * @return
     * The actors
     */
    public String getActors() {
        return actors;
    }

    /**
     *
     * @param actors
     * The Actors
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     *
     * @return
     * The plot
     */
    public String getPlot() {
        return plot;
    }

    /**
     *
     * @param plot
     * The Plot
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The Language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The awards
     */
    public String getAwards() {
        return awards;
    }

    /**
     *
     * @param awards
     * The Awards
     */
    public void setAwards(String awards) {
        this.awards = awards;
    }

    /**
     *
     * @return
     * The poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     *
     * @param poster
     * The Poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     *
     * @return
     * The metascore
     */
    public String getMetascore() {
        return metascore;
    }

    /**
     *
     * @param metascore
     * The Metascore
     */
    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    /**
     *
     * @return
     * The imdbRating
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     *
     * @param imdbRating
     * The imdbRating
     */
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     *
     * @return
     * The imdbVotes
     */
    public String getImdbVotes() {
        return imdbVotes;
    }

    /**
     *
     * @param imdbVotes
     * The imdbVotes
     */
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    /**
     *
     * @return
     * The imdbID
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     *
     * @param imdbID
     * The imdbID
     */
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The response
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The Response
     */
    public void setResponse(String response) {
        this.response = response;
    }

}
