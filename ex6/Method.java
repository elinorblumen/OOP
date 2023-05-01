package oop.ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Method extends Scope{
    private final String name;
    private final GlobalScope globalScope;
    private final List<Variable> parameters;
    private final List<Variable> globalVariables = new ArrayList<>();

    public Method(List<String> codeLines, String name,
                  List<Variable> parameters, GlobalScope globalScope){
        super(codeLines);
        this.name = name;
        this.globalScope = globalScope;
        this.parameters = parameters;
        variables.addAll(parameters);
    }

    @Override
    public void checkScope()  throws SjavaException {
        int index = 0; int lastIndex = codeLines.size();
        while (index < lastIndex-1) {
            Matcher validMatcher = valid_line.matcher(codeLines.get(index));
            if (validMatcher.matches()) {
                index++;
                continue;
            }
            Matcher returnMatcher = returnLine.matcher(codeLines.get(index));
            if (returnMatcher.matches()) {
                index++;
                continue;
            }
            Matcher variableMatcher = variable.matcher(codeLines.get(index));
            if (variableMatcher.matches()) {
                declareVariables(variables, codeLines.get(index), variableMatcher);
                index++;
                continue;
            }
            Matcher assignmentMatcher = assignment.matcher(codeLines.get(index));
            if (assignmentMatcher.matches()) {
                String variableName = assignmentMatcher.group(1);
                String variableValue = assignmentMatcher.group(2);
                Variable variable = containsVariable(variables, variableName);
                if (variable != null) {
                    if (variable.getIsFinal()){
                        throw new SjavaException("Error: cannot assign a value to " +
                                "a final variable, "+ variableName);
                    }
                    assign(variable, variableValue);
                } else {
                    Variable globalVariable = containsVariable(globalScope.variables, variableName);
                    if (globalVariable==null){
                        throw new SjavaException("Error: variable "+ variableName+ " " +
                                "does not exits in any scope");
                    }
                    Variable variableCopy = globalVariable.copyVariable();
                    if (variableCopy.getIsFinal()){
                        throw new SjavaException("Error: cannot assign a value to " +
                                "a final variable, "+ variableName);
                    }
                    assign(variableCopy, variableValue);
                    globalVariables.add(variableCopy);
                }
                index++;
                continue;
            }
            Matcher methodCallMatcher = methodCall.matcher(codeLines.get(index));
            if(methodCallMatcher.matches()){
                String methodName = methodCallMatcher.group(1);
                Method method = globalScope.containsMethod(methodName);
                if(method==null){
                    throw new SjavaException("Error: a call for method "+ methodName+ " " +
                            "which does not exist");
                }
                List<Variable> methodParameters = method.getParameters();
                checkParameterTypes(codeLines.get(index), methodParameters, methodName);
                index++;
                continue;
            }
            Matcher ifOrWhileMatcher = ifOrWhile.matcher(codeLines.get(index));
            if (ifOrWhileMatcher.matches()){
                checkConditions(codeLines.get(index), name);
                List<String> innerScopeLines = new ArrayList<>();
                index ++;
                index = getScopeIndex(codeLines, index, innerScopeLines);
                InnerScope innerScope = new InnerScope(innerScopeLines, this, variables);
                innerScope.checkScope();
                continue;
            }
            throw new SjavaException("Error: syntax error in method "
                    + name);
        }
        Matcher returnMatcher = returnLine.matcher(codeLines.get(index));
        if (!returnMatcher.matches()) {
            throw new SjavaException("Error: return statement missing in method "
                    + name);
        }
    }

    @Override
    protected Variable searchVariableInScopes(String assignmentValue) {
        Variable checkedVariable = containsVariable(variables, assignmentValue);
        if (checkedVariable==null){
            checkedVariable = containsVariable(globalVariables, assignmentValue);
        }
        if (checkedVariable==null){
            checkedVariable = containsVariable(globalScope.getVariables(), assignmentValue);
        }
        return checkedVariable;
    }

    public String getName(){return name;}

    public List<Variable> getParameters(){return parameters;}

    public GlobalScope getGlobalScope(){return globalScope;}

    public List<Variable> getGlobalVariables(){return globalVariables;}

    public void addGlobalVariable(Variable variable){
        globalVariables.add(variable);
    }
}
