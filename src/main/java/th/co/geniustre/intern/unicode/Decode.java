package th.co.geniustre.intern.unicode;

import java.util.ArrayList;
import java.util.List;

public class Decode extends Unicode {


    public static String decode(byte[] utf8Bytes) {
        Decode decoder = new Decode();
        //parse utf8s to codePoint
        List<String> codePointList = new ArrayList<>();
        for (int i = 0; i < utf8Bytes.length; i+=3) {
            int codePoint = 0;
            for (int j = 0; j < 3; j++) {
                codePoint = codePoint << 8;
                codePoint += utf8Bytes[i + j] & 0xFF;
            }
            //parse codePoint string of binary to int
            String codePointString = Integer.toBinaryString(codePoint);
            codePointList.add(codePointString);
        }
        System.out.println(codePointList);

        List<String> decode = new ArrayList<>();
        for ( String codePointString : codePointList) {
            //split codePointString string to 3 parts each 8 bits
            int partition = 8;
            for (int i = 0; i < codePointString.length(); i += partition) {
                String part = codePointString.substring(i, i + partition);

                //check first two bit of each part
                String firstTwoBit = part.substring(0, 2);
                if (firstTwoBit.equals("11")) {
                    String lastFourBit = part.substring(4);
                    decode.add(lastFourBit);
                } else if (firstTwoBit.equals("10")) {
                    String lastSixBit = part.substring(2);
                    decode.add(lastSixBit);
                }
            }
        }


        //concatenate string every three parts of bitForCodePoint to get code point
        List<String> codePoint = new ArrayList<>();
        for (int i = 0; i < decode.size(); i+=3) {
            String part = decode.get(i) + decode.get(i + 1) + decode.get(i + 2);
            codePoint.add(part);
        }
        System.out.println(codePoint);

        //convert code point to alphabets
        StringBuilder alphabets = new StringBuilder();
        for (String binary : codePoint) {
            int valueOfCodePoint = Integer.parseInt(binary, 2);
            System.out.println(decoder.getChar(valueOfCodePoint) + " code point: " + valueOfCodePoint);
            alphabets.append(decoder.getChar(valueOfCodePoint));
        }

        return alphabets.toString();
    }
    public static void main(String[] args) {
        byte[] utfBytes = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x81, (byte) 0xE0, (byte) 0xB8, (byte) 0x82,(byte) 0xE0, (byte) 0xB8, (byte) 0x84, (byte) 0xE0, (byte) 0xB8, (byte) 0x87};
        byte[] a = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x81};
        byte[] b = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x82};
        byte[] c = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x84};
        byte[] d = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x87};
        String word1 = decode(utfBytes);
        System.out.println(word1);
        String word5 = decode(a);
        System.out.println(word5);
        String word2 = decode(b);
        System.out.println(word2);
        String word3 = decode(c);
        System.out.println(word3);
        String word4 = decode(d);
        System.out.println(word4);
    }
}
