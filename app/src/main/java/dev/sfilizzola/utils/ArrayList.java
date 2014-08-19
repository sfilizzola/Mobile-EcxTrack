
package dev.sfilizzola.utils;

import java.util.Collection;
import dev.sfilizzola.utils.Log;

/**
 * ArrayList com suporte a eventos ao inserir, excluir ou substituir items
 * 
 * @author Samuel Filizzola
 * @param <E>
 */
public class ArrayList<E> extends java.util.ArrayList<E> {

    private static final long serialVersionUID = 1L;

    private transient java.util.ArrayList<ArrayListDuplicarPedidoEventListener> oArrayListChangedEventListenerList;

    /**
     * Adiciona um ouvinte ao objeto
     * 
     * @param listener
     */
    public void addItemsChangedListener(ArrayListDuplicarPedidoEventListener listener) {

        if (oArrayListChangedEventListenerList == null)
            oArrayListChangedEventListenerList = new ArrayList<ArrayListDuplicarPedidoEventListener>();
        oArrayListChangedEventListenerList.add(listener);

    }

    /**
     * Remove o ouvinte selecionado da selecao
     * 
     * @param listener
     */
    public void removeItemsChangedListener(ArrayListDuplicarPedidoEventListener listener) {

        if (oArrayListChangedEventListenerList == null)
            return;
        oArrayListChangedEventListenerList.remove(listener);

    }

    /**
     * Dispara o evento para todos os ouvintes validos
     * 
     * @param evt
     */
    private void fireItemsChangedEvent(ArrayListChangedEvent evt) {

        // Se nao houver ouvintes, nao precisa processar
        if (oArrayListChangedEventListenerList == null || oArrayListChangedEventListenerList.isEmpty())
            return;

        Log.v("ArrayList Event", String.format("ArrayList Event was throw. Current arraysize: %s Operation: %s", this.size(), evt.getOperation().toString()));

        // Dispara o evento
        for (ArrayListDuplicarPedidoEventListener listener : oArrayListChangedEventListenerList)
            if (listener != null)
                listener.arrayListChangedOccurred(evt);
    }

    @Override
    public boolean add(E object) {

        boolean retval = super.add(object);
        fireItemsChangedEvent(new ArrayListChangedEvent(object, ArrayListChangedEventType.ADD));
        return retval;
    }

    @Override
    public void add(int index, E object) {

        super.add(index, object);
        fireItemsChangedEvent(new ArrayListChangedEvent(object, ArrayListChangedEventType.ADD));
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {

        boolean retval = super.addAll(collection);
        fireItemsChangedEvent(new ArrayListChangedEvent(collection, ArrayListChangedEventType.ADD));
        return retval;
    }

    @Override
    public boolean addAll(int location, Collection<? extends E> collection) {

        boolean retval = super.addAll(location, collection);
        fireItemsChangedEvent(new ArrayListChangedEvent(collection, ArrayListChangedEventType.ADD));
        return retval;
    }

    @Override
    public E remove(int index) {

        E retval = super.remove(index);
        fireItemsChangedEvent(new ArrayListChangedEvent(retval, ArrayListChangedEventType.REMOVE));
        return retval;
    }

    @Override
    public boolean remove(Object object) {

        boolean retval = super.remove(object);
        fireItemsChangedEvent(new ArrayListChangedEvent(object, ArrayListChangedEventType.REMOVE));
        return retval;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {

        super.removeRange(fromIndex, toIndex);
        fireItemsChangedEvent(new ArrayListChangedEvent(null, ArrayListChangedEventType.REMOVE));
    }

    public void replace(Object objectOld, E objectNew) {

        super.remove(objectOld);
        super.add(objectNew);
        fireItemsChangedEvent(new ArrayListChangedEvent(objectNew, ArrayListChangedEventType.REPLACE));
    }

    public void replace(Object objectOld, int indexNew, E objectNew) {

        super.remove(objectOld);
        super.add(indexNew, objectNew);
        fireItemsChangedEvent(new ArrayListChangedEvent(objectNew, ArrayListChangedEventType.REPLACE));
    }

}
