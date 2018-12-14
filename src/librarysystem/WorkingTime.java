/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;
import java.util.ArrayList;
/**
 *
 * @author Lietotajs
 */
public class WorkingTime {
    public String weekday;
    private String opens;
    private String closes;

    public WorkingTime() {
    }

    public WorkingTime(String weekday, String opens, String closes) {
        this.weekday = weekday;
        this.opens = opens;
        this.closes = closes;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String opens) {
        this.opens = opens;
    }

    public String getCloses() {
        return closes;
    }

    public void setCloses(String closes) {
        this.closes = closes;
    }

}
