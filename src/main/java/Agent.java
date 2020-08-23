import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class Agent extends jade.core.Agent {
    protected void setup() {

        Time time = new Time();
        ParsConfig config=new ParsConfig();
//        config.setPow();
        GenerationInfo power = new GenerationInfo(time.getCurrentTime());
        power.setAll();

//        System.out.println(power.LastAsk);
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
                dfRegister("Generation");
                addBehaviour(new GenBehStart(time,power));
                break;
            }//                dfRegister("System");
            case "Distributor": {
                addBehaviour(new DistribBehStart(power,time));
                break;
            }
            case "Consumer1":
            case "Consumer3":
            case "Consumer2": {
//                int period=Math.toIntExact(Math.round(time.minute * 60 / config.pow(time.getCurrentTime(),getLocalName() + ".xml")));
                int period=time.minute*60;
                addBehaviour(new ConsumerBeh(this, period, time,config));
                break;
            }
        }
//
    }

    private void dfRegister(String name) {
        DFAgentDescription dfc = new DFAgentDescription();
        dfc.setName(getAID());
        ServiceDescription dfs = new ServiceDescription();
        dfs.setName(name + getLocalName());
        dfs.setType(name);
        dfc.addServices(dfs);
        try {
            DFService.register(this, dfc);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
