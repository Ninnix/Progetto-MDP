
import com.sun.xml.internal.bind.v2.TODO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestGUI extends Application {
    public static void main(String[] args) {
        launch(args);  // Lancia l'applicazione, ritorna quando l'applicazione
    }                  // termina. Può essere invocato una sola volta

    @Override
    public void start(Stage stage) throws InvalidPopulationException {
        Parent root = createChart();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("La Battaglia dei Sessi");
        stage.setScene(scene);
        stage.show();
    }

    int type[] = new int[4];

    private Parent createChart() throws InvalidPopulationException {

        Popolazione p1 = prepareData();

        //Creo barChart1 (instogramma)
        VBox vBoxBarChart1 = new VBox();
        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        final BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setCategoryGap(0);
        barChart1.setBarGap(0);
        barChart1.setMaxSize(500, 450);

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("Morigerati", type[0]));
        series1.getData().add(new XYChart.Data("Avventurieri", type[1]));
        series1.getData().add(new XYChart.Data("Prundenti", type[2]));
        series1.getData().add(new XYChart.Data("Spregiudicate", type[3]));

        barChart1.getData().addAll(series1);
        barChart1.setAnimated(false);
        vBoxBarChart1.getChildren().addAll(barChart1);
        //- End of barChart1

        //Creo PieChart2 (Diagramma a torta)
        VBox vBoxPieChart2 = new VBox();

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("Morigerati", type[0]),
                new PieChart.Data("Avventurieri", type[1]),
                new PieChart.Data("Prudenti", type[2]),
                new PieChart.Data("Spregiudicate", type[3])
        );

        final PieChart pieChart2 = new PieChart(pieChartData);
        pieChart2.setTitle("La Battaglia dei Sessi");
        pieChart2.setMaxSize(500, 450);

        pieChart2.setAnimated(false);
        vBoxPieChart2.getChildren().addAll(pieChart2);
        //fine del pieChart2

        //bottone start/stop
        Button startStop = new Button("Stop");
        startStop.setOnAction(e -> {
            if (p1.isRunning()) {
                p1.stop();
                startStop.setText("Start");
            } else {
                //dovrebbe rifar settare i parametri o altro
                startStop.setText("Stop");
            }
        });

        //Layout
        HBox hBoxCharts = new HBox();
        hBoxCharts.getChildren().addAll(vBoxBarChart1, vBoxPieChart2);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBoxCharts, startStop);
        vBox.setAlignment(Pos.CENTER);  // Allineamento dei nodi
        vBox.setSpacing(30); // Spazio tra i nodi

        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        //fine Layout

        //Tread che fa partire il mondo
        Thread startWorld = new Thread(()->{p1.start();});
        startWorld.start();

        //Applica l'animazione ai dati dei grafici(Charts)
        //Source: http://docs.oracle.com/javafx/2/charts/bar-chart.htm
        //Sezione "Animating Data in Charts"
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(200), (ActionEvent actionEvent) -> {
                        //aggiorno i grafici dei morigerati
                        series1.getData().set(0, new XYChart.Data("Morigerati", p1.morigerati.size()));
                        pieChartData.set(0, new PieChart.Data("Morigerati", p1.morigerati.size()));
                        //aggiorno i grafici degli avventurieri
                        series1.getData().set(1, new XYChart.Data("Avventurieri", p1.avventurieri.size()));
                        pieChartData.set(1, new PieChart.Data("Avventurieri",  p1.avventurieri.size()));
                        //aggiorno i grafici delle prudenti
                        series1.getData().set(2, new XYChart.Data("Prudenti", p1.prudenti.size()));
                        pieChartData.set(2, new PieChart.Data("Prudenti", p1.prudenti.size()));
                        //aggiorno i grafici delle spregiudicate
                        series1.getData().set(3, new XYChart.Data("Spregiudicate", p1.spregiudicate.size()));
                        pieChartData.set(3, new PieChart.Data("Spregiudicate", p1.spregiudicate.size()));
                }));

        timeline.setCycleCount(1000);
        timeline.setAutoReverse(true);  //!?
        timeline.play();
        return root;
    }

    //Prepara la i dati per i grafici a seconda della popolazione iniziale
    private Popolazione prepareData() throws InvalidPopulationException{
        Popolazione p1 = new Popolazione(15, 20, 3, 50, 50, 50, 50);
        for (int i = 0; i < 4; i++) {
            int numIni = 0;
            if (i == 0) numIni = p1.morigerati.size();
            else if (i == 1) numIni = p1.avventurieri.size();
            else if (i == 2) numIni = p1.prudenti.size();
            else if (i == 3) numIni = p1.spregiudicate.size();
            type[i] = numIni;
        }
        return p1;
    }
}
