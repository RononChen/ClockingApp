package entity;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Employee implements IEmployee {
    public final static int EMPTY_NUMBER=13;
    public final static int INVALID_NUMBER=1;
    public final static int EMPTY_LASTNAME=2;
    public final static int INVALID_LASTNAME=3;
    public final static int EMPTY_FIRSTNAME=4;
    public final static int INVALID_FIRSTNAME=5;

    public final static int EMPTY_USERNAME=6;
    public final static int INVALID_USERNAME=11;

    public final static int EMPTY_PASSWORD=9;
    public final static int INVALID_PASSWORD=10;
    public final static int EMPTY_MAIL=14;
    public final static int INVALID_MAIL=12;
    public final static int EMPTY_BIRTHDATE=15

                    ;

//public  final static String SIMPLE="Simple", HEAD="Directeur",CHIEF="Chef personnel";


    //Les infos personnelles sur l'employé
    private int RegistrationNumber;

    private String Firstname;
    private String CurrentStatus;

    private String Lastname;
    private char Gender;
    private String Birthdate;

    private String MailAddress;
    private byte[] Picture;

    private String Username;
    private String Password;
    private String Type;
    private byte[] Workdays;
    private  String AddDate;
    private  boolean isAdmin;

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    //Constructeurs


    public Employee(int registrationNumber, String lastname,
                    String firstname, char gender, String birthdate,
                    String mailAddress, byte[] picture,  String username,
                    String password,String type) {

        RegistrationNumber = registrationNumber;

        Firstname = firstname;
        Lastname = lastname;
        Gender = gender;
        Birthdate = birthdate;
        MailAddress = mailAddress;
        Picture = picture;
        Username = username;
        Password = password;
        Type=type;

    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public Employee(int registrationNumber, String lastname, String firstname,

                    char gender, String birthdate, String mailAddress, String username,
                    String password, String type) {

        this(registrationNumber, lastname,firstname, gender,
                birthdate, mailAddress, null,  username, password,type);


    }
    public Employee(String username,String password){
        Username=username;
        Password=password;
    }

    public Employee(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    //getters
    public int getRegistrationNumber() {
        return RegistrationNumber;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public char getGender() {
        return Gender;
    }


    public String getBirthdate() {
        return Birthdate;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public  byte[] getPicture() {
        return Picture;
    }

    public  String getUsername(){
        return  Username;
    }
    public  String getPassword(){
        return  Password;
    }
    public String getType(){ return Type;}

    public byte[] getWorkdays() {
        return Workdays;
    }

    public void setCurrentStatus(String currentStatus) {
        CurrentStatus = currentStatus;
    }

    public void setWorkdays(byte[] workdays) {
        Workdays = workdays;
    }

    public String getCurrentStatus() {
        return CurrentStatus;
    }

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(""+RegistrationNumber))
            return EMPTY_NUMBER;
        else if(hasInvalidNumber())
            return INVALID_NUMBER;
        else if(TextUtils.isEmpty(Lastname))
            return EMPTY_LASTNAME;
        else if (hasInvalidLastName())
            return INVALID_LASTNAME;
       else if(TextUtils.isEmpty(Firstname))
            return EMPTY_FIRSTNAME;
        else if (hasInvalidFirstname())
            return INVALID_FIRSTNAME;
        else if(TextUtils.isEmpty(Birthdate))
            return EMPTY_BIRTHDATE;
        else if (TextUtils.isEmpty(MailAddress))
            return EMPTY_MAIL;
        else if(hasInvalidEmail())
            return INVALID_MAIL;
        else if(TextUtils.isEmpty(Username))
            return EMPTY_USERNAME;
        else if (hasInvalidUsername())
            return INVALID_USERNAME;
        else if (TextUtils.isEmpty(Password))
            return EMPTY_PASSWORD;
        else if(hasInvalidPassword())
            return  INVALID_PASSWORD;

        return 0;
    }

    @Override
    public void setPassword(String password) {
        Password=password;

    }


    //Setters
    public void setRegistrationNumber(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;

    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;

    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }
    public void setType(String type){
        Type=type;
    }

    public void setUsername(String username){
        Username=username;

    }
    public  boolean hasInvalidUsername() {


        return !Username.matches("^[A-Z][A-Za-z0-9]{5,29}$")
                &&!Patterns.EMAIL_ADDRESS.matcher(Username).matches();
    }

    public boolean hasInvalidLastName() {
        String pattern = "^[A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][A-Za-zâêîûôáéíúóàèùìòç]" +

                "+([-' ][A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+)?";
        return !Lastname.matches(pattern);



    }    public boolean hasInvalidFirstname(){
        String pattern ="^[A-ZÂÊÛÎÔÁÉÚÍÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+" +
                "([-'][A-ZÂÊÛÎÔÁÉÚÍ ÓÀÈÙÌÒÇ][a-zâêîûôáéíúóàèùìòç]+)?";
        return !Firstname.matches(pattern);


    }

    public boolean hasInvalidNumber(){
        return !String.valueOf(RegistrationNumber).matches("^[0-9]+$");


    }

    public boolean hasInvalidEmail() {
return  !Patterns.EMAIL_ADDRESS.matcher(MailAddress).matches();
    }
    public  boolean hasInvalidPassword() {

        return !(Password.length()>=6&& is_in_the_range("[A-Z]")&&

                is_in_the_range("[a-z]")&&is_in_the_range("[0-9]")&&
                is_in_the_range("\\W"));
    }
    public int nb_occur(String str, String pattern){

        Matcher matcher= Pattern.compile(pattern).matcher(str);
        int count=0;
        while(matcher.find())
            count++;
        return count;
    }
    public boolean is_in_the_range(String pattern){
        return 1<= nb_occur(Password,pattern);


    }

    public boolean isAdmin() {
        return isAdmin;
    }
}