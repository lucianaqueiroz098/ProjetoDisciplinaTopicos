package br.com.projetoquestoesconcurso;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import br.com.projetoquestoesconcurso.model.Questao;

public class Questoes_detalhadas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questoes_detalhadas);

		Bundle bundle = getIntent().getExtras();
		Questao questao = (Questao) bundle.get("objeto");
		setQuestions(questao);
		;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questoes_detalhadas, menu);
		return true;
	}

	private void setQuestions(Questao questaoCorrente) {

		int alternativaCorreta = questaoCorrente.setaAlternativaCorreta();
		int alternativaMarcada = questaoCorrente.getAlternativaMarcadaUsuario();

		String question = questaoCorrente.getDsc_questao();
		TextView qText = (TextView) findViewById(R.id.txtQuestao);
		qText.setText(question);

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

		if (alternativaCorreta == 1) {
			opcao1.setTextColor(Color.GREEN);
		} else if (alternativaCorreta == 2) {
			opcao2.setTextColor(Color.GREEN);
		} else if (alternativaCorreta == 3) {
			opcao3.setTextColor(Color.GREEN);
		} else if (alternativaCorreta == 4) {
			opcao4.setTextColor(Color.GREEN);
		} else if (alternativaCorreta == 5) {
			opcao5.setTextColor(Color.GREEN);
		}

		if (alternativaMarcada == 1 && alternativaCorreta != 1) {
			opcao1.setTextColor(Color.RED);
		} else if (alternativaMarcada == 2 && alternativaCorreta != 2) {
			opcao2.setTextColor(Color.RED);
		} else if (alternativaMarcada == 3 && alternativaCorreta != 3) {
			opcao3.setTextColor(Color.RED);
		} else if (alternativaMarcada == 4 && alternativaCorreta != 4) {
			opcao4.setTextColor(Color.RED);
		} else if (alternativaMarcada == 5 && alternativaCorreta != 5) {
			opcao5.setTextColor(Color.RED);
		}
	}

	public void voltar(View view) {
		finish();
	}
}
