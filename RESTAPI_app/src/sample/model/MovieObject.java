package sample.model;

public class MovieObject {
    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String poster;
    private String director;
    private String release;
    private String rated;
    private String genre;
    private String language;

    public MovieObject(String title, String year, String Type,String director, String release, String rated,String genre,String language){
        this.title=title;
        this.year=year;
        this.type=Type;
        this.director=director;
        this.release=release;
        this.rated=rated;
        this.genre=genre;
        this.language=language;
    }
    // Getter Methods

    public String getDirector(){
            return director;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    // Setter Methods

    public void setTitle(String Title) {
        this.title = Title;
    }

    public void setYear(String Year) {
        this.year = Year;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public void setPoster(String Poster) {
        this.poster = Poster;
    }
}