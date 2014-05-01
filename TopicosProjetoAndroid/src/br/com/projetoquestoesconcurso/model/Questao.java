package br.com.projetoquestoesconcurso.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.R.integer;

public class Questao implements Serializable {

	private int idt_questao;
	private String dsc_questao;
	private integer idt_cargo;

	private ArrayList<Alternativa> alternativas;

	public ArrayList<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(ArrayList<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

	public Questao(int idt_questao, String dsc_questao) {
		this.idt_questao = idt_questao;
		this.dsc_questao = dsc_questao;
	}

	public int getIdt_questao() {
		return idt_questao;
	}

	public void setIdt_questao(int idt_questao) {
		this.idt_questao = idt_questao;
	}

	public String getDsc_questao() {
		return dsc_questao;
	}

	public void setDsc_questao(String dsc_questao) {
		this.dsc_questao = dsc_questao;
	}

	public integer getIdt_cargo() {
		return idt_cargo;
	}

	public void setIdt_cargo(integer idt_cargo) {
		this.idt_cargo = idt_cargo;
	}

}
