package br.com.projetoquestoesconcurso.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.projetoquestoesconcurso.R;
import br.com.projetoquestoesconcurso.model.Prova;

public class ListaProvasAdapter extends ArrayAdapter<Prova> {

	private Context context;
	private List<Prova> provas = null;


	public ListaProvasAdapter(Context context, List<Prova> provas) {
		super(context, 0, provas);
		this.provas = provas;
		this.context = context;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Prova prova = provas.get(position);

		if (view == null)
			view = LayoutInflater.from(context).inflate(
					R.layout.item_list_provas, null);

		TextView textNomeProva = (TextView) view
				.findViewById(R.id.txtNomeProva);
		textNomeProva.setText(prova.getDsc_prova());

		TextView textViewNomeprova = (TextView) view
				.findViewById(R.id.txtNomeCargo);
		textViewNomeprova.setText(prova.getDsc_cargo());

		TextView textViewIdade = (TextView) view.findViewById(R.id.txtAno);
		String textoIdade = String.valueOf(prova.getAno());
		textViewIdade.setText(textoIdade);

		return view;
	}


}
