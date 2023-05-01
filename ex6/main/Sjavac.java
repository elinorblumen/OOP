package oop.ex6.main;

import oop.ex6.GlobalScope;
import oop.ex6.Method;
import oop.ex6.SjavaException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Sjavac {

    public static void main(String[] args) {
        try {
            List<String> codeLines;
            File source = new File(args[0]);
            codeLines = Files.readAllLines(source.toPath());
            GlobalScope globalScope = new GlobalScope(codeLines);
            globalScope.checkScope();
            for(Method method: globalScope.getMethods()){
                method.checkScope();
            }
            System.out.println("0");

        } catch (SjavaException e){
            System.err.println(e.getMessage());
            System.out.println("1");
            return;

        } catch (IOException e) {
            System.err.println("ERROR: I/O problem occurred while " +
                "accessing the source file");
            System.out.println("2");
            return;
        }

    }
}
