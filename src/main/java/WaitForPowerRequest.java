import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForPowerRequest extends Behaviour {
    private myAgent agent;
    private ACLMessage rep;
    private String name;
    private boolean f=false;
    public WaitForPowerRequest(myAgent agent) {
        this.agent=agent;
    }

    @Override
    public void action() {

        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchProtocol("doHaveEnergy"));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null){
           // System.out.println(" I'm "+ agent.getLocalName() +" have power messege");
            name = msg.getContent();
            for (boolean s: agent.getStateOfBreakers().values()) {
                if (s) {
                    f = true;
                }
            }
            if (f && !agent.isHaveEnergy()){
                rep = new ACLMessage(ACLMessage.DISCONFIRM);
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            }
            else if((f && agent.isHaveEnergy() && !agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName()))))){
                rep = new ACLMessage(ACLMessage.CONFIRM);
                rep.setProtocol("haveEnergy");
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            }
            else if((f && agent.isHaveEnergy() && agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName()))))){
                rep = new ACLMessage(ACLMessage.DISCONFIRM);
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            }
            else if (!f && agent.isHaveEnergy()){
                rep = new ACLMessage(ACLMessage.CONFIRM);
                rep.setProtocol("haveEnergy");
                name = msg.getContent();
                rep.addReceiver(new AID(name, false));
               // System.out.println(agent.getLocalName()+ " yes, i have energy "+ name);
                agent.send(rep);
            }
            else if (!f && !agent.isHaveEnergy() && agent.getListAgent().size()>1){
                ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                msg2.setProtocol("doHaveEnergy");
                msg2.setContent(msg.getContent());
                for (String s: agent.getListAgent()){
                    if (!s.equals(msg.getSender().getLocalName())) {
                      //  System.out.println("I'm "+ agent.getLocalName()+ " have mes from "+ msg.getSender().getLocalName()+ " send to " + s);
                        msg2.addReceiver(new AID(s, false));
                    }
                }
                agent.send(msg2);
            }
            else if (!f && !agent.isHaveEnergy() && agent.getListAgent().size()==1){
                rep = new ACLMessage(ACLMessage.DISCONFIRM);
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            }
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
