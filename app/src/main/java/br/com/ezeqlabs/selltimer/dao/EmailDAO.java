package br.com.ezeqlabs.selltimer.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class EmailDAO extends SQLiteOpenHelper {
    public static final String TABELA = "emails";

    public EmailDAO(Context context){
        super(context, Constantes.NOME_BD, null, Constantes.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + "(id_em INTEGER PRIMARY KEY, "
                + "email TEXT, "
                + "cliente_id_em INTEGER, "
                + " FOREIGN KEY(cliente_id_em) REFERENCES "+ ClienteDAO.TABELA +"(id)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

    public Long insere(Email email, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("email", email.getEmail());
        values.put("cliente_id_em", clienteId);

        return getWritableDatabase().insert(TABELA, null, values);
    }
}
