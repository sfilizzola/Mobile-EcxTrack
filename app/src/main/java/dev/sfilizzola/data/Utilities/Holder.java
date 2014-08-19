
package dev.sfilizzola.data.Utilities;


/**
 * Classe que encapsula um determinado valor. Utilizada como alternativa para os parametros out, que nao existem no java.
 * 
 * @author Samuel Filizzola
 * @param <T>
 */
public class Holder<T> {

    public Holder(T value) {

        this.value = value;
    }

    public Holder() {

    }

    public T value;
}
