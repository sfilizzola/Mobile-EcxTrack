package dev.ecxtrack.ecxtrack.BLL;

import org.ksoap2.transport.HttpResponseException;

import dev.ecxtrack.ecxtrack.Entidades.Usuario;
import dev.ecxtrack.ecxtrack.Exceptions.UserException;

/**
 * Created by Samuel on 13/08/2014.
 */
public class Login implements IDisposable{
    private dev.ecxtrack.ecxtrack.DAL.Login oLogDAL;

    public Login(){
        oLogDAL = new dev.ecxtrack.ecxtrack.DAL.Login();
    }

    public Usuario ValidaLogin (String pNomeUsuario, String pSenha) throws UserException {
        Usuario vRetVal = null;
        try {
            vRetVal =  oLogDAL.VerificaLogin(pNomeUsuario, pSenha);
        } catch (HttpResponseException e) {
            throw new UserException(e.getMessage());
        }
        if (vRetVal == null)
            throw new UserException("Usuário ou senha inválidos.");
        if (vRetVal.getStatus() == null || !vRetVal.getStatus().equals("OK"))
            throw new UserException("Login/Senha Inválido.");

        return vRetVal;
    }


    @Override
    public void Dispose() {
        if (oLogDAL != null)
            oLogDAL.Dispose();
    }
}
