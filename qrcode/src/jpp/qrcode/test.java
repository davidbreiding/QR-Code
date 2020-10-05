package jpp.qrcode;

import jpp.qrcode.encode.Encoder;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {

        while(true){
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Wollen sie einen neuen QRCode erstellen? [y/n]");
                    String yn = sc.next();
                    if (yn.equals("n")) {
                        System.out.println("Programm wird beendet");
                        sc.close();
                        break;
                    } else if (yn.equals("y")) {
                        System.out.println("Bitte Inhalt von QRCode eingeben:");
                        String msg = sc.next();
                        System.out.println("Fehlerkorrekturlevel festlegen:");
                        System.out.println("1 -> High");
                        System.out.println("2 -> Medium");
                        System.out.println("3 -> Low");
                        System.out.println("4 -> Quartile");

                        ErrorCorrection errorCorrection = null;

                        String level = sc.next();

                        if (level.equals("1"))
                            errorCorrection = ErrorCorrection.HIGH;
                        else if (level.equals("2"))
                            errorCorrection = ErrorCorrection.MEDIUM;
                        else if (level.equals("3"))
                            errorCorrection = ErrorCorrection.LOW;
                        else if (level.equals("4"))
                            errorCorrection = ErrorCorrection.QUARTILE;

                        System.out.println("Ihr QRCode wird erstellt...");

                        System.out.println(createCode(msg, errorCorrection));


                    } else {
                        System.out.println("Bitte y für ja, n für nein eingeben und bestätigen");
                        continue;
                    }
                }
    }


    public static String createCode(String msg, ErrorCorrection errorCorrection){
        QRCode code = Encoder.createFromString(msg, errorCorrection);
        return code.matrixToString();
    }
}