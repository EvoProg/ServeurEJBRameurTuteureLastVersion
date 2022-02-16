/*package ejb.message;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(
        name="Defis",
        activationConfig = {
              @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/Defis"),
              @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
              @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
        }
)
public class QueueListener implements MessageListener {

    List<Defis> defisList = new ArrayList<>();

    private int idUtil;

    public QueueListener(int idUtil){
        this.idUtil = idUtil;
    }

    @Override
    public void onMessage(Message message) {
        if(message instanceof Defis){
            Defis d = null;
            try {
                d = message.getBody(Defis.class);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            if(d.getIdUtilDefier() == idUtil)
            {
                defisList.add(d);
            }
        }else{
            System.out.println("ceci n'est pas un défis");
        }
    }

    public List<Defis> recupererDefis(){
        return defisList;
    }
}*/
