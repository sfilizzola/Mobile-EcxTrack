package dev.ecxtrack.ecxtrack.Entidades;

/**
 * Created by samuel.filizzola on 14/08/2014.
 */
public class Veiculo {

    private String Placa;
    private int CodTipo;
    private String TipoVeiculoNome;
    private int CodVeiculo;
    private String ContatoNome;
    private String ContatoTelefone;
    private int CodCliente;

    public Veiculo(){}

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public int getCodTipo() {
        return CodTipo;
    }

    public void setCodTipo(int codTipo) {
        CodTipo = codTipo;
    }

    public String getTipoVeiculoNome() {
        return TipoVeiculoNome;
    }

    public void setTipoVeiculoNome(String tipoVeiculoNome) {
        TipoVeiculoNome = tipoVeiculoNome;
    }

    public int getCodVeiculo() {
        return CodVeiculo;
    }

    public void setCodVeiculo(int codVeiculo) {
        CodVeiculo = codVeiculo;
    }

    public String getContatoNome() {
        return ContatoNome;
    }

    public void setContatoNome(String contatoNome) {
        ContatoNome = contatoNome;
    }

    public String getContatoTelefone() {
        return ContatoTelefone;
    }

    public void setContatoTelefone(String contatoTelefone) {
        ContatoTelefone = contatoTelefone;
    }

    public int getCodCliente() {
        return CodCliente;
    }

    public void setCodCliente(int codCliente) {
        CodCliente = codCliente;
    }
}
