import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForAskPower extends Behaviour {
    private myAgent agent;
    private String name;
    private ACLMessage rep;
    private boolean f;
    private String breaker;
    ACLMessage msg2;

    public WaitForAskPower(myAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),MessageTemplate.MatchProtocol("needPower"));
        ACLMessage msg = agent.receive(mt);
        if (msg!=null) {
            name = msg.getContent();
            for (String s : agent.getStateOfBreakers().keySet()) {
                if (agent.getBreakersForComm().contains(s)) {
                    f = true;
                }
            }
            if (f && !agent.isNeedPower() && agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                if (agent.getBreakersForComm().contains(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                    rep = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    rep.setProtocol("sendPower");
                    rep.addReceiver(new AID(name, false));
                    agent.getStateOfBreakers().put(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())), false);
                    agent.send(rep);
                } else {
                    rep = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    rep.setProtocol("cantSend");
                    rep.addReceiver(new AID(name, false));
                    agent.send(rep);
                }
            }
            else if (f && !agent.isNeedPower() && !agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                    rep = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    rep.setProtocol("sendPower");
                    rep.addReceiver(new AID(name, false));
                    agent.send(rep);
            }
            else if (!f && !agent.isNeedPower() && !agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                rep = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                rep.setProtocol("sendPower");
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            } else if (!f && !agent.isNeedPower() && agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                rep = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                rep.setProtocol("cantSend");
                rep.addReceiver(new AID(name, false));
                agent.send(rep);
            } else if (f && agent.isNeedPower() && agent.getListAgent().size() > 1 && !agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                boolean f3 = false;
                for (String s : agent.getListAgent()) {
                    boolean f1 = agent.getBreakersForComm().contains(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)));
                    if (!s.equals(msg.getSender().getLocalName()) && (!agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s))) || f1)) {
                        msg2 = new ACLMessage(ACLMessage.PROPOSE);
                        msg2.setProtocol("needPower");
                        msg2.setContent(msg.getContent());
                        msg2.addReceiver(new AID(s, false));
                        f3 = true;
                    }
                }
                if (!f3) {
                    msg2 = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    msg2.setProtocol("cantSend");
                    msg2.addReceiver(new AID(name, false));
                }
                agent.send(msg2);
            } else if (f && agent.isNeedPower() && agent.getListAgent().size() > 1 && agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())))) {
                boolean f2 = agent.getBreakersForComm().contains(agent.getMapLinksBreaker().get(agent.getLinksMap().get(msg.getSender().getLocalName())));
                boolean f3 = false;
                if (f2) {
                    for (String s : agent.getListAgent()) {
                        boolean f1 = agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s)));
                        if (!s.equals(msg.getSender().getLocalName()) && (!agent.getStateOfBreakers().get(agent.getMapLinksBreaker().get(agent.getLinksMap().get(s))) || f1)) {
                            msg2 = new ACLMessage(ACLMessage.PROPOSE);
                            msg2.setProtocol("needPower");
                            msg2.setContent(msg.getContent());
                            msg2.addReceiver(new AID(s, false));
                            f3 = true;
                        }
                    }
                    if (!f3) {
                        msg2 = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                        msg2.setProtocol("cantSend");
                        msg2.addReceiver(new AID(name, false));
                    }
                } else {
                    msg2 = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    msg2.setProtocol("cantSend");
                    msg2.addReceiver(new AID(name, false));
                }
                agent.send(msg2);
            }
        }

        }

    @Override
    public boolean done() {
        return false;
    }
}
