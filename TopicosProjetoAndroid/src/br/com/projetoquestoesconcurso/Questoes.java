package br.com.projetoquestoesconcurso;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.com.projetoquestoesconcurso.conexao.Conexao;
import br.com.projetoquestoesconcurso.model.Questao;

public class Questoes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questoes);
		SQLiteDatabase db = openOrCreateDatabase("bd_concurso2",
				Context.MODE_PRIVATE, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questoes, menu);
		return true;
	}

	public void iniciar(View view) {

		Intent i;
		// Pegando Todas as QUest�es do SQLite e passando para a outra tela
		ArrayList<Questao> questions = getQuestionSetFromDb();
		Log.i("QUESTOES", "PASSOU PELO METODO DE PEGAR AS QUESTOES DO BANCO");
		i = new Intent(this, GeracaoProvas.class);

		Bundle bundle = new Bundle();
		bundle.putSerializable("objeto", questions);
		i.putExtras(bundle);

		startActivity(i);
	}

	private ArrayList<Questao> getQuestionSetFromDb() throws Error {

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
		ArrayList<Questao> questions = myDbHelper.getQuestionSet();
		myDbHelper.close();
		return questions;
	}
}
