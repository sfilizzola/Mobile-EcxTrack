
package dev.sfilizzola.data.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe que facilita manipula��o e uso de reflection
 * 
 * @author eder.almeida
 */
public class Reflection {

    public Reflection() {
    }

    /**
     * Recebe um objeto de uma classe e o nome do campo a ser recuperado
     * 
     * @param object
     * @param fieldName
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public Object getValue(Object object, String methodName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            SecurityException, NoSuchMethodException {
         
        //Verifica se objeto n�o esta nulo. Se estiver retorna null
        if(object == null)
            return null;
        
        // Verifica se nome tem underline e da split
        int pos = methodName.indexOf("_");
        if(pos != -1)
        {
            String parcName = methodName.substring(0, pos);
            
            // Obtem o objeto pai
            Object objNested = getValue(object, parcName);
            
            // Procura a referencia do proximo objeto dentro do objeto pai
            return getValue(objNested, methodName.substring(pos + 1));
        }
        
        
        // Trata nome da funcao Get e Is
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String methodNameGet = String.format("get%s", methodName);
        String methodNameIs = String.format("is%s", methodName);

        // Objeto que recebe valor de retorno
        Object pReturn = null;
        
        // Faz loops pelas fun��es do objeto a procura de uma das duas possiveis fun��es
        Method[] allMethods = object.getClass().getDeclaredMethods();
        for (Method met : allMethods) {
            if (met.getName().equals(methodNameGet) || met.getName().equals(methodNameIs)) {
                pReturn = met.invoke(object);
                break;
            }
        }

        return pReturn;
    }

}
