package uac.imsp.clockingapp.Models.entity;

import java.util.Hashtable;

public class Planning implements IPlanning {

    private int Id;
    private String StartTime;
    private String EndTime;

    public Planning(String startTime,String endTime){
        StartTime=startTime;
        EndTime=endTime;
    }
    public Planning(int id){
        Id=id;
    }

    public  int getId(){
        return Id;
   }
    public String getStartTime(){
        return StartTime;
    }
    public String getEndTime(){
        return EndTime;
    }

    public void setStartTime(String startTime){
        StartTime = startTime;
    }
    public void setEndTime(String endTime){
        EndTime = endTime;
    }
    public void setId(int id){
        Id=id;
    }
    public Hashtable<String,String> extractHours(){
        Hashtable<String,String> h = new Hashtable<>();

        h.put("start",StartTime.split(":")[0]);
        h.put("end",EndTime.split(":")[0]);
        return h;

    }



}