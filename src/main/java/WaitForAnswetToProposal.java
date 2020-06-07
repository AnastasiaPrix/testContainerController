import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.Map;

public class WaitForAnswetToProposal extends Behaviour {
    private myAgent agent;
    private String masContent[];
    private String newContent = null;
    private int k = 0;
    private boolean doneB=false;
    private String num="";
    private String prot;
    private Map<String,Boolean> brMap = new HashMap<>();
    public WaitForAnswetToProposal(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL),
                MessageTemplate.MatchProtocol("cantSend2")),
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                        MessageTemplate.MatchProtocol("sendPower2")));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
                masContent = msg.getContent().split("/");
                newContent = masContent[0];
                if (msg.getPerformative()==ACLMessage.ACCEPT_PROPOSAL){
                    brMap=agent.getStateOfBreakers();
                    for (String b: brMap.keySet()){
                        if (agent.getStateOfBreakers().get(b)){
                            if (b.equals(agent.getLinksMap().get(msg.getSender().getLocalName()))||b.equals(agent.getLinksMap().get(masContent[masContent.length-2]))){
                                brMap.put(b,false);
                            }
                        }
                    }
                    agent.setStateOfBreakers(brMap);
                    prot="sendPower";
                }
                else {
                    prot="cantSend";
                }
                if (masContent.length>2) {
                    for (int i = 1; i < masContent.length - 1; i++) {
                        newContent = newContent + "/" + masContent[i];
                        num="2";
                    }
                }
            ACLMessage rep = new ACLMessage(msg.getPerformative());
            rep.setContent(newContent);
            rep.setProtocol(prot+num);
            rep.addReceiver(new AID(masContent[masContent.length-2],false));
            agent.send(rep);
                //doneB=true;

            }
        }

    @Override
    public boolean done() {
        return doneB;
    }
}
