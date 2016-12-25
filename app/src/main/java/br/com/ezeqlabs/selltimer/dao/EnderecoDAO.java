package br.com.ezeqlabs.selltimer.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class EnderecoDAO extends SQLiteOpenHelper {
    public static final String TABELA = "enderecos";

    public EnderecoDAO(Context context){
        super(context, Constantes.NOME_BD, null, Constantes.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA
                + " (id_end INTEGER PRIMARY KEY, "
                + " endereco TEXT, "
                + " cliente_id_end INTEGER, "
                + " FOREIGN KEY(cliente_id) REFERENCES "+ ClienteDAO.TABELA +"(id)";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

    public Long insere(Endereco endereco, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("endereco", endereco.getEndereco());
        values.put("cliente_id_end", clienteId);

        return getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Endereco> getEnderecosCliente(Long idCliente){
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + " WHERE cliente_id_end = "+ idCliente +" ORDER BY id DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Endereco endereco = new Endereco();

            endereco.setId(c.getLong(c.getColumnIndex("id_end")));
            endereco.setEndereco(c.getString(c.getColumnIndex("endereco")));
            endereco.setClienteId(c.getLong(c.getColumnIndex("cliente_id_end")));

            enderecos.add(endereco);
        }

        return enderecos;
    }
}
