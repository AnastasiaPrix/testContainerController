import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class InformForAgent extends Behaviour {
    private boolean doneB = false;
    private Agent agent;
    private String content="";
    private DFAgentDescription[] agents;

    public InformForAgent(Agent agent, DFAgentDescription[] agentList) {
        this.agent = agent;
        for (DFAgentDescription a: agentList){
            this.content = this.content +a.getName().getLocalName()+"/";
        }
        this.agents = agentList;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(content);
        msg.setProtocol("AgentInformation");
        for (DFAgentDescription a: agents){
            msg.addReceiver(a.getName());
        }
        agent.send(msg);
        doneB = true;
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
