package model.classes.people;

public class Person {

    private String pesel;
    private String name;
    private String surname;

    public Person(String pesel, String name, String surname) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
    }

    /** GETTERS AND SETTERS */
    public String getPesel() {return pesel;}
    public void setPesel(String pesel) {this.pesel = pesel;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}

}
