
package dev.sfilizzola.data;

public class DataParameter {

    public enum DataType {
        STRING, DATETIME, NUMBER
    }

    private DataType DbType;
    private String ParameterName;
    private Object Value;

    /**
     * Contrutor
     */
    public DataParameter() {

    }

    /**
     * Popula um objeto do tipo DataParameter e limpa o valor do objeto
     * 
     * @param pParameterName Nome do parametro
     * @param pDbType Tipo do parametro
     */
    public DataParameter(String pParameterName, DataType pDbType) {

        this.setParameterName(pParameterName);
        this.setDbType(pDbType);
    }

    /**
     * Popula um objeto do tipo DataParameter
     * 
     * @param pParameterName Nome do parametro
     * @param pDbType Tipo do parametro
     * @param value valor
     */
    public DataParameter(String pParameterName, DataType pDbType, Object pValue) {

        this(pParameterName, pDbType);
        this.setValue(pValue);
    }

    public DataType getDbType() {

        return DbType;
    }

    public String getParameterName() {

        return ParameterName;
    }

    public Object getValue() {

        return Value;
    }

    public void setDbType(DataType pDbType) {

        DbType = pDbType;
    }

    public void setParameterName(String parameterName) {

        if (!parameterName.startsWith(":"))
            ParameterName = ":" + parameterName.toLowerCase();
        else
            ParameterName = parameterName.toLowerCase();
    }

    public void setValue(Object value) {

        Value = value;
    }
}
