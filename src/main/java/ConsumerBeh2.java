import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ConsumerBeh2 extends TickerBehaviour {
    public Time time;
    ParsConfig poww;
    double pow;
    int StartTime;
    double power=0;
    double period;
    //    List<Double> pow=Arrays.asList(1.0,2.4,3.7,4.0,5.8);
    public ConsumerBeh2(Agent a, int period, Time time, ParsConfig poww) {
        super(a, period);
        this.time = time;
        this.poww = poww;

    }

    @Override
    public void onStart() {
//        poww.setPow();
        StartTime = time.getCurrentTime();
        super.onStart();
    }

    @Override
    protected void onTick() {
        period=Math.toIntExact(Math.round(time.minute * 60 / poww.pow(time.getCurrentTime(),myAgent.getLocalName() + ".xml")));

        if (time.getCurrentTime() <= StartTime + time.minute * 60) {
            if (power < poww.pow(time.getCurrentTime() / 60, myAgent.getLocalName() + ".xml")) {
                pow = 1.0;
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setContent(String.valueOf(pow));
                message.setProtocol("NeedAuction");
                message.addReceiver(myAgent.getAID("Distributor"));
                myAgent.send(message);
//        System.out.println(message);
                myAgent.addBehaviour(new Behaviour() {
                    boolean flag = false;

                    @Override
                    public void action() {
                        MessageTemplate mt = MessageTemplate.MatchProtocol("End");
                        ACLMessage receivedMsg = myAgent.receive(mt);
                        if (receivedMsg != null) {
                            if (!(receivedMsg.getContent().equals("No"))) {
                                System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
                                power =pow+power;
                            } else {
                                System.out.println(myAgent.getLocalName() + " не купил ");
                                //                        запрос минимальной мощности
                            }
                            System.out.println("");
                            flag = true;
                        }
                    }

                    @Override
                    public boolean done() {
                        return flag;
                    }
                });
                try {
                    Thread.sleep((long) (period*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
//        pow = poww.pow(time.getCurrentTime()/60,myAgent.getLocalName()+".xml");
//        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//        message.setContent(String.valueOf(pow));
//        message.setProtocol("NeedAuction");
//        message.addReceiver(myAgent.getAID("Distributor"));
//        myAgent.send(message);
////        System.out.println(message);
//        myAgent.addBehaviour(new Behaviour() {
//            boolean flag = false;
//
//            @Override
//            public void action() {
//                MessageTemplate mt = MessageTemplate.MatchProtocol("End");
//                ACLMessage receivedMsg = myAgent.receive(mt);
//                if (receivedMsg != null) {
//                    if (!(receivedMsg.getContent().equals("No"))) {
//                        System.out.println(myAgent.getLocalName() + " купил за  " + receivedMsg.getContent());
//                    }
//                    else  {
//                        System.out.println(myAgent.getLocalName() + " не купил ");
//                        //                        запрос минимальной мощности
//                    }
//                    System.out.println("");
//                    flag = true;
//                }
//            }
//
//            @Override
//            public boolean done() {
//                return flag;
//            }
//        });

//        else{
//            block();
//        }
//        ожидание сообщ от дистр о вышло/не вышло

