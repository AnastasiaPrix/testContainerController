import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.Set;

public class startAgent extends Agent {
    private DFAgentDescription[] agents;

    public void setup() {

        super.setup();
        ContainerController cc = this.getContainerController();
        AgentController ac;
        try {
            loadOntology l = new loadOntology();
            Set<OWLNamedIndividual> setAgents = l.load();
            Set<OWLNamedIndividual> setProtection = l.loadProtection();
            for (OWLNamedIndividual i: setAgents){
               String name =  i.getIRI().toString().split("#")[1];
                ac = cc.createNewAgent(name, "myAgent", null);
                ac.start();
            }
            for (OWLNamedIndividual j: setProtection){
                String name =  j.getIRI().toString().split("#")[1];
                ac = cc.createNewAgent(name, "ProtectAgent", null);
                ac.start();
            }
        } catch (OWLOntologyCreationException | StaleProxyException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(19000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

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
        addBehaviour(new InformForAgent(this,agents));
    }
}
