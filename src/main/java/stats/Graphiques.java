package stats;

import ejb.entities.Performance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
    Classe objet réalisant des graphiques à partir des données récupérées et traitées de la classe statistiques.
 */

public class Graphiques {
    //Déclaration des variables
    private Statistiques st;

    //Constructeur de la classe ayant pour paramètre l'identifiant de l'utilisateur qui initialise un objet de type Statistiques
    public Graphiques(int idUtil){
        st = new Statistiques(idUtil);
    }

    //Méthode créant un graphe des Puissances des cinq dernières sessions de l'utilisateur
    public void getPuissanceGraph(HttpServletResponse response) throws IOException {
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetPerformances();
        //Mise en place des labels
        JFreeChart graph = ChartFactory.createXYLineChart("Puissances des cinq dernières sessions","Axe du temps (en s)","Axe des puissances (en w)", dataset, PlotOrientation.VERTICAL,true,true,false);
        //On trace les données du dataset
        XYPlot plot = graph.getXYPlot();
        //Sous la forme d'une courbe à points
        XYSplineRenderer renderer = new XYSplineRenderer(1);
        plot.setRenderer(renderer);
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des puissances à l'aide de l'objet statistiques. Celui-ci donne les données des puissances d'un utilisateur
    private XYDataset createDatasetPerformances(){
        //On crée la collection de données
        XYSeriesCollection dataset = new XYSeriesCollection();
        //On récupère les puissances des dernières sessions
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                XYSeries series = new XYSeries("Session n° " + st.getSession().get(i));
                List<Performance> performances = st.getPerfSession(st.getSession().get(i));

                series.add(0, performances.get(0).getPuissanceW());

                for (int j = 1; j < performances.size(); j++) {
                    series.add(j, performances.get(j).getPuissanceW());
                }
                dataset.addSeries(series);
            }
        }
        return dataset;
    }

    //Méthode créant un graphe des Calories des cinq dernières sessions de l'utilisateur
    public void getCaloriesGraph(HttpServletResponse response) throws IOException {
        //Creer le dataset contenant les donnees
        DefaultCategoryDataset dataset = createDatasetCalories();
        //Mise en place des labels et du graphe sous la forme d'un histogramme vertical
        JFreeChart graph = ChartFactory.createBarChart("Calories perdues dans les cinq dernières sessions", "","Calories perdues", dataset, PlotOrientation.VERTICAL, true, true, false);
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des calories à l'aide de l'objet statistiques. Celui-ci donne les données des calories d'un utilisateur
    private DefaultCategoryDataset createDatasetCalories(){
        //On crée la collection de données
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        //On récupère les calories des dernières sessions
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                result.addValue(st.CaloriesTotales(st.getSession().get(i)), "Session n° " + st.getSession().get(i), st.getPerfSessionfDate(st.getSession().get(i)));
            }
        }
        return result;
    }

    //Méthode créant un graphe des Distances des cinq dernières sessions de l'utilisateur
    public void getDistancesGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        DefaultCategoryDataset dataset = createDatasetDistances();
        //Mise en place des labels et du graphe sous la forme d'un histogramme horizontal
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        JFreeChart graph = ChartFactory.createBarChart("Distances parcourues durant les cinq dernières sessions", "","Distances parcourues (en dcm)", dataset, PlotOrientation.HORIZONTAL, true, true, false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des distances à l'aide de l'objet statistiques. Celui-ci donne les données des distances d'un utilisateur
    private DefaultCategoryDataset createDatasetDistances(){
        //On crée la collection de données
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        //On récupère les distances des dernières sessions
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                result.addValue(st.DistancesTotales(st.getSession().get(i)), "Session n° " + st.getSession().get(i), st.getPerfSessionfDate(st.getSession().get(i)));
            }
        }
        return result;
    }

    //Méthode créant un graphe des Coups des cinq dernières sessions de l'utilisateur
    public void getCoupsMoyenneGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetCoups();
        //Mise en place des labels et du graphe sous la forme d'une courbe
        JFreeChart graph = ChartFactory.createXYLineChart("Moyennes des Coups des cinq dernières sessions","Sessions","Coups Moyens", dataset, PlotOrientation.VERTICAL,true,true,false);
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des coups à l'aide de l'objet statistiques. Celui-ci donne les données des coups d'un utilisateur
    private XYDataset createDatasetCoups(){
        //On crée la collection de données
        XYSeriesCollection dataset = new XYSeriesCollection();
        //On récupère les coups des dernières sessions traités par l'objet Statistiques
        if(st.getPerfs() != null) {
            XYSeries series = new XYSeries("Évolution des coups moyens par sessions");
            for (int i = 0; i < st.getSession().size(); i++) {
                int session = st.getSession().get(i);
                double moyenne = st.CoupsPmMoyenne(st.getSession().get(i));
                series.add(session, moyenne);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    //Méthode créant un graphe des Rythmes des cinq dernières sessions de l'utilisateur
    public void getRythmeMoyenGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        DefaultPieDataset dataset = createDatasetRythme();
        //Mise en place des labels et du graphe sous la forme d'un camembert 3D
        JFreeChart graph = ChartFactory.createPieChart3D("Moyenne des Rythmes des cinq dernières sessions", dataset,true,true,false);
        //Paramètres 3D
        PiePlot3D plot = ( PiePlot3D ) graph.getPlot( );
        plot.setStartAngle( 180 );
        plot.setForegroundAlpha( 0.60f );
        plot.setInteriorGap( 0.02 );
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des rythmes à l'aide de l'objet statistiques. Celui-ci donne les données des rythmes d'un utilisateur
    private DefaultPieDataset createDatasetRythme(){
        //On crée la collection de données
        DefaultPieDataset dataset = new DefaultPieDataset();
        //On récupère les rythmes des dernières sessions traités par l'objet Statistiques
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                int session = st.getSession().get(i);
                double moyenne = st.RythmeMoyenne(st.getSession().get(i));
                dataset.setValue("Session n° " + session + " : " + moyenne, moyenne);
            }
        }
        return dataset;
    }

    //Méthode créant un graphe des Fréquences des cinq dernières sessions de l'utilisateur
    public void getFrequenceMoyenneGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetFrequence();
        //Mise en place des labels et du graphe sous la forme d'une courbe
        JFreeChart graph = ChartFactory.createXYLineChart("Moyenne des Fréquences des cinq dernières sessions","Sessions","Coups Moyens", dataset, PlotOrientation.VERTICAL,true,true,false);
        //On enregistre le graphe sous la forme d'une image puis on l'envoie à la ServletReponse passée en paramètre
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    //Méthode créant le dataset des fréquences à l'aide de l'objet statistiques. Celui-ci donne les données des fréquences d'un utilisateur
    private XYDataset createDatasetFrequence(){
        //On crée la collection de données
        XYSeriesCollection dataset = new XYSeriesCollection();
        //On récupère les fréquences des dernières sessions traitées par l'objet Statistiques
        if(st.getPerfs() != null) {
            XYSeries series = new XYSeries("Évolution de la Fréquence cardiaque moyenne par sessions");
            for (int i = 0; i < st.getSession().size(); i++) {
                int session = st.getSession().get(i);
                double moyenne = st.FrequenceMoyenne(st.getSession().get(i));
                series.add(session, moyenne);
            }

            dataset.addSeries(series);
        }
        return dataset;
    }


}
