import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class AskForPower extends Behaviour {
    private myAgent agent;

    public AskForPower(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setProtocol("needPower");
        for (String s:agent.getListAgent()){
            if(agent.getBreakersForComm().contains(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)))){

            }
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
