package br.com.ezeqlabs.selltimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABELA_CLIENTES = "clientes";
    public static final String TABELA_ENDERECOS = "enderecos";
    public static final String TABELA_EMAILS = "emails";
    public static final String TABELA_TELEFONES = "telefones";
    public static final String TABELA_CONTATOS = "contatos";
    public static final String NOME_BD = "selltimer.db";
    public static final int VERSAO_BD = 1;

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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

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

    private String criaTabelaContatos(){
        String sql = "CREATE TABLE " + TABELA_CONTATOS
                + "(id_con INTEGER PRIMARY KEY, "
                + "data DATE, "
                + "anotacoes TEXT, "
                + "interesse TEXT, "
                + "cliente_id_con INTEGER, "
                + " FOREIGN KEY(cliente_id_con) REFERENCES "+ TABELA_CLIENTES +"(id)"
                + ");";

        return sql;
    }

    public Long insereContato(Contato contato, Long clienteId){
        ContentValues values = new ContentValues();

        values.put("data", contato.getData().toString());
        values.put("anotacoes", contato.getAnotacoes());
        values.put("interesse", contato.getInteresse());
        values.put("cliente_id_con", clienteId);

        return getWritableDatabase().insert(TABELA_CONTATOS, null, values);
    }

    public List<Contato> getContatosCliente(Long idCliente) throws ParseException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_CONTATOS + " WHERE cliente_id_con = "+ idCliente +" ORDER BY data DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();

            contato.setId(c.getLong(c.getColumnIndex("id_con")));
            contato.setData(c.getString(c.getColumnIndex("data")));
            contato.setAnotacoes(c.getString(c.getColumnIndex("anotacoes")));
            contato.setInteresse(c.getString(c.getColumnIndex("interesse")));
            contato.setClienteId(c.getLong(c.getColumnIndex("cliente_id_con")));

            contatos.add(contato);
        }

        return contatos;
    }

}
