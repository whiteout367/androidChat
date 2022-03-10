package kr.pe.jw.citychat;

public class WordItemData {
    public String nn;
    public  String age;
    public  String area;
    public  String sex;

    public WordItemData(String nn, String age, String area, String sex) {
        this.nn = nn;
        this.age = age;
        this.area = area;
        this.sex = sex;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
