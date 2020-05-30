import jade.core.behaviours.Behaviour;

import java.util.HashMap;
import java.util.Map;

public class CheckPower extends Behaviour {
    private myAgent agent;
    private Map<String,Boolean> breakerMap = new HashMap<>();
    private boolean doneB = false;

    public CheckPower(myAgent agent) {
        this.agent = agent;
    }


    @Override
    public void action() {
        breakerMap = agent.getStateOfBreakers();
        int k = 0;
        for (boolean b: breakerMap.values()){
            if (b){
                k++;
            }
        }
        if (k==breakerMap.size()){
            System.out.println("I'm "+ agent.getLocalName()+ " need Power!!!!!");
            doneB= true;

        }

    }

    @Override
    public boolean done() {
        return doneB;
    }
}
