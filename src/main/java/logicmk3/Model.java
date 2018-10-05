package logicmk3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Model {
    private List<Rule> rules = new ArrayList<>();
    private Set<String> facts =  new TreeSet<>();

    public Set<String> getFacts() {
        return facts;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void eval(){
        int currentFactCount;
        for (int i = 0; i < getRules().size(); i++){
            int initialFactsCount = getFacts().size();
            for (Rule rule : getRules()){
                rule.eval();
                currentFactCount = getFacts().size();
                if (currentFactCount != initialFactsCount) break;
            }

        }
    }
}
