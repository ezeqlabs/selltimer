package br.com.ezeqlabs.selltimer;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.onurciner.toastox.ToastOX;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class DetalheClienteActivity extends AppCompatActivity {
    private LinearLayout llEndereco, llTelefone, llEmail, llContato;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;
    private List<Email> emails;
    private List<Contato> contatos;
    private Cliente cliente;
    private Contato contato;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private TextView nomeCliente, mensagem;
    private AdView mAdView;
    private Intent ligacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        atribuiVariaveis();
        preparaPublicidade();
    }

    @Override
    protected void onResume(){
        super.onResume();
        limpaLayout();
        preparaNomeCliente();
        preparaEnderecos();
        preparaTelefone();
        preparaEmail();
        preparaContatos();
    }

    @Override
    protected void onStop(){
        super.onStop();
        limpaLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_editar:
                abreEdicao();
                return true;
            case R.id.action_deletar:
                exibeAlertDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abreEdicao(){
        Intent edicao = new Intent(this, CadastroClientesActivity.class);
        edicao.putExtra(Constantes.CLIENTE_INTENT, cliente);
        startActivity(edicao);
        finish();
    }

    private void preparaPublicidade(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void adicionaContato(View v){
        Intent cadastroContato = new Intent(this, CadastroContatoActivity.class);
        cadastroContato.putExtra(Constantes.CLIENTE_INTENT, cliente);
        startActivity(cadastroContato);
    }

    private void geraEndereco(Endereco endereco){
        View v = LayoutInflater.from(this).inflate(R.layout.linearlayout_endereco, null);

        TextView enderecoText = (TextView) v.findViewById(R.id.endereco_detalhe_cliente);
        enderecoText.setText( endereco.getEndereco() );

        ImageView mapa = (ImageView) v.findViewWithTag(Constantes.TAG_MAPA);
        int id = Integer.parseInt(String.valueOf( endereco.getId() ));
        mapa.setId(id);

        llEndereco.addView(v);
    }

    private void geraTelefone(Telefone telefone){
        View v = LayoutInflater.from(this).inflate(R.layout.linearlayout_telefone, null);

        TextView telefoneText = (TextView) v.findViewById(R.id.telefone_detalhe_cliente);
        telefoneText.setText( telefone.getTelefone() );

        ImageView fone = (ImageView) v.findViewWithTag(Constantes.TAG_FONE);
        int id = Integer.parseInt( String.valueOf(telefone.getTelefone()).replaceAll("-", "") );
        fone.setId( id );

        llTelefone.addView(v);
    }

    public void ligaCliente(View v){
        int numero = v.getId();
        ligacao = new Intent(Intent.ACTION_CALL);
        ligacao.setData(Uri.parse("tel:" + String.valueOf(numero)));

        if(Permissive.checkPermission(this, Manifest.permission.CALL_PHONE)) {
            realizaLigacao();
        }else{
            pedePermissaoLigacao();
        }


    }

    private void realizaLigacao(){
        startActivity(ligacao);
    }

    private void pedePermissaoLigacao(){
        new Permissive.Request(Manifest.permission.CALL_PHONE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        startActivity(ligacao);
                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {
                        exibeAlertPermissaoNegada();
                    }
                })
                .execute(this);
    }

    public void abreMapaCliente(View v){
        int numero = v.getId();
        String endereco = "";

        if(enderecos != null){
            if(enderecos.size() > 0){
                for( Endereco tmp : enderecos ){
                    if( numero == Integer.parseInt(String.valueOf(tmp.getId())) ){
                        endereco = tmp.getEndereco();
                    }
                }
            }
        }

        String uri = "geo:0,0?q=" + endereco;
        Intent mapa = new Intent(Intent.ACTION_VIEW);
        mapa.setData(Uri.parse(uri));
        startActivity(mapa);
    }

    private void geraEmail(Email email){
        llEmail.addView(geraTextView(email.getEmail()));
    }

    private TextView geraTextView(String texto){
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(texto);
        return textView;
    }

    private void atribuiVariaveis(){
        llEndereco = (LinearLayout) findViewById(R.id.container_enderecos_detalhe);
        llTelefone = (LinearLayout) findViewById(R.id.container_telefones_detalhe);
        llEmail = (LinearLayout) findViewById(R.id.container_emails_detalhe);
        llContato = (LinearLayout) findViewById(R.id.container_contatos_cliente);
        nomeCliente = (TextView) findViewById(R.id.nome_cliente_detalhe);
        mensagem = (TextView) findViewById(R.id.mensagem_cliente_detalhe);
        mAdView = (AdView) findViewById(R.id.adViewCliente);

        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        enderecos = databaseHelper.getEnderecosCliente(cliente.getId());
        telefones = databaseHelper.getTelefonesCliente(cliente.getId());
        emails = databaseHelper.getEmailsCliente(cliente.getId());
    }

    private void preparaNomeCliente(){
        nomeCliente.setText(cliente.getNome());
    }

    private void preparaEnderecos(){
        if(enderecos != null){
            if(enderecos.size() > 0){
                for(Endereco endereco : enderecos){
                    geraEndereco(endereco);
                }
            }else{
                apagaView(R.id.titulo_endereco_detalhe, R.id.container_enderecos_detalhe);
            }
        } else{
            apagaView(R.id.titulo_endereco_detalhe, R.id.container_enderecos_detalhe);
        }
    }

    private void preparaTelefone(){
        if(telefones != null){
            if(telefones.size() > 0){
                for(Telefone telefone : telefones){
                    geraTelefone(telefone);
                }
            }else{
                apagaView(R.id.titulo_telefone_detalhe, R.id.container_telefones_detalhe);
            }
        }else{
            apagaView(R.id.titulo_telefone_detalhe, R.id.container_telefones_detalhe);
        }
    }

    private void preparaEmail(){
        if(emails != null){
            if(emails.size() > 0){
                for(Email email : emails){
                    geraEmail(email);
                }
            }else{
                apagaView(R.id.titulo_emails_detalhes, R.id.container_emails_detalhe);
            }
        }else{
            apagaView(R.id.titulo_emails_detalhes, R.id.container_emails_detalhe);
        }
    }

    private void preparaContatos(){
        contatos = databaseHelper.getContatosCliente( cliente.getId() );

        if(!contatos.isEmpty()){
            mensagem.setVisibility(View.GONE);
            llContato.setVisibility(View.VISIBLE);

            for(Contato con : contatos){
                Button botao = (Button) LayoutInflater.from(this).inflate(R.layout.button_contato, null);
                botao.setId(Integer.parseInt(String.valueOf(con.getId())));
                botao.setText( con.toString() );

                llContato.addView(botao);
            }

        }else{
            mensagem.setVisibility(View.VISIBLE);
            llContato.setVisibility(View.GONE);
        }
    }

    private void limpaLayout(){
        llContato.removeAllViews();
        llEmail.removeAllViews();
        llEndereco.removeAllViews();
        llTelefone.removeAllViews();
    }

    public void abreContato(View v){
        for( int i = 0; i < contatos.size(); i++ ){
            if( v.getId() == contatos.get(i).getId() ){
                contato = contatos.get(i);
            }
        }

        Intent detalhe = new Intent(this, DetalheContatoActivity.class);
        detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
        detalhe.putExtra(Constantes.CONTATO_INTENT, contato);
        startActivity(detalhe);
    }

    private void apagaView(int titulo, int container){
        findViewById(titulo).setVisibility(View.GONE);
        findViewById(container).setVisibility(View.GONE);
    }

    private void exibeAlertDelete(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning)
                .setTitle(getString(R.string.title_alert_deleta_cliente))
                .setMessage(getString(R.string.texto_alert_deleta_cliente))
                .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletaCliente();
                    }
                })
                .setNegativeButton(getString(R.string.nao), null)
                .show();
    }

    private void exibeAlertPermissaoNegada(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning)
                .setTitle(getString(R.string.title_alert_permissao_negada))
                .setMessage(getString(R.string.texto_alert_permissao_telefone_negada))
                .setPositiveButton(getString(R.string.ok), null)
                .setNegativeButton(getString(R.string.tentar_novamente), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pedePermissaoLigacao();
                    }
                })
                .show();
    }

    private void deletaCliente(){
        databaseHelper.deletaTodosContatosCliente(cliente);
        databaseHelper.deletaTodosEmailsCliente(cliente);
        databaseHelper.deletaTodosEnderecosCliente(cliente);
        databaseHelper.deletaTodosTelefonesCliente(cliente);
        databaseHelper.deletaCliente(cliente);

        ToastOX.ok(this, getString(R.string.cliente_deletado_sucesso), Toast.LENGTH_LONG);
        finish();
    }
}
