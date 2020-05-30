import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class SearchForNeighbours extends Behaviour {
    private myAgent agent;
    private String[] agentList;
    private boolean doneBeh= false;

    public SearchForNeighbours(myAgent agent, DataStore ds) {
        this.agent= agent;
        setDataStore(ds);
        this.agentList = (String[]) ds.get("agentList");
    }

    @Override
    public int onEnd() {
        agent.addBehaviour(new WaitForAnswerFromNeighbours(agent,getDataStore()));
        return super.onEnd();
    }

    @Override
    public void action() {
        System.out.println("Starting Search");
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setProtocol("Search");
        String content = "";
        for (String s: agent.getListLinks()){
            content = content+s+"/";
        }
        msg.setContent(content);
        for (String a: agentList){
            if (!a.equals(agent.getLocalName())) {
                msg.addReceiver(new AID(a,false));
            }
        }
        agent.send(msg);
        doneBeh=true;
    }

    @Override
    public boolean done() {
        return doneBeh;
    }
}
