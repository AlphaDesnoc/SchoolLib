package fr.desnoc.gestionnary.objects;

public class Teacher {

    private String[] name;

    public Teacher(String... name){
        this.name = name;
    }

    public void changeName(String... name){
        setName(name);
    }
    private void setName(String... name) {
        this.name = name;
    }

    public String[] getNames(){
        return name;
    }

}