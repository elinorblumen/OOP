package oop.ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class InnerScope extends Scope{
    Method containingMethod;
    List<Variable> localVariables;

    public InnerScope(List<String> codeLines, Method containingMethod,
                      List<Variable> localVariables) {
        super(codeLines);
        this.containingMethod = containingMethod;
        this.localVariables = localVariables;

    }

    @Override
    public void checkScope()  throws SjavaException{
    int index = 0; int lastIndex = codeLines.size();
        while (index < lastIndex) {
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
                Variable localVariable = containsVariable(localVariables, variableName);
                if (localVariable != null) {
                    if (localVariable.getIsFinal()) {
                        throw new SjavaException("Error: cannot assign a value to " +
                                "a final variable, " + variableName);
                    }
                    assign(localVariable, variableValue);
                }
                else{
                    Variable globalVariable = containsVariable(
                            containingMethod.getGlobalScope().getVariables(), variableName);
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
                    containingMethod.addGlobalVariable(variableCopy);
                }
            }
            index++;
            continue;
        }
        Matcher methodCallMatcher = methodCall.matcher(codeLines.get(index));
        if(methodCallMatcher.matches()){
            String methodName = methodCallMatcher.group(1);
            Method method = containingMethod.getGlobalScope().containsMethod(methodName);
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
            checkConditions(codeLines.get(index), containingMethod.getName());
            List<String> innerScopeLines = new ArrayList<>();
            index ++;
            index = getScopeIndex(codeLines, index, innerScopeLines);
            List<Variable> newVariableList = localVariables;
            newVariableList.addAll(variables);
            InnerScope innerScope = new InnerScope(innerScopeLines,
                    containingMethod, newVariableList);
            innerScope.checkScope();
            continue;
        }
        throw new SjavaException("Error: syntax error in method "
                + containingMethod.getName());
    }
}

    @Override
    protected Variable searchVariableInScopes(String assignmentValue) {
        Variable checkedVariable = containsVariable(variables, assignmentValue);
        if (checkedVariable==null){
            checkedVariable = containsVariable(localVariables, assignmentValue);
        }
        if (checkedVariable==null){
            checkedVariable = containsVariable(containingMethod.getGlobalVariables(), assignmentValue);
        }
        if (checkedVariable==null){
            checkedVariable = containsVariable(containingMethod.
                    getGlobalScope().variables, assignmentValue);
        }
        return checkedVariable;
    }

}
