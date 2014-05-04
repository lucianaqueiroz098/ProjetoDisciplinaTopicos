package br.com.projetoquestoesconcurso;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.com.projetoquestoesconcurso.List.QuestoesList;
import br.com.projetoquestoesconcurso.conexao.Conexao;
import br.com.projetoquestoesconcurso.model.Questao;

public class GeracaoProvas extends Activity {

	private Questao questaoCorrente;
	private QuestoesList currentGame = new QuestoesList();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geracao_provas, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geracao_provas);

		ArrayList<Questao> questions = (ArrayList<Questao>) getIntent()
				.getExtras().getSerializable("objeto");

		// Recuperando as alternativas de cada quest�o
		recuperaAlternativa(questions);
		currentGame.setQuestoes(questions);

		gerar(this);
	}

	private void gerar(Context cont) {
		questaoCorrente = currentGame.getNextQuestion();

		if (questaoCorrente == null) {
			// Toast.makeText(
			// getApplicationContext(),
			// "Voc� ja fez todas as quest�es dispon�veis em nosso banco de dados",
			// Toast.LENGTH_LONG).show();
			Log.i("N�O TEM PROX", "CHAMADA PARA OUTRA ACTIVITY");

			Intent intent = new Intent(cont, Resultado_prova.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("objeto", currentGame.getQuestoes());
			intent.putExtras(bundle);

			startActivity(intent);
			return;
		}
		Log.i("GERA��OPROVA",
				"CHEGOU NA CLASSE GERA��O DE PROVA - 2 tela, Passou do recupera a LISTA");
		setQuestions();

		Button btnProx = (Button) findViewById(R.id.btnprox);
		Button btnCerto = (Button) findViewById(R.id.btnCerto);
		Button btnErrado = (Button) findViewById(R.id.btnErrado);
		btnProx.setVisibility(View.INVISIBLE);
		btnErrado.setVisibility(View.INVISIBLE);
		btnCerto.setVisibility(View.INVISIBLE);
	}

	private void recuperaAlternativa(ArrayList<Questao> questions) {

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
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).setAlternativas(
					myDbHelper.getAlternativaPorQuestao(questions.get(i)
							.getIdt_questao()));
		}
		myDbHelper.close();

	}

	private void setQuestions() {
		Button btnValida = (Button) findViewById(R.id.btnvalidar);
		btnValida.setVisibility(View.VISIBLE);
		// set the question text from current question
		String question = questaoCorrente.getDsc_questao();
		TextView qText = (TextView) findViewById(R.id.txtQuestao);
		qText.setText(question);

		Log.i("Trouxe Alternativas?", "VEREMOMS:   "
				+ questaoCorrente.getAlternativas().get(0).getDsc_alternativa());

		// Setando as Alternativas

		TextView opcao1 = (TextView) findViewById(R.id.resp1);
		opcao1.setText(questaoCorrente.getAlternativas().get(0)
				.getDsc_alternativa());

		TextView opcao2 = (TextView) findViewById(R.id.resp2);
		opcao2.setText(questaoCorrente.getAlternativas().get(1)
				.getDsc_alternativa());

		TextView opcao3 = (TextView) findViewById(R.id.resp3);
		opcao3.setText(questaoCorrente.getAlternativas().get(2)
				.getDsc_alternativa());

		TextView opcao4 = (TextView) findViewById(R.id.resp4);
		opcao4.setText(questaoCorrente.getAlternativas().get(3)
				.getDsc_alternativa());
		TextView opcao5 = (TextView) findViewById(R.id.resp5);
		opcao5.setText(questaoCorrente.getAlternativas().get(4)
				.getDsc_alternativa());

		// desmarcarRadios
		desmarcaRadios();
	}

	private void desmarcaRadios() {

		RadioGroup r1 = (RadioGroup) findViewById(R.id.radioAlternativas);
		r1.clearCheck();

	}

	public void geraQuestao(View view) {
		gerar(view.getContext());
	}

	public void validaQuestao(View view) {

		RadioGroup tipos = (RadioGroup) findViewById(R.id.radioAlternativas);
		int marcado = -1;
		switch (tipos.getCheckedRadioButtonId()) {
		case R.id.resp1:
			marcado = 1;
			break;
		case R.id.resp2:
			marcado = 2;
			break;
		case R.id.resp3:
			marcado = 3;
			break;
		case R.id.resp4:
			marcado = 4;
			break;
		case R.id.resp5:
			marcado = 5;
			break;

		}
		if (marcado == -1) {
			Toast.makeText(getApplicationContext(), "� preciso marcar um item",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			// setando a alternativa que o usu�rio marcou, para gerar a tela de
			// Resultados.
			questaoCorrente.setAlternativaMarcadaUsuario(marcado);

			Button btnProx = (Button) findViewById(R.id.btnprox);
			Button btnCerto = (Button) findViewById(R.id.btnCerto);
			Button btnErrado = (Button) findViewById(R.id.btnErrado);
			if (marcado == currentGame.setaAlternativaCorreta()) {
				btnCerto.setVisibility(View.VISIBLE);
				btnErrado.setVisibility(View.INVISIBLE);
			} else {
				btnErrado.setVisibility(View.VISIBLE);
				btnCerto.setVisibility(View.INVISIBLE);
			}
			btnProx.setVisibility(View.VISIBLE);
			Button btnValida = (Button) findViewById(R.id.btnvalidar);
			btnValida.setVisibility(View.INVISIBLE);

		}
	}

}
