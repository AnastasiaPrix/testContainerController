import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitForAnswerFromNeighbours extends Behaviour {
    private myAgent agent;
    private Map<String, String> linksMap= new HashMap<>();
    private boolean doneB=false;
    private List<String> agentList = new ArrayList<>();
    private String[] agents;
    private int k =1;

    public WaitForAnswerFromNeighbours(myAgent agent, DataStore ds) {
        this.agent = agent;
        setDataStore(ds);
        this.agents = (String[]) ds.get("agentList");
    }



    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and
                (MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchProtocol("Search"));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
            k++;
            for (String s: msg.getContent().split("/")){
                if (agent.getListLinks().contains(s)){
                    ACLMessage ans = msg.createReply();
                    ans.setPerformative(ACLMessage.CONFIRM);
                    agent.send(ans);
                    agentList.add(msg.getSender().getLocalName());
                    linksMap.put(msg.getSender().getLocalName(),s);
                    System.out.println("I'm "+ agent.getLocalName()+ " have message From "+ msg.getSender().getLocalName());
                }
            }
            if (k==agents.length){
                doneB=true;
            }

        }
        else{
            block();
        }
    }
    @Override
    public boolean done() {
        return doneB;
    }
    @Override
    public int onEnd() {
        agent.setListAgent(agentList);
        agent.setLinksMap(linksMap);
        System.out.println("endWaiting");
        agent.addBehaviour(new checkParams(agent));
        return super.onEnd();
    }
}
