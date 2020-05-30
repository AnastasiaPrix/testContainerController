import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForRequest extends Behaviour {
    private myAgent agent;
    private String sender;
    public WaitForRequest(myAgent agent) {
        this.agent= agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.
                and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),MessageTemplate.MatchProtocol("findKZ")),
                MessageTemplate.or(MessageTemplate.
                and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchProtocol("hasKZ")),MessageTemplate.
                                and(MessageTemplate.MatchPerformative(ACLMessage.FAILURE),
                                        MessageTemplate.MatchProtocol("KZ"))));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
            if (msg.getPerformative()==ACLMessage.REQUEST) {
                if (agent.isState()) {
                    agent.addBehaviour(new SendRequest(agent, msg.getSender().getLocalName()));
                    sender= msg.getSender().getLocalName();
                } else {
                    agent.addBehaviour(new SendInformationAboutKz(agent,msg.getSender().getLocalName()));
                    agent.addBehaviour(new turnOffBreaker(agent, msg.getSender().getLocalName()));
                }
            }
            else if (msg.getPerformative()==ACLMessage.FAILURE) {
                agent.addBehaviour(new turnOffBreaker(agent,msg.getSender().getLocalName()));
                agent.addBehaviour(new SendInformationLocalization(agent,sender));
            }
            else if(msg.getPerformative()==ACLMessage.INFORM){
                agent.addBehaviour(new SendInformationLocalization(agent,sender));
                agent.setState(false);
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
