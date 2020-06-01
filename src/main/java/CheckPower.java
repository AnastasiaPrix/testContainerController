import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.Map;

public class CheckPower extends Behaviour {
    private myAgent agent;
    private Map<String,Boolean> breakerMap = new HashMap<>();
    private boolean doneB = false;
    private int counter=0;
    private int k=0;
    private int k2=0;
    private boolean flag;
    private int kR =0;


    public CheckPower(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void onStart() {
        if (!agent.isHaveEnergy()){
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setProtocol("doHaveEnergy");
            msg.setContent(agent.getLocalName());
            for (String s : agent.getListAgent()) {
                if (!agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)))) {
                    msg.addReceiver(new AID(s, false));
                    kR++;
                }
            }
            agent.send(msg);
        }
        super.onStart();
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM),
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
                        MessageTemplate.MatchProtocol("haveEnergy")));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
            k++;
            if (msg.getPerformative()==ACLMessage.DISCONFIRM){
                counter++;
            }
        }
        if (k==kR){
            if (k==counter ){
                flag = true;
            }
            doneB = true;
        }
    }

    @Override
    public int onEnd() {
        for (Boolean b : agent.getStateOfBreakers().values()) {
            if (b) {
                k2++;
            }
        }
        if ((flag||(k2==agent.getStateOfBreakers().size()))&& !agent.isHaveEnergy()){
            agent.addBehaviour(new WaitForAskPower(agent));
            agent.addBehaviour(new AskForPower(agent));
            System.out.println("I'm "+ agent.getLocalName()+" need Power");
            agent.setNeedPower(true);
        }
        else {
            System.out.println("I'm "+ agent.getLocalName()+" don't need Power");
            agent.addBehaviour(new WaitForAskPower(agent));

        }
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
