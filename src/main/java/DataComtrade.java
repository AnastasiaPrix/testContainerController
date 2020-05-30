import java.io.*;

public class DataComtrade {

    private File comtrCfg;
    private File comtrDat;
    private BufferedReader bufferedReader;
    private String line;
    private String[] lineData;
    private boolean f = true;

    private checkKZ kzA;
    private checkKZ kzB;
    private checkKZ kzC;
    private boolean trip = false;



    private double k1[]= new double [3];
    private double k2[]=new double [3];
    private double[] realIs = new double[3];
    private double[] realUs = new double[3];
    private double[] imagIs = new double[3];
    private double[] imagUs = new double[3];
    private boolean ABflag = false;
    private boolean BCflag = false;
    private boolean CAflag = false;



    public DataComtrade(String path, String file) {
        comtrCfg = new File(path+file+".cfg");
        comtrDat = new File(path+file+".dat");
    }

    public void run() {
        try {
            bufferedReader = new BufferedReader(new FileReader(comtrCfg));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int lineNum = 0;
            while((line = bufferedReader.readLine()) != null) {
                if (lineNum == 2) {
                    lineData = line.split(",");
                    k1[0]= Double.parseDouble(lineData[5]);
                    k2[0]= Double.parseDouble(lineData[6]);

                }
                if (lineNum == 3) {
                    lineData = line.split(",");
                    k1[1]= Double.parseDouble(lineData[5]);
                    k2[1]= Double.parseDouble(lineData[6]);
                }
                if (lineNum == 4) {
                    lineData = line.split(",");
                    k1[2]= Double.parseDouble(lineData[5]);
                    k2[2]= Double.parseDouble(lineData[6]);
                }
                lineNum++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //_________________________________________________________________________________________________
        try {
            bufferedReader = new BufferedReader( new FileReader(comtrDat));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Furie fIa = new Furie();
        Furie fIb = new Furie();
        Furie fIc = new Furie();

        Double Iainst;
        Double Ibinst;
        Double Icinst;
        Double rmsA;
        Double rmsB;
        Double rmsC;

        try {
            while(((bufferedReader.readLine()) != null) && f) {
                line = bufferedReader.readLine();
                lineData = line.split(",");

                Iainst = Double.parseDouble(lineData[2])*k1[0]+k2[0];
                Ibinst = Double.parseDouble(lineData[3])*k1[1]+k2[1];
                Icinst = Double.parseDouble(lineData[4])*k1[2]+k2[2];
                rmsA=fIa.getRMS(Iainst);
                rmsB=fIb.getRMS(Ibinst);
                rmsC=fIc.getRMS(Icinst);
                if (fIa.wait80() == true) {
                    if (kzA.chekTrip(rmsA)|| kzB.chekTrip(rmsB)|| kzC.chekTrip(rmsC)){
                        trip = true;
                      //  System.out.println("trip!!!!!!!!!!!!!!!!!!!!!!!!!!");
                         f = false;
//                         for(int i=0; i<fIa.getLast80().length; i++){
                           // System.out.println(fIa.getLast80()[i]);
//                         }
                         break;
                    }
                }
               }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setKzA(checkKZ r){
        this.kzA = r;
    }

    public void setKzB(checkKZ r){
        this.kzB = r;
    }

    public void setKzC(checkKZ r){
        this.kzC= r;
    }

    public boolean isTrip() {
        return trip;
    }
}
