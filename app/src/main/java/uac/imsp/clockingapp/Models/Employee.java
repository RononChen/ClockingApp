package uac.imsp.clockingapp.Models;

import android.text.TextUtils;


public class Employee implements IEmployee{
    public final static int EMPTY_NUMBER=13,
                    INVALID_NUMBER=1,
                    EMPTY_LASTNAME=2,
                    INVALID_LASTNAME=3,
                    EMPTY_FIRSTNAME=4,
                    INVALID_FIRSTNAME=5,

                    EMPTY_USERNAME=6,
                  INVALID_USERNAME=11,
                    EMPTY_PICTURE=7,
                   EMPTY_QRCODE=8, EMPTY_PASSWORD=9,
                            INVALID_PASSWORD=10,EMPTY_MAIL=13,INVALID_MAIL=12

                    ;

public  final static String SIMPLE="simple", HEAD="Directeur",CHIEF="Chef personnel";


    //Les infos personnelles sur l'employé
    private int RegistrationNumber;
    private String Firstname;

    private String Lastname;
    private char Gender;
    private String Birthdate;

    private String MailAddress;
    private byte[] Picture;
    private byte[] QRCode;

    private String Username;
    private String Password;
    private String Type;



    //Constructeurs


    public Employee(int registrationNumber, String firstname,
                    String lastname, char gender, String birthdate,
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

    public Employee(int registrationNumber, String firstname, String lastname,
                    char gender, String birthdate, String mailAddress, String username,
                    String password,String type) {

        this(registrationNumber, firstname, lastname, gender,
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

    public int getGender() {
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

    public byte[] getQRCode() {
        return QRCode;
    }
    public  String getUsername(){
        return  Username;
    }
    public  String getPassword(){
        return  Password;
    }
    public String getType(){ return Type;}

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(""+RegistrationNumber))
            return EMPTY_NUMBER;
        else if(hasInvalidNumber())
            return INVALID_NUMBER;
        if(TextUtils.isEmpty(Lastname))
            return EMPTY_LASTNAME;
        else if (hasInvalidLastName())
        return INVALID_LASTNAME;
        if(TextUtils.isEmpty(Firstname))
            return EMPTY_FIRSTNAME;
        else if (hasInvalidFirstname())
            return INVALID_FIRSTNAME;
        else if(hasInvalidEmail())
            return INVALID_MAIL;
        if(TextUtils.isEmpty(Username))
            return EMPTY_USERNAME;
        else if (hasInvalidUsername())
            return INVALID_USERNAME;
        else if (TextUtils.isEmpty(Password))
            return EMPTY_PASSWORD;
        else if(hasInvalidPassword())
            return  INVALID_PASSWORD;

               return 0;
    }


    //Setters
    public void setRegistrationNumber(int registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;

    }
    public int validUser(){
        if(TextUtils.isEmpty(Username))
            return EMPTY_USERNAME;
        else if (hasInvalidUsername())
            return INVALID_USERNAME;
        else if (TextUtils.isEmpty(Password))
            return EMPTY_PASSWORD;
        else if(hasInvalidPassword())
            return  INVALID_PASSWORD;
        return 0;

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

    public void setQRCode(byte[] qRCode) {
        QRCode = qRCode;
    }
    public void setType(String type){
        Type=type;
    }

    public  boolean hasInvalidUsername() {


        return !Username.matches("^[A-Z][A-Za-z0-9]{5,29}$");
    }

    public boolean hasInvalidLastName() {
//A revoir
        return !Lastname.matches("[A-Za-z]+");

    }    public boolean hasInvalidFirstname(){

        //A revoir
        return !Firstname.matches("^[A-B][a-zéèçàâêûîô]+$");


    }

    public boolean hasInvalidNumber(){
        return !String.valueOf(RegistrationNumber).matches("^[1-9]+$");

    }

    public boolean hasInvalidEmail() {
        return !MailAddress.matches("^[A-Za-z]+\\.?\\w+@[A-Za-z0-9_-]+\\.\\w+$");


    }
    public  boolean hasInvalidPassword() {
        return !Password.matches("\\w{6,}");

    }
}