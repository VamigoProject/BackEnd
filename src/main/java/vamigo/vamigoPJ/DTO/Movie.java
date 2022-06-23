package vamigo.vamigoPJ.DTO;

public class Movie {
    private String id;

    private String name;

    private String movieNmE;

    private String year;

    private String country;

    private String typE;

    private String genre;

    private String pStatus;

    private String director;

    private String producer;

    private String category;

    private String image;

    public String getImage() { return  image;}

    public void setImage(String image) {this.image = image;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieNmE() {
        return movieNmE;
    }

    public void setMovieNmE(String movieNmE) {
        this.movieNmE = movieNmE;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTypE() {
        return typE;
    }

    public void setTypE(String typE) {
        this.typE = typE;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
