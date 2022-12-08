package entity;

import java.util.Hashtable;

public class Planning implements IPlanning {

    private int Id;
    private final String StartTime;
    private final String EndTime;
    private byte[] WorkDays;



    public Planning(String startTime,String endTime,byte[] workDays){
        StartTime=startTime;
        EndTime=endTime;
        WorkDays=workDays;
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

    public byte[] getWorkDays() {
        return WorkDays;
    }

    public void setWorkDays(byte[] workDays) {
            WorkDays = workDays;
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