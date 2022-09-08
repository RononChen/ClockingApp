package uac.imsp.clockingapp.Models.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Locale;

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
    public int getDayOfWeek()  {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
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
        return calendar.get(Calendar.DAY_OF_WEEK)+1 ;
    }
    public int getDayOfMonth()  {
        return Integer.parseInt(FormatedDay) ;


    }

    public int getWeekOfMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.FRENCH);
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
        return calendar.get(Calendar.WEEK_OF_MONTH) ;

    }
    public int  getFirstDayOfMonth()  {


        Day d = new Day(this.getYear(),this.getMonth(),1);

        return  d.getDayOfMonth();
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

    public void setFormatedYear(String formatedYear) {
        FormatedYear = formatedYear;
    }

    public String getFormatedMonth() {
        return FormatedMonth;
    }

    public void setFormatedMonth(String formatedMonth) {
        FormatedMonth = formatedMonth;
    }

    public String getFormatedDay() {
        return FormatedDay;
    }

    public void setFormatedDay(String formatedDay) {
        FormatedDay = formatedDay;
    }
}