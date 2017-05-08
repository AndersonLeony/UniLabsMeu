package Model_dados_de_tabelas;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Leony on 24/04/2017.
 */

public class Model_dadosUsuarios extends RealmObject {

    private String login;
    private String senha;
    @PrimaryKey
    private String idUsuario;
    private int tipo;

    public Model_dadosUsuarios(){

    }

    public Model_dadosUsuarios(String login, String senha, int tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    public void setIdUsuario(String login, String tipo){
        this.idUsuario = login+tipo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
