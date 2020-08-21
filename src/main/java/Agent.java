import jade.core.Service;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Register;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class Agent extends jade.core.Agent {
    protected void setup() {

        Time time = new Time();
        Const poww=new Const();
        poww.setPow();
        GenInf power = new GenInf(time.getCurrentTime());
        power.setAll("Consumer1t");
        power.setAll("Consumer2t");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println(myAgent.getLocalName() + " was born");
            }
        });
        switch (getLocalName()) {
            case "Sun":
            case "Wind":
            case "Heat":
            case "System": {
                dfRegister();
                addBehaviour(new GenBehStart(time,power));
                break;
            }
            case "Distributor": {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                addBehaviour(new DistribBehStart(power,time));
                break;
            }
            case "Consumer1":
            case "Consumer2": {
//                int period=Math.toIntExact(Math.round(time.minute * 60 / poww.pow(time.getCurrentTime(),getLocalName() + ".xml")));
                int period=time.minute*60;
                addBehaviour(new ConsumerBeh(this, period, time,poww));
                break;
            }
        }
//
    }

    private void dfRegister() {
        DFAgentDescription dfc = new DFAgentDescription();
        dfc.setName(getAID());
        ServiceDescription dfs = new ServiceDescription();
        dfs.setName("Generation" + getLocalName());
        dfs.setType("Generation");
        dfc.addServices(dfs);
        try {
            DFService.register(this, dfc);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
