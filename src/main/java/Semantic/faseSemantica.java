package Semantic;

import Semantic.TreeAST.ASTnode;
import Semantic.TreeAST.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class faseSemantica {

    ASTnode program = new Expression();

    Map<String,Integer> symbolTable = new HashMap<>();
    public void buildNode(String tokenName){
        switch(tokenName){
            case "ASIGNACION":

        }
    }
    public void  buildAssignment(){

    }
}
