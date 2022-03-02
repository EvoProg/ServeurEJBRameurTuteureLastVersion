package ejb.sessions;

import ejb.message.Defis;
import ejb.message.ListDefis;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.util.List;

@Stateless
public class CourseBean {
    ListDefis ld;

    public CourseBean(){
        ld = ListDefis.getInstance();
    }

    public void lancerDefis(int Distance, int idAdversaire, int idUtil){
        Defis d = new Defis(idUtil,idAdversaire,Distance);
        ld.addDefis(d);
    }

    public List<Defis> recevoirDefis(int idUtil) throws JMSException, InterruptedException {
        return ld.getDefis(idUtil);
    }

    public List<Integer> getUtil(){
        return ld.getUtil();
    }

    public void addUtilDispo(int id){
        ld.addUtil(id);
    }

    public void supprUtilDispo(int id){
        ld.supprUtil(id);
    }
}
