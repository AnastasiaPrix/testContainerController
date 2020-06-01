import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class needPower extends Behaviour {
    private ACLMessage rep;
    private boolean doneB = false;
    private int k=0;
    private myAgent agent;
    private Map<String, Boolean> stateOfBreakers = new HashMap<>();
    private List<String> agents= new ArrayList<>();
    public needPower(myAgent agent) {
        this.agent=agent;
    }

    @Override
    public void action() {
            for (Boolean b : agent.getStateOfBreakers().values()) {
                if (b) {
                    k++;
                }
            }
            if (k == agent.getStateOfBreakers().size()){
            rep = new ACLMessage(ACLMessage.PROPOSE);
            rep.setProtocol("needPower");
            System.out.println("I'm "+ agent.getLocalName() + " needPower!!!");
            for (String s : agent.getListAgent()) {
                rep.addReceiver(new AID(s, false));
            }
            agent.send(rep);
            }
        doneB =true;
    }

    @Override
    public int onEnd() {
        //System.out.println("I'm "+ agent.getLocalName() + " needPower!!!");
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
