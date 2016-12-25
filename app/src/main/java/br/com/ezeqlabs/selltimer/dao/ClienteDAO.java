package br.com.ezeqlabs.selltimer.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class ClienteDAO extends SQLiteOpenHelper {
    public static final String TABELA = "clientes";

    public ClienteDAO(Context context){
        super(context, Constantes.NOME_BD, null, Constantes.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT); ";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

    public Long insere(Cliente cliente){
        ContentValues values = new ContentValues();

        values.put("nome", cliente.getNome());

        return getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Cliente> getClientes(){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + " ORDER BY id DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Cliente cliente = new Cliente();

            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));

            clientes.add(cliente);
        }

        return clientes;
    }
}
