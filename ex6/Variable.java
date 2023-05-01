package oop.ex6;

public class Variable {
    private String name; private String type;
    private String value; private boolean isFinal;

    public Variable(String name, String type, String value, boolean isFinal){
        this.name = name; this.type = type; this.value = value;
        this.isFinal = isFinal;
    }

    public Variable copyVariable(){
        return new Variable(name, type, value, isFinal);
    }

    public String getName(){return name;}

    public String getValue(){return value;}

    public String getType(){return type;}

    public boolean getIsFinal(){return isFinal;}

    public void setValue(String value){this.value = value;}
}
