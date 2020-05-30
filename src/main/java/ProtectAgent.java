import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class ProtectAgent extends Agent {
    DFAgentDescription[] agents;
    @Override
    protected void setup() {
        super.setup();
        ServiceDescription sd=new ServiceDescription();
        DFAgentDescription dfd=new DFAgentDescription();
        sd.setName("search");
        sd.setType("search");
        dfd.addServices(sd);
        try {
            agents = DFService.search(this,dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
       // addBehaviour(new checkParams(this));

    }
}
