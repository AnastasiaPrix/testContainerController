import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;

public class WaitForTurnOn extends Behaviour {
    private myAgent agent;
    private boolean doneB= false;

    public WaitForTurnOn(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CANCEL);
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
            //System.out.println("I'm "+ agent.getLocalName()+ " find turn on");
            ACLMessage rep = new ACLMessage(ACLMessage.CANCEL);
            for (String s: agent.getListAgent()){
                if (!s.equals(msg.getSender().getLocalName())){
                    rep.addReceiver(new AID(s,false));
                }
            }
            agent.send(rep);
            doneB =true;
        }
        else block();

    }

    @Override
    public int onEnd() {
        agent.addBehaviour(new WaitForPowerRequest(agent));
//        for (Map.Entry m :agent.getStateOfBreakers().entrySet() ){
//            System.out.println("Hello. My name is "+agent.getLocalName() +"  "+ m.getKey()+ "_ " +m.getValue());
//        }
      //  if (!agent.isHaveEnergy()) {
           agent.addBehaviour(new CheckPower(agent));
     //   }
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
