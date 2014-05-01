package br.com.projetoquestoesconcurso.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Alternativa implements Serializable {

	private int idt_alternativa;
	private String dsc_alternativa;
	private String correta;

	public int getIdt_alternativa() {
		return idt_alternativa;
	}

	public void setIdt_alternativa(int idt_alternativa) {
		this.idt_alternativa = idt_alternativa;
	}

	public String getDsc_alternativa() {
		return dsc_alternativa;
	}

	public void setDsc_alternativa(String dsc_alternativa) {
		this.dsc_alternativa = dsc_alternativa;
	}

	public String getCorreta() {
		return correta;
	}

	public void setCorreta(String correta) {
		this.correta = correta;
	}

	public Alternativa(int idt_alternativa, String dsc_alternativa,
			String correta) {
		this.idt_alternativa = idt_alternativa;
		this.dsc_alternativa = dsc_alternativa;
		this.correta = correta;
	}

}
