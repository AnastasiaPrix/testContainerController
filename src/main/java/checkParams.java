import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;

import java.io.File;

public class checkParams extends OneShotBehaviour {

    private myAgent agent;
    private boolean doneB = false;
    private DataComtrade comtrade;

    public checkParams(myAgent agent) {
        this.agent= agent;
    }

    @Override
    public void action() {
        String path = "src\\main\\resources\\";
        String file = "KZ1_vnutr";
        String file2 = "Normal";
        if (agent.getLocalName().equals("TP_2")||agent.getLocalName().equals("TP_3")||agent.getLocalName().equals("RP_2")){
            comtrade = new DataComtrade(path,file2);
        }
        else {
            comtrade = new DataComtrade(path, file);
        }
        checkKZ kzA = new checkKZ();
        comtrade.setKzA(kzA);
        checkKZ kzB = new checkKZ();
        comtrade.setKzB(kzB);
        checkKZ kzC = new checkKZ();
        comtrade.setKzC(kzC);
        comtrade.run();
    }

    @Override
    public int onEnd() {
       // System.out.println("I'm "+ agent.getLocalName()+ " "+ comtrade.isTrip());
        agent.setState(comtrade.isTrip());
        if (agent.getLocalName().contains("R") && comtrade.isTrip()){
            System.out.println("I'm "+ agent.getLocalName()+ " find KZ turn of ");
            agent.addBehaviour(new SendRequest(agent,null));
            agent.addBehaviour(new WaitForLocalizationKz(agent));
        }
        else if (agent.getLocalName().contains("T")) {
            agent.addBehaviour(new WaitForRequest(agent));
        }
        agent.addBehaviour(new CheckPower(agent));
        return super.onEnd();
    }
}
