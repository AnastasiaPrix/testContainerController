import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import java.io.File;
import java.util.*;

public class loadOntology {


    private File file;
    public OWLOntology ontology;
    private OWLOntologyManager manager;
    private  String ns;
    private  OWLDataFactory df;

    public loadOntology() throws OWLOntologyCreationException {
        this.file= new File("src\\main\\resources\\ontNetwork.owl");
        this.manager = OWLManager.createOWLOntologyManager();
        this.ontology = manager.loadOntologyFromOntologyDocument(new File("src\\main\\resources\\ontNetwork.owl"));
        this.ns= ontology.getOntologyID().getOntologyIRI().get().toString() + "#";
        this.df = manager.getOWLDataFactory();
    }


    public Set<OWLNamedIndividual> load() throws OWLOntologyCreationException {

        OWLReasonerFactory reasonerF = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerF.createReasoner(ontology);
        OWLClass RPClass = df.getOWLClass(IRI.create(ns+"RP"));
        OWLClass TPClass = df.getOWLClass(IRI.create(ns+"TP"));
        Set<OWLNamedIndividual> setRP = getDataFromOntology.getIndividualsOfClass(RPClass,reasoner);
        Set<OWLNamedIndividual> setTP = getDataFromOntology.getIndividualsOfClass(TPClass,reasoner);
        for (OWLNamedIndividual i: setRP){
            setTP.add(i);
        }
        return setTP;
    }
    public Set<OWLNamedIndividual> loadProtection() throws OWLOntologyCreationException {

        OWLReasonerFactory reasonerF = new StructuralReasonerFactory();
        OWLReasoner reasoner = reasonerF.createReasoner(ontology);
        OWLClass ProtClass = df.getOWLClass(IRI.create(ns+"Protection"));
        Set<OWLNamedIndividual> setProt = getDataFromOntology.getIndividualsOfClass(ProtClass,reasoner);
        return setProt;
    }

    public List<String> getLinksForAgent(String AgentName){

        List<String> listOfLinks = new ArrayList<>();
        OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(ns+AgentName));
       // OWLObjectProperty hasConnection = df.getOWLObjectProperty(IRI.create(ns+"hasConnection"));
        OWLObjectProperty hasConnection = df.getOWLObjectProperty(IRI.create(ns+"hasLine"));
        Collection<OWLIndividual> linksCollection = getDataFromOntology.getIndividualsFromProperty(ind,ontology,hasConnection);
        for (OWLIndividual i: linksCollection){
            listOfLinks.add(i.toStringID().split("#")[1]);
        }
        return listOfLinks;
    }

    public List<String> getBreakerForLinks(String Name){

        List<String> listOfBreaker = new ArrayList<>();
        OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(ns+Name));
        OWLObjectProperty hasConnection = df.getOWLObjectProperty(IRI.create(ns+"hasBreaker"));
        Collection<OWLIndividual> linksCollection = getDataFromOntology.getIndividualsFromProperty(ind,ontology,hasConnection);
        for (OWLIndividual i: linksCollection){
            listOfBreaker.add(i.toStringID().split("#")[1]);
        }
        return listOfBreaker;
    }
    public boolean getAgentPower(String Name){
        boolean listOfPower=false;
        OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(ns+Name));
        OWLDataProperty hasEnergy = df.getOWLDataProperty(IRI.create(ns+"hasEnergy"));
        Collection<OWLLiteral> linksCollection = getDataFromOntology.getDataPropertyValue(ind,ontology,hasEnergy);
        for (OWLLiteral i: linksCollection){
            if (i.parseInteger()==0){
               listOfPower = false;
            }
            else{
                listOfPower=true;
            }
        }
        return listOfPower;
    }

    public  Map<String,Boolean> getВreakersState(String AgentName){
        Map<String,Boolean> stateOfBreakers = new HashMap<>();
        OWLNamedIndividual ind = df.getOWLNamedIndividual(IRI.create(ns+AgentName));
        OWLObjectProperty hasBreaker = df.getOWLObjectProperty(IRI.create(ns+"hasBreaker"));
        OWLDataProperty state = df.getOWLDataProperty(IRI.create(ns+"state"));
        Collection<OWLIndividual> listBreakers = getDataFromOntology.getIndividualsFromProperty(ind,ontology,hasBreaker);
        for (OWLIndividual i: listBreakers){
            Collection<OWLLiteral> stateList = getDataFromOntology.getDataPropertyValue(i,ontology,state);
            for (OWLLiteral j: stateList){
                if (j.parseInteger()==0){
                    stateOfBreakers.put(i.toStringID().split("#")[1],false);
                }
                else {
                    stateOfBreakers.put(i.toStringID().split("#")[1],true);
                }
            }
        }
        return stateOfBreakers;
    }

}
