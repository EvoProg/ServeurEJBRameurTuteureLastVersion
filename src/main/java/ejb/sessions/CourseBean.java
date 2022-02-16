package ejb.sessions;

import ejb.message.Defis;
import ejb.message.QueueListener;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.*;
import java.util.List;


@JMSDestinationDefinition(
        name="java:/queue/Defis",
        interfaceName = "javax.jms.Queue",
        destinationName = "QueueListener"
)
@Stateful
public class CourseBean {

    @Resource(lookup = "java:/queue/Defis")
    private Queue queue;

    @Inject
    private JMSContext context;

    private QueueListener ql;

    private int idUtil;
    private int idAdversaire;
    private int DistanceCourse;

    public void init(int idUtil){
        this.idUtil = idUtil;
        ql = new QueueListener(idUtil);
    }

    public void lancerDefis(int Distance, int idAdversaire){
        this.idAdversaire = idAdversaire;
        this.DistanceCourse = Distance;
        Destination destination = queue;
        Defis d = new Defis(idUtil,this.idAdversaire,this.DistanceCourse);
        context.createProducer().send(destination, d);
    }

    public List<Defis> recevoirDefis() throws JMSException, InterruptedException {
        Destination destination = queue;
        return ql.recupererDefis();
    }
}
