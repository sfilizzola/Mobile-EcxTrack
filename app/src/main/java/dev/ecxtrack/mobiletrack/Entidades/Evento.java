package dev.ecxtrack.mobiletrack.Entidades;

/**
 * Created by samuel.filizzola on 19/08/2014.
 */
public class Evento {

    private int CodCliente;
    private int CodEquipamento;
    private int CodEvento;
    private int CodVeiculo;
    private int Hodometro;
    private long Latitude;
    private long Longitude;

    public Evento(){}

    public int getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(int codCliente) {
        CodCliente = codCliente;
    }

    public int getCodEquipamento() {
        return CodEquipamento;
    }

    public void setCodEquipamento(int codEquipamento) {
        CodEquipamento = codEquipamento;
    }

    public int getCodEvento() {
        return CodEvento;
    }

    public void setCodEvento(int codEvento) {
        CodEvento = codEvento;
    }

    public int getCodVeiculo() {
        return CodVeiculo;
    }

    public void setCodVeiculo(int codVeiculo) {
        CodVeiculo = codVeiculo;
    }

    public int getHodometro() {
        return Hodometro;
    }

    public void setHodometro(int hodometro) {
        Hodometro = hodometro;
    }

    public long getLatitude() {
        return Latitude;
    }

    public void setLatitude(long latitude) {
        Latitude = latitude;
    }

    public long getLongitude() {
        return Longitude;
    }

    public void setLongitude(long longitude) {
        Longitude = longitude;
    }
}
