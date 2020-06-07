import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AskForPower extends Behaviour {
    private myAgent agent;
    private int kR=0;
    private int k=0;
    private int counter=0;
    private boolean doneB=false;
    public AskForPower(myAgent agent) {
        this.agent = agent;
    }
    @Override
    public void onStart() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setProtocol("needPower");
        msg.setContent(agent.getLocalName());
        for (String s: agent.getListAgent()){
            if (!agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)))|| agent.getBreakersForComm().contains(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)))) {
                msg.addReceiver(new AID(s, false));
                kR++;
            }
        }
        agent.send(msg);
        super.onStart();
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL),
                MessageTemplate.MatchProtocol("cantSend")),
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                        MessageTemplate.MatchProtocol("sendPower")));
        ACLMessage msg = agent.receive(mt);

        if (msg!=null){
                k++;
                // System.out.println(agent.getLocalName()+ " have messege from " +msg.getSender() + " " + msg.getPerformative() + " " + kR + " "+ k);
                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    counter++;
                }
        }
        if (k==kR){
            doneB = true;
        }
    }

    @Override
    public int onEnd() {
        if (counter>0){
            System.out.println("I'm "+ agent.getLocalName()+ " restored power");
        }
        else{
            System.out.println("I'm "+ agent.getLocalName()+ " cant restored power");
        }

        return super.onEnd();
    }

    @Override
    public boolean done() {
        return doneB;
    }
}
