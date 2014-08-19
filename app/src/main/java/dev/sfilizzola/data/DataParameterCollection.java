
package dev.sfilizzola.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dev.sfilizzola.data.DataParameter.DataType;

/**
 * Representa a cole��o de parametros que � utilizada pelo objeto DbManager
 * 
 * @author Samuel Filizzola
 */
public class DataParameterCollection implements List<DataParameter> {

    // Lista que contem os parametros que serao utilizados pelo sistema
    private List<DataParameter> paramList = new ArrayList<DataParameter>();

    @Override
    public boolean add(DataParameter parameter) {

        return paramList.add(parameter);
    }

    @Override
    public void add(int location, DataParameter parameter) {

        paramList.add(location, parameter);
    }

    public boolean add(String pParameterName, DataType pDbType) {

        DataParameter p = new DataParameter(pParameterName, pDbType);
        return this.add(p);
    }

    public boolean add(String pParameterName, DataType pDbType, Object pValue) {

        DataParameter p = new DataParameter(pParameterName, pDbType, pValue);
        return this.add(p);
    }

    @Override
    public boolean addAll(Collection<? extends DataParameter> arg0) {

        return paramList.addAll(arg0);
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends DataParameter> arg1) {

        return paramList.addAll(arg0, arg1);
    }

    @Override
    public void clear() {

        paramList.clear();
    }

    @Override
    public boolean contains(Object object) {

        return paramList.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {

        return paramList.containsAll(arg0);
    }

    @Override
    public DataParameter get(int location) {

        return paramList.get(location);
    }

    public DataParameter get(String name) {

        if (!name.startsWith(":"))
            name = ":" + name;

        for (DataParameter item : paramList) {
            if (item.getParameterName().toLowerCase().equals(name.toLowerCase()))
                return item;
        }
        return null;
    }

    @Override
    public int indexOf(Object object) {

        return paramList.indexOf(object);
    }

    @Override
    public boolean isEmpty() {

        return paramList.isEmpty();
    }

    @Override
    public Iterator<DataParameter> iterator() {

        return paramList.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {

        return paramList.lastIndexOf(object);
    }

    @Override
    public ListIterator<DataParameter> listIterator() {

        return paramList.listIterator();
    }

    @Override
    public ListIterator<DataParameter> listIterator(int location) {

        return paramList.listIterator(location);
    }

    @Override
    public DataParameter remove(int location) {

        return paramList.remove(location);
    }

    @Override
    public boolean remove(Object object) {

        return paramList.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {

        return paramList.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {

        return paramList.retainAll(arg0);
    }

    @Override
    public DataParameter set(int location, DataParameter object) {

        return paramList.set(location, object);
    }

    @Override
    public int size() {

        return paramList.size();
    }

    @Override
    public List<DataParameter> subList(int start, int end) {

        return paramList.subList(start, end);
    }

    @Override
    public Object[] toArray() {

        return paramList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {

        return paramList.toArray(array);
    }

    public void sortParameters() {

        Comparator<DataParameter> comparadorParam = new Comparator<DataParameter>() {

            @Override
            public int compare(DataParameter param1, DataParameter param2) {

                return param2.getParameterName().compareTo(param1.getParameterName());
            }
        };
        Collections.sort(paramList, comparadorParam);
    }

}
