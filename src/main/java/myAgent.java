import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.*;

public class myAgent extends Agent {
    private DFAgentDescription[] agents;
    private boolean needPower = false;
    private  boolean state;
    private boolean haveEnergy;
    private Map<String, String> linksMap = new HashMap<>();
    private Map<String, String> mapLinksBreaker = new HashMap<>();
    private List<String> listLinks = new ArrayList<>();
    private Map<String, Boolean> stateOfBreakers = new HashMap<>();
    private List<String> listAgent = new ArrayList<>();
    private List<String> breakersForComm = new ArrayList<>();

    public List<String> getBreakersForComm() {
        return breakersForComm;
    }

    public void setBreakersForComm(List<String> breakersForComm) {
        this.breakersForComm = breakersForComm;
    }

    public boolean isNeedPower() {
        return needPower;
    }

    public void setNeedPower(boolean needPower) {
        this.needPower = needPower;
    }

    public void setup() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        super.setup();

        ServiceDescription sd=new ServiceDescription();
        DFAgentDescription dfd=new DFAgentDescription();
        sd.setName("search");
        sd.setType("search");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        loadOntology l = null;
        try {
            l = new loadOntology();
            this.stateOfBreakers = l.getВreakersState(getLocalName());
            this.haveEnergy = l.getAgentPower(getLocalName());
            for ( String i: l.getLinksForAgent(getLocalName())){
                this.listLinks.add(i);
                for (String s : l.getBreakerForLinks(i)){
                    if (this.stateOfBreakers.containsKey(s)){
                        this.mapLinksBreaker.put(i,s);
                    }
                }
            }

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        for ( String b: stateOfBreakers.keySet()){
            if (stateOfBreakers.get(b)){
                breakersForComm.add(b);
            }
        }
        for (Map.Entry m :stateOfBreakers.entrySet() ){
            System.out.println("Hello. My name is "+getLocalName() +"  "+ m.getKey()+ "_ " +m.getValue());

        }
        String[] agentList = null;
        DataStore ds = new DataStore();
        ds.put("agentList", agentList);
        addBehaviour(new WaitForInform(this, ds));
    }

    public Map<String, String> getLinksMap() {
        return linksMap;
    }

    public void setLinksMap(Map<String, String> linksMap) {
        this.linksMap = linksMap;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Map<String, Boolean> getStateOfBreakers() {
        return stateOfBreakers;
    }


    public Map<String, String> getMapLinksBreaker() {
        return mapLinksBreaker;
    }

    public void setMapLinksBreaker(Map<String, String> mapLinksBreaker) {
        this.mapLinksBreaker = mapLinksBreaker;
    }

    public void setStateOfBreakers(Map<String, Boolean> stateOfBreakers) {
        this.stateOfBreakers = stateOfBreakers;
    }


    public List<String> getListLinks() {
        return listLinks;
    }

    public void setListLinks(List<String> listLinks) {
        this.listLinks = listLinks;
    }

    public List<String> getListAgent() {
        return listAgent;
    }

    public void setListAgent(List<String> listAgent) {
        this.listAgent = listAgent;
    }

    public boolean isHaveEnergy() {
        return haveEnergy;
    }

    public void setHaveEnergy(boolean haveEnergy) {
        this.haveEnergy = haveEnergy;
    }
}

