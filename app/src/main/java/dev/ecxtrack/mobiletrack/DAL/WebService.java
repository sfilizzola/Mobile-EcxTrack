package dev.ecxtrack.mobiletrack.DAL;

//import app.desenvolvimento.ECXSaldo.Entidades.Cartao;
//import app.desenvolvimento.ECXSaldo.Entidades.RespostaCartao;
//import app.desenvolvimento.ECXSaldo.Exceptions.CardInvalidException;
import dev.ecxtrack.mobiletrack.Entidades.Evento;
import dev.ecxtrack.mobiletrack.Entidades.Usuario;
import dev.ecxtrack.mobiletrack.Entidades.Veiculo;
import dev.sfilizzola.data.Utilities.ArrayAdapterFilizzola;
import dev.sfilizzola.utils.ArrayList;
import dev.sfilizzola.utils.Log;

import org.joda.time.DateTime;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.util.List;

/**
 * Created by Samuel on 07/03/14.
 */
public class WebService {
    private final String URL = "http://www.trackecx.com:8090/EcxTrackAppServices.svc";
    private String SOAP_ACTION;
    private final String NAMESPACE = "http://tempuri.org/";
    private String METHOD_NAME;
    private static final String TAG = "WEBSERVICE";

    public WebService(){ }

    public Usuario Login (String pNomeUsuario, String pSenha) throws XmlPullParserException, HttpResponseException, SoapFault {

        SOAP_ACTION = "http://tempuri.org/IEcxTrackAppServices/Login";
        METHOD_NAME = "Login";

        //Define Objeto SOAP
        SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);
        soap.addProperty("NomeUsuario", pNomeUsuario);
        soap.addProperty("Senha", pSenha);

        //cria envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);
        Log.i(TAG, "Chamando Webservice: " + URL);
        //Cria HTTPTransport para enviar os dados (SOAP)

