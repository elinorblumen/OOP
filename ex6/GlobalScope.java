package oop.ex6;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalScope extends Scope{
    private final List<Method> methods = new ArrayList<>();
    static final Pattern parameterPattern = Pattern.compile(parameter);
    static final Pattern method = Pattern.compile("\\s*void\\s+([a-zA-Z]\\w*)\\s*[\\(]"
            +parameter+"?"+"(?:\\s*,\\s*"+parameter+")*[\\)]\\s*[\\{]\\s*");

    /**
     * Constructor for GlobalScope.
     * @param codeLines The code lines of the whole Sjavac file.
     */
    public GlobalScope(List<String> codeLines) {
        super(codeLines);
    }

    /**
     * Checks the validity of the given scope without checking it's
     * sub-scopes validity.
     * @throws SjavaException SjavaException.
     */
    @Override
    public void checkScope() throws SjavaException {
        int index = 0; int lastIndex = codeLines.size();
        while (index<lastIndex){
            Matcher validMatcher = valid_line.matcher(codeLines.get(index));
            if (validMatcher.matches()){
                index++;
                continue;
            }
            Matcher variableMatcher = variable.matcher(codeLines.get(index));
            if(variableMatcher.matches()){
                declareVariables(variables, codeLines.get(index), variableMatcher);
                index++;
                continue;
            }
            Matcher assignmentMatcher = assignment.matcher(codeLines.get(index));
            if(assignmentMatcher.matches()){
                String variableName = assignmentMatcher.group(1);
                String variableValue = assignmentMatcher.group(2);
                Variable variable = containsVariable(variables, variableName);
                if (variable==null){
                    throw new SjavaException("Error: variable "+ variableName+ " " +
                            "does not exits in global scope");
                }
                if (variable.getIsFinal()){
                    throw new SjavaException("Error: cannot assign a value to " +
                            "a final variable, "+ variableName);
                }
                assign(variable, variableValue);
                index++;
                continue;
            }
            Matcher methodMatcher = method.matcher(codeLines.get(index));
            if (methodMatcher.matches()){
                String methodName = methodMatcher.group(1);
                List<String> methodLines = new ArrayList<>();
                List<Variable> parameters = parametersToVariables(codeLines.get(index));
                index ++;
                index = getScopeIndex(codeLines, index, methodLines);
                Method newMethod = new Method(methodLines, methodName, parameters, this);
                methods.add(newMethod);
                continue;
            }
            throw new SjavaException("Error: syntax error in line "
                    + Integer.toString(index+1));
        }
    }

    @Override
    protected Variable searchVariableInScopes(String assignmentValue) {
        return containsVariable(variables, assignmentValue);
    }

    private List<Variable> parametersToVariables(String codeLine){
        List<Variable> parametersAsVariables = new ArrayList<>();
        int openParIndex = codeLine.indexOf("(");
        int closeParIndex = codeLine.indexOf(")");
        codeLine = codeLine.substring(openParIndex+1, closeParIndex);
        if (codeLine.matches("\\s*")){
            return parametersAsVariables;
        }
        String[] parameters = codeLine.split(",");
        for(String parameter: parameters){
            Matcher parameterMatcher = parameterPattern.matcher(parameter);
            if (parameterMatcher.matches()){
                boolean isFinal = parameterMatcher.group(1)!=null;
                String type = parameterMatcher.group(2);
                String variableName = parameterMatcher.group(3);
                Variable newVariable = new Variable(variableName, type, null, isFinal);
                parametersAsVariables.add(newVariable);
            }
        }
        return parametersAsVariables;
    }

    protected Method containsMethod(String name) {
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

    public List<Method> getMethods(){return methods;}
}
