package fivesounds.dms.br.fivesounds;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// http://www.animal-sounds.org/exotic-animal-sounds.html

// http://www.baixamais.net/efeitos-sonoros


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // carrega os dados

    List<Som> sons;  // Array de "SOM"
    List<Tipo> tipos; // Array de "TIPO"


    // seta variaveis
    ListView androidListView;
    ListView androidMainListView;

    String lingua="";
    String lingua_add="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // gerado pelo sistema
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar não será usado nesta versão.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inicializa variaveis
        lingua = getString(R.string.lang);

        if ("en".equals(lingua)) {
            lingua_add = "";
        } else {
           // lingua_add = "_".concat(lingua);
         }

        // FloatingActionButton não será usado nesta versão
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // o botão de ação não será utilizado nesta versão do app.
        fab.setVisibility(View.GONE);  // desabilita o botão

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Navigation view não será usado nesta versão.
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // O menu do lado esquerdo não será utilizado nesta versão do app. Então desabilita.
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



        // carrega variaveis
        pegaJson("json_sons");
        pegaJson("json_tipos");

        // pega os ids das views.
        androidListView = (ListView) findViewById(R.id.list_view);
        androidMainListView = (ListView) findViewById(R.id.list_main_view);

        // trata a captura do click na lista principal
        androidMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // pega o textView que foi clicado.

                TextView txtMainCdId = (TextView) view.findViewById(R.id.tvMainId);
                int id_clicado = getIdTipo(txtMainCdId.getText().toString());

                Tipo tipoClicado = tipos.get(id_clicado);

                // monta o texto para mostrar
                //String textoSnack = "Clicou no item: " + position + " : " + tipoClicado.getTipo();
                String textoSnack = "Sons de " + tipoClicado.getTipo();


                Snackbar.make(view,textoSnack,Snackbar.LENGTH_SHORT).setAction("Action",null).show();

                // toca o Som ta tecla
                view.playSoundEffect(android.view.SoundEffectConstants.CLICK);

                // mostra a nova lista correspondente.
                androidMainListView.setVisibility(View.GONE);
                mostraLista(tipoClicado.getTipo());
                androidListView.setVisibility(View.VISIBLE);
            }
        });


        //  trata a captura do click na lista secundaria
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txtCdId = (TextView) view.findViewById(R.id.tvId);
                int id_clicado = getIdSons(txtCdId.getText().toString());

                Som somClicado = sons.get(id_clicado);

                //String textoSnack = "Clicou no item: " + position + " : " + somClicado.getTitulo();
                String textoSnack = "Tocando o som: " + somClicado.getTitulo();

                Snackbar.make(view,textoSnack,Snackbar.LENGTH_SHORT).setAction("Action",null).show();

                // chama o Som correspondente
                tocaSom(view, somClicado.getSom());

            }
        });

        // Chama metodo mostraMainLista para mostrar a lista principal
        mostraMainList();
    }

    /**
     * retorna o id de um som.
     * @param cdSom
     * @return
     */
    public int getIdSons(String cdSom) {
        // retorna o id da array de sons com base no idSom
        int resultado=-1;
        String tmp;
        //Log.i("getIdSite","Size:" + String.valueOf(getSize()));
        for(int i=0; i<=sons.size()-1; i++){
            tmp = sons.get(i).getId();
            if(tmp == cdSom) {
                resultado = i;
                break;
            }
        } // end for
        if (resultado==-1) {
            Log.d("getIdSons", "Não Achei:" + String.valueOf(cdSom));
        }
        return resultado;
    }

    /**
     * Retorna o id de um tipo.
     * @param cdMain
     * @return
     */
    public int getIdTipo(String cdMain) {
        // retorna o id da array de sons com base no idSom
        int resultado=-1;
        String tmp;
        //Log.i("getIdSite","Size:" + String.valueOf(getSize()));
        for(int i=0; i<=tipos.size()-1; i++){  // estava sons ?!?!?
            tmp = tipos.get(i).getId();
            if(tmp == cdMain) {
                resultado = i;
                break;
            }
        } // end for
        if (resultado==-1) {
            Log.d("getIdTipo", "Não Achei:" + String.valueOf(cdMain));
        }
        return resultado;
    }


    @Override
    public void onBackPressed() {
        // organiza as telas se clicar no botão back.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (androidMainListView.getVisibility()==View.GONE) {
                // apaga a lista secundaria e mosta a principal.
                androidMainListView.setVisibility(View.VISIBLE);
                androidListView.setVisibility(View.GONE);
            } else {
                // se não estiver aberto a navegação e se estiver mostrando o menu principal sai do app.
                super.onBackPressed();
            }

        }
    }

    /**
     * Carrega as arrays com base no nome do arquivo.
     * @param arquivo
     */
    public void pegaJson(String arquivo) {
        // pega os dados

        Type collectionType;

        /* Type collectionType = new TypeToken<List<Som>>(){}.getType(); */

        if (arquivo.equals("json_sons")) {
            collectionType = new TypeToken<List<Som>>(){}.getType();
            sons = new ArrayList<Som>();
            //Instruct GSON to parse as a Post array (which we convert into a list)
            sons = (List<Som>) new Gson()
                    .fromJson( leArquivoRaw("json_sons" + lingua_add) , collectionType);
        } else {
            collectionType = new TypeToken<List<Tipo>>(){}.getType();
            tipos = new ArrayList<Tipo>();
            //Instruct GSON to parse as a Post array (which we convert into a list)
            tipos = (List<Tipo>) new Gson()
                    .fromJson( leArquivoRaw("json_tipos") , collectionType);
        }
    }

    /**
     * Le um arquivo do diretorio asset
     * @param nomeArquivo
     * @return
     */
    public String leArquivoRaw(String nomeArquivo) {
        //AssetManager assetManager = getAssets();
        InputStream input;
        String text = "";

        try {
            //input = assetManager.open(nomeArquivo);
            input = getResources().openRawResource(
                    getResources().getIdentifier(nomeArquivo,
                            "raw", getPackageName()));

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(text);
        return text;
    }

    /**
     * Mostra a lista dos animais ou outros sons.
     * @param tipo - tipo para mostar na lista secundaria
     */
    public void mostraLista(String tipo) {

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i=0 ; i < sons.size(); i++) {
            // adiciona apenas os que tiverem o mesmo tipo
            if (sons.get(i).getTipo().equals(tipo)) {
                HashMap<String, String> hm = new HashMap<String, String> ();
                hm.put("listview_title", sons.get(i).getTitulo());
                hm.put("listview_description", sons.get(i).getDescricao());
                hm.put("listview_image", Integer.toString(getResources().getIdentifier(sons.get(i).getImagem(),"drawable",getPackageName())));
                hm.put("listview_id", sons.get(i).getId());
                aList.add(hm);
                Log.d("lista",sons.get(i).getImagem()); // mostrar o nome das imagens.
            }
        }

        String[] from = {"listview_image", "listview_title", "listview_description", "listview_id"};
        int[] to = {R.id.listImage, R.id.firstLine, R.id.secondLine, R.id.tvId};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custon_list, from, to);

        androidListView.setAdapter(simpleAdapter);
    }

    /**
     * Mosta a lista principal
     */
    public void mostraMainList() {
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < tipos.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_image", Integer.toString(getResources().getIdentifier(tipos.get(i).getImagem(),"drawable",getPackageName())));
            hm.put("listview_tipo", tipos.get(i).getTipo());
            hm.put("listview_id", tipos.get(i).getId());
            aList.add(hm);
            Log.d("listaM",tipos.get(i).getImagem()); // mostrar o nome das imagens.
        }

        String[] from = {"listview_image", "listview_tipo", "listview_id"};
        int[] to = {R.id.listImage, R.id.tvMainTipo, R.id.tvMainId};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custon_main_list, from, to);

        androidMainListView.setAdapter(simpleAdapter);
    }


    /**
     * Toca um som definido em arquivoSom
     * @param v
     * @param arquivoSom
     */
    public void tocaSom(View v, String arquivoSom) {
        //Snackbar.make(v,"Tocando o Som",Snackbar.LENGTH_LONG).setAction("Action",null).show();

        MediaPlayer mp = new MediaPlayer();
        if(mp.isPlaying())
        {
            mp.stop();
        }
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(arquivoSom); // "blue_macaw.wav"
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mosta o menu, mas não será utilizado nesta versão.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    // Não é usado nesta versão do app
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
