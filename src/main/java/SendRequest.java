import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SendRequest extends Behaviour {
    private myAgent agent;
    private boolean doneB =false;
    private String name;

    public SendRequest(myAgent agent, String name) {
        this.agent = agent;
        this.name = name;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setProtocol("hasKZ");
        for (String s:agent.getListAgent()){
            if (!s.equals(name)) {
                msg.addReceiver(new AID(s, false));
            }
        }
        agent.send(msg);
        doneB=true;

    }

    @Override
    public boolean done() {
        return doneB;
    }
}
