package br.com.projetoquestoesconcurso.List;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import br.com.projetoquestoesconcurso.model.Questao;

public class QuestoesList {

	private int round;
	private ArrayList<Questao> questoes = new ArrayList<Questao>();

	public Questao getNextQuestion() {
		if (questoes.size() <= getRound()) {
			return null;
		}

		Questao next = questoes.get(this.getRound());
		this.setRound(this.getRound() + 1);
		return next;
	}

	public int setaAlternativaCorreta() {
		return questoes.get(getRound() - 1).setaAlternativaCorreta();
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public ArrayList<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(ArrayList<Questao> questoes) {
		this.questoes = questoes;
	}

}
