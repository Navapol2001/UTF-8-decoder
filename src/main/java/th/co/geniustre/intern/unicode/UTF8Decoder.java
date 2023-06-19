package th.co.geniustre.intern.unicode;

import java.util.ArrayList;
import java.util.List;

public class UTF8Decoder extends Unicode {
    public String decode(byte[] utf8s) {
        UTF8Decoder decoder = new UTF8Decoder();
        //1110_xxxx 10xx_xxxx 10xx_xxxx
        //1110_0000 1011_1000 1000_0001 -> ก -> 3585 = 0000_111000_000001
        //1110_0000 1011_1000 1000_0010 -> ข -> 3586 = 0000_111000_000010
        //1110_0000 1011_1000 1000_0011 -> ค -> 3588 = 0000_111000_000100
        //1110_0000 1011_1000 1000_0100 -> ง -> 3591 = 0000_111000_000101

        //record each byte of utf8s to binary ArrayList
        List<String> binaryList= new ArrayList<>();
        for (byte utf8 : utf8s) {
            String binaryPerByte = Integer.toBinaryString(utf8).substring(24);
            binaryList.add(binaryPerByte);
        }


        //UTF-8 algorithm to decode byte to code point in decimal
        List<String> decodePart = new ArrayList<>(); //record last 4 or 6 bits of each byte
        for (String binary : binaryList) {
            String firstTwoBit = binary.substring(0, 2); //check first two bit of each byte
            if (firstTwoBit.equals("11")) { //if first two bit is 11, get last 4 bits
                String lastFourBit = binary.substring(4);
                decodePart.add(lastFourBit);
            } else if (firstTwoBit.equals("10")) { //if first two bit is 10, get last 6 bits
                String lastSixBit = binary.substring(2);
                decodePart.add(lastSixBit);
            }
        }

        //concatenate string every three parts of bitForCodePoint to get code point
        List<String> codePoint = new ArrayList<>();
        for (int i = 0; i < decodePart.size(); i+=3) {
            String part = decodePart.get(i) + decodePart.get(i + 1) + decodePart.get(i + 2);
            codePoint.add(part);
        }

        //convert code point string of binary to int and get character
        StringBuilder alphabets = new StringBuilder();
        for (String binary : codePoint) {
            int valueOfCodePoint = Integer.parseInt(binary, 2);
            System.out.println(decoder.getChar(valueOfCodePoint) + " code point: " + valueOfCodePoint);
            alphabets.append(decoder.getChar(valueOfCodePoint));
        }
        System.out.println("UTF-8: " + alphabets);
        return alphabets.toString();

    }

    public static void main(String[] args) {
        UTF8Decoder decoder = new UTF8Decoder();
        byte[] utfBytes = new byte[]{(byte) 0xE0, (byte) 0xB8, (byte) 0x81, (byte) 0xE0, (byte) 0xB8, (byte) 0x82,(byte) 0xE0, (byte) 0xB8, (byte) 0x84, (byte) 0xE0, (byte) 0xB8, (byte) 0x87};
        String word = decoder.decode(utfBytes);
        System.out.println(word);

   }

}
