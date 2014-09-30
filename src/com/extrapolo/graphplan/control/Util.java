package com.extrapolo.graphplan.control;

import com.extrapolo.graphplan.model.Action;
import com.extrapolo.graphplan.model.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Util {

    public static String readFile(File file) throws IOException {
        String lineSep = System.getProperty("line.separator");
        BufferedReader br = new BufferedReader(new FileReader(file.toString()));
        String nextLine = "";
        StringBuilder sb = new StringBuilder();
        while ((nextLine = br.readLine()) != null) {
            sb.append(nextLine);
            sb.append(lineSep);
        }
        return sb.toString();
    }

    public static String ground(String name) {
        return name.replace("(", "-").replace(",", "-").replace(")", "");
    }

    public static Planner parsePlanFromADLString(String planString) {
        try {
            planString = planString.replace("\n", " ").replaceAll("\\s+", "");
            
            // define fatos
            HashMap facts = new HashMap<String, String>();            
            Pattern ops = Pattern.compile("Facts\\((.*?)\\)I");
            Matcher matcher = ops.matcher(planString);
            if (matcher.find()) {
                String factString = matcher.group(1);            
                for (String fato : factString.split("\\^")) {
                    String[] factSet = fato.replace("(", ":").replace(")", "").split(":");
                    facts.put(factSet[1], factSet[0]);
                }
            }
            
            // define estado inicial
            ArrayList<State> inicialStates = new ArrayList<State>();
            String initState = "";
            ops = Pattern.compile("Init\\((.*?)\\)G");
            matcher = ops.matcher(planString);
            if (matcher.find()) {
                initState = matcher.group(1);
            }
            for (String estado : initState.split("\\^")) {
                inicialStates.add(State.addOrCreate(ground(estado)));
            }

            // define estado final
            ArrayList<State> finalStates = new ArrayList<State>();
            String finalState = "";
            ops = Pattern.compile("Goal\\((.*?)\\)A");
            matcher = ops.matcher(planString);
            if (matcher.find()) {
                finalState = matcher.group(1);
            }
            for (String estado : finalState.split("\\^")) {
                finalStates.add(State.addOrCreate(ground(estado)));
            }

            // define ações
            ArrayList<Action> actions = new ArrayList<Action>();
            ops = Pattern.compile("Action\\((.*?EFFECT.*?\\)\\))");
            matcher = ops.matcher(planString);
            while (matcher.find()) {
                String actionString = matcher.group(1);
                // parse action string
                ops = Pattern.compile("(\\w*)\\(?(.*)\\)PRECOND:(.*)EFFECT:(.*)");
                Matcher actionParser = ops.matcher(actionString);
                if (actionParser.find()) {
                    String actionName = actionParser.group(1);
                    String params = actionParser.group(2);
                    String preconds = actionParser.group(3);
                    String effects = actionParser.group(4);
                    // instancio
                    Action action = new Action(actionName);
                    // atribui parametros
                    HashMap paramsMap = new HashMap<String, String>();
                    for (String param : params.split(",")) {
                        String value[] = param.split(":");
                        paramsMap.put((String) value[0], (String) value[1]);
                    }
                    action.setParams(paramsMap);
                    // atribui precondicoes
                    ArrayList precondicoes = new ArrayList<State>();
                    for (String precondicao : preconds.split("\\^")) {
                        precondicoes.add(State.addOrCreate(ground(precondicao)));
                    }
                    action.setPrecondition(precondicoes);
                    // atribui efeitos                
                    ArrayList efeitos = new ArrayList<State>();
                    for (String efeito : effects.split("\\^")) {
                        efeitos.add(State.addOrCreate(ground(efeito)));
                    }
                    action.setEffects(efeitos);
                    // "parseou"
                    actions.add(action);
                }

            }
            return new Planner(facts, inicialStates, finalStates);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Erro ao interpretar ADL.", "Opz!", JOptionPane.ERROR_MESSAGE);
        }
        return null;

    }
// retorna combinações entre listas

    public static <T> List<List<T>> combinate(List<List<T>> uncombinedList) {
        List<List<T>> list = new ArrayList<List<T>>();
        int index[] = new int[uncombinedList.size()];
        int combinations = combinations(uncombinedList) - 1;
        for (int i = 0; i < index.length; i++) {
            index[i] = 0;
        }
        List<T> combination = new ArrayList<T>();
        for (int m = 0; m < index.length; m++) {
            combination.add(uncombinedList.get(m).get(index[m]));
        }
        list.add(combination);
        for (int k = 0; k < combinations; k++) {
            combination = new ArrayList<T>();
            boolean found = false;
            for (int l = index.length - 1; l >= 0 && found == false; l--) {
                int currentListSize = uncombinedList.get(l).size();
                if (index[l] < currentListSize - 1) {
                    index[l] = index[l] + 1;
                    found = true;
                } else {
                    index[l] = 0;
                }
            }
            for (int m = 0; m < index.length; m++) {
                combination.add(uncombinedList.get(m).get(index[m]));
            }
            list.add(combination);
        }
        return list;
    }

    private static <T> int combinations(List<List<T>> list) {
        int count = 1;
        for (List<T> current : list) {
            count = count * current.size();
        }
        return count;
    }
}
