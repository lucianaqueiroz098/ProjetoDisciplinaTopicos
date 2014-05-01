package br.com.projetoquestoesconcurso.List;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import br.com.projetoquestoesconcurso.model.Questao;

public class QuestoesList {

	public Questao getNextQuestion() {

		// get the question
		Questao next = questoes.get(this.getRound());
		// update the round number to the next round
		setaAlternativaCorreta();
		this.setRound(this.getRound() + 1);

		return next;
	}

	private void setaAlternativaCorreta() {
		for (int i = 0; i < questoes.get(getRound()).getAlternativas().size(); i++) {
			if (questoes.get(getRound()).getAlternativas().get(i).getCorreta()
					.equals("S")) {
				setPosicaoCerta(i);
			}
		}

	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getWrong() {
		return wrong;
	}

	public void setWrong(int wrong) {
		this.wrong = wrong;
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

	private int numRounds;
	private int difficulty;
	private int right;
	private int wrong;
	private int round;
	private int posicaoCerta;

	public void setPosicaoCerta(int posicaoCerta) {
		this.posicaoCerta = posicaoCerta;
	}

	public int getPosicaoCerta() {
		return posicaoCerta;
	}

	private ArrayList<Questao> questoes = new ArrayList<Questao>();

}
