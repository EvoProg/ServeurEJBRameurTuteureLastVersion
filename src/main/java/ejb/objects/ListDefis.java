package ejb.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

//TODO: ajouter un sémaphore sur la classe ?
public class ListDefis {
    private static ListDefis instance;
    List<Defis> ld;
    List<Integer> lud;
    private Semaphore semaphoreUtilisateurs;
    private  Semaphore semaphoreDefis;

    public synchronized static ListDefis getInstance(){
        if(instance == null){
            instance = new ListDefis();
        }
        return instance;
    }

    private ListDefis(){
        //Semaphore à 1 jeton pour gérer l'accès aux utilisateurs
        semaphoreUtilisateurs = new Semaphore(1);
        //Semaphore à 1 jeton pour gérer l'accès aux défis
        semaphoreDefis = new Semaphore(1);

        ld = new ArrayList<>();
        lud = new ArrayList<>();
    }

    public void addDefis(Defis d){
        try {
            semaphoreDefis.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ld.add(d);
        System.out.println("dans list defis: "+ld.size());

        semaphoreDefis.release();
    }

    public void addUtil(int id)
    {
        try {
            semaphoreUtilisateurs.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Si l'id n'est pas déjà dans la liste
        if(!lud.contains(id))
            lud.add(id);


        semaphoreUtilisateurs.release();
    }

    public void supprUtil(int id){
        try {
            semaphoreUtilisateurs.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lud.remove((Integer) id);

        semaphoreUtilisateurs.release();
    }

    public List<Integer> getUtil(){
        return lud;
    }

    public List<Defis> getDefis(int idUtil){
        try {
            semaphoreDefis.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Defis> ldt = new ArrayList<>();
        for(int i = 0;i<ld.size();i++){
            if(ld.get(i).getIdUtilDefier()==idUtil)
            {
                ldt.add(ld.get(i));
            }
        }

        semaphoreDefis.release();

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

    public void supprDefis(Defis d){
        try {
            semaphoreDefis.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ld.remove(d);

        semaphoreDefis.release();
    }
}
