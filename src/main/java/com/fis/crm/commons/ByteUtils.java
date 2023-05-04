package com.fis.crm.commons;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteUtils
{

    public ByteUtils()
    {
    }

    public static byte[] asciiStringToBytes(String s)
    {
        byte b[] = null;
        if(s != null)
        {
            b = new byte[s.length()];
            asciiStringToBytes(s, b);
        }
        return b;
    }

    public static void asciiStringToBytes(String s, byte b[])
    {
        int len = s.length() <= b.length ? s.length() : b.length;
        for(int i = 0; i < len; i++)
            b[i] = (byte)s.charAt(i);

    }

    public static String bytesToASCIIString(byte b[])
    {
        StringBuffer sb = new StringBuffer(b.length);
        for(int i = 0; i < b.length; i++)
            sb.append((char)((char)b[i] & 0xff));

        return sb.toString();
    }

    public static byte[] stringToBytes(String s)
    {
        return stringToBytes(s, 0);
    }

    public static byte[] stringToBytes(String s, int minLength)
    {
        if(s.length() % 2 != 0)
            s = "0".concat(s);
        int len = s.length() / 2;
        int padLength = len;
        if(padLength < minLength)
            padLength = minLength;
        int offset = padLength - len;
        byte b[] = new byte[padLength];
        for(int i = 0; i < len; i++)
        {
            b[i + offset] = (byte)(hexToInt(s.charAt(i * 2)) << 4);
            b[i + offset] = (byte)(b[i + offset] | hexToInt(s.charAt(i * 2 + 1)) & 0xf);
        }

        return b;
    }

    public static void stringToBytes(String s, byte b[])
    {
        if(s.length() % 2 != 0)
            s = "0".concat(s);
        int len = s.length() / 2;
        if(len > b.length)
            len = b.length;
        for(int i = 0; i < len; i++)
        {
            b[i] = (byte)(hexToInt(s.charAt(i * 2)) << 4);
            b[i] = (byte)(b[i] | hexToInt(s.charAt(i * 2 + 1)) & 0xf);
        }

    }

    public static String bytesToString(byte b[])
    {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for(int i = 0; i < b.length; i++)
            sb.append(bch[b[i] & 0xff]);

        return sb.toString();
    }

    public static char intToHex(int i)
    {
        return hex[i & 0xf];
    }

    public static int hexToInt(char c)
    {
        return ascii[c & 0x7f];
    }

    public static byte[] ensureDESParity(byte desKey[])
    {
        for(int i = 0; i < desKey.length; i++)
            desKey[i] = setOddParity(desKey[i]);

        return desKey;
    }

    public static byte setOddParity(byte b)
    {
        b &= 0xfe;
        int setBits = 0;
        for(int i = 0; i < 8; i++)
            if((b >> i & 1) != 0)
                setBits++;

        if(setBits % 2 == 0)
            b |= 1;
        return b;
    }

    public static void bytesToFile(byte b[], String filename)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            byte buffer[] = new byte[1024];
            int bytesRead;
            while((bytesRead = bis.read(buffer)) != -1)
                fos.write(buffer, 0, bytesRead);
        }
        catch(IOException e) { }
    }

    public static String bytesToDebugString(byte b[])
    {
        StringBuffer sb = new StringBuffer();
        if(b != null && b.length > 0)
        {
            sb.append((new StringBuilder()).append("0x").append(bch[b[0] & 0xff]).toString());
            for(int i = 1; i < b.length; i++)
                sb.append((new StringBuilder()).append(" 0x").append(bch[b[i] & 0xff]).toString());

        }
        return sb.toString();
    }

    public static String bytesToDebugString(byte b[], int length)
    {
        StringBuffer sb = new StringBuffer();
        if(b != null && b.length > 0 && length > 0)
        {
            if(length > b.length)
                length = b.length;
            sb.append((new StringBuilder()).append("0x").append(bch[b[0] & 0xff]).toString());
            for(int i = 1; i < length; i++)
                sb.append((new StringBuilder()).append(" 0x").append(bch[b[i] & 0xff]).toString());

        }
        return sb.toString();
    }

    private static final char hex[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final int ascii[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        2, 3, 4, 5, 6, 7, 8, 9, 0, 0,
        0, 0, 0, 0, 0, 10, 11, 12, 13, 14,
        15, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 10, 11, 12,
        13, 14, 15, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };
    private static final String bch[] = {
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
        "0A", "0B", "0C", "0D", "0E", "0F", "10", "11", "12", "13",
        "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D",
        "1E", "1F", "20", "21", "22", "23", "24", "25", "26", "27",
        "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
        "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B",
        "3C", "3D", "3E", "3F", "40", "41", "42", "43", "44", "45",
        "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
        "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
        "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63",
        "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D",
        "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77",
        "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81",
        "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B",
        "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95",
        "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
        "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9",
        "AA", "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3",
        "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD",
        "BE", "BF", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7",
        "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1",
        "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB",
        "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4", "E5",
        "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF",
        "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
        "FA", "FB", "FC", "FD", "FE", "FF"
    };

}
