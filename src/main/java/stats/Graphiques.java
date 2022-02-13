package stats;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Classe objet réalisant un graphique à partir des données de la classe statistiques
 */
public class Graphiques {
    //Déclaration des variables
    private Statistiques st;

    public Graphiques(int idUtil){
        st = new Statistiques(idUtil);
    }

    public void getPuissanceMoyGraph(HttpServletResponse response) throws IOException {
        //Creer le dataset contenant les donnees
        DefaultXYDataset dataset = createDatasetPerformances();
        JFreeChart graph = ChartFactory.createXYLineChart("Puissances des cinq dernières sessions","Axe du temps (en s)","Axe des puissances (en w)", dataset, PlotOrientation.VERTICAL,true,true,false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private DefaultXYDataset createDatasetPerformances(){
        DefaultXYDataset result = new DefaultXYDataset();
        for (int i = 1; i <= st.getSession(); i++) {
            result.addSeries("Session n° "+i, st.getPuissanceMoySessions(i));
        }
        return result;
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
        for (int i = 1; i <= st.getSession(); i++) {
            result.addValue(st.CaloriesTotales(i),"Session n° "+i, st.getPerfSessionfDate(i));
        }
        return result;
    }

    public void getDistancesGraph(HttpServletResponse response) throws IOException{
        //Creer le dataset contenant les donnees
        DefaultCategoryDataset dataset = createDatasetDistances();
        JFreeChart graph = ChartFactory.createBarChart("Distances parcourues durant les cinq dernières sessions", "","Distances parcourues", dataset, PlotOrientation.HORIZONTAL, true, true, false);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, graph, 700, 500);
    }

    private DefaultCategoryDataset createDatasetDistances(){
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        for (int i = 1; i <= st.getSession(); i++) {
            result.addValue(st.DistancesTotales(i),"Session n° "+i, st.getPerfSessionfDate(i));
        }
        return result;
    }
}
