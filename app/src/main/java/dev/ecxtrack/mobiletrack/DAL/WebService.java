package dev.ecxtrack.mobiletrack.DAL;

//import app.desenvolvimento.ECXSaldo.Entidades.Cartao;
//import app.desenvolvimento.ECXSaldo.Entidades.RespostaCartao;
//import app.desenvolvimento.ECXSaldo.Exceptions.CardInvalidException;
import dev.ecxtrack.mobiletrack.Entidades.Usuario;
import dev.sfilizzola.utils.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by Samuel on 07/03/14.
 */
public class WebService {
    private final String URL = "http://www.trackecx.com:8090/EcxTrackAppServices.svc";
    private String SOAP_ACTION;
    private final String NAMESPACE = "http://tempuri.org/";
    private String METHOD_NAME;
    private static final String TAG = "WEBSERVICE";

    public WebService(){
    }

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

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        }catch (Exception e){
            Log.e(TAG, "Erro HttpTransportSE: "+ e.getMessage());
        }
        //Recupera Resultado
        Usuario oUsu = new Usuario();

        SoapObject resposta = (SoapObject)envelope.getResponse();

        oUsu.setCodUsuario(Integer.parseInt(resposta.getPropertyAsString("CodUsuario")));
        oUsu.setNome(resposta.getPropertyAsString("Nome"));
        //oUsu.setCPF(resposta.getPropertyAsString("CPF"));
        oUsu.setEmail(resposta.getPropertyAsString("Email"));
        oUsu.setStatus(resposta.getPropertyAsString("Status"));
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
