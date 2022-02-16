package stats;

import ejb.entities.Performance;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Classe objet réalisant un graphique à partir des données de la classe statistiques
 */
public class Graphiques {
    //Déclaration des variables
    private Statistiques st;

    public Graphiques(int idUtil){
        st = new Statistiques(idUtil);
    }

    public void getPuissanceGraph(HttpServletResponse response) throws IOException {
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetPerformances();
        JFreeChart graph = ChartFactory.createXYLineChart("Puissances des cinq dernières sessions","Axe du temps (en s)","Axe des puissances (en w)", dataset, PlotOrientation.VERTICAL,true,true,false);
        XYPlot plot = graph.getXYPlot();
        XYSplineRenderer renderer = new XYSplineRenderer(1);
        plot.setRenderer(renderer);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private XYDataset createDatasetPerformances(){
        XYSeriesCollection dataset = new XYSeriesCollection();
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

    public void getCaloriesGraph(HttpServletResponse response) throws IOException {
        //Creer le dataset contenant les donnees
        DefaultCategoryDataset dataset = createDatasetCalories();
        JFreeChart graph = ChartFactory.createBarChart("Calories perdues dans les cinq dernières sessions", "","Calories perdues", dataset, PlotOrientation.VERTICAL, true, true, false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private DefaultCategoryDataset createDatasetCalories(){
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                result.addValue(st.CaloriesTotales(st.getSession().get(i)), "Session n° " + st.getSession().get(i), st.getPerfSessionfDate(st.getSession().get(i)));
            }
        }
        return result;
    }

    public void getDistancesGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        DefaultCategoryDataset dataset = createDatasetDistances();
        JFreeChart graph = ChartFactory.createBarChart("Distances parcourues durant les cinq dernières sessions", "","Distances parcourues (en cm)", dataset, PlotOrientation.HORIZONTAL, true, true, false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private DefaultCategoryDataset createDatasetDistances(){
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                result.addValue(st.DistancesTotales(st.getSession().get(i)), "Session n° " + st.getSession().get(i), st.getPerfSessionfDate(st.getSession().get(i)));
            }
        }
        return result;
    }

    public void getCoupsMoyenneGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetCoups();
        JFreeChart graph = ChartFactory.createXYLineChart("Coups Moyens des cinq dernières sessions","Sessions","Coups Moyens", dataset, PlotOrientation.VERTICAL,true,true,false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private XYDataset createDatasetCoups(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        if(st.getPerfs() != null) {
            XYSeries series = new XYSeries("Evolution des coups moyens par sessions");
            for (int i = 0; i < st.getSession().size(); i++) {
                int session = st.getSession().get(i);
                double moyenne = st.CoupsPmMoyenne(st.getSession().get(i));
                series.add(session, moyenne);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    public void getRythmeMoyenGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        DefaultPieDataset dataset = createDatasetRythme();
        JFreeChart graph = ChartFactory.createPieChart3D("Ryhtme Moyen des cinq dernières session", dataset,true,true,false);
        PiePlot3D plot = ( PiePlot3D ) graph.getPlot( );
        plot.setStartAngle( 180 );
        plot.setForegroundAlpha( 0.60f );
        plot.setInteriorGap( 0.02 );
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private DefaultPieDataset createDatasetRythme(){
        DefaultPieDataset dataset = new DefaultPieDataset();
        if(st.getPerfs() != null) {
            for (int i = 0; i < st.getSession().size(); i++) {
                int session = st.getSession().get(i);
                double moyenne = st.RythmeMoyenne(st.getSession().get(i));
                dataset.setValue("Session n° " + session + " : " + moyenne, moyenne);
            }
        }
        return dataset;
    }

    public void getFrequenceMoyenneGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        XYDataset dataset = createDatasetFrequence();
        JFreeChart graph = ChartFactory.createXYLineChart("Fréquence Moyennes des cinq dernières sessions","Sessions","Coups Moyens", dataset, PlotOrientation.VERTICAL,true,true,false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private XYDataset createDatasetFrequence(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        if(st.getPerfs() != null) {
            XYSeries series = new XYSeries("Evolution de la Fréquence cardiaque moyens par sessions");
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
