package br.com.projetoquestoesconcurso.conexao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoquestoesconcurso.model.Alternativa;
import br.com.projetoquestoesconcurso.model.Questao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Conexao extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/br.com.projetoquestoesconcurso/databases/";
	private static String DB_NAME = "bd_concurso2";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public Conexao(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	// Creates a empty database
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
		Log.i("SUCESSO", "Done with createDataBase()");
	}

	// Check if the database already exist
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// If database does't exist.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		// Log.i("Caminho", " " + myPath);
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, 0);
		// SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public ArrayList<Questao> getQuestionSet() {
		ArrayList<Questao> questionSet = new ArrayList<Questao>();
		Log.i("Antes de rodar a query", " obtido." + DB_NAME);
		try {
			Cursor c = myDataBase.rawQuery(
					" select idt_questao, dsc_questao from questoes ", null);

			Log.i("DEPOIS de rodar a query", " DEPOIS.");
			// + " ORDER BY RANDOM() LIMIT " + numQ, null);
			while (c.moveToNext()) {
				Questao q = new Questao(c.getInt(0), c.getString(1));
				//q.setAlternativas(getAlternativaPorQuestao(c.getInt(0)));
				questionSet.add(q);
				Log.i("Resultado Query", " idt_questao: " + q.getIdt_questao()
						+ " dsc_questao: " + q.getDsc_questao());

			}
		} catch (SQLiteException s) {

			Log.i("ERRO select  Quest�es e alternativas", s.getMessage());
		}
		return questionSet;
	}

	public ArrayList<Alternativa> getAlternativaPorQuestao(int idt_questao) {
		String query = " select  idt_alternativa, dsc_alternativa, correta, idt_prova "
				+ " from alternativas a inner join questoes q on (a.idt_questao=q.idt_questao) where a.idt_questao =  "
				+ idt_questao;
		ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();

		try {
			Cursor c = myDataBase.rawQuery(query, null);
			Log.i(" query ALTERNATIVAS", " DEPOIS.");
			// + " ORDER BY RANDOM() LIMIT " + numQ, null);
			while (c.moveToNext()) {
				Alternativa a = new Alternativa(c.getInt(0),c.getString(1), c.getString(2));
//				a.setIdt_alternativa(c.getInt(0));
//				a.setDsc_alternativa(c.getString(1));
//				a.setCorreta(c.getString(2));

				alternativas.add(a);
			}
		} catch (SQLiteException s) {

			// myDataBase
			// .execSQL("create table cargos ( idt_cargo primay key, dsc_cargo varchar(60))");
			Log.i("EEERRO  Recupera��o Alternativas", s.getMessage());
		}
		return alternativas;
	}
}
