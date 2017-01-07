package br.com.ezeqlabs.selltimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.fragment.DashboardFragment;
import br.com.ezeqlabs.selltimer.service.AlarmeReceiver;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preparaBoasVindas();

        AlarmeReceiver.preparaAlarme(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent tela = null;

        switch (id){
            // VER CLIENTES
            case R.id.ver_clientes_sidemenu:
                tela = new Intent(this, ClientesActivity.class);
                break;

            // CADASTRO DE CLIENTES
            case R.id.novo_cliente_sidemenu:
                tela = new Intent(this, CadastroClientesActivity.class);
                break;

            // SOBRE
            case R.id.info_sidemenu:
                tela = new Intent(this, SobreActivity.class);
                break;
        }

        if(tela != null){
            startActivity(tela);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());



        adapter.addFragment(preparaHojeFragment(), getString(R.string.tab_hoje));
        adapter.addFragment(preparaSemanaFragment(), getString(R.string.tab_semana));
        adapter.addFragment(preparaMesFragment(), getString(R.string.tab_mes));

        viewPager.setAdapter(adapter);
    }

    private DashboardFragment preparaHojeFragment(){
        DashboardFragment clientesHoje = new DashboardFragment();

        clientesHoje.setActivity(this);
        clientesHoje.setTxtTitulo(R.string.contatos_hoje);
        clientesHoje.setCorBgTitulo(R.color.vermelhoCrimson);
        clientesHoje.setCorBgListView(R.color.vermelhoPink);
        clientesHoje.setTxtSemContatos(R.string.texto_sem_contatos_hoje);
        clientesHoje.setListaPares(databaseHelper.getClientesHoje());

        return clientesHoje;
    }

    private DashboardFragment preparaSemanaFragment(){
        DashboardFragment clientesSemana = new DashboardFragment();

        clientesSemana.setActivity(this);
        clientesSemana.setTxtTitulo(R.string.contatos_semana);
        clientesSemana.setCorBgTitulo(R.color.amareloGoldenrod);
        clientesSemana.setCorBgListView(R.color.amareloLightYellow);
        clientesSemana.setTxtSemContatos(R.string.texto_sem_contatos_semana);
        clientesSemana.setListaPares(databaseHelper.getClientesSemana());

        return clientesSemana;
    }

    private DashboardFragment preparaMesFragment(){
        DashboardFragment clientesMes = new DashboardFragment();

        clientesMes.setActivity(this);
        clientesMes.setTxtTitulo(R.string.contatos_mes);
        clientesMes.setCorBgTitulo(R.color.azulSteelBlue);
        clientesMes.setCorBgListView(R.color.azulLightBlue);
        clientesMes.setTxtSemContatos(R.string.texto_sem_contatos_mes);
        clientesMes.setListaPares(databaseHelper.getClientesMes());

        return clientesMes;
    }

    private void preparaBoasVindas(){
        sharedPreferences = getSharedPreferences(Constantes.NOME_PREFS, 0);
        if( sharedPreferences.getBoolean(Constantes.BOAS_VINDAS_PREFS, true) ){
            exibeBoasVindas();
        }
    }

    private void exibeBoasVindas(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_monetization)
                .setTitle(getString(R.string.title_boas_vindas))
                .setMessage(getString(R.string.mensagem_boas_vindas))
                .setPositiveButton(getString(R.string.entendi), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Constantes.BOAS_VINDAS_PREFS, false);
                        editor.commit();
                    }
                })
                .show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}