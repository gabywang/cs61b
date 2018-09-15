package timing;

import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class GraphSorters extends JPanel {

    /* Graph variables */
    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 50;
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    /* timing.Sorter variables */
    private List<List<Double>> allscores;
    private static Sorter[] sorters = {
            new JavaSorter(),
            new BubbleSorter(),
            new WipingBubbleSorter(),
            new InsertionSorter()};
    private Color[] lineColors = {
            new Color(44, 102, 230, 180),
            new Color(200, 102, 230, 180),
            new Color(200, 40, 20, 180),
            new Color(44, 200, 20, 180)};
    private int nrepeats;
    private int by;

    public GraphSorters(List<List<Double>> allscores, int nrepeats, int by) {
        this.allscores = allscores;
        this.nrepeats = nrepeats;
        this.by = by;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (getNumberScores() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        // draw white background
        g2.setColor(Color.WHITE);
        g2.setColor(Color.BLACK);

        // Get font metrics
        FontMetrics metrics = g2.getFontMetrics();

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (getNumberScores() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // create y axis label.
        AffineTransform originalTransform = g2.getTransform();
        g2.rotate(-Math.PI/2);
        g2.drawString("Microseconds", -getHeight() / 2 - metrics.stringWidth("Microseconds") / 2, 20);
        g2.setTransform(originalTransform);
        // create x axis label.
        g2.drawString("Size of Array", getWidth() / 2 - metrics.stringWidth("Size of Array") / 2,getHeight() - 20 );

        /* Create Legend */
        // nRepetitions label.
        String repString = "Number of Repetitions: " + this.nrepeats;
        g2.drawString(repString, 100, 50 );

        // Sorters
        int spacing = metrics.getHeight() + 5;
        for(int n = 0; n < sorters.length; n++) {
            String sortString = (sorters[n].getClass().toString()).split(" ")[1];
            g2.setColor(Color.BLACK);
            g2.drawString(sortString, 100 + pointWidth * 3 + 5, 50 + spacing * (n + 1));
            g2.setColor(lineColors[n]);
            g2.fillOval(100,50 + spacing * n + spacing / 2, pointWidth * 3, pointWidth * 3);
        }
        g2.setColor(Color.BLACK);



        // and for x axis
        for (int i = 0; i < getNumberScores(); i++) {
            if (getNumberScores() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (getNumberScores() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((getNumberScores() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = 5 + i * by + "";
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);


        //for(List<Double> scores : allscores) {
        for(int index = 0; index < allscores.size(); index ++) {
            List<Double> scores = allscores.get(index);
            List<Point> graphPoints = new ArrayList<>();
            for (int i = 0; i < getNumberScores(); i++) {
                int x1 = (int) (i * xScale + padding + labelPadding);
                int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
                graphPoints.add(new Point(x1, y1));
            }

            Stroke oldStroke = g2.getStroke();
            g2.setColor(lineColors[index]);
            g2.setStroke(GRAPH_STROKE);
            for (int i = 0; i < graphPoints.size() - 1; i++) {
                int x1 = graphPoints.get(i).x;
                int y1 = graphPoints.get(i).y;
                int x2 = graphPoints.get(i + 1).x;
                int y2 = graphPoints.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }

            g2.setStroke(oldStroke);
            g2.setColor(pointColor);
            for (int i = 0; i < graphPoints.size(); i++) {
                int x = graphPoints.get(i).x - pointWidth / 2;
                int y = graphPoints.get(i).y - pointWidth / 2;
                int ovalW = pointWidth;
                int ovalH = pointWidth;
                g2.fillOval(x, y, ovalW, ovalH);
            }
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (List<Double> scores : allscores) {
            for (Double score : scores) {
                minScore = Math.min(minScore, score);
            }
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;

        for (List<Double> scores : allscores) {
            for (Double score : scores) {
                maxScore = Math.max(maxScore, score);
            }
        }
        return maxScore;
    }

    private int getNumberScores() {
        assert allscores.size() >= 1;
        int number = allscores.get(0).size();
        for (List<Double> scores : allscores) {
            assert number == scores.size();
        }
        return number;
    }

    private static void createAndShowGui(int ntrials, int by, int nrepeats) {
        List<List<Double>> allscores = new ArrayList<List<Double>>();

        for (int nsorts = 0; nsorts < sorters.length; nsorts++) {
            List<Double> scores = sorters[nsorts].score(by, ntrials, nrepeats);
            allscores.add(scores);
        }

        GraphSorters mainPanel = new GraphSorters(allscores, nrepeats, by);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int ntrials = 0;
                int by = 0;
                int nrepeats = 0;
                if(args.length == 0 ) {
                    ntrials = 50;
                    by = 100;
                    nrepeats = 10;
                } else if (args.length == 3) {
                    ntrials = Integer.parseInt(args[0]);
                    by = Integer.parseInt(args[1]);
                    nrepeats = Integer.parseInt(args[2]);
                } else {
                    System.err.println("args should either be empty or 3 values: ntrials, by, and nrepeats");
                    System.exit(1);
                }
                createAndShowGui(ntrials, by, nrepeats);
            }
        });
    }
}
