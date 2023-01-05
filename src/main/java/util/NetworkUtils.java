package util;

import java.util.StringTokenizer;

public class NetworkUtils {
    public static void main(String[] args) {
     /*   System.out.println(binaryIpAddress("192.168.8.1"));
        System.out.println(binaryIpAddress("192.168.8.2"));
        System.out.println(binaryIpAddress("255.255.255.0"));*/
        System.out.println(isWithinNetwork("192.168.8.0",23,"192.168.9.1"));//t
        System.out.println(isWithinNetwork("192.168.8.0",23,"192.168.9.1"));//t
        System.out.println(isWithinNetwork("192.168.8.0",24,"192.168.10.1"));//f
        System.out.println(isWithinNetwork("192.168.8.0",23,"210.168.10.1"));//f
    }

    public static boolean isWithinNetwork(String networkIpAddress,int subnetMask,String ipAddress){
        String binarynNetworkIpAddress = binaryIpAddress(networkIpAddress);
       // String binarySubnetMask = binaryIpAddress(subnetMask);
        String binaryIpAddress = binaryIpAddress(ipAddress);
        System.out.println(binarynNetworkIpAddress);
        //System.out.println(binarySubnetMask);
        System.out.println(binaryIpAddress);
       /* System.out.println(binarySubnetMask.replaceAll("0",""));
        System.out.println(binarySubnetMask.replaceAll("0","").length());
        System.out.println(binarySubnetMask.indexOf("0"));
        System.out.println(binarySubnetMask.lastIndexOf('1')+1);
        int networkBitsLength = binarySubnetMask.replaceAll("0", "").length();*/
      /*  int count=0;
        for (char c:binarySubnetMask.toCharArray()){
            if(c=='1') count++;
        }
        System.out.println(count);*/
        int networkBitsLength = subnetMask;
        String networkPortion = binarynNetworkIpAddress.substring(0, networkBitsLength);
        System.out.println(networkPortion);
        return binaryIpAddress.substring(0,networkBitsLength).equals(networkPortion);





      /*  String networkBinary = octetBinaryForm(Integer.parseInt(networkIpAddress));
        String subnetBinary = octetBinaryForm(Integer.parseInt(subnetMask));
        String ipBinary = octetBinaryForm(Integer.parseInt(ipAddress));
        if (networkBinary==ipBinary) return true;
        for (int i =binaryIpAddress(networkBinary).length()-1 ;i<0; i++) {

        }
       */



    }

    private static String octetBinaryForm(int octet){
        return Integer.toBinaryString((1<<8) | octet).substring(1);

    }
    public static String binaryIpAddress(String decimalIpAddress){
        //divide 4bits by 4bits
        StringTokenizer tokenizer = new StringTokenizer(decimalIpAddress, ".");
        if(tokenizer.countTokens()!=4) throw new RuntimeException("Invalid IP Address");
        StringBuilder sb = new StringBuilder();
        while (tokenizer.hasMoreTokens()){
            //System.out.println(tokenizer.nextToken());
            sb.append(octetBinaryForm(Integer.parseInt(tokenizer.nextToken())));
//            if(tokenizer.hasMoreTokens()) sb.append(".");
        }
        return sb.toString();
    }

}


