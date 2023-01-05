import java.util.StringTokenizer;

public class NetTest {
    public static void main(String[] args) {
    /*    System.out.println(Integer.toBinaryString((1<<8)));
        System.out.println(Integer.toBinaryString((1<<8) | 8));
        System.out.println(Integer.toBinaryString((1<<8) | 8).substring(1));*/
//        System.out.print(Integer.toBinaryString((1<<8) | 192).substring(1)+".");
//        System.out.print(Integer.toBinaryString((1<<8) | 168).substring(1)+".");
//        System.out.print(Integer.toBinaryString((1<<8) | 8).substring(1)+".");
//        System.out.println(Integer.toBinaryString((1<<8) | 1).substring(1));
//        System.out.println(Integer.toBinaryString((1<<8)).substring(0));
//        System.out.println(Integer.toBinaryString((1<<7)).substring(0));
//        System.out.println(binaryIpAddress("192.168.8.1"));
        int x=8;
        String s = Integer.toBinaryString(x);
        System.out.println(s);


    }
    public static String binaryIpAddress(String decimalIpAddress){
        //divide 4bits by 4bits
        StringTokenizer tokenizer = new StringTokenizer(decimalIpAddress, ".");
       /* System.out.println(tokenizer.nextToken());
        System.out.println(tokenizer.nextToken());
        System.out.println(tokenizer.nextToken());
        System.out.println(tokenizer.nextToken());*/
        if(tokenizer.countTokens()!=4) throw new RuntimeException("Invalid IP Address");
        while (tokenizer.hasMoreTokens()){
            System.out.println(tokenizer.nextToken());
        }


        return null;
    }


}
