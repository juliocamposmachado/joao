package com.example.lavarapido;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lavarapido.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de Serviços
    private static final String TABLE_SERVICOS = "servicos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_PRECO = "preco";

    // Tabela de Fechamentos
    private static final String TABLE_FECHAMENTOS = "fechamentos";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_TOTAL = "total";

    // SQL Create Tables
    private static final String CREATE_TABLE_SERVICOS = "CREATE TABLE " + TABLE_SERVICOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOME + " TEXT NOT NULL,"
            + COLUMN_PRECO + " REAL NOT NULL" + ")";

    private static final String CREATE_TABLE_FECHAMENTOS = "CREATE TABLE " + TABLE_FECHAMENTOS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DATA + " TEXT NOT NULL UNIQUE,"
            + COLUMN_TOTAL + " REAL NOT NULL" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVICOS);
        db.execSQL(CREATE_TABLE_FECHAMENTOS);
        
        // Inserir serviços padrão
        insertDefaultServices(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FECHAMENTOS);
        onCreate(db);
    }

    private void insertDefaultServices(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_NOME, "Lavagem Simples");
        values.put(COLUMN_PRECO, 15.00);
        db.insert(TABLE_SERVICOS, null, values);
        
        values.clear();
        values.put(COLUMN_NOME, "Lavagem Completa");
        values.put(COLUMN_PRECO, 25.00);
        db.insert(TABLE_SERVICOS, null, values);
        
        values.clear();
        values.put(COLUMN_NOME, "Enceramento");
        values.put(COLUMN_PRECO, 40.00);
        db.insert(TABLE_SERVICOS, null, values);
        
        values.clear();
        values.put(COLUMN_NOME, "Higienização Interna");
        values.put(COLUMN_PRECO, 30.00);
        db.insert(TABLE_SERVICOS, null, values);
    }

    // CRUD Serviços
    public long addServico(Servico servico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, servico.getNome());
        values.put(COLUMN_PRECO, servico.getPreco());
        
        long id = db.insert(TABLE_SERVICOS, null, values);
        db.close();
        return id;
    }

    public List<Servico> getAllServicos() {
        List<Servico> servicos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SERVICOS + " ORDER BY " + COLUMN_NOME;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Servico servico = new Servico();
                servico.setId(cursor.getInt(0));
                servico.setNome(cursor.getString(1));
                servico.setPreco(cursor.getDouble(2));
                servicos.add(servico);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return servicos;
    }

    public int updateServico(Servico servico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, servico.getNome());
        values.put(COLUMN_PRECO, servico.getPreco());
        
        int result = db.update(TABLE_SERVICOS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(servico.getId())});
        db.close();
        return result;
    }

    public void deleteServico(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SERVICOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD Fechamentos
    public long addFechamento(Fechamento fechamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, fechamento.getData());
        values.put(COLUMN_TOTAL, fechamento.getTotal());
        
        long id = db.insertWithOnConflict(TABLE_FECHAMENTOS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return id;
    }

    public List<Fechamento> getAllFechamentos() {
        List<Fechamento> fechamentos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FECHAMENTOS + " ORDER BY " + COLUMN_DATA + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Fechamento fechamento = new Fechamento();
                fechamento.setId(cursor.getInt(0));
                fechamento.setData(cursor.getString(1));
                fechamento.setTotal(cursor.getDouble(2));
                fechamentos.add(fechamento);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return fechamentos;
    }

    public double getTotalByData(String data) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FECHAMENTOS,
                new String[]{COLUMN_TOTAL},
                COLUMN_DATA + "=?",
                new String[]{data}, null, null, null, null);
        
        double total = 0.0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(0);
            cursor.close();
        }
        db.close();
        return total;
    }
}
