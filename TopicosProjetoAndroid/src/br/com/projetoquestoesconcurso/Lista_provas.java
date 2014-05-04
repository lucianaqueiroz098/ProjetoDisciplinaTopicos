package br.com.projetoquestoesconcurso;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.projetoquestoesconcurso.adapter.ListaProvasAdapter;
import br.com.projetoquestoesconcurso.conexao.Conexao;
import br.com.projetoquestoesconcurso.model.Prova;
import br.com.projetoquestoesconcurso.model.Questao;

public class Lista_provas extends Activity implements OnItemClickListener {
	private ListaProvasAdapter provasAdapter;
	private ListView listView;
	ArrayList<Prova> listaDeProvas;

	// IMPLEMENTAR AINDA
	private EditText pesquisaTxt;
	private ArrayList<Prova> pesquisaEncontrado = new ArrayList<Prova>();

	private ListaProvasAdapter pesquisa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_provas);
		listView = (ListView) findViewById(R.id.minha_lista);
		listaDeProvas = getProvasFromDb();
		// iniciando insta�ncoa para funcionar o campo de pesquisa
		// pesquisa = new ListaProvasAdapter(this, listaDeProvas);

		provasAdapter = new ListaProvasAdapter(this, listaDeProvas);
		listView.setAdapter(provasAdapter);

		listView.setOnItemClickListener(this);

		// pesquisaTxt = (EditText) findViewById(R.id.lblPesquisa);
		// pesquisaTxt.addTextChangedListener(new TextWatcher() {
		// public void afterTextChanged(Editable arg0) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void beforeTextChanged(CharSequence arg0, int arg1,
		// int arg2, int arg3) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void onTextChanged(CharSequence arg0, int arg1, int arg2,
		// int arg3) {
		// // Pesquisar();
		//
		// // listView.setAdapter(pesquisa);
		// }
		// });
	}

	private ArrayList<Prova> getProvasFromDb() throws Error {

		Conexao myDbHelper = new Conexao(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		ArrayList<Prova> provas = myDbHelper.listaProvas();
		myDbHelper.close();
		return provas;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_provas, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posicao,
			long arg3) {
		iniciar2(arg1, posicao);
	}

	private void iniciar2(View view, int posicao) {

		Intent i;
		// Pegando Todas as QUest�es do SQLite e passando para a outra tela
		ArrayList<Questao> questions = getQuestaoFromDb(posicao);
		if (questions.size() == 0) {
			Toast.makeText(getBaseContext(),
					"N�o existe quest�es cadastradas para essa prova!",
					Toast.LENGTH_LONG).show();
			return;
		}
		Log.i("QUESTOES", "PASSOU PELO METODO DE PEGAR AS QUESTOES DO BANCO");
		i = new Intent(this, GeracaoProvas.class);

		Bundle bundle = new Bundle();
		bundle.putSerializable("objeto", questions);
		i.putExtras(bundle);

		startActivity(i);
	}

	private ArrayList<Questao> getQuestaoFromDb(int posicao) throws Error {

		Conexao myDbHelper = new Conexao(this);
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		ArrayList<Questao> questions = myDbHelper.getQuestionSet(listaDeProvas
				.get(posicao).getIdt_prova());
		myDbHelper.close();
		return questions;
	}

	public void Pesquisar() {
		int textlength = pesquisaTxt.getText().length();
		pesquisa.clear();

		for (int i = 0; i < listaDeProvas.size(); i++) {
			if (textlength <= listaDeProvas.get(i).getDsc_prova().length()) {
				String teste = pesquisaTxt.getText().toString();
				String prova = listaDeProvas.get(i).getDsc_prova();
				int posicaod = listaDeProvas.get(i).getDsc_prova()
						.indexOf(teste);
				String corte = (String) listaDeProvas.get(i).getDsc_prova()
						.subSequence(posicaod, posicaod + 1);

				if (pesquisaTxt
						.getText()
						.toString()
						.equalsIgnoreCase(
								(String) listaDeProvas
										.get(i)
										.getDsc_prova()
										.subSequence(prova.indexOf(teste),
												prova.indexOf(teste) + 1))) {
					pesquisa.add(listaDeProvas.get(i));
				}
			}
		}
		if (pesquisa.isEmpty()) {
			// pesquisa = provasAdapter;
		}
	}
}