        //Recupera Resultado
        Usuario oUsu = null;

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);

            SoapObject resposta = (SoapObject)envelope.getResponse();


            if (resposta.getPropertyAsString("Status").equals("Zica")){
                return null;
            } else {
                oUsu = new Usuario();
                oUsu.setCodUsuario(Integer.parseInt(resposta.getPropertyAsString("CodUsuario")));
                oUsu.setNome(resposta.getPropertyAsString("Nome"));
                //oUsu.setCPF(resposta.getPropertyAsString("CPF"));
                oUsu.setEmail(resposta.getPropertyAsString("Email"));
                oUsu.setStatus(resposta.getPropertyAsString("Status"));
            }

        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
            throw new HttpResponseException("Erro de conexão com o servidor. Tente novamente mais tarde.", 404);
        }


        /*
    <xs:element minOccurs="0" name="Cliente" nillable="true" type="tns:Cliente"/>
    <xs:element minOccurs="0" name="DataValidade" nillable="true" type="xs:dateTime"/>
    <xs:element minOccurs="0" name="Perfil" nillable="true" type="tns:Perfil"/>

        private Perfil Perfil;
        private Cliente Cliente;
        private DateTime dtValidade;
    ;*/

        return oUsu;
    }

    public Evento UltimaLocalizacaoVeiculo (int pCodVeiculo) throws XmlPullParserException, HttpResponseException, SoapFault{
        //CodVeiculo
        SOAP_ACTION = "http://tempuri.org/IEcxTrackAppServices/UltimaLocalizacaoVeiculo";
        METHOD_NAME = "UltimaLocalizacaoVeiculo";

        //Define Objeto SOAP
        SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);
        soap.addProperty("CodVeiculo", pCodVeiculo);

        //cria envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);
        Log.i(TAG, "Chamando Webservice: " + URL);
        //Cria HTTPTransport para enviar os dados (SOAP)

        //Evento de resultado
        Evento oEvento = null;

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);

            SoapObject resposta = (SoapObject)envelope.getResponse();

            oEvento = new Evento();

            if (resposta != null)
            {
                oEvento.setCodCliente(Integer.parseInt(resposta.getPropertyAsString("CodCliente")));
                oEvento.setCodEquipamento(Integer.parseInt(resposta.getPropertyAsString("CodEquipamento")));
                oEvento.setCodEvento(Integer.parseInt(resposta.getPropertyAsString("CodEvento")));
                oEvento.setCodVeiculo(Integer.parseInt(resposta.getPropertyAsString("CodVeiculo")));
                oEvento.setHodometro(Integer.parseInt(resposta.getPropertyAsString("Hodometro")));
                oEvento.setLatitude(Double.parseDouble(resposta.getPropertyAsString("Latitude")));
                oEvento.setLongitude(Double.parseDouble(resposta.getPropertyAsString("Longitude")));
                oEvento.setStatusIgnicao(resposta.getPropertyAsString("StatusIgnicao").equals("true"));
                oEvento.setDataEvento(new DateTime(resposta.getPropertyAsString("DataEvento")));
            }

        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
        }

        return oEvento;
    }

    public List<Veiculo> VeiculosPorCliente (int pCodusuario) throws XmlPullParserException, HttpResponseException, SoapFault {
        return VeiculosPorCliente (pCodusuario, false);
    }

    public List<Veiculo> VeiculosPorCliente (int pCodusuario, boolean IsClienteAdicional) throws XmlPullParserException, HttpResponseException, SoapFault {
        //CodVeiculo
        SOAP_ACTION = "http://tempuri.org/IEcxTrackAppServices/VeiculosPorCliente";
        METHOD_NAME = "VeiculosPorCliente";

        //Define Objeto SOAP
        SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);
        soap.addProperty("CodUsuario", pCodusuario);
        soap.addProperty("buscaClienteAdicional", IsClienteAdicional);

        //cria envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);
        Log.i(TAG, "Chamando Webservice: " + URL);
        //Cria HTTPTransport para enviar os dados (SOAP)

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
        }


        List<Veiculo> vRet = new ArrayList<Veiculo>();
        SoapObject result = (SoapObject)envelope.getResponse();

        for(int i= 0; i< result.getPropertyCount(); i++){

            Veiculo oVei = new Veiculo();

            SoapObject object = (SoapObject)result.getProperty(i);

            oVei.setPlaca(object.getPropertyAsString("Placa"));
            oVei.setCodVeiculo(Integer.parseInt(object.getPropertyAsString("CodVeiculo")));

            vRet.add(oVei);

        }
        return vRet;
    }

    public List<Evento> Trajetos (DateTime pDataInicial, DateTime pDataFinal, int pCodVeiculo)  throws XmlPullParserException, HttpResponseException, SoapFault{
        //CodVeiculo
        SOAP_ACTION = "http://tempuri.org/IEcxTrackAppServices/ListaPontosPeriodo";
        METHOD_NAME = "ListaPontosPeriodo";

        //Define Objeto SOAP
        SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);
        soap.addProperty("DataIni", pDataInicial.toString("dd/MM/YYYY"));
        soap.addProperty("DataFim", pDataFinal.toString("dd/MM/YYYY"));
        soap.addProperty("HoraIni", pDataInicial.toString("HH:mm:ss"));
        soap.addProperty("HoraFim", pDataFinal.toString("HH:mm:ss"));
        soap.addProperty("CodVeiculo", pCodVeiculo);


        //cria envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);
        Log.i(TAG, "Chamando Webservice: " + URL);
        //Cria HTTPTransport para enviar os dados (SOAP)

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
        }


        List<Evento> vRet = new ArrayList<Evento>();
        SoapObject result = (SoapObject)envelope.getResponse();

        for(int i= 0; i< result.getPropertyCount(); i++){

            Evento oEvento = new Evento();

            SoapObject object = (SoapObject)result.getProperty(i);

            oEvento.setHodometro(Integer.parseInt(object.getPropertyAsString("Hodometro")));
            oEvento.setLatitude(Double.parseDouble(object.getPropertyAsString("Latitude")));
            oEvento.setLongitude(Double.parseDouble(object.getPropertyAsString("Longitude")));
            oEvento.setDataEvento(new DateTime(object.getPropertyAsString("DataEvento")));
            vRet.add(oEvento);
        }
        return vRet;
    }


    /*
        -------------------- RESPOSTA VEICULO --------------------------
        <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
         <s:Header />
         <s:Body>
           <UltimaLocalizacaoVeiculoResponse xmlns="http://tempuri.org/">
             <UltimaLocalizacaoVeiculoResult xmlns:a="http://schemas.datacontract.org/2004/07/EcxTrackWCF.Entities" xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
               <a:CodCliente>10</a:CodCliente>
               <a:CodEquipamento>5</a:CodEquipamento>
               <a:CodEvento>1957091</a:CodEvento>
               <a:CodVeiculo>12</a:CodVeiculo>
               <a:Hodometro>5064</a:Hodometro>
               <a:Latitude>-19.935869</a:Latitude>
               <a:Longitude>-43.944614</a:Longitude>
             </UltimaLocalizacaoVeiculoResult>
           </UltimaLocalizacaoVeiculoResponse>
         </s:Body>
        </s:Envelope>
    */



    /*

    -------------------RESPOSTA LISTA VEICULOS----------------------

    <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
 <s:Header />
 <s:Body>
   <VeiculosPorClienteResponse xmlns="http://tempuri.org/">
     <VeiculosPorClienteResult xmlns:a="http://schemas.datacontract.org/2004/07/EcxTrackWCF.Entities" xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
       <a:Veiculo>
         <a:CodCliente>8</a:CodCliente>
         <a:CodTipoVeiculo>1</a:CodTipoVeiculo>
         <a:CodVeiculo>9</a:CodVeiculo>
         <a:ContatoNome>JOSE FRANCISCO</a:ContatoNome>
         <a:ContatoTelefone>(31)9295-3100</a:ContatoTelefone>
         <a:NomeTipoVeiculo>Carro</a:NomeTipoVeiculo>
         <a:Placa>FAH-0870</a:Placa>
       </a:Veiculo>
       <a:Veiculo>
 <a:CodCliente>8</a:CodCliente>
         <a:CodTipoVeiculo>1</a:CodTipoVeiculo>
         <a:CodVeiculo>13</a:CodVeiculo>
         <a:ContatoNome>CESAR</a:ContatoNome>
         <a:ContatoTelefone>(31)8411-9333</a:ContatoTelefone>
         <a:NomeTipoVeiculo>Carro</a:NomeTipoVeiculo>
         <a:Placa>GSW-5201</a:Placa>
       </a:Veiculo>
       <a:Veiculo>
         <a:CodCliente>8</a:CodCliente>
         <a:CodTipoVeiculo>1</a:CodTipoVeiculo>
         <a:CodVeiculo>14</a:CodVeiculo>
         <a:ContatoNome>FERNANDO</a:ContatoNome>
         <a:ContatoTelefone>(31)9295-2714</a:ContatoTelefone>
         <a:NomeTipoVeiculo>Carro</a:NomeTipoVeiculo>
         <a:Placa>GVU-4470</a:Placa>
       </a:Veiculo>
     </VeiculosPorClienteResult>
   </VeiculosPorClienteResponse>
 </s:Body>
</s:Envelope>
     */


}





