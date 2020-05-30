import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForInform extends Behaviour {
    private boolean doneB = false;
    private myAgent agent;
    private String[] agentList;

    public WaitForInform(myAgent agent, DataStore ds) {
        this.agent=agent;
        setDataStore(ds);
        this.agentList = (String[]) ds.get("agentList");
    }


    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and
                (MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchProtocol("AgentInformation"));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
            this.agentList = msg.getContent().split("/");
            System.out.println("I'm "+ agent.getLocalName());
            for (String s: agentList){
                if (!s.equals(agent.getLocalName())){
                 //   System.out.println("Find "+s);
                }
            }
            doneB=true;
        }
        else {
            block();
        }


    }

    @Override
    public int onEnd() {
        getDataStore().put("agentList",agentList);
        agent.addBehaviour(new SearchForNeighbours(agent,getDataStore()));
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
