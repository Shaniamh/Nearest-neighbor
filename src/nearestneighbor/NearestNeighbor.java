package nearestneighbor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.math.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author ASUS
 */
public class NearestNeighbor {
    static int jum_error=0;
    static Double persentase_error;
    
     public static ArrayList<String> readTeks(String bacateks) throws FileNotFoundException, IOException {
        File bacafile = new File(bacateks);
        FileReader inputDokumen = new FileReader(bacafile);
        BufferedReader bufferBaca = new BufferedReader(inputDokumen);
        StringBuffer content = new StringBuffer();
        String barisData;
        ArrayList<String> data = new ArrayList<String>();
        while ((barisData = bufferBaca.readLine()) != null) {
            content.append(barisData);
            content.append(System.getProperty("line.separator"));
            data.add(barisData);
        }
        return data;
}
     
      public static ArrayList<String> token(String kalimat) throws FileNotFoundException, IOException {
        ArrayList<String> listKata = new ArrayList<String>();
        StringTokenizer token = new StringTokenizer(kalimat, ",");//pemisahan kata dengan delimiter koma
        while (token.hasMoreTokens()) {
            listKata.add(token.nextToken());
        }
        return listKata;
    }
 
    public static String[][] saveToArray(ArrayList<String> input) throws FileNotFoundException, IOException{
        String[][] data=new String[input.size()][3];
        for (int i = 0; i < input.size(); i++) {
             ArrayList<String> item=token(input.get(i));
             for (int j = 0; j < item.size(); j++) {
                data[i][j]=item.get(j);//memasukkan data ke dalam array
            }
        }
        return data;
    }
    
    public static void cetak(int data, Double tes, Double training, String ket){
        System.out.println(data+" \t\t"+tes+" \t\t"+training+" \t\t"+ket);
    }
    
    public static void cek(int datake, Double tes, Double training){
        
        if(training.equals(tes)){
            cetak(datake,tes,training,"True");
        } else {
            cetak(datake,tes,training,"Error");
            jum_error++;
        }
    }
    
    public static void cekN(int datake, int k, Double tes, Double[][] training){
        Double max = 0.0;
        Double[][] a  = new Double[4][2];
        
        a[0][1]=0.0;
        a[1][1]=0.0;
        a[2][1]=0.0;
        a[3][1]=0.0;
        
        a[0][0]=1.0;
        a[1][0]=2.0;
        a[2][0]=3.0;
        a[3][0]=4.0;
       
        for(int i=0; i<k; i++){
            if(training[i][2].equals(a[0][0]))
                a[0][1]++;
            else if(training[i][2].equals(a[1][0]))
                a[1][1]++;
            else if(training[i][2].equals(a[2][0]))
                a[2][1]++;
            else if(training[i][2].equals(a[3][0]))
                a[3][1]++;
        }
   
        for(int i=1;i<=4;i++){
            if(i!=4){
                if(a[i][1]<a[i-1][1]){
                    max=a[i-1][0];
                }
            } else {
                if(a[0][1]<a[i-1][1]){
                    max=a[i-1][0];
                }
            }
        }
//        System.out.println(max);
        cek(datake,tes,max);
        
        
        
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<String> learning = readTeks("data_learning.txt");
        ArrayList<String> tes      = readTeks("test_data.txt");
        String[][] dataTraining    = saveToArray(learning);
        String[][] dataTes         = saveToArray(tes);
        Double[][] tabelTraining         = new Double[dataTraining.length][4];
        Double[][] tabelTes          = new Double[dataTes.length][3];
        Double[] jarak         = new Double[dataTraining.length];
        Double[] pilihan = new Double[5];
        int i,j,k;
                
        Scanner sc = new Scanner (System.in);
        System.out.println("============2110161033============");
        System.out.print("Masukkan nilai K : ");
        k=Integer.valueOf(sc.nextLine());
        System.out.println("==================================================================");
        System.out.println("Index \t Label asli \t Setelah training \t Keterangan ");
        System.out.println("==================================================================");

            for(i=0;i<dataTes.length;i++){
                tabelTes[i][0]=Double.parseDouble(dataTes[i][0]);
                tabelTes[i][1]=Double.parseDouble(dataTes[i][1]);
                tabelTes[i][2]=Double.parseDouble(dataTes[i][2]);
               
                
                for(j=0;j<dataTraining.length;j++){
                    tabelTraining[j][0]=Double.parseDouble(dataTraining[j][0]);
                    tabelTraining[j][1]=Double.parseDouble(dataTraining[j][1]);
                    tabelTraining[j][2]=Double.parseDouble(dataTraining[j][2]);
                    //hitung jarak
                    jarak[j]=Math.sqrt(Math.pow((tabelTraining[j][0]-tabelTes[i][0]),2)+Math.pow((tabelTraining[j][1]-tabelTes[i][1]),2));  
                    tabelTraining[j][3]=jarak[j];
                }
                
                Arrays.sort(tabelTraining, new Comparator<Double[]>() {
			@Override
                        //arguments to this method represent the arrays to be sorted   
			public int compare(Double[] hasil1, Double[] hasil2) {
                                //get the item ids which are at index 0 of the array
				Double result1 = hasil1[3]; //yg dibandingkan untuk sorting yakni kolom ke3
				Double result2 = hasil2[3];
				// sort on item id
				return result1.compareTo(result2);
			}
		});
                
                if(k==1){
                    cek(i,tabelTes[i][2],tabelTraining[0][2]);
                }else if(k>1){
                    cekN(i,k,tabelTes[i][2],tabelTraining);
                }
            }
            persentase_error=((double)jum_error/tabelTes.length)*100;
            System.out.println("Persentase Error = "+persentase_error+"%");       
    }           

}