/*
    public RespostaCartao VerificaCartao(Cartao pCard) throws SoapFault, HttpResponseException, XmlPullParserException {

        RespostaCartao aResposta = Saldo(pCard);

        return aResposta;

        /*SOAP_ACTION = "http://ECXWSMobile.org/VerificaCartao";
        METHOD_NAME = "VerificaCartao";

        //Define Objeto SOAP
        SoapObject soap = new SoapObject(NAMESPACE, METHOD_NAME);
        soap.addProperty("pCardNumber", pCard.getNumeroCartao());
        soap.addProperty("pCPF", pCard.getCPFusuario());

        //cria envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);
        Log.i(TAG, "Chamando Webservice: " + URL);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
            e.printStackTrace();
        }
        //Recupera Resultado
        boolean vVerificaObj = Boolean.parseBoolean(envelope.getResponse().toString());

        return vVerificaObj;*/



     /*
    POST /ServicoCard.asmx HTTP/1.1
    Host: 201.73.60.186
    Content-Type: text/xml; charset=utf-8
    Content-Length: length
    SOAPAction: "http://www.ecx.com.br/validarCartao"

    <?xml version="1.0" encoding="utf-8"?>
    <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <soap:Body>
        <validarCartao xmlns="http://www.ecx.com.br/">
          <numeroCartao>string</numeroCartao>
          <senhaCartao>string</senhaCartao>
          <codSegCartao>string</codSegCartao>
          <nomImpCartao>string</nomImpCartao>
        </validarCartao>
      </soap:Body>
    </soap:Envelope>
     */
/*


/*

private final String NAMESPACE = "http://www.webserviceX.NET/";
    private final String URL = "http://www.webservicex.net/ConvertWeight.asmx";
    private final String SOAP_ACTION = "http://www.webserviceX.NET/ConvertWeight";
    private final String METHOD_NAME = "ConvertWeight";

    // Called when the activity is first created.
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

    String weight = "3700";
    String fromUnit = "Grams";
    String toUnit = "Kilograms";

    PropertyInfo weightProp =new PropertyInfo();
    weightProp.setName("Weight");
    weightProp.setValue(weight);
    weightProp.setType(double.class);
    request.addProperty(weightProp);

    PropertyInfo fromProp =new PropertyInfo();
    fromProp.setName("FromUnit");
    fromProp.setValue(fromUnit);
    fromProp.setType(String.class);
    request.addProperty(fromProp);

    PropertyInfo toProp =new PropertyInfo();
    toProp.setName("ToUnit");
    toProp.setValue(toUnit);
    toProp.setType(String.class);
    request.addProperty(toProp);

    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    envelope.dotNet = true;
    envelope.setOutputSoapObject(request);
    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

    try {
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        Log.i("myApp", response.toString());

        TextView tv = new TextView(this);
        tv.setText(weight+" "+fromUnit+" equal "+response.toString()+ " "+toUnit);
        setContentView(tv);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
 */
