
package modelo;

public class m_cliente {
    private Integer codigo;
    private String ci_ruc;
    private String nombre_razon;
    private String telefono;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCi_ruc() {
        return ci_ruc;
    }

    public void setCi_ruc(String ci_ruc) {
        this.ci_ruc = ci_ruc;
    }

    public String getNombre_razon() {
        return nombre_razon;
    }

    public void setNombre_razon(String nombre_razon) {
        this.nombre_razon = nombre_razon;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
