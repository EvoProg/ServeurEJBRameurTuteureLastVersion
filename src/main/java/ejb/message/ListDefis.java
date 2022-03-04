package ejb.message;

import java.util.ArrayList;
import java.util.List;

//TODO: ajouter un sémaphore sur la classe ?
public class ListDefis {
    private static ListDefis instance;
    List<Defis> ld;
    List<Integer> lud;

    public static ListDefis getInstance(){
        if(instance == null){
            instance = new ListDefis();
        }
        return instance;
    }

    private ListDefis(){
        ld = new ArrayList<>();
        lud = new ArrayList<>();
    }

    public synchronized void addDefis(Defis d){
        ld.add(d);
        System.out.println("dans list defis: "+ld.size());
    }

    public synchronized void addUtil(int id){
        lud.add(id);
    }

    public synchronized void supprUtil(int id){
        lud.remove(id);
    }

    public synchronized List<Integer> getUtil(){
        return lud;
    }

    public synchronized List<Defis> getDefis(int idUtil){
        List<Defis> ldt = new ArrayList<>();
        for(int i = 0;i<ld.size();i++){
            if(ld.get(i).getIdUtilDefier()==idUtil)
            {
                ldt.add(ld.get(i));
            }
        }
        return ldt;
    }

    public synchronized Defis getDefi(int idDefieur, int idDefier){
        for(int i = 0 ; i< ld.size();i++){
            if(ld.get(i).getIdUtilDefier() == idDefier && ld.get(i).getIdUtilDefieur() == idDefieur) return ld.get(i);
        }
        return null;
    }

    public synchronized void ajouteIdRameur(int idDefieur, int idDefier, int idRameur){
        for(int i = 0 ; i< ld.size();i++){
            if(ld.get(i).getIdUtilDefier() == idDefier && ld.get(i).getIdUtilDefieur() == idDefieur) ld.get(i).setIdRameurDefier(idRameur);
        }
    }

    public synchronized void supprDefis(Defis d){
        ld.remove(d);
    }
}
