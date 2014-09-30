package com.extrapolo;

import com.extrapolo.graphplan.control.Util;
import com.extrapolo.graphplan.model.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GroundTest {

    public static String groundType(String ground, HashMap facts) {
        String[] literals = ground.split("-");
        String result = literals[0];
        for (int x = 1; x < literals.length; x++) {
            String literal = literals[x];
            result += "-" + facts.get(literal);
        }
        return result;
    }

    public static String getGroundLiteral(String literal, State state, HashMap facts) {
        String[] literaisGround = state.getId().split("-");
        for (int x = 1; x < literaisGround.length; x++) {
            String ground = literaisGround[x];
            String groundType = (String) facts.get(ground);
            if (groundType.equals(literal)) {
                return ground;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        HashMap facts = new HashMap<String, String>();

        facts.put("C1", "Cargo");
        facts.put("C2", "Cargo");
        facts.put("JFK", "Airport");
        facts.put("SFO", "Airport");
        facts.put("P1", "Plane");
        facts.put("P2", "Plane");

        ArrayList<State> estados = new ArrayList<State>();
        estados.add(State.addOrCreate(Util.ground("At(C1,SFO)")));
        estados.add(State.addOrCreate(Util.ground("At(C2,JFK)")));
        estados.add(State.addOrCreate(Util.ground("At(P1,SFO)")));
        estados.add(State.addOrCreate(Util.ground("At(P2,JFK)")));

        HashMap params = new HashMap<String, String>();
        params.put("c", "Cargo");
        params.put("p", "Plane");
        params.put("a", "Airport");

        ArrayList<State> precond = new ArrayList<State>();
        precond.add(State.addOrCreate(Util.ground("At(c,a)")));
        precond.add(State.addOrCreate(Util.ground("At(p,a)")));

        ArrayList<String> allLiterais = new ArrayList<String>();
        ArrayList<String> ungroundPreconds = new ArrayList<String>();
        HashSet sameUnground = new HashSet<String>();
        // partindo das précondições
        for (State unground : precond) {
            String[] literals = unground.getId().split("-");
            String actionName = literals[0];
            // para cada literal a, c..., qual seu tipo?
            String result = actionName;
            for (int x = 1; x < literals.length; x++) {
                String literal = literals[x];
                if (allLiterais.contains(literal)) {
                    // pegar literais unground repetidas
                    sameUnground.add(literal);
                } else {
                    allLiterais.add(literal);
                }
                // getParamsFromAction()
                // se c do literal = c do parametro
                String type = (String) params.get(literal);
                if (type != null) {
                    // sei seu tipo de objeto
                    result += "-";
                    result += type;
                }
            }
            // unground: At-Block-Table
            ungroundPreconds.add(result);
        }
        // qual tipo de literal deve ter igualdade entre as precondições
        for (Object same : sameUnground) {
            String literalType = (String) params.get(same);
            sameUnground.remove(same);
            sameUnground.add(literalType);
        }

        // tenho literais ungrounds e quais devem ser iguais, procurar possíveis
        ArrayList<State> possible = new ArrayList<State>();
        for (State ground : estados) {

            for (String ungroundPrecond : ungroundPreconds) {
                // ground <> unground
                String[] groundLiterals = ground.getId().split("-");
                String[] ungroundLiterals = ungroundPrecond.split("-");
                // same action
                if (groundLiterals[0].equals(ungroundLiterals[0]) && groundLiterals.length == ungroundLiterals.length) {
                    String groundType = groundType(ground.getId(), facts);
                    // estados = precondicao unground                
                    if (groundType.equals(ungroundPrecond)) {
                        possible.add(ground);
                    }
                }
            }
            
            // é preciso satisfazer a igualdade dos literais  
            HashSet<String> unrepeted = new HashSet<String>();
            for (Object same : sameUnground) {
              //  for (State possibleState : possible) {
                    for (String l : ground.getId().split("-")) {
                        String type = (String) facts.get(l);
                        if (type != null && type.equals(same)) {
                            // lista dos estados por igualdade de literal                                                     
                            unrepeted.add(ground.getId());                              
                        }
                    }
                //}
            }
            for (String notRepeated : unrepeted){
                System.out.println(notRepeated);
            }
        }

    }
}
