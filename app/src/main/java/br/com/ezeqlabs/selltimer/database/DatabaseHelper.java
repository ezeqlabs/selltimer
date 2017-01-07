package br.com.ezeqlabs.selltimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ezeqlabs.selltimer.helpers.PairHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Datas;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABELA_CLIENTES = "clientes";
    public static final String TABELA_ENDERECOS = "enderecos";
    public static final String TABELA_EMAILS = "emails";
    public static final String TABELA_TELEFONES = "telefones";
    public static final String TABELA_CONTATOS = "contatos";
    public static final String NOME_BD = "selltimer.db";
    public static final int VERSAO_BD = 2;

    public DatabaseHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(criaTabelaClientes());
        sqLiteDatabase.execSQL(criaTabelaEnderecos());
        sqLiteDatabase.execSQL(criaTabelaEmails());
        sqLiteDatabase.execSQL(criaTabelaTelefones());
        sqLiteDatabase.execSQL(criaTabelaContatos());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {
        switch (versaoAntiga){
            case 1:
                String sql = "ALTER TABLE " + TABELA_CONTATOS + " ADD COLUMN retorno DATE;";
                sqLiteDatabase.execSQL(sql);
        }
    }

    private String criaTabelaClientes(){
        String sql = "CREATE TABLE " + TABELA_CLIENTES
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT); ";

        return sql;
    }

    public Long insereCliente(Cliente cliente){
        ContentValues values = new ContentValues();

        values.put("nome", cliente.getNome());

        return getWritableDatabase().insert(TABELA_CLIENTES, null, values);
    }

    public List<Cliente> getClientes(){
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CLIENTES + " ORDER BY id DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Cliente cliente = new Cliente();

            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));

            clientes.add(cliente);
        }

        return clientes;
    }

    public void atualizaCliente(Cliente cliente){
        ContentValues values = new ContentValues();

        values.put("nome", cliente.getNome());

        String[] args = { cliente.getId().toString() };
        getWritableDatabase().update(TABELA_CLIENTES, values, "id=?", args);
    }

    public void deletaCliente(Cliente cliente){
        String[] args = { cliente.getId().toString() };
        getWritableDatabase().delete(TABELA_CLIENTES, "id=?", args);
    }

    private String criaTabelaEnderecos(){
        String sql = "CREATE TABLE " + TABELA_ENDERECOS
                + " (id_end INTEGER PRIMARY KEY, "
                + " endereco TEXT, "
                + " cliente_id_end INTEGER, "
                + " FOREIGN KEY(cliente_id_end) REFERENCES "+ TABELA_CLIENTES +"(id)"
                + ");";

        return sql;
    }

    public Long insereEndereco(Endereco endereco, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("endereco", endereco.getEndereco());
        values.put("cliente_id_end", clienteId);

        return getWritableDatabase().insert(TABELA_ENDERECOS, null, values);
    }

    public List<Endereco> getEnderecosCliente(Long idCliente){
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_ENDERECOS + " WHERE cliente_id_end = "+ idCliente +" ORDER BY id_end DESC;";

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

    public void deletaTodosEnderecosCliente(Cliente cliente){
        String[] args = { cliente.getId().toString() };
        getWritableDatabase().delete(TABELA_ENDERECOS, "cliente_id_end=?", args);
    }

    private String criaTabelaEmails(){
        String sql = "CREATE TABLE " + TABELA_EMAILS
                + "(id_em INTEGER PRIMARY KEY, "
                + "email TEXT, "
                + "cliente_id_em INTEGER, "
                + " FOREIGN KEY(cliente_id_em) REFERENCES "+ TABELA_CLIENTES +"(id)"
                + ");";

        return sql;
    }

    public Long insereEmail(Email email, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("email", email.getEmail());
        values.put("cliente_id_em", clienteId);

        return getWritableDatabase().insert(TABELA_EMAILS, null, values);
    }

    public List<Email> getEmailsCliente(Long idCliente){
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_EMAILS + " WHERE cliente_id_em = "+ idCliente +" ORDER BY id_em DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Email email = new Email();

            email.setId(c.getLong(c.getColumnIndex("id_em")));
            email.setEmail(c.getString(c.getColumnIndex("email")));
            email.setClienteId(c.getLong(c.getColumnIndex("cliente_id_em")));

            emails.add(email);
        }

        return emails;
    }

    public void deletaTodosEmailsCliente(Cliente cliente){
        String[] args = { cliente.getId().toString() };
        getWritableDatabase().delete(TABELA_EMAILS, "cliente_id_em=?", args);
    }

    private String criaTabelaTelefones(){
        String sql = "CREATE TABLE " + TABELA_TELEFONES
                + "(id_tel INTEGER PRIMARY KEY, "
                + "telefone TEXT, "
                + "cliente_id_tel INTEGER, "
                + " FOREIGN KEY(cliente_id_tel) REFERENCES "+ TABELA_CLIENTES +"(id)"
                + ");";

        return sql;
    }

    public Long insereTelefone(Telefone telefone, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("telefone", telefone.getTelefone());
        values.put("cliente_id_tel", clienteId);

        return getWritableDatabase().insert(TABELA_TELEFONES, null, values);
    }

    public List<Telefone> getTelefonesCliente(Long idCliente){
        List<Telefone> telefones = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_TELEFONES + " WHERE cliente_id_tel = "+ idCliente +" ORDER BY id_tel DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Telefone telefone = new Telefone();

            telefone.setId(c.getLong(c.getColumnIndex("id_tel")));
            telefone.setTelefone(c.getString(c.getColumnIndex("telefone")));
            telefone.setClienteId(c.getLong(c.getColumnIndex("cliente_id_tel")));

            telefones.add(telefone);
        }

        return telefones;
    }

    public void deletaTodosTelefonesCliente(Cliente cliente){
        String[] args = { cliente.getId().toString() };
        getWritableDatabase().delete(TABELA_TELEFONES, "cliente_id_tel=?", args);
    }

    private String criaTabelaContatos(){
        String sql = "CREATE TABLE " + TABELA_CONTATOS
                + "(id_con INTEGER PRIMARY KEY, "
                + "data DATE, "
                + "retorno DATE, "
                + "anotacoes TEXT, "
                + "interesse TEXT, "
                + "cliente_id_con INTEGER, "
                + " FOREIGN KEY(cliente_id_con) REFERENCES "+ TABELA_CLIENTES +"(id)"
                + ");";

        return sql;
    }

    public Long insereContato(Contato contato, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("data", contato.getDataParaBanco());
        values.put("retorno", contato.getRetornoParaBanco());
        values.put("anotacoes", contato.getAnotacoes());
        values.put("interesse", contato.getInteresse());
        values.put("cliente_id_con", clienteId);

        return getWritableDatabase().insert(TABELA_CONTATOS, null, values);
    }

    public void atualizaContato(Contato contato, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("data", contato.getDataParaBanco());
        values.put("retorno", contato.getRetornoParaBanco());
        values.put("anotacoes", contato.getAnotacoes());
        values.put("interesse", contato.getInteresse());
        values.put("cliente_id_con", clienteId);

        String[] args = { contato.getId().toString() };
        getWritableDatabase().update(TABELA_CONTATOS, values, "id_con=?", args);
    }

    public List<Contato> getContatosCliente(Long idCliente){
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CONTATOS + " WHERE cliente_id_con = "+ idCliente +" ORDER BY data DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();

            contato.setId(c.getLong(c.getColumnIndex("id_con")));
            contato.setDataDoBanco(c.getString(c.getColumnIndex("data")));
            contato.setRetornoDoBanco(c.getString(c.getColumnIndex("retorno")));
            contato.setAnotacoes(c.getString(c.getColumnIndex("anotacoes")));
            contato.setInteresse(c.getString(c.getColumnIndex("interesse")));
            contato.setClienteId(c.getLong(c.getColumnIndex("cliente_id_con")));

            contatos.add(contato);
        }

        return contatos;
    }

    public void deletaContatoCliente(Contato contato){
        String[] args = { contato.getId().toString() };
        getWritableDatabase().delete(TABELA_CONTATOS, "id_con=?", args);
    }

    public void deletaTodosContatosCliente(Cliente cliente){
        String[] args = { cliente.getId().toString() };
        getWritableDatabase().delete(TABELA_CONTATOS, "cliente_id_con=?", args);
    }

    public List<PairHelper> getClientesHoje(){
        List<PairHelper> retorno = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CLIENTES + " INNER JOIN " + TABELA_CONTATOS + " " +
                "ON id = cliente_id_con " +
                "WHERE retorno = date('"+ Datas.dataAtual() +"') " +
                "GROUP BY id ORDER BY data DESC ";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();
            Cliente cliente = new Cliente();

            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));

            contato.setId(c.getLong(c.getColumnIndex("id_con")));
            contato.setDataDoBanco(c.getString(c.getColumnIndex("data")));
            contato.setAnotacoes(c.getString(c.getColumnIndex("anotacoes")));
            contato.setInteresse(c.getString(c.getColumnIndex("interesse")));
            contato.setClienteId(c.getLong(c.getColumnIndex("cliente_id_con")));

            PairHelper par = new PairHelper(cliente, contato);

            retorno.add(par);
        }

        return retorno;
    }

    public List<PairHelper> getClientesSemana(){
        List<PairHelper> retorno = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CLIENTES + " INNER JOIN " + TABELA_CONTATOS + " " +
                "ON id = cliente_id_con " +
                "WHERE retorno <= date('"+ Datas.dataAtual() +"', '-1 day') " +
                "AND retorno >= date('"+ Datas.dataAtual() +"', '-7 day') " +
                "GROUP BY id ORDER BY data DESC ";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();
            Cliente cliente = new Cliente();

            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));

            contato.setId(c.getLong(c.getColumnIndex("id_con")));
            contato.setDataDoBanco(c.getString(c.getColumnIndex("data")));
            contato.setAnotacoes(c.getString(c.getColumnIndex("anotacoes")));
            contato.setInteresse(c.getString(c.getColumnIndex("interesse")));
            contato.setClienteId(c.getLong(c.getColumnIndex("cliente_id_con")));

            PairHelper par = new PairHelper(cliente, contato);

            retorno.add(par);
        }

        return retorno;
    }

    public List<PairHelper> getClientesMes(){
        List<PairHelper> retorno = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CLIENTES + " INNER JOIN " + TABELA_CONTATOS + " " +
                "ON id = cliente_id_con " +
                "WHERE retorno <= date('"+ Datas.dataAtual() +"', '-8 day') " +
                "AND retorno >= date('"+ Datas.dataAtual() +"', '-30 day') " +
                "GROUP BY id ORDER BY data DESC ";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();
            Cliente cliente = new Cliente();

            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));

            contato.setId(c.getLong(c.getColumnIndex("id_con")));
            contato.setDataDoBanco(c.getString(c.getColumnIndex("data")));
            contato.setAnotacoes(c.getString(c.getColumnIndex("anotacoes")));
            contato.setInteresse(c.getString(c.getColumnIndex("interesse")));
            contato.setClienteId(c.getLong(c.getColumnIndex("cliente_id_con")));

            PairHelper par = new PairHelper(cliente, contato);

            retorno.add(par);
        }

        return retorno;
    }

}
