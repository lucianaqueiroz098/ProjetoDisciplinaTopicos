package br.com.projetoquestoesconcurso;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import br.com.projetoquestoesconcurso.List.QuestoesList;
import br.com.projetoquestoesconcurso.conexao.Conexao;
import br.com.projetoquestoesconcurso.model.Questao;

public class GeracaoProvas extends Activity {

	private Questao questaoCorrente;
	private QuestoesList currentGame = new QuestoesList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geracao_provas);
		Log.i("GERA��OPROVA", "Antes de pegar a quest�o - ");

		ArrayList<Questao> questions = (ArrayList<Questao>) getIntent()
				.getExtras().getSerializable("objeto");

		// Recuperando as alternativas de cada quest�o
		recuperaAlternativa(questions);
		currentGame.setQuestoes(questions);

		questaoCorrente = currentGame.getNextQuestion();

		Log.i("GERA��OPROVA",
				"CHEGOU NA CLASSE GERA��O DE PROVA - 2 tela, Passou do recupera a LISTA");
		// Button nextBtn = (Button) findViewById(R.id.btnProx);
		// nextBtn.setOnClickListener(this);
		setQuestions();

		Button btnProx = (Button) findViewById(R.id.btnProx);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geracao_provas, menu);
		return true;
	}

	private void setQuestions() {
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

	}

	public void geraQuestao(View view) {

	}

	public void validaQuestao(View view) {

		RadioGroup tipos = (RadioGroup) findViewById(R.id.radioAlternativas);
		int marcado = -1;
		switch (tipos.getCheckedRadioButtonId()) {
		case R.id.resp1:
			marcado = 0;
			break;
		case R.id.resp2:
			marcado = 1;
			break;
		case R.id.resp3:
			marcado = 2;
			break;
		case R.id.resp4:
			marcado = 3;

		case R.id.resp5:
			marcado = 4;
			break;

		}
		Button btnProx = (Button) findViewById(R.id.btnProx);
		Button btnCerto = (Button) findViewById(R.id.btnCerto);
		Button btnErrado = (Button) findViewById(R.id.btnErrado);
		if (marcado == currentGame.getPosicaoCerta()) {
			btnProx.setVisibility(View.VISIBLE);
			btnCerto.setVisibility(View.VISIBLE);
			btnErrado.setVisibility(View.INVISIBLE);
		} else {
			btnProx.setVisibility(View.INVISIBLE);
			btnErrado.setVisibility(View.VISIBLE);
			btnCerto.setVisibility(View.INVISIBLE);
		}
	}

}
