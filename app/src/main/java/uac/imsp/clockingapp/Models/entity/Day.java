package uac.imsp.clockingapp.Models.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;

public class Day  implements IDay {



    private int Id;
    private String FormatedMonth="",FormatedYear="",FormatedDay="";

    private  String Date;
    public Day (){
        Date =java.time.LocalDate.now().toString();

    }

    public Day(String date ) {

  this.Date=date;
    }
    public Day(int year,int month,int day){
        FormatedYear=""+year;

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
    public int getMonth() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String dateInString = this.Date;

        java.util.Date date = sdf.parse(dateInString);

        Calendar calendar = Calendar.getInstance();

        assert date != null;
        calendar.setTime(date);


        return calendar.get(Calendar.MONTH)+1 ;
    }


    public int getYear() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = this.Date;
        java.util.Date date = sdf.parse(dateInString);

        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);

return Integer.parseInt(dateInString.split("-")[0]);
       // return calendar.get(Calendar.YEAR) ;
    }
    public int getDayOfWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = this.Date;
        java.util.Date date = sdf.parse(dateInString);


        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK)+1 ;
    }
    public int getDayOfMonth() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = this.Date;
        java.util.Date date = sdf.parse(dateInString);

        Calendar calendar = Calendar.getInstance();
        assert date != null;

        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) ;


    }

    public int getWeekOfMonth() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = this.Date;
        java.util.Date date = sdf.parse(dateInString);

        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH) ;

    }
    public int  getFirstDayOfMonth() throws ParseException {

        Day d = new Day(this.getYear(),this.getMonth(),1);

        return  d.getDayOfMonth();
    }
    public int getLenthOfMonth() throws ParseException {
        YearMonth yearMonth=YearMonth.of(getYear(),getMonth());
        return yearMonth.lengthOfMonth();
    }
    public boolean isWeekEnd() throws ParseException {
        return (getDayOfWeek()==6 || getDayOfWeek()==7);
    }

    public String getFrenchFormat() throws ParseException {
        return getDayOfMonth()+"/"+getMonth()+"/"+getYear();
    }


    public void setId(int id) {
        Id = id;
    }


    public String getDayNumber() {
        return FormatedDay;
    }

    public void setDayNumber(String formatedDay) {
        FormatedDay = formatedDay;
    }

    public String getFormatedYear() {
        return FormatedYear;
    }

    public void setFormatedYear(String formatedYear) {
        FormatedYear = formatedYear;
    }
}