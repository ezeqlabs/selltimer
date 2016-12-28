package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.fragment.ClientesHojeFragment;
import br.com.ezeqlabs.selltimer.fragment.ClientesMesFragment;
import br.com.ezeqlabs.selltimer.fragment.ClientesSemanaFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

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

    private ClientesHojeFragment preparaHojeFragment(){
        ClientesHojeFragment clientesHojeFragment = new ClientesHojeFragment();
        clientesHojeFragment.setContext(this);
        clientesHojeFragment.setDatabaseHelper(databaseHelper);

        return clientesHojeFragment;
    }

    private ClientesSemanaFragment preparaSemanaFragment(){
        ClientesSemanaFragment clientesSemanaFragment = new ClientesSemanaFragment();
        clientesSemanaFragment.setContext(this);
        clientesSemanaFragment.setDatabaseHelper(databaseHelper);

        return clientesSemanaFragment;
    }

    private ClientesMesFragment preparaMesFragment(){
        ClientesMesFragment clientesMesFragment = new ClientesMesFragment();
        clientesMesFragment.setContext(this);
        clientesMesFragment.setDatabaseHelper(databaseHelper);

        return clientesMesFragment;
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