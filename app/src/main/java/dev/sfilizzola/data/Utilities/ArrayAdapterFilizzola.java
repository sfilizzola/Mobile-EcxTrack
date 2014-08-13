
package dev.sfilizzola.data.Utilities;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class ArrayAdapterFilizzola<T> extends ArrayAdapter<T> {

    /** Tamanho do resultado */
    public final static int SIZE = 200;

    /** Responsavel por informar se existem mais itens na cole��o */
    protected boolean possuiItens = true;

    /** Lista de objetos genericos usados para popular o adapter */
    protected List<T> items;

    /**
     * Construtor da classe
     * 
     * @param context
     * @param textViewResourceId
     * @param items Lista de objetos que � copiada para atribulto da classe
     */
    public ArrayAdapterFilizzola(Context context, int textViewResourceId, List<T> items) {

        super(context, textViewResourceId, items);
        this.items = items;
    }

    /**
     * Adiciona uma cole��o de itens ao Adapter
     * 
     * @param lista generica de objetos
     */
    public void addAll(List<T> list) {

        items.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * Fun��o que limpa a lista atual e insere uma nova
     * 
     * @param lista generica de objetos
     */
    public void cleanAndAddAll(List<T> list) {

        items.clear();
        // Insere novos itens
        this.addAll(list);
    }

    /**
     * Classe que deve ser implementada com as informa��es para acesso aos dados do objeto especifico
     */
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
