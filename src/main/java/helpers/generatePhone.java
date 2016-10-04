package helpers;

import java.util.Random;

/**
 * Created by penko.yordanov on 02-Aug-16.
 */
public class generatePhone {
    public static void main(String[] args) {
        System.out.println(getRndNumber());
        for (int i=1;i<1000;i++){
            System.out.println(getRndNumber());
        }
    }

    private static int getRndNumber() {
        Random random=new Random();
        int randomNumber=0;
        boolean loop=true;
        while(loop) {
            randomNumber=random.nextInt();
            if(Integer.toString(randomNumber).length()==10 && !Integer.toString(randomNumber).startsWith("-")) {
                loop=false;
            }
        }
        return randomNumber;
    }
}
