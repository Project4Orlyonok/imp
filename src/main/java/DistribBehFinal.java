import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class DistribBehFinal extends Behaviour {
   private AID topic;
    private AID[] resultsAID;
    private String consumer;
    private AID agent;
    private double minprice = 1000.1;
    private int countAllAgents = 0, countNoLeftAgents =0;
    private double request;
    private boolean flag = false;
    private JsonDistr json;
    private Time time;

    public DistribBehFinal(AID topic, AID[] resultsAID, String consumer, double request, JsonDistr json, Time time) {
        this.topic = topic;
        this.resultsAID = resultsAID;
        this.consumer = consumer;
        this.request = request;
        this.json=json;
        this.time=time;
    }


    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchProtocol("Price"), MessageTemplate.MatchTopic(topic));
        ACLMessage receivedMsg = myAgent.receive(mt);

        if (receivedMsg != null) {
            countAllAgents++;

            if (!(receivedMsg.getContent().equals("Left"))) {
                countNoLeftAgents++;

                //поиск минимальной цены
                if (minprice > (Double.parseDouble(receivedMsg.getContent()))) {
                    minprice = (Double.parseDouble(receivedMsg.getContent()));
                    agent = receivedMsg.getSender();
                }

            }

            ACLMessage consumerMessage = new ACLMessage(ACLMessage.INFORM);
            consumerMessage.addReceiver(myAgent.getAID(consumer));
            consumerMessage.setProtocol("End");
            consumerMessage.setOntology(String.valueOf(request));

            ACLMessage winnerMessage = new ACLMessage(ACLMessage.INFORM);
            winnerMessage.setProtocol("Winner");
            winnerMessage.addReceiver(topic);
            winnerMessage.setOntology(String.valueOf(request));

            //если все агенты отправили сообщения и хотя бы кто-то из агентов смог предоставить требуемую мощность,
            // то отправить сообщение с именем победителя и сообщение потребителю
            if (countAllAgents == resultsAID.length && 0 != countNoLeftAgents) {

                winnerMessage.setContent(agent.getLocalName());
                myAgent.send(winnerMessage);
//                System.out.println(topic.getLocalName() + "   выиграл  " + agent.getLocalName());

                Map<String, String> data = json.data(minprice, request, agent.getLocalName(), consumer, time.getCurrentTime() / 60);
                String stroka = json.stroka(data);
                String fileName = "C:\\Users\\anna\\IdeaProjects\\imp\\Distr.json";
                try {
                    FileOutputStream file = new FileOutputStream(fileName);
                    file.write(stroka.getBytes());
                    file.flush();
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                consumerMessage.setContent(String.valueOf(minprice));
                myAgent.send(consumerMessage);
                flag = true;

            }

            //если все агенты отправили сообщения и никто из агентов не смог предоставить требуемую мощность,
            // то отправить сообщение с протоколом "Winner" и сообщение потребителю
            if (countNoLeftAgents == 0 && countAllAgents == resultsAID.length) {

                winnerMessage.setContent("No");
//                winnerMessage.addReceiver(topic);
                myAgent.send(winnerMessage);


                consumerMessage.setContent("No");
                myAgent.send(consumerMessage);
                System.out.println(topic.getLocalName() + "   недостаточно энергии");
                flag = true;
                System.out.println("");
            }
        }
    }
        @Override
        public boolean done() {
            return flag;
        }
    }



