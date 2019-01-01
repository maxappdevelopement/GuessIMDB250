package appdevelopement.max.guessimdb250;

public class Movie {

    private String movie;
    private String imageUrl;
    private String director;
    private String actors;


    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }


}
