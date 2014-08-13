
package MaximaSistemas.Android.Data.Utilities;

import java.security.MessageDigest;

public class Cryptography {

    /**
     * Faz a encriptacao de uma string
     * 
     * @param String a ser encriptada
     * @return Resultado da encriptacao
     */
    public String Encrypt(String value) {

        try {
            MessageDigest messagedigest;
            messagedigest = MessageDigest.getInstance("SHA");
            messagedigest.update(value.getBytes());
            byte digest[] = messagedigest.digest();
            return GetAsHexaDecimal(digest).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private String GetAsHexaDecimal(byte[] bytes) {

        StringBuffer s = new StringBuffer(bytes.length * 2);
        int length = bytes.length;
        for (int n = 0; n < length; n++) {
            int number = bytes[n];
            number = (number < 0) ? (number + 256) : number; // shift to positive range
            if (Integer.toString(number, 16).length() == 1)
                s.append("0" + Integer.toString(number, 16));
            else
                s.append(Integer.toString(number, 16));
        }
        return s.toString();
    }
}
