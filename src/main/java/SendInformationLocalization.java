import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendInformationLocalization extends OneShotBehaviour {
    private myAgent agent;
    private String name;
    public SendInformationLocalization(myAgent agent, String name) {
        this.agent = agent;
        this.name = name;
    }

    @Override
    public void action() {
        if (name!=null) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setProtocol("findKZ");
            msg.addReceiver(new AID(name, false));
            agent.send(msg);
        }
        else {
            ACLMessage msg2 = new ACLMessage(ACLMessage.CANCEL);
            for (String s: agent.getListAgent()){
                msg2.addReceiver(new AID(s,false));
            }
            agent.send(msg2);
            agent.addBehaviour(new WaitForPowerRequest(agent));
            agent.addBehaviour(new CheckPower(agent));
        }

    }
}
