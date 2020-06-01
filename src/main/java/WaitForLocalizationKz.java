import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaitForLocalizationKz extends Behaviour {
    private myAgent agent;
    private Map<String, Boolean> states;
    private List<String> agents = new ArrayList<>();
    private boolean doneB = false;
    public WaitForLocalizationKz(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate
                .and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchProtocol("findKZ"));
        ACLMessage msg = agent.receive(mt);
        if (msg != null) {
            states = agent.getStateOfBreakers();
           // states.put(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())),false);
            agent.setStateOfBreakers(states);
            agent.setState(false);
            System.out.println("I.m " + agent.getLocalName()+ "  turn on " + agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())));
            doneB = true;
        }

    }

    @Override
    public int onEnd() {
        ACLMessage msg2 = new ACLMessage(ACLMessage.CANCEL);
        for (String s: agent.getListAgent()){
            msg2.addReceiver(new AID(s,false));
        }
        agent.send(msg2);
        agent.addBehaviour(new WaitForPowerRequest(agent));
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }

}
