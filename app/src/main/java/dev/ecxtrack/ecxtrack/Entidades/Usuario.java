package dev.ecxtrack.ecxtrack.Entidades;

import org.joda.time.DateTime;

/**
 * Created by Samuel on 07/08/2014.
 */
public class Usuario {

    private int CodUsuario;
    private String Nome;
    private String CPF;
    private String Email;
    private String Senha;
    private Perfil Perfil;
    private Cliente Cliente;
    private DateTime dtValidade;
    private String status;

    public Usuario() {
    }

    public int getCodUsuario() {
        return CodUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        CodUsuario = codUsuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public Perfil getPerfil() {
        return Perfil;
    }

    public void setPerfil(Perfil perfil) {
        Perfil = perfil;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente cliente) {
        Cliente = cliente;
    }

    public DateTime getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(DateTime dtValidade) {
        this.dtValidade = dtValidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
/*

        CodUsuario] [int] IDENTITY(1,1) NOT NULL,

       [Nome] [varchar](500) NULL,

       [CPF] [varchar](12) NULL,

       [Email] [varchar](150) NULL,

       [Senha] [varchar](50) NULL,

       [Ativo] [varchar](150) NULL,

       [CEP] [varchar](50) NULL,

       [Bairro] [varchar](150) NULL,

       [Rua] [varchar](500) NULL,

       [Numero] [varchar](50) NULL,

       [Complemento] [varchar](50) NULL,

       [CodCidade] [int] NULL,

       [Telefone] [varchar](30) NULL,

       [Celular] [varchar](30) NULL,

       [CodPerfil] [int] NOT NULL,

       [NomeUsuario] [varchar](50) NULL,

       [SenhaTemporaria] [varchar](10) NULL,

       [CodCliente] [int] NULL,

       [CodFusoHorario] [int] NOT NULL,

       [DataValidade] [datetime] NULL,

       [ValorAtualizacaoAutomatica] [int] NULL
 */