package oop.ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Scope {
    List<String> codeLines;
    List<Variable> variables;

    static final String type ="(int|double|String|boolean|char)";
    static final String variable_name = "(?:[a-zA-Z]\\w*)|(?:_\\w+)";
    static final String int_value = "(?:(?:-?\\d+)|0)";
    static final String double_value = int_value+"(?:[\\.][0-9]+)?";
    static final String boolean_value = "(?:"+double_value+"|true|false)";
    static final String char_value = "[\\'].[\\']";
    static final String string_value = "[\\\"].*[\\\"]";
    static final String validValue = "(?:\\s*("+int_value+"|"+
            double_value+"|"+boolean_value+"|"+char_value+"|"
            + string_value +"|"+variable_name+")\\s*)";
    static final String initialize_value = "(?:\\s*=\\s*("+int_value+"|"+
            double_value+"|"+boolean_value+"|"+char_value+"|"
            + string_value +"|"+variable_name+")\\s*)";
    static final String initialize_variable_optional = "("+variable_name+")"+initialize_value+"?";
    static final String initialize_variable = "("+variable_name+")"+initialize_value;
    static final String parameter = "(?:\\s*(final\\s+)?"+type+"\\s+("+variable_name+"))";

    static final Pattern valid_line = Pattern.compile("\\s*|//.*");
    static final Pattern variable = Pattern.compile("\\s*(final\\s+)?"+type+
            "\\s+"+ initialize_variable_optional +"(?:\\s*,\\s*"+
            initialize_variable_optional +")*\\s*;\\s*");
    static final Pattern regular_value =  Pattern.compile(int_value+"|"+double_value
            +"|"+boolean_value+"|"+char_value+"|"+ string_value);
    static final Pattern assignment = Pattern.compile("\\s*"+ initialize_variable +"\\s*;\\s*");
        static final Pattern open_par=Pattern.compile(".*[\\{]\\s*");
    static final Pattern close_par=Pattern.compile("\\s*[\\}]\\s*");

    static final Pattern ifOrWhile = Pattern.compile("\\s*(?:if|while)\\s*[\\(]\\s*"+validValue+
            "(?:(?:[\\|]{2}|[\\&]{2})"+validValue+")*\\s*[\\)]\\s*[\\{]\\s*");
    static final Pattern methodCall = Pattern.compile("\\s*([a-zA-Z]\\w*)\\s*[\\(]"
            +validValue+"?(?:\\s*,\\s*"+validValue+")*[\\)]\\s*;\\s*");
    static final Pattern returnLine = Pattern.compile("\\s*return\\s*;\\s*");

    public Scope(List<String> codeLines) {
        this.codeLines = codeLines;
        this.variables = new ArrayList<Variable>();
    }

    public abstract void checkScope()  throws SjavaException;

    /**
     * Method that gets the lines of a scope that has been opened and throws exception
     * if the block hasn't been closed.
     * @param codeLinesList A list of the code lines.
     * @param index The index in which the block begins.
     * @param scopeLines A list of the specific block lines.
     * @return The last index of the block line, as an index in the array.
     * @throws SjavaException Sjava exception.
     */
    protected static int getScopeIndex(List<String> codeLinesList, int index,
            List<String> scopeLines) throws SjavaException{
        int lastIndex = codeLinesList.size();
        int parenthesisCount = 1;
        while (index<lastIndex) {
            Matcher closeMatcher = close_par.matcher(codeLinesList.get(index));
            if(closeMatcher.matches()){
                parenthesisCount --;
                if(parenthesisCount==0){break;}
            }
            Matcher openMatcher = open_par.matcher(codeLinesList.get(index));
            if(openMatcher.matches()){
                parenthesisCount ++;
            }
            scopeLines.add(codeLinesList.get(index));
            index++;
        }
        if (index==lastIndex){
            throw new SjavaException("Error: invalid syntax, " +
                    "parenthesis opened but not closed");
        }
        return index +1;
    }

    /**
     * Method that gets a list of variables and checks if a given name
     * refers to a variable that exists in the list.
     * @param variables A list of variables.
     * @param name The given name.
     * @return The variable if it exists, null otherwise.
     */
    protected static Variable containsVariable(List<Variable> variables, String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name)) {
                return variable;
            }
        }
        return null;
    }

    /**
     * Method that receives a given value for initialization and checks if the
     * type of the variable is valid.
     * @param type Variable type.
     * @param value Given value.
     * @return True if the value has the correct type, false otherwise.
     */
    protected static boolean isInitializationValid(String type, String value){
        if(value.matches(int_value)&&type.equals("int")){
            return true;
        }
        if(value.matches(double_value)&&type.equals("double")){
            return true;
        }
        if(value.matches(boolean_value)&&type.equals("boolean")){
            return true;
        }
        if(value.matches(char_value)&&type.equals("char")){
            return true;
        }
        if(value.matches(string_value)&&type.equals("String")){
            return true;
        }
        return false;
    }

    /**
     * Method that receives two types of variables and checks whether
     * we can assign one to the other.
     * @param type1 Variable type.
     * @param type2 The type of the variable to assign.
     * @return True if the value has the correct type, false otherwise.
     */
    protected static boolean isAssignmentValid(String type1, String type2){
        if(type1.equals(type2)){
            return true;
        }
        if(type1.equals("double") && type2.equals("int")){
            return true;
        }
        if(type1.equals("boolean") && (type2.equals("int")
                ||type2.equals("double"))){
            return true;
        }
        return false;
    }

    /**
     * a method which receives a code line which declares variables
     * and adds then to the list of variables.
     * @param variables the list of variables.
     * @param codeLine a string which represents the code line.
     * @param variableMatcher a matcher object of the code line.
     * @throws SjavaException Sjava exception.
     */
    protected void declareVariables(List<Variable> variables, String codeLine,
                                 Matcher variableMatcher) throws SjavaException{
        boolean isFinal = variableMatcher.group(1)!=null;
        String type = variableMatcher.group(2);
        String variableName = variableMatcher.group(3);
        String variableValue = variableMatcher.group(4);
        codeLine = codeLine.replaceAll(";", "");
        String[] namesAndValues = codeLine.split(",");
        for (int i = 0 ; i < namesAndValues.length ; i++){
            if(i != 0){
                String[] variableSplitArray = namesAndValues[i].split("=");
                variableName = variableSplitArray[0].replaceAll("\\s", "");
                if(variableSplitArray.length>1){
                    variableValue = variableSplitArray[1].replaceAll("\\s", "");
                }
                else {
                    variableValue = null;
                }
            }
            if (containsVariable(variables, variableName)!=null){
                throw new SjavaException("Error: variable "+ variableName+ " " +
                        "already exits in global scope");
            }
            Variable newVariable = new Variable(variableName, type, null, isFinal);
            if (variableValue == null){
                if (isFinal){
                    throw new SjavaException("Error: final variable "+ variableName+ " " +
                            "has no assignment");
                }
                variables.add(newVariable);
                continue;
            }
            assign(newVariable, variableValue);
            variables.add(newVariable);
        }
    }

    /**
     * Method that receives a variable and a value to be initialized with and
     * checks if the value is another variable, if so it checks whether it is initialized.
     * Also checks whether the type of the value matches the variable type.
     * @param variable a variable to assign a value to.
     * @param variableValue the value.
     * @throws SjavaException SjavaException.
     */
    protected void assign(Variable variable, String variableValue) throws SjavaException{
        Variable variableAssigned= variableAsValue(variableValue);
        String type = variable.getType();
        if (variableAssigned!=null){
            variableValue = variableAssigned.getValue();
            if (!isAssignmentValid(type, variableAssigned.getType())){
                throw new SjavaException("Error: the type of the variable named "
                        + variable.getName() +" does not match the value assigned");
            }
        }
        if (!isInitializationValid(type, variableValue)){
            throw new SjavaException("Error: the type of the variable named "
                    + variable.getName() +" does not match the value assigned");
        }
        variable.setValue(variableValue);
    }

    /**
     * Method that receives an value and checks whether it's a variable
     * or an assignment of a regular type.
     * @param assignmentValue Given value.
     * @return Variable if the assignment is one, null otherwise.
     * @throws SjavaException SjavaException.
     */
    protected Variable variableAsValue(String assignmentValue) throws SjavaException{
        Matcher valueMatcher = regular_value.matcher(assignmentValue);
        if (!valueMatcher.matches()){
            Variable checkedVariable = searchVariableInScopes(assignmentValue);
            if (checkedVariable==null){
                throw new SjavaException("Error: a call for variable "
                        + assignmentValue+ " which does not exist");
            }
            if (checkedVariable.getValue()==null){
                throw new SjavaException("Error: a call for variable "+
                        assignmentValue+ " which is not initialized");
            }
            return checkedVariable;
        }
        return null;
    }

    protected abstract Variable searchVariableInScopes(String assignmentValue);

    protected void checkParameterTypes(String codeLine,
                                     List<Variable> parameters,
                                     String methodName) throws SjavaException{
        int openParIndex = codeLine.indexOf("(");
        int closeParIndex = codeLine.indexOf(")");
        codeLine = codeLine.substring(openParIndex+1, closeParIndex);
        if (codeLine.matches("\\s*")){
            if (parameters.size()!=0){
                throw new SjavaException("Error: method " + methodName +
                        " requires " + parameters.size()+ " parameters and has not received any");
            }
        }
        String[] values = codeLine.split(",");
        if (parameters.size() != values.length){
            throw new SjavaException("Error: method " + methodName +
                    " receive a wrong number of parameters");
        }
        for (int i =0; i< values.length; i++){
            String value = values[i].replaceAll("\\s*", "");
            assign(parameters.get(i), value);
        }
    }

    protected void checkConditions(String codeLine,
                                   String methodName) throws SjavaException{
        int openParIndex = codeLine.indexOf("(");
        int closeParIndex = codeLine.indexOf(")");
        codeLine = codeLine.substring(openParIndex+1, closeParIndex);
        if (codeLine.matches("\\s*")){
            throw new SjavaException("Error: empty condition statement in method "+ methodName);
        }
        String[] conditions = codeLine.split("(?:[\\|]{2}|[\\&]{2})");
        for(String condition: conditions){
            condition = condition.replaceAll("\\s*", "");
            Variable variable = variableAsValue(condition);
            if(variable==null){
                if(!condition.matches(boolean_value)){
                    throw new SjavaException("Error: condition receives a value " +
                            "which is not boolean in method "+ methodName);
                }
            }
            else{
                if(variable.getType().equals("String") || variable.getType().equals("char")){
                    throw new SjavaException("Error: condition receives a variable " +
                            "which is not of type boolean in method "+ methodName);
                }
            }
        }
    }

    protected List<Variable> getVariables(){return variables;}
}

