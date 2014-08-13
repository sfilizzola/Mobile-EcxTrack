package dev.ecxtrack.mobiletrack.Entidades;

import java.util.Enumeration;

/**
 * Created by Samuel on 09/08/2014.
 */
public class Perfil {

    private int CodPerfil;
    private String Nome;
    private boolean Personalizado;
    private TipoPerfil TipoPerfil;


    public Perfil() {
    }

    public int getCodPerfil() {
        return CodPerfil;
    }

    public void setCodPerfil(int codPerfil) {
        CodPerfil = codPerfil;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public boolean isPersonalizado() {
        return Personalizado;
    }

    public void setPersonalizado(boolean personalizado) {
        Personalizado = personalizado;
    }

    public Perfil.TipoPerfil getTipoPerfil() {
        return TipoPerfil;
    }

    public void setTipoPerfil(Perfil.TipoPerfil tipoPerfil) {
        TipoPerfil = tipoPerfil;
    }

    public enum TipoPerfil {
        Administrativo(1), Operacional(2), Usuario(3);

        public int valorTipoPerfil;
        TipoPerfil(int valor){
            valorTipoPerfil = valor;
        }

        public int getTipoPerfil(){
            return valorTipoPerfil;
        }
    }

}
