import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Map;

public class turnOffBreaker extends OneShotBehaviour {
    private myAgent agent;
    private String name;
    private Map<String, Boolean> states;
    public turnOffBreaker(myAgent agent, String agentName) {
        this.agent = agent;
        this.name = agentName;
    }
    @Override
    public void action() {
        states = agent.getStateOfBreakers();
        states.put(agent.getMapLinksBreaker().get(agent.getLinksMap().get(name)),true);
        agent.setStateOfBreakers(states);
        System.out.println("I.m " + agent.getLocalName()+ " find Kz on "+agent.getLinksMap().get(name) + " turn off " + agent.getMapLinksBreaker().get(agent.getLinksMap().get(name)));
        agent.setState(false);
    }
}
