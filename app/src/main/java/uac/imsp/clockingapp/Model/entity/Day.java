package entity;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;

public class Day  implements IDay {



    private int Id;
    private String FormatedMonth,FormatedYear,FormatedDay,Date;
    public Day (){
        Date =java.time.LocalDate.now().toString();
        setFormat();


    }
    public void setFormat(){
        String []  date =Date.split("-");
        FormatedYear=date[0];
        FormatedMonth=date[1];
        FormatedDay=date[2];

    }

    public Day(String date ) {

     this.Date=date;
     setFormat();
    }
    public Day(int year,int month,int day){

        FormatedYear=""+year;
        FormatedDay="";
        FormatedMonth="";
        if(month<10)
            FormatedMonth="0";
        if(day <10)
            FormatedDay="0";
        FormatedDay+=day;
        FormatedMonth+=month;
        this.Date=FormatedYear+"-"+FormatedMonth+"-"+FormatedDay;

    }


    public Day(int month,int day){

        this( Calendar.getInstance().get(Calendar.YEAR) ,month,day);
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
    public int getMonth()  {
        return  Integer.parseInt(FormatedMonth) ;

    }

/*//public int getDay(){


}*/
    public int getYear() {

return Integer.parseInt(FormatedYear);

    }
    public  Day addMonth(){
        Day day;
        if(this.getMonth()==12)
            day=new Day(this.getYear()+1,1,1);
        else
            day=new Day(this.getYear(),this.getMonth()+1,1);
        return day;
    }

    public  Day subtractMonth(){
        Day day;
        if(this.getMonth()==1)
            day=new Day(this.getYear()-11,12,1);
        else
            day=new Day(this.getYear(),this.getMonth()-1,1);
        return day;
    }
    public String getMonthPart(){
        return this.getDate().split("-")[0]+"-"+this.getDate().split("-")[1];
    }
    public int getDayOfWeek()  {
     int dayOfWeek;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = this.Date;
        java.util.Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        if(dayOfWeek==0)
            dayOfWeek=7;
        return dayOfWeek ;
    }
    public int getDayOfMonth()  {
        return Integer.parseInt(FormatedDay) ;


    }
    public int  getFirstDayOfMonth()  {
        //wether it is monday, tuesday , etc


        Day d = new Day(this.getYear(),this.getMonth(),1);

        return  d.getDayOfWeek();
    }
    public int getLenthOfMonth()  {
        YearMonth yearMonth=YearMonth.of(getYear(),getMonth());
        return yearMonth.lengthOfMonth();
    }

    public boolean isWeekEnd()  {
        return (getDayOfWeek()==6 || getDayOfWeek()==7);
    }

    public String getFrenchFormat()  {
        return FormatedDay+
                "/"+FormatedMonth+
                "/"+FormatedYear;

    }


    public void setId(int id) {
        Id = id;

    }


    public String getFormatedYear() {
        return FormatedYear;
    }

    public String getFormatedMonth() {
        return FormatedMonth;
    }

    public String getFormatedDay() {
        return FormatedDay;
    }

}