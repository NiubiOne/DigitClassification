package PedestrianDetection;

public class HistogramOfGradients {
      //  90 90 45 135 90 90 90 90
      //  0 0 0 45 90 90 90 90
      //  90 90 90 0 0 0 0 0
      //  0 0 0 0 0 0 0 0
     //   0 0 0 0 90 0 0 0
       // 0 90 90 45 90 135 0 0
      //  0 0 90 90 0 0 0 0
      //  0 135 135 0 45 45 0 0
    public static int[] histogramOfGradients(int x,int y,int[][] gradientMagnitude, int[][] gradientDirection) {
        int[] vector = new int[9];
            for (int i=0;i<8;i++){
                for (int j=0;j<8;j++){
                    int bin,bin1=0;

                    if(gradientDirection[y+i][x+j]>160) {
                        bin = gradientDirection[y + i][x + j] / 20;
                        double percent = 20.0 / (gradientDirection[y + i][x + j] % 20);
                        double cut = (double) gradientMagnitude[y + i][x + j] / percent;
                        vector[bin] += (int) (gradientMagnitude[y + i][x + j] - cut);
                        vector[bin1] += (int) cut;
                    }else if ((gradientDirection[y+i][x+j]%20) ==0) {
                        bin = gradientDirection[y + i][x + j] / 20;
                        vector[bin] += (gradientMagnitude[y + i][x + j]);
                    }

                   else{
                       bin = gradientDirection[y+i][x+j]/20;
                       bin1 = bin+1;
                       double percent = 20.0 / (gradientDirection[y+i][x+j] % 20);
                       double cut = (double)gradientMagnitude[y+i][x+j] /percent;
                       vector[bin]+=(int)(gradientMagnitude[y+i][x+j]- cut);
                       vector[bin1]+=(int) cut;
                   }


                }
            }

        return vector;
    }
}
