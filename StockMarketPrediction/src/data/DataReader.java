package data;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class DataReader {

    double[][] data;

    /**
     * @param args the command line arguments
     */
    public DataReader() {
        data = new double[countData()][6];
    }

    /**
     * statistics of whole input
     * @return -- see
     */
    public double[][] getStatistics(){
        double [][] statistics = new double [3][6];
        
        for (int i = 0; i < data.length; i++){
            for (int j = 0; j < data[i].length; j++){
                
                statistics [0][j] = statistics [0][j] + data[i][j];
                if (data[i][j] < statistics [1][j]){
                    statistics [1][j] = data[i][j];
                }
                if (data[i][j] > statistics [2][j]){
                    statistics [2][j] = data[i][j];
                }
                
            }
        }
        for (int j = 0; j < data[0].length; j++){
        statistics [0][j] =  statistics [0][j] / data.length;
        }
        
        for (int i = 0; i < statistics.length; i++) {
            for (int j = 0; j < statistics[i].length; j++) {
                System.out.print(statistics[i][j] + " ");
            }
            System.out.println();
        }
        
        return statistics;
    }
    /**
     * statistics of one-dimension data array
     * @param input - on dimensional array
     * @return [0] = min, [1] = max, [2] = average values of input-array
     */
    public static double[] getStatistics (double[] input){
    	double [] back = {input[0],input[0],input[0]};
    	
    	for (int i = 1; i < input.length; i++){
    		back[2] = back[2] + input[i];
    		
    		if (back [0] > input [i]){
    			back [0] = input [i];
    		}
    		if (back [1] < input [i]){
    			back [1] = input [i];
    		}
    	}
    	
    	back[2] = back[2] / input.length;
    	
    	return back;
    }
    /**
     * double [i][x] returns the i-th chart data
     * x = 0 is t + 1
     * x = 1 is t
     * x = 2 is t - 5
     * x = 3 is average of last 5 days
     * x = 4 is average of last 20 days
     * x = 5 is volume value 
     * @return
     */
    public double[][] readData() {


        String date;
        double open;
        int volume;
        double yesterday = 0.0;
        String[] back;

        int dataPointer = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader("input1.txt"));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                // System.out.println(zeile);
                back = zeile.split(",");
                date = back[0].substring(1, 11);
                open = Double.valueOf(back[1]);
                volume = Integer.valueOf(back[2]);

                data[dataPointer][0] = open;
                data[dataPointer][5] = volume;
                data[dataPointer][1] = yesterday;
                yesterday = open;
                dataPointer++;
               
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        protokoll();

        for (int i = 20; i < data.length; i++) {
            data[i][3] = (data[i][0] / data[i - 5][0]) - 1;
            data[i][4] = (data[i][0] / data[i - 20][0]) - 1;
        }

        yesterday = data[0][0];

        for (int i = 1; i < data.length - 1; i++) {
            data[i][1] = (data[i][0] / yesterday) - 1;
            yesterday = data[i][0];
            data[i][0] = (data[i + 1][0] / data[i][0]) - 1;

            if (i > 5) {
                data[i][2] = data[i - 5][1];
            }

        }

        System.out.println("-------------------------");
        protokoll();

        double[][] temp = new double[data.length - 21][6];

        for (int i = 20, j = 0; j < temp.length; i++, j++) {
            temp[j] = data[i];
        }

        data = temp;
        
        int bla = 0;
        while (bla < 100){
            bla++;
        System.out.println("-------------------------");}
        
        protokoll();

        return data;
    }

    private int countData() {
        int back = 1;
        try {
            BufferedReader in = new BufferedReader(new FileReader("input1.txt"));
           String zeile = null;
            while ((zeile = in.readLine()) != null) {
                back++;
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(back);
        return back;
    }

    public void protokoll() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}
