package dev.ecxtrack.mobiletrack.Entidades;

/**
 * Created by samuel.filizzola on 19/08/2014.
 */
public class Evento {

    private Integer CodCliente;
    private Integer CodEquipamento;
    private Integer CodEvento;
    private Integer CodVeiculo;
    private Integer Hodometro;
    private Double Latitude;
    private Double Longitude;

    public Evento(){
        CodCliente = 0;
        CodEquipamento = 0;
        CodEvento = 0;
        CodVeiculo = 0;
        Hodometro = 0;
        Latitude = 0.0;
        Longitude = 0.0;
    }

    public Integer getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(Integer codCliente) {
        if (codCliente == null)
            codCliente = 0;
        CodCliente = codCliente;
    }

    public Integer getCodEquipamento() {
        return CodEquipamento;
    }

    public void setCodEquipamento(Integer codEquipamento) {
        if (codEquipamento == null)
            codEquipamento = 0;
        CodEquipamento = codEquipamento;
    }

    public Integer getCodEvento() {
        return CodEvento;
    }

    public void setCodEvento(Integer codEvento) {
        if (codEvento == null)
            codEvento = 0;
        CodEvento = codEvento;
    }

    public Integer getCodVeiculo() {
        return CodVeiculo;
    }

    public void setCodVeiculo(Integer codVeiculo) {
        if (codVeiculo == null)
            codVeiculo = 0;
        CodVeiculo = codVeiculo;
    }

    public Integer getHodometro() {
        return Hodometro;
    }

    public void setHodometro(Integer hodometro) {
        if (hodometro == null)
            hodometro = 0;
        Hodometro = hodometro;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        if (latitude == null)
            latitude = 0.0;
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        if (longitude == null)
            longitude = 0.0;
        Longitude = longitude;
    }
}
