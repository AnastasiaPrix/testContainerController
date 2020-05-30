import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendInformationLocalization extends OneShotBehaviour {
    private Agent agent;
    private String name;
    public SendInformationLocalization(Agent agent, String name) {
        this.agent = agent;
        this.name = name;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("findKZ");
        msg.addReceiver(new AID(name,false));
        agent.send(msg);

    }
}
