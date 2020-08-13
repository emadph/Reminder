package co.pourahmadi.emad.Models;

import java.util.GregorianCalendar;

/**
 * Created by Emad on 15/09/2018.
 */

public class CalenderEvent {

    private String title;
    private GregorianCalendar engDate;

    public CalenderEvent (String title, GregorianCalendar engDate) {
        this.title = title;
        this.engDate = engDate;
    }

    public GregorianCalendar getEngDate () {
        return engDate;
    }

    public void setEngDate (GregorianCalendar engDate) {
        this.engDate = engDate;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }
}
