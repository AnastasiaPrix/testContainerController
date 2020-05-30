import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendInformationAboutKz extends OneShotBehaviour {
    private Agent agent;
    private String agentName;

    public SendInformationAboutKz(Agent agent, String agentName ) {
        this.agent = agent;
        this.agentName = agentName;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.FAILURE);
        msg.setProtocol("KZ");
        msg.addReceiver(new AID(agentName,false));
        agent.send(msg);

    }
}
