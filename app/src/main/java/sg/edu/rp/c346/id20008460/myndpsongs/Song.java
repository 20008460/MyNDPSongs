package sg.edu.rp.c346.id20008460.myndpsongs;

import java.io.Serializable;

public class Song implements Serializable {

    private int _id;
    private String title;
    private String singers;
    private int year;
    private int stars;

    public Song(int _id, String title, String singers, int year, int stars) {
        this._id = _id;
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSingers(String singers) {
        this.singers = singers;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        String strStar = " ";
        this.stars = stars;

        if (stars == 1){
            strStar = "*";
        } else if (stars == 2) {
            strStar = "* *";
        } else if (stars == 3) {
            strStar = "* * *";
        } else if (stars == 4) {
            strStar = "* * * *";
        } else if (stars == 5 ) {
            strStar = "* * * * *";
        }
        return  strStar;
    }
}
