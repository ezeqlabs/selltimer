package br.com.ezeqlabs.selltimer.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class TelefoneDAO extends SQLiteOpenHelper {
    public static final String TABELA = "telefones";

    public TelefoneDAO(Context context){
        super(context, Constantes.NOME_BD, null, Constantes.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + "(id_tel INTEGER PRIMARY KEY, "
                + "telefone TEXT, "
                + "cliente_id_tel INTEGER, "
                + " FOREIGN KEY(cliente_id_tel) REFERENCES "+ ClienteDAO.TABELA +"(id)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

    public Long insere(Telefone telefone, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("telefone", telefone.getTelefone());
        values.put("cliente_id_end", clienteId);

        return getWritableDatabase().insert(TABELA, null, values);
    }
}
