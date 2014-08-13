
package dev.sfilizzola.data.Utilities;

/**
 * @author R�gis Daniel de Oliveira (Conversao do Codigo JS original de M�rcio d'�vila) Este script foi retirado de: http://www.mhavila.com.br/topicos/web/cpf_cnpj.html Licenciado sob os termos da
 *         licen�a Creative Commons, Atribui��o - Compartilhamento pela mesma licen�a 2.5: http://creativecommons.org/licenses/by-sa/2.5/br/ Qualquer outra forma de uso requer autoriza��o expressa do
 *         autor. PROT�TIPOS: metodo String.lpad(int pSize, char pCharPad) método String.trim() String unformatNumber(String pNum) String formatCpfCnpj(String pCpfCnpj, boolean pUseSepar, boolean
 *         pIsCnpj) String dvCpfCnpj(String pEfetivo, boolean pIsCnpj) boolean isCpf(String pCpf) boolean isCnpj(String pCnpj) boolean isCpfCnpj(String pCpfCnpj)
 */
public class Validacoes {

    private final static int NUM_DIGITOS_CPF = 11;
    private final static int NUM_DIGITOS_CNPJ = 14;
    private final static int NUM_DGT_CNPJ_BASE = 8;

    public static String RPad(String str, Integer length, char car) {

        if (str.length() >= length)
            return str;

        return str + String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car));

    }

    public static String LPad(String str, Integer length, char car) {

        if (str.length() >= length)
            return str;

        return String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)) + str;

    }

    /**
     * Elimina caracteres de formata��o e zeros � esquerda da string de n�mero fornecida.
     * 
     * @param String pNum String de n�mero fornecida para ser desformatada.
     * @return String de n�mero desformatada.
     */
    public static String unformatNumber(String pNum) {

        return pNum.replaceAll("\\D|^0+", "");

    }

    public static String formatCpfCnpj(String pCpfCnpj, Boolean pUseSepar) {

        Boolean isCnpj;
        String numero = unformatNumber(pCpfCnpj);
        if (numero.length() > NUM_DIGITOS_CPF)
            isCnpj = true;
        else
            isCnpj = false;

        return formatCpfCnpj(pCpfCnpj, pUseSepar, isCnpj);

    }

    /**
     * Formata a string fornecida como CNPJ ou CPF, adicionando zeros � esquerda se necess�rio e caracteres separadores, conforme solicitado.
     * 
     * @param String pCpfCnpj String fornecida para ser formatada.
     * @param boolean pUseSepar Indica se devem ser usados caracteres separadores (. - /).
     * @param boolean pIsCnpj Indica se a string fornecida � um CNPJ. Caso contr�rio, � CPF. Default = false (CPF).
     * @return String de CPF ou CNPJ devidamente formatada.
     */
    public static String formatCpfCnpj(String pCpfCnpj, Boolean pUseSepar, Boolean pIsCnpj) {

        if (pIsCnpj == null)
            pIsCnpj = false;
        if (pUseSepar == null)
            pUseSepar = true;
        int maxDigitos = pIsCnpj ? NUM_DIGITOS_CNPJ : NUM_DIGITOS_CPF;
        String numero = unformatNumber(pCpfCnpj);

        numero = LPad(numero, maxDigitos, '0');

        if (!pUseSepar)
            return numero;

        if (pIsCnpj) {
            String reCnpj = "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})$";
            numero = numero.replaceAll(reCnpj, "$1.$2.$3/$4-$5");
        } else {
            String reCpf = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})$";
            numero = numero.replaceAll(reCpf, "$1.$2.$3-$4");
        }
        return numero;
    } // formatCpfCnpj

    /**
     * Calcula os 2 d�gitos verificadores para o n�mero-efetivo pEfetivo de CNPJ (12 d�gitos) ou CPF (9 d�gitos) fornecido. pIsCnpj � booleano e informa se o n�mero-efetivo fornecido � CNPJ (default =
     * false).
     * 
     * @param String pEfetivo String do n�mero-efetivo (SEM d�gitos verificadores) de CNPJ ou CPF.
     * @param boolean pIsCnpj Indica se a string fornecida � de um CNPJ. Caso contr�rio, � CPF. Default = false (CPF).
     * @return String com os dois d�gitos verificadores.
     */
    public static String dvCpfCnpj(String pEfetivo, Boolean pIsCnpj) {

        if (pIsCnpj == null)
            pIsCnpj = false;
        int i, j, k, soma, dv;
        int cicloPeso = pIsCnpj ? NUM_DGT_CNPJ_BASE : NUM_DIGITOS_CPF;
        int maxDigitos = pIsCnpj ? NUM_DIGITOS_CNPJ : NUM_DIGITOS_CPF;
        String calculado = formatCpfCnpj(pEfetivo + "00", false, pIsCnpj);
        calculado = calculado.substring(0, maxDigitos - 2);
        String result = "";

        for (j = 1; j <= 2; j++) {
            k = 2;
            soma = 0;
            for (i = calculado.length() - 1; i >= 0; i--) {
                soma += (calculado.charAt(i) - '0') * k;
                k = (k - 1) % cicloPeso + 2;
            }
            dv = 11 - soma % 11;
            if (dv > 9)
                dv = 0;
            calculado += dv;
            result += dv;
        }

        return result;
    } // dvCpfCnpj

    /**
     * Testa se a String pCpf fornecida � um CPF v�lido. Qualquer formata��o que n�o seja algarismos � desconsiderada.
     * 
     * @param String pCpf String fornecida para ser testada.
     * @return <code>true</code> se a String fornecida for um CPF v�lido.
     */
    public static boolean isCpf(String pCpf) {

        String numero = formatCpfCnpj(pCpf, false, false);
        if (numero.length() > NUM_DIGITOS_CPF)
            return false;

        String base = numero.substring(0, numero.length() - 2);
        String digitos = dvCpfCnpj(base, false);
        boolean algUnico;
        int i;

        // Valida d�gitos verificadores
        if (!numero.equals(base + digitos))
            return false;

        /*
         * N�o ser�o considerados v�lidos os seguintes CPF: 000.000.000-00, 111.111.111-11, 222.222.222-22, 333.333.333-33, 444.444.444-44, 555.555.555-55, 666.666.666-66, 777.777.777-77,
         * 888.888.888-88, 999.999.999-99.
         */
        algUnico = true;
        for (i = 1; algUnico && i < NUM_DIGITOS_CPF; i++) {
            algUnico = (numero.charAt(i - 1) == numero.charAt(i));
        }
        return (!algUnico);
    } // isCpf

    /**
     * Testa se a String pCnpj fornecida � um CNPJ v�lido. Qualquer formata��o que n�o seja algarismos � desconsiderada.
     * 
     * @param String pCnpj String fornecida para ser testada.
     * @return <code>true</code> se a String fornecida for um CNPJ v�lido.
     */
    public static boolean isCnpj(String pCnpj) {

        String numero = formatCpfCnpj(pCnpj, false, true);
        if (numero.length() > NUM_DIGITOS_CNPJ)
            return false;

        String base = numero.substring(0, NUM_DGT_CNPJ_BASE);
        String ordem = numero.substring(NUM_DGT_CNPJ_BASE, 12);
        String digitos = dvCpfCnpj(base + ordem, true);
        boolean algUnico;
        int i;

        // Valida d�gitos verificadores
        if (!numero.equals(base + ordem + digitos))
            return false;

        /*
         * N�o ser�o considerados v�lidos os CNPJ com os seguintes n�meros B�SICOS: 11.111.111, 22.222.222, 33.333.333, 44.444.444, 55.555.555, 66.666.666, 77.777.777, 88.888.888, 99.999.999.
         */
        algUnico = numero.charAt(0) != '0';
        for (i = 1; algUnico && i < NUM_DGT_CNPJ_BASE; i++) {
            algUnico = (numero.charAt(i - 1) == numero.charAt(i));
        }
        if (algUnico)
            return false;

        /*
         * N�o ser� considerado v�lido CNPJ com n�mero de ORDEM igual a 0000. N�o ser� considerado v�lido CNPJ com n�mero de ORDEM maior do que 0300 e com as tr�s primeiras posi��es do n�mero B�SICO
         * com 000 (zeros). Esta cr�tica n�o ser� feita quando o no B�SICO do CNPJ for igual a 00.000.000.
         */
        if (ordem == "0000")
            return false;
        return (base == "00000000" || Integer.parseInt(ordem, 10) <= 300 || base.substring(0, 3) != "000");
    } // isCnpj

    /**
     * Testa se a String pCpfCnpj fornecida � um CPF ou CNPJ v�lido. Se a String tiver uma quantidade de d�gitos igual ou inferior a 11, valida como CPF. Se for maior que 11, valida como CNPJ.
     * Qualquer formata��o que n�o seja algarismos � desconsiderada.
     * 
     * @param String pCpfCnpj String fornecida para ser testada.
     * @return <code>true</code> se a String fornecida for um CPF ou CNPJ v�lido.
     */
    public static boolean isCpfCnpj(String pCpfCnpj) {

        String numero = unformatNumber(pCpfCnpj);
        if (numero.length() > NUM_DIGITOS_CPF)
            return isCnpj(pCpfCnpj);
        else
            return isCpf(pCpfCnpj);
    } // isCpfCnpj

}
