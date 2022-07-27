package uac.imsp.clockingapp.Models;

public class Day  implements IDay{


    private int Id;
    private  String Date;

    public Day(String date ) {

  this.Date=date;
    }

    public  String getDate(){
        return Date;
    }

    public  void setDate(String date){
        Date=date;
    }
    public int getId(){
        return Id;
    }


}
