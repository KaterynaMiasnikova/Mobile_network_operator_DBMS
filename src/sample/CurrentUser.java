package sample;

public class CurrentUser {
    protected static String surname;
    protected static String name;
    protected static String fathername;
    protected String loginCur;
    protected String passwordCur;
    protected String job;
    protected static Integer id;

    public static Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        CurrentUser.id = id;
    }

    public CurrentUser(String l, String p){
        this.loginCur = l;
        this.passwordCur = p;
    }
    public CurrentUser(){}

    public String getLoginCur(){
        return this.loginCur;
    }
    public void setLoginCur(String l){
        this.loginCur = l;
    }

    public String getPasswordCur(){
        return this.passwordCur;
    }
    public void setPasswordCur(String p){
        this.passwordCur = p;
    }

    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    public static String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        CurrentUser.surname = surname;
    }

    public static String getName() {
        return name;
    }
    public void setName(String name) {
        CurrentUser.name = name;
    }

    public static String getFathername() {
        return fathername;
    }
    public void setFathername(String fathername) {
        CurrentUser.fathername = fathername;
    }
}